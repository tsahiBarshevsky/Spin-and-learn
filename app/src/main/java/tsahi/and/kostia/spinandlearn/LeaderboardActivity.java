package tsahi.and.kostia.spinandlearn;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.Date;

public class LeaderboardActivity extends AppCompatActivity {

    ArrayList<UserInfo> userInfoList;
    ScoreAdapter adapter;

    GlobalVar global;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        SharedPreferences sharedPref = this.getSharedPreferences("sound", this.MODE_PRIVATE);

        Animation buttonAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_anim);
        Button sound = findViewById(R.id.soundLed);
        Button music = findViewById(R.id.musicLed);
        if(sharedPref.getBoolean("mute", false)){
            sound.setBackground(getResources().getDrawable(R.drawable.ic_soundoff));
        }
        else{
            sound.setBackground(getResources().getDrawable(R.drawable.ic_soundon));
        }
        if(sharedPref.getBoolean("muteMusic", false)){
            music.setBackground(getResources().getDrawable(R.drawable.ic_musicoff));
        }
        else{
            music.setBackground(getResources().getDrawable(R.drawable.ic_musicon));
        }
        sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(sharedPref.getBoolean("mute", false));
                if(sharedPref.getBoolean("mute", false)){
                    sound.setBackground(getResources().getDrawable(R.drawable.ic_soundoff));
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putBoolean("mute", false);
                    editor.commit();
                    global.setMute(false);
                    global.startMusic(LeaderboardActivity.this);
                }
                else{
                    sound.setBackground(getResources().getDrawable(R.drawable.ic_soundon));
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
                    music.setBackground(getResources().getDrawable(R.drawable.ic_musicoff));
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putBoolean("musicMute", false);
                    editor.commit();
                    global.setMusicMute(false);
                    global.startMusic(LeaderboardActivity.this);
                }
                else{
                    music.setBackground(getResources().getDrawable(R.drawable.ic_musicon));
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putBoolean("musicMute", true);
                    editor.commit();
                    global.setMusicMute(true);
                    global.pauseMusic();
                }
            }
        });
        sound.startAnimation(buttonAnim);
        music.startAnimation(buttonAnim);
        if (Locale.getDefault().toString().equals("iw_IL")) {
            TextView champions = findViewById(R.id.champions);
            Typeface typeface = ResourcesCompat.getFont(this, R.font.dana);
            champions.setTypeface(typeface);
        }
        global = ((GlobalVar) this.getApplication());

        global.setAppPaused(false);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userInfoList = new ArrayList<>();

        SharedPreferences sharedPrefGD = this.getSharedPreferences("gameData", this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefGD.edit();

        Integer size = sharedPrefGD.getInt("size", 0);

        for(Integer i = 1; i<=size;i++){
            String tmp = sharedPrefGD.getString(i.toString(), "");
            if(!tmp.equals("")){
                UserInfo user = new UserInfo(tmp);
                if(user.getPhoto() == null){
                    user.setPhoto(BitmapFactory.decodeResource(getResources(), R.drawable.camera));
                }
                userInfoList.add(user);
            }
        }
        Collections.sort(userInfoList, new Comparator<UserInfo>() {
            @Override
            public int compare(UserInfo o1, UserInfo o2) {

                int score1 = o1.getScore();
                int score2 = o2.getScore();

                if(score1!=score2){
                    return score2-score1;
                }

                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");

                Date date1 = null;
                Date date2 = null;
                try {
                    date1 = dateFormat.parse(o1.getDate());
                    date2 = dateFormat.parse(o2.getDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if(date1.after(date2) || date2.after(date1)){
                    return (int)(date1.getTime() - date2.getTime());
                }

                DateFormat timeFormat = new SimpleDateFormat("HH:mm");

                Date time1 = null;
                Date time2 = null;
                try {
                    time1 = timeFormat.parse(o1.getTime());
                    time2 = timeFormat.parse(o2.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                return (int)(time1.getTime() - time2.getTime());
            }
        });

        if(size > 0) {
            editor.putInt("HighScore", userInfoList.get(0).getScore());
            editor.commit();
        }

        adapter = new ScoreAdapter(userInfoList);
        recyclerView.setAdapter(adapter);
        adapter.notifyItemInserted(userInfoList.size());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(LeaderboardActivity.this, MainActivity.class);
        intent.putExtra("Name", getIntent().getStringExtra("Name"));
        startActivity(intent);
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
}
