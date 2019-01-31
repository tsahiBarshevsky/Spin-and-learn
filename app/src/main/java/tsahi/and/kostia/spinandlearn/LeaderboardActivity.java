package tsahi.and.kostia.spinandlearn;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class LeaderboardActivity extends AppCompatActivity {

    ArrayList<UserInfo> userInfoList;
    ScoreAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userInfoList = new ArrayList<>();

        SharedPreferences sharedPref = this.getSharedPreferences("gameData", this.MODE_PRIVATE);

        Integer size = sharedPref.getInt("size", 0);

        for(Integer i = 1; i<=size;i++){
            String tmp = sharedPref.getString(i.toString(), "");
            if(!tmp.equals("")){
                UserInfo user = new UserInfo(tmp);
                userInfoList.add(user);
            }
        }
        Collections.sort(userInfoList, new Comparator<UserInfo>() {
            @Override
            public int compare(UserInfo o1, UserInfo o2) {
                return o2.getScore() - o1.getScore();
            }
        });
        adapter = new ScoreAdapter(userInfoList);
        recyclerView.setAdapter(adapter);
        adapter.notifyItemInserted(userInfoList.size());
    }

}
