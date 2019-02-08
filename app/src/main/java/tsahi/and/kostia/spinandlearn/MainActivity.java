package tsahi.and.kostia.spinandlearn;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Bitmap bitmap = null;

    GlobalVar global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        global = ((GlobalVar) this.getApplication());

        global.setAppPaused(false);
        SharedPreferences sharedPref = this.getSharedPreferences("sound", this.MODE_PRIVATE);
        global.setMute(sharedPref.getBoolean("mute", false));

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
        Button sound = findViewById(R.id.sound);
        Button music = findViewById(R.id.music);
        if(sharedPref.getBoolean("mute", false)){
            sound.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_musicoff), null, null, null);
        }
        else{
            sound.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_musicon), null, null, null);
        }
        if(sharedPref.getBoolean("musicMute", false)){
            music.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_soundoff), null, null, null);
        }
        else{
            music.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_soundon), null, null, null);
        }
        sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(sharedPref.getBoolean("mute", false));
                if(sharedPref.getBoolean("mute", false)){
                    sound.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_musicon), null, null, null);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putBoolean("mute", false);
                    editor.commit();
                    global.setMute(false);
                    global.startMusic(MainActivity.this);
                }
                else{
                    sound.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_musicoff), null, null, null);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putBoolean("mute", true);
                    editor.commit();
                    global.setMute(true);
                    global.pauseMusic();
                }
            }
        });
        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sharedPref.getBoolean("musicMute", false)){
                    music.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_soundon), null, null, null);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putBoolean("musicMute", false);
                    editor.commit();
                    global.setMusicMute(false);
                    global.startMusic(MainActivity.this);
                }
                else{
                    music.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_soundoff), null, null, null);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putBoolean("musicMute", true);
                    editor.commit();
                    global.setMusicMute(true);
                    global.pauseMusic();
                }
            }
        });


        Animation slideRight = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation slideLeft  = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_left);
        LinearLayout linearLayout = findViewById(R.id.soundsButtons);
        if (Locale.getDefault().toString().equals("iw_IL"))
            linearLayout.startAnimation(slideLeft);
        else
            linearLayout.startAnimation(slideRight);
        difficulty[0].startAnimation(slideRight);
        difficulty[1].startAnimation(slideLeft);
        difficulty[2].startAnimation(slideRight);
        leaderboardBtn.startAnimation(slideLeft);
        howToPlay.startAnimation(slideRight);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation buttonAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_anim);
                for(int i=0;i<3;i++) {
                    difficulty[i].startAnimation(buttonAnim);
                    difficulty[i].setEnabled(true);
                }
                leaderboardBtn.startAnimation(buttonAnim);
                leaderboardBtn.setEnabled(true);
                howToPlay.startAnimation(buttonAnim);
                howToPlay.setEnabled(true);
                sound.startAnimation(buttonAnim);
                sound.setEnabled(true);
                music.startAnimation(buttonAnim);
                music.setEnabled(true);
            }
        }, 1550);

        Typeface typeface = ResourcesCompat.getFont(MainActivity.this, R.font.stephia);;
        if (Locale.getDefault().toString().equals("iw_IL"))
        {
            typeface = ResourcesCompat.getFont(MainActivity.this, R.font.abraham);
        }
        else if(Locale.getDefault().toString().equals("ru_RU")){
            typeface = ResourcesCompat.getFont(MainActivity.this, R.font.wagnasty);
        }
            massage.setTypeface(typeface);
            difficulty[0].setTypeface(typeface);
            difficulty[1].setTypeface(typeface);
            difficulty[2].setTypeface(typeface);
            leaderboardBtn.setTypeface(typeface);
            howToPlay.setTypeface(typeface);

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
                finishAffinity();

            }});
        Button cancelBtn = dialogView.findViewById(R.id.cancle);
        cancelBtn.startAnimation(animation);
        cancelBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }});
        Typeface typeface = ResourcesCompat.getFont(MainActivity.this, R.font.stephia);;
        if (Locale.getDefault().toString().equals("iw_IL"))
        {
            typeface = ResourcesCompat.getFont(MainActivity.this, R.font.abraham);
        }
        else if(Locale.getDefault().toString().equals("ru_RU")){
            typeface = ResourcesCompat.getFont(MainActivity.this, R.font.wagnasty);
        }
            TextView textView = dialogView.findViewById(R.id.space);
            textView.setTypeface(typeface);
            okBtn.setTypeface(typeface);
            cancelBtn.setTypeface(typeface);

    }

    @Override
    protected void onPause() {
        super.onPause();
        global.setAppPaused(true);
        global.pauseMusic();
    }

    @Override
    protected void onResume() {
        super.onResume();
        global.setAppPaused(false);
        global.startMusic(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.exit(0);
    }
}