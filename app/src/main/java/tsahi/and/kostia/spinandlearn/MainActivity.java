package tsahi.and.kostia.spinandlearn;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Bitmap bitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Animation fade = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        final ImageView logo = findViewById(R.id.logo);
        logo.startAnimation(fade);
        logo.animate().rotationY(360).setDuration(3000);
        Animation scaleUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_up);
        logo.startAnimation(scaleUp);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation scaleDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_down);
                logo.startAnimation(scaleDown);
            }
        }, 1500);
        TextView massage = findViewById(R.id.massage);
        massage.startAnimation(fade);
        final String userName = getIntent().getStringExtra("Name");
        massage.setText(getString(R.string.hey) + " " + userName + ", " + getString(R.string.choose_level));
        Bundle extras = getIntent().getExtras();
        if (extras != null)
            bitmap = extras.getParcelable("user_pic");
        Button howToPlay = findViewById(R.id.howToPlayBtn);
        howToPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WalkTroughActivity.class);
                intent.putExtra("from", MainActivity.class);
                intent.putExtra("Name", userName);
                startActivity(intent);
            }
        });

        Button difficulty[] = {
                findViewById(R.id.easyBtn),
                findViewById(R.id.mediumBtn),
                findViewById(R.id.hardBtn)
        };

        for(int i=0;i<3;i++){
            difficulty[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String diff = "";
                    if((v).getId() == R.id.easyBtn){
                        diff = "Easy";
                    }
                    else if((v).getId() == R.id.mediumBtn){
                        diff = "Medium";
                    }
                    else if((v).getId() == R.id.hardBtn){
                        diff = "Hard";
                    }

                    Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                    intent.putExtra("Level", diff);
                    intent.putExtra("Name", userName);
                    Bundle extras = new Bundle();
                    extras.putParcelable("user_pic", bitmap);
                    intent.putExtras(extras);
                    startActivity(intent);
                }
            });
        }

        Button leaderboardBtn = findViewById(R.id.leaderboardBtn);
        leaderboardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LeaderboardActivity.class);
                intent.putExtra("Name", userName);
                startActivity(intent);
            }
        });
        Animation slideRight = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation slideLeft  = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_left);
        for(int i=0;i<3;i++) {
            difficulty[i].startAnimation(slideLeft);
        }
        leaderboardBtn.startAnimation(slideLeft);
        howToPlay.startAnimation(slideRight);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation buttonAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_anim);
                for(int i=0;i<3;i++) {
                    difficulty[i].startAnimation(buttonAnim);
                }
                leaderboardBtn.startAnimation(buttonAnim);
                howToPlay.startAnimation(buttonAnim);
            }
        }, 2000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_change_player){
            Intent intent = new Intent(MainActivity.this, FirstActivity.class);
            startActivity(intent);
        }
        else if(id == R.id.action_sound_toggle){
            //to do
        }
        else if (id == R.id.action_how_to_play)
        {
            Intent intent = new Intent(MainActivity.this, WalkTroughActivity.class);
            intent.putExtra("from", MainActivity.class);
            intent.putExtra("Name", getIntent().getStringExtra("Name"));
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.CustomAlertDialog);
        View dialogView = getLayoutInflater().inflate(R.layout.exit_dialog, null);
        builder.setView(dialogView).setCancelable(false);
        final AlertDialog dialog = builder.show();
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_anim);
        Button okBtn = dialogView.findViewById(R.id.ok);
        okBtn.startAnimation(animation);
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
        cancelBtn.startAnimation(animation);
        cancelBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }});

    }
}