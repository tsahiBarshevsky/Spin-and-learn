package tsahi.and.kostia.spinandlearn;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

public class FirstActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1, PICK_IMAGE = 2, PERMISSION_REQUEST = 3;
    protected CircleImageView userImage;
    EditText name;
    Button enterBtn;
    Bitmap bitmap;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        enterBtn = findViewById(R.id.enter);
        name = findViewById(R.id.name);
        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = name.getText().toString();
                Intent intent = new Intent(FirstActivity.this, MainActivity.class);
                intent.putExtra("Name", string);
                startActivity(intent);
            }
        });
        userImage = findViewById(R.id.userImage);
        registerForContextMenu(userImage);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            if (data != null)
                bitmap = (Bitmap) data.getExtras().get("data");
            userImage.setImageBitmap(bitmap);
        }
        else
            if (requestCode == PICK_IMAGE && resultCode == RESULT_OK){
                if (data != null)
                    imageUri = data.getData();
                userImage.setImageURI(imageUri);
            }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.userImage)
            getMenuInflater().inflate(R.menu.first_image_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.capture) {
            if(Build.VERSION.SDK_INT>=23) {
                int hasCallPermission = checkSelfPermission(Manifest.permission.CAMERA);
                if (hasCallPermission == PackageManager.PERMISSION_GRANTED)
                    takeImage();
                else //PERMISSION_DENIED
                    requestPermissions(new String[] {Manifest.permission.CAMERA}, CAMERA_REQUEST);
            }
            else
                takeImage();
            return true;
        }
        else
            if (item.getItemId() == R.id.gallery) {
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
            }
        return super.onContextItemSelected(item);
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
}
