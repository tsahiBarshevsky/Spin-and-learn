package tsahi.and.kostia.spinandlearn;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    Bitmap bitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String userName = getIntent().getStringExtra("Name");
        TextView massage = findViewById(R.id.massage);
        massage.setText(getString(R.string.hey) + " " + userName + ", " + getString(R.string.choose_level));
        Bundle extras = getIntent().getExtras();
        if (extras != null)
            bitmap = extras.getParcelable("user_pic");
        Button howToPlay = findViewById(R.id.howToPlayBtn);
        howToPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.CustomAlertDialog);
                View dialogView = getLayoutInflater().inflate(R.layout.how_to_play_dialog, null);
                builder.setView(dialogView).setCancelable(false);
                final AlertDialog dialog = builder.show();
                Button backBtn = dialogView.findViewById(R.id.back);
                backBtn.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        dialog.dismiss();
                    }});
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
        Animation slideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down_in);
        easyBtn.startAnimation(slideDown);
        mediumBtn.startAnimation(slideDown);
        hardBtn.startAnimation(slideDown);
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
        if (Locale.getDefault().toString().equals("iw_IL"))
        {
            howToPlay.startAnimation(slideRight);
            leaderboardBtn.startAnimation(slideLeft);
        }
        else
        {
            leaderboardBtn.startAnimation(slideRight);
            howToPlay.startAnimation(slideLeft);
        }
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
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}