package tsahi.and.kostia.spinandlearn;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
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
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PlayActivity extends AppCompatActivity implements Animation.AnimationListener{

    int roundsCounter = 1, scoreCounter = 0;
    int scoreToAdd;
    ArrayList<Exercise> exercises;
    boolean blnButtonRotation = true;
    int intNumber = 10;
    long lngDegrees = 0;
    ImageView imageRoulette;
    TextView round, score, levelTV;
    CountDownTimer countDownTimer;
    long timeLeftInMillis, temp;
    String level;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        levelTV = findViewById(R.id.level_TV);
        level = getIntent().getStringExtra("Level");
        switch (level)
        {
            case "Easy":
                scoreToAdd = 10;          //add 10 points for right answer
                timeLeftInMillis = 60000; //1 minute
                temp = timeLeftInMillis;
                levelTV.setText(getString(R.string.level) + getString(R.string.easy));
                break;
            case "Medium":
                scoreToAdd = 20;          //add 10 points for right answer
                timeLeftInMillis = 30000; //30 seconds
                temp = timeLeftInMillis;
                levelTV.setText(getString(R.string.level) + getString(R.string.medium));
                break;
            case "Hard":
                scoreToAdd = 50;          //add 50 points for right answer
                timeLeftInMillis = 10000; //10 seconds
                temp = timeLeftInMillis;
                levelTV.setText(getString(R.string.level) + getString(R.string.hard));
                break;
        }
        initArray();
        imageRoulette = findViewById(R.id.roulette);
        round = findViewById(R.id.round);
        round.setText(getString(R.string.round) + " " + "1");
        score = findViewById(R.id.score);
        score.setText(getString(R.string.score) + " " + "0");
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
            }
        });
    }

    @Override
    public void onAnimationStart(Animation animation) {
        blnButtonRotation = false;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onAnimationEnd(Animation animation) {
        //String types[] = {"Math", "Orange", "Gold", "Orange", "Green", "Red", "Math", "Orange", "Green", "Red"};
        String string = String.valueOf((int)(((double)this.intNumber)
                - Math.floor(((double)this.lngDegrees) / (360.0d / ((double)this.intNumber)))));
        int pos = Integer.parseInt(string);
        pos--;
        blnButtonRotation = true;
        /*switch (pos)
        {
            case 0:
                mathQuestion();
                break;
            case 6:
                mathQuestion();
                break;
        }*/
        mathQuestion();
        if (roundsCounter > 2)
        {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    finish();
                }

            }, timeLeftInMillis);
        }
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
                timeLeftInMillis = temp;
                countDownTimer.cancel();
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

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void mathQuestion()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this, R.style.CustomAlertDialog);
        View dialogView = getLayoutInflater().inflate(R.layout.math_dialog, null);
        builder.setView(dialogView).setCancelable(false);
        final AlertDialog dialog = builder.show();
        Random random = new Random();
        int size = exercises.size();
        final int index = random.nextInt(size); //draw lots question's number
        startTimer(dialogView);
        TextView exercise = dialogView.findViewById(R.id.exerciseMath);
        exercise.setText(exercises.get(index).getQuestion());
        final RadioButton rb1 = dialogView.findViewById(R.id.rb1);
        final RadioButton rb2 = dialogView.findViewById(R.id.rb2);
        final RadioButton rb3 = dialogView.findViewById(R.id.rb3);
        final RadioButton rb4 = dialogView.findViewById(R.id.rb4);

        final int rightAnswer = random.nextInt(4) + 1;
        List<Integer> digits = IntStream.range(0,3).boxed().collect(Collectors.toList());
        Collections.shuffle(digits);
        int wrongAnswers[] = new int[3];
        for (int i=0;i<3;i++)
            wrongAnswers[i] = digits.get(i);
        /*int[] wrongAnswers = new int[3];
        for (int i=0;i<3;i++)
            wrongAnswers[i] = random.nextInt(3);*/
        //final int wrongAnswer = random.nextInt(3);
        switch (rightAnswer)
        {
            case 1:
                rb1.setText(exercises.get(index).getAnswer());
                rb2.setText(exercises.get(index).getWrongAnswers()[wrongAnswers[0]]);
                rb3.setText(exercises.get(index).getWrongAnswers()[wrongAnswers[1]]);
                rb4.setText(exercises.get(index).getWrongAnswers()[wrongAnswers[2]]);
                break;
            case 2:
                rb1.setText(exercises.get(index).getWrongAnswers()[wrongAnswers[0]]);
                rb2.setText(exercises.get(index).getAnswer());
                rb3.setText(exercises.get(index).getWrongAnswers()[wrongAnswers[1]]);
                rb4.setText(exercises.get(index).getWrongAnswers()[wrongAnswers[2]]);
                break;
            case 3:
                rb1.setText(exercises.get(index).getWrongAnswers()[wrongAnswers[0]]);
                rb2.setText(exercises.get(index).getWrongAnswers()[wrongAnswers[1]]);
                rb3.setText(exercises.get(index).getAnswer());
                rb4.setText(exercises.get(index).getWrongAnswers()[wrongAnswers[2]]);
                break;
            case 4:
                rb1.setText(exercises.get(index).getWrongAnswers()[wrongAnswers[0]]);
                rb2.setText(exercises.get(index).getWrongAnswers()[wrongAnswers[1]]);
                rb3.setText(exercises.get(index).getWrongAnswers()[wrongAnswers[2]]);
                rb4.setText(exercises.get(index).getAnswer());
                break;
        }
        Button answerBtn = dialogView.findViewById(R.id.mathAnswerBtn);
        answerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (rightAnswer)
                {
                    case 1:
                        if (rb1.isChecked()) {
                            scoreCounter += scoreToAdd;
                            dialog.dismiss();
                            Toast.makeText(PlayActivity.this, "correct :)", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            dialog.dismiss();
                            Toast.makeText(PlayActivity.this, "incorrect :(", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 2:
                        if (rb2.isChecked()) {
                            scoreCounter += scoreToAdd;
                            dialog.dismiss();
                            Toast.makeText(PlayActivity.this, "correct :)", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            dialog.dismiss();
                            Toast.makeText(PlayActivity.this, "incorrect :(", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 3:
                        if (rb3.isChecked()) {
                            scoreCounter += scoreToAdd;
                            dialog.dismiss();
                            Toast.makeText(PlayActivity.this, "correct :)", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            dialog.dismiss();
                            Toast.makeText(PlayActivity.this, "incorrect :(", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 4:
                        if (rb4.isChecked()) {
                            scoreCounter += scoreToAdd;
                            dialog.dismiss();
                            Toast.makeText(PlayActivity.this, "correct :)", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            dialog.dismiss();
                            Toast.makeText(PlayActivity.this, "incorrect :(", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
                round.setText(getString(R.string.round) + " " + roundsCounter);
                score.setText(getString(R.string.score) + " " + scoreCounter);
                countDownTimer.cancel();
                timeLeftInMillis = temp;
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
        }, timeLeftInMillis);
        /*round.setText(getString(R.string.round) + " " + roundsCounter);
        score.setText(getString(R.string.score) + " " + scoreCounter);*/
    }

    public void initArray()
    {
        exercises = new ArrayList<>();
        String[] wrong = {"78", "30", "70"};
        exercises.add(new Exercise("1+1", "2", "Math", wrong));
        exercises.add(new Exercise("2+2", "4", "Math", wrong));
        exercises.add(new Exercise("10+5", "15", "Math", wrong));
        exercises.add(new Exercise("7*8", "56", "Math", wrong));
    }
}
