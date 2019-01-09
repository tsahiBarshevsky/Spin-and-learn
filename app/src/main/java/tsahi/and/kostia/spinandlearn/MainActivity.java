package tsahi.and.kostia.spinandlearn;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

//    private ArrayList<Exercise> exercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*exercises = new ArrayList<>();
        exercises.add(new Exercise("1+1", "2", "Math"));
        exercises.add(new Exercise("2+2", "4", "Math"));*/

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

        Button playBtn = findViewById(R.id.playBtn);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                startActivity(intent);
            }
        });

        /*playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.CustomAlertDialog);
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
        });*/
    }
}