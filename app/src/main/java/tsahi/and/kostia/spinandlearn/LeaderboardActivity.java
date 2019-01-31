package tsahi.and.kostia.spinandlearn;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class LeaderboardActivity extends AppCompatActivity {

    List<UserInfo> userInfoList;
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

        adapter = new ScoreAdapter(userInfoList);
        recyclerView.setAdapter(adapter);
        adapter.notifyItemInserted(userInfoList.size());
    }

}
