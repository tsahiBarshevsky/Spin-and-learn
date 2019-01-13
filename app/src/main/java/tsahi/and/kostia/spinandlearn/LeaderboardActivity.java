package tsahi.and.kostia.spinandlearn;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class LeaderboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        TextView name = findViewById(R.id.nameShow);
        TextView score = findViewById(R.id.scoreShow);

        SharedPreferences sp = getSharedPreferences("High scores", MODE_PRIVATE);
        name.setText(sp.getString("Name", null));
        int scoreInt = sp.getInt("Score", -20);
        score.setText(String.valueOf(scoreInt));
    }
}
