package tsahi.and.kostia.spinandlearn;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class LeaderboardActivity extends AppCompatActivity {

    ArrayList<UserInfo> userInfoList;
    ScoreAdapter adapter;

    GlobalVar global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        Animation buttonAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_anim);
        Button sound = findViewById(R.id.soundLed);
        Button music = findViewById(R.id.musicLed);
        sound.startAnimation(buttonAnim);
        music.startAnimation(buttonAnim);

        global = ((GlobalVar) this.getApplication());

        global.setAppPaused(false);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userInfoList = new ArrayList<>();

        SharedPreferences sharedPref = this.getSharedPreferences("gameData", this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        Integer size = sharedPref.getInt("size", 0);

        for(Integer i = 1; i<=size;i++){
            String tmp = sharedPref.getString(i.toString(), "");
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
                return o2.getScore() - o1.getScore();
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
