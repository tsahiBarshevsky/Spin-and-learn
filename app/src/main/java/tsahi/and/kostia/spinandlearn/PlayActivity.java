package tsahi.and.kostia.spinandlearn;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class PlayActivity extends AppCompatActivity implements Animation.AnimationListener{

    int roundsCounter = 1;
    ArrayList<Exercise> exercises;
    boolean blnButtonRotation = true;
    int intNumber = 10;
    long lngDegrees = 0;
    ImageView imageRoulette;
    TextView round;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        exercises = new ArrayList<>();
        exercises.add(new Exercise("1+1", "2", "Math"));
        exercises.add(new Exercise("2+2", "4", "Math"));

        imageRoulette = findViewById(R.id.roulette);
        round = findViewById(R.id.round);
        round.setText(getString(R.string.round) + " " + roundsCounter);
        Button spinBtn = findViewById(R.id.spinBtn);
        spinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (blnButtonRotation)
                {
                    roundsCounter++;
                    int ran = new Random().nextInt(360) + 3600;
                    RotateAnimation rotateAnimation = new RotateAnimation((float)lngDegrees, (float)
                            (lngDegrees + ((long)ran)),1,0.5f,1,0.5f);

                    lngDegrees = (lngDegrees + ((long)ran)) % 360;
                    rotateAnimation.setDuration((long)ran);
                    rotateAnimation.setFillAfter(true);
                    rotateAnimation.setInterpolator(new DecelerateInterpolator());
                    rotateAnimation.setAnimationListener(PlayActivity.this);
                    imageRoulette.setAnimation(rotateAnimation);
                    imageRoulette.startAnimation(rotateAnimation);
                }
                /*AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this, R.style.CustomAlertDialog);
                View dialogView = getLayoutInflater().inflate(R.layout.game_dialog, null);
                builder.setView(dialogView).show();
                TextView textView = dialogView.findViewById(R.id.exercise);
                textView.setText(exercises.get(1).getQuestion());
                if (exercises.get(1).getType().equals("Math"))
                {
                    EditText answer = dialogView.findViewById(R.id.answer);
                    answer.setInputType(InputType.TYPE_CLASS_NUMBER);
                }*/
            }
        });
    }

    @Override
    public void onAnimationStart(Animation animation) {
        blnButtonRotation = false;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onAnimationEnd(Animation animation) {
        String types[] = {"Math", "Orange", "Gold", "Orange", "Green", "Red", "Math", "Orange", "Green", "Red"};
        String string = String.valueOf((int)(((double)this.intNumber)
                - Math.floor(((double)this.lngDegrees) / (360.0d / ((double)this.intNumber)))));
        int pos = Integer.parseInt(string);
        pos--;
        blnButtonRotation = true;
        //if (pos == 0 || pos == 6)
        //{
        AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this, R.style.CustomAlertDialog);
        View dialogView = getLayoutInflater().inflate(R.layout.game_dialog, null);
        builder.setView(dialogView).setCancelable(false);
        final AlertDialog dialog = builder.show();
        TextView textView = dialogView.findViewById(R.id.exercise);
        textView.setText(exercises.get(1).getQuestion());
        if (exercises.get(1).getType().equals("Math"))
        {
            EditText answer = dialogView.findViewById(R.id.answer);
            answer.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
        final Timer timer = new Timer(); //timer round
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                dialog.dismiss();
                timer.cancel();
                new Thread()
                {
                    @Override
                    public void run() {
                        PlayActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(PlayActivity.this, "Sorry, you run out of time", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }.start();
            }
        }, 5000);

        /*final AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this, R.style.CustomAlertDialog);
            View dialogView = getLayoutInflater().inflate(R.layout.game_dialog, null);
                builder.setView(dialogView).show();
                final AlertDialog closedialog= builder.create();
                TextView textView = dialogView.findViewById(R.id.exercise);
                textView.setText(exercises.get(1).getQuestion());
                if (exercises.get(1).getType().equals("Math"))
                {
                    EditText answer = dialogView.findViewById(R.id.answer);
                    answer.setInputType(InputType.TYPE_CLASS_NUMBER);
                }
        //}*/

        if (roundsCounter > 2)
        {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    finish();
                }

            }, 5000); // 5000ms delay
        }
        round.setText(getString(R.string.round) + " " + roundsCounter);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
