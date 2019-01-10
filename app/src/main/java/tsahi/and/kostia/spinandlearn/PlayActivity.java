package tsahi.and.kostia.spinandlearn;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
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
    CountDownTimer countDownTimer;
    long timeLeftInMillis = 10000;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        exercises = new ArrayList<>();
        exercises.add(new Exercise("1+1", "2", "Math"));
        exercises.add(new Exercise("2+2", "4", "Math"));
        exercises.add(new Exercise("10+5", "15", "Math"));

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
        //if (pos == 0 || pos == 6) {
            AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this, R.style.CustomAlertDialog);
            View dialogView = getLayoutInflater().inflate(R.layout.math_dialog, null);
            builder.setView(dialogView).setCancelable(false);
            final AlertDialog dialog = builder.show();
            Random random = new Random();
            int size = exercises.size();
            final int index = random.nextInt(size);
            startTimer(dialogView);


            TextView exercise = dialogView.findViewById(R.id.exerciseMath);
            exercise.setText(exercises.get(index).getQuestion());
            final RadioButton rb1 = dialogView.findViewById(R.id.rb1);
            final RadioButton rb2 = dialogView.findViewById(R.id.rb2);
            final RadioButton rb3 = dialogView.findViewById(R.id.rb3);
            final RadioButton rb4 = dialogView.findViewById(R.id.rb4);
            if (exercises.get(index).getType().equals("Math")) {
                rb1.setText("50");
                rb2.setText(exercises.get(index).getAnswer());
                rb3.setText("24");
                rb4.setText("0");
            }
            Button answerBtn = dialogView.findViewById(R.id.mathAnswerBtn);
            answerBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rb2.isChecked()) {
                        dialog.dismiss();
                        Toast.makeText(PlayActivity.this, "correct :)", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        dialog.dismiss();
                        Toast.makeText(PlayActivity.this, "incorrect :(", Toast.LENGTH_SHORT).show();
                    }
                }
            });
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
            }, 10000);
        //}
        //final EditText answer = dialogView.findViewById(R.id.answer);


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

            }, 10000); // 10 seconds delay
        }
        round.setText(getString(R.string.round) + " " + roundsCounter);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    private void startTimer(final View v) {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimer(v);
            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 10000;
            }
        }.start();
    }

    public void updateTimer(View v)
    {
        TextView timerText = v.findViewById(R.id.timeMath);
        int minutes = (int)timeLeftInMillis / 60000;
        int seconds = (int)timeLeftInMillis % 60000 / 1000;
        String timeLeft;
        timeLeft = "" + minutes;
        timeLeft += ":";
        if (seconds < 10)
            timeLeft += "0";
        timeLeft += seconds;
        timerText.setText(timeLeft);
    }
}
