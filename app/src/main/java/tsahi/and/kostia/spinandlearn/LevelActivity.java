package tsahi.and.kostia.spinandlearn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LevelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        final String userName = getIntent().getStringExtra("Name");
        TextView massage = findViewById(R.id.massage);
        massage.setText(getString(R.string.hey) + " " + userName + ", " + getString(R.string.choose_level));
        Button easyBtn = findViewById(R.id.easyBtn);
        easyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevelActivity.this, PlayActivity.class);
                intent.putExtra("Level", "Easy");
                startActivity(intent);
            }
        });
        Button mediumBtn = findViewById(R.id.mediumBtn);
        mediumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevelActivity.this, PlayActivity.class);
                intent.putExtra("Level", "Medium");
                startActivity(intent);
            }
        });
        Button hardBtn = findViewById(R.id.hardBtn);
        hardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevelActivity.this, PlayActivity.class);
                intent.putExtra("Level", "Hard");
                startActivity(intent);
            }
        });
    }
}
