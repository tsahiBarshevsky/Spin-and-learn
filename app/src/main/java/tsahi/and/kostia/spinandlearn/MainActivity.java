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
                startActivity(intent);
            }
        });
        Button easyBtn = findViewById(R.id.easyBtn);
        easyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                intent.putExtra("Level", "Easy");
                intent.putExtra("Name", userName);
                Bundle extras = new Bundle();
                extras.putParcelable("user_pic", bitmap);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        Button mediumBtn = findViewById(R.id.mediumBtn);
        mediumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                intent.putExtra("Level", "Medium");
                intent.putExtra("Name", userName);
                Bundle extras = new Bundle();
                extras.putParcelable("user_pic", bitmap);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        Button hardBtn = findViewById(R.id.hardBtn);
        hardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                intent.putExtra("Level", "Hard");
                intent.putExtra("Name", userName);
                Bundle extras = new Bundle();
                extras.putParcelable("user_pic", bitmap);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        Button leaderboardBtn = findViewById(R.id.leaderboardBtn);
        leaderboardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LeaderboardActivity.class);
                startActivity(intent);
            }
        });
        Animation slideRight = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation slideLeft  = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_left);
        easyBtn.startAnimation(slideRight);
        mediumBtn.startAnimation(slideLeft);
        hardBtn.startAnimation(slideRight);
        leaderboardBtn.startAnimation(slideLeft);
        howToPlay.startAnimation(slideRight);
        /*Thread thread = new Thread()
        {
            public void run()
            {

            }
        };
        thread.start();*/
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation buttonAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_anim);
                easyBtn.startAnimation(buttonAnim);
                mediumBtn.startAnimation(buttonAnim);
                hardBtn.startAnimation(buttonAnim);
                leaderboardBtn.startAnimation(buttonAnim);
                howToPlay.startAnimation(buttonAnim);
            }
        }, 2000);
        /*Button playBtn = findViewById(R.id.playBtn);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LevelActivity.class);
                intent.putExtra("Name", userName);
                startActivity(intent);
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_how_to_play)
        {
            Intent intent = new Intent(MainActivity.this, WalkTroughActivity.class);
            intent.putExtra("from", MainActivity.class);
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
        Button okBtn = dialogView.findViewById(R.id.ok);
        okBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
                android.os.Process.killProcess(android.os.Process.myPid());
            }});
        Button cancelBtn = dialogView.findViewById(R.id.cancle);
        cancelBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }});

    }
}