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
        SharedPreferences sp = getSharedPreferences("score detail", MODE_PRIVATE);
        String imageS = sp.getString("Pic", "");
        Bitmap imageB = null;
        if(!imageS.equals(""))
            imageB = decodeToBase64(imageS);
        CircleImageView circleImageView = new CircleImageView(this);
        circleImageView.setImageBitmap(imageB);
        userInfoList.add(new UserInfo(circleImageView, sp.getString("Name", "def"), sp.getInt("Score", 0)));
        adapter = new ScoreAdapter(userInfoList);
        recyclerView.setAdapter(adapter);
        adapter.notifyItemInserted(userInfoList.size());
    }

    public static Bitmap decodeToBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}
