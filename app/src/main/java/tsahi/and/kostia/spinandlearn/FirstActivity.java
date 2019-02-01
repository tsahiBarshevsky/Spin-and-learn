package tsahi.and.kostia.spinandlearn;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import java.io.IOException;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class FirstActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1, PICK_IMAGE = 2, PERMISSION_REQUEST = 3;
    protected CircleImageView userImage;
    LinearLayout mainLayout;
    EditText name;
    Button enterBtn, exitBtn;
    Bitmap bitmap;
    Uri imageUri;
    String userName;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedPref = this.getSharedPreferences("firstRun", this.MODE_PRIVATE);
        int firstRun = sharedPref.getInt("firstRun", 0);
        if (firstRun == 0){
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("firstRun", 1);
            editor.commit();
            Intent intent = new Intent(FirstActivity.this, WalkTroughActivity.class);
            intent.putExtra("from", FirstActivity.class);
            intent.putExtra("firstRun", true);
            startActivity(intent);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        sharedPref = this.getSharedPreferences("lastUser", this.MODE_PRIVATE);
        bitmap = new UserInfo().StringToBitMap(sharedPref.getString("Photo", "-1"));
        userName = sharedPref.getString("Name", "");

        enterBtn = findViewById(R.id.enter);
        name = findViewById(R.id.name);
        name.setText(userName);
        exitBtn = findViewById(R.id.exit);
        userImage = findViewById(R.id.userImage);
        if(bitmap != null){
            userImage.setImageBitmap(bitmap);
        }
        final Animation slideRight = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        final Animation slideRightOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        final Animation slideLeft = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_left);
        final Animation slideLeftOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_left);
        final Animation fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        final Animation fadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
        ImageView logo = findViewById(R.id.logo);
        logo.startAnimation(slideRight);
        logo.animate().rotationY(360).setDuration(2500);
        final ImageView man = findViewById(R.id.man);
        if (Locale.getDefault().toString().equals("iw_IL")) //hebrew
            man.startAnimation(slideRight);
        else
            man.startAnimation(slideLeft);
        Handler speechHandler = new Handler();
        speechHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ImageView speech = findViewById(R.id.speech);
                speech.setVisibility(View.VISIBLE);
                speech.startAnimation(fadeIn);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        speech.startAnimation(fadeOut);
                        Animation slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up_in);
                        if (Locale.getDefault().toString().equals("iw_IL")) //hebrew
                            man.startAnimation(slideRightOut);
                        else
                            man.startAnimation(slideLeftOut);
                        mainLayout = findViewById(R.id.mainLayout);
                        mainLayout.setVisibility(View.VISIBLE);
                        mainLayout.startAnimation(fadeIn);
                        enterBtn.startAnimation(slideUp);
                        exitBtn.startAnimation(slideUp);
                        name.startAnimation(slideUp);
                        userImage.startAnimation(slideUp);
                        man.setVisibility(View.INVISIBLE);
                        speech.setVisibility(View.INVISIBLE);
                        Handler animation = new Handler();
                        animation.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Animation buttonAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_anim);
                                enterBtn.startAnimation(buttonAnim);
                                exitBtn.startAnimation(buttonAnim);
                            }
                        }, 1700);
                    }
                }, 3000);
            }
        }, 1000);
        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPref.edit();
                if(bitmap != null){
                    editor.putString("Photo", new UserInfo().BitMapToString(bitmap));
                }
                userName = name.getText().toString();
                editor.putString("Name", userName);
                editor.commit();

                Intent intent = new Intent(FirstActivity.this, MainActivity.class);
                intent.putExtra("Name", userName);
                Bundle extras = new Bundle();
                extras.putParcelable("user_pic", bitmap);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FirstActivity.this, R.style.CustomAlertDialog);
                View dialogView = getLayoutInflater().inflate(R.layout.exit_dialog, null);
                builder.setView(dialogView).setCancelable(false);
                final AlertDialog dialog = builder.show();
                Button okBtn = dialogView.findViewById(R.id.ok);
                okBtn.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        startActivity(intent);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        finishAffinity();
                        System.exit(0);
                    }});
                Button cancelBtn = dialogView.findViewById(R.id.cancle);
                cancelBtn.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        dialog.dismiss();
                    }});
            }
        });
        //registerForContextMenu(userImage);
        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(v);
            }
        });
    }

    public void showMenu(View v)
    {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.capture:
                        if(Build.VERSION.SDK_INT>=23) {
                            int hasCameraPermission = checkSelfPermission(Manifest.permission.CAMERA);
                            if (hasCameraPermission == PackageManager.PERMISSION_GRANTED)
                                takeImage();
                            else //PERMISSION_DENIED
                                requestPermissions(new String[] {Manifest.permission.CAMERA}, CAMERA_REQUEST);
                        }
                        return true;
                    case R.id.gallery:
                        if(Build.VERSION.SDK_INT>=23) {
                            int hasCallPermission = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
                            if (hasCallPermission == PackageManager.PERMISSION_GRANTED)
                                selectImage();
                            else //PERMISSION_DENIED
                                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
                        }
                        else
                            selectImage();
                        return true;
                    case R.id.reset:
                        userImage.setImageBitmap(null);
                        userImage.setImageDrawable(getResources().getDrawable(R.drawable.camera));
                        bitmap = null;
                        return true;
                }
                return false;
            }
        });
        popup.inflate(R.menu.first_image_menu);
        popup.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            if (data != null) {
                bitmap = (Bitmap) data.getExtras().get("data");
                bitmap = getResizedBitmap(bitmap, 100);
            }
            userImage.setImageBitmap(bitmap);
        }
        else
            if (requestCode == PICK_IMAGE && resultCode == RESULT_OK){
                if (data != null)
                    imageUri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bitmap = getResizedBitmap(bitmap, 100);
                userImage.setImageBitmap(bitmap);
            }
    }

    private void selectImage()
    {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE);
    }

    private void takeImage()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST);
    }

    Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(FirstActivity.this, R.style.CustomAlertDialog);
        View dialogView = getLayoutInflater().inflate(R.layout.exit_dialog, null);
        builder.setView(dialogView).setCancelable(false);
        final AlertDialog dialog = builder.show();
        Button okBtn = dialogView.findViewById(R.id.ok);
        okBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
                android.os.Process.killProcess(android.os.Process.myPid());
                finishAffinity();
                System.exit(0);
            }});
        Button cancelBtn = dialogView.findViewById(R.id.cancle);
        cancelBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }});
    }
}
