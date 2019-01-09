package tsahi.and.kostia.spinandlearn;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class PlayActivity extends AppCompatActivity {

    private int turnCounter = 0;
    private ArrayList<Exercise> exercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        exercises = new ArrayList<>();
        exercises.add(new Exercise("1+1", "2", "Math"));
        exercises.add(new Exercise("2+2", "4", "Math"));
        Button spinBtn = findViewById(R.id.spinBtn);
        spinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this, R.style.CustomAlertDialog);
                View dialogView = getLayoutInflater().inflate(R.layout.game_dialog, null);
                builder.setView(dialogView).show();
                TextView textView = dialogView.findViewById(R.id.exercise);
                textView.setText(exercises.get(1).getQuestion().toString());
                if (exercises.get(1).getType().toString().equals("Math"))
                {
                    EditText answer = dialogView.findViewById(R.id.answer);
                    answer.setInputType(InputType.TYPE_CLASS_NUMBER);
                }
            }
        });
    }
}
