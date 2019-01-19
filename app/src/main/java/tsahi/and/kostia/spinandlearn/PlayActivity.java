package tsahi.and.kostia.spinandlearn;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class PlayActivity extends AppCompatActivity implements Animation.AnimationListener{

    int roundsCounter = 1, scoreCounter = 0, scoreToAdd;
    boolean blnButtonRotation = true, answer, bonus;
    int intNumber = 10;
    long lngDegrees = 0, timeLeftInMillis, temp;
    Exercises exercises;
    ImageView imageRoulette;
    TextView round, score, levelTV;
    CountDownTimer countDownTimer;
    String level, type;
    Button spinBtn;
    SharedPreferences sp;
    Bitmap bitmap;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
            bitmap = extras.getParcelable("user_pic");
        sp = getSharedPreferences("score detail", MODE_PRIVATE);
        exercises = new Exercises();
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
        spinBtn = findViewById(R.id.spinBtn);
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
        if (!bonus) {
            answer = false;
            String string = String.valueOf((int) (((double) this.intNumber)
                    - Math.floor(((double) this.lngDegrees) / (360.0d / ((double) this.intNumber)))));
            int pos = Integer.parseInt(string);
            pos--;
            blnButtonRotation = true;
            switch (pos)
            {
                case 0:
                    type = "math";
                    mathQuestion();
                    break;
                case 1:
                    type = "cities";
                    citiesQuestion();
                    break;
                case 2:
                    showBonus();
                    break;
                case 3:
                    type = "cities";
                    citiesQuestion();
                    break;
                case 6:
                    type = "math";
                    mathQuestion();
                    break;
                case 7:
                    type = "cities";
                    citiesQuestion();
                    break;
            }
        }
        else //bonus wheel spin - need to check this
        {
            String string = String.valueOf((int)(((double)4)
                    - Math.floor(((double)4) / (360.0d / ((double)4)))));
            int pos = Integer.parseInt(string);
            pos--;
            switch (pos)
            {
                case 0:
                    Toast.makeText(PlayActivity.this, "Blue", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(PlayActivity.this, "Green", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(PlayActivity.this, "Orange", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(PlayActivity.this, "Red", Toast.LENGTH_SHORT).show();
                    break;
            }
            bonus = false;
            changeBack();
        }
        if (roundsCounter > 2)
        {
            spinBtn.setVisibility(View.INVISIBLE);
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
        TextView timerText = null;
        switch (type)
        {
            case "math":
                timerText = v.findViewById(R.id.timeMath);
                break;
            case "cities":
                timerText = v.findViewById(R.id.timeCities);
                break;
        }
        int minutes = (int)timeLeftInMillis / 60000;
        int seconds = (int)timeLeftInMillis % 60000 / 1000;
        String timeLeft;
        timeLeft = "" + minutes;
        timeLeft += ":";
        if (seconds < 10)
            timeLeft += "0";
        timeLeft += seconds;
        if (timerText != null)
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
        int size = exercises.getMathExercises().size();
        final int index = random.nextInt(size); //draw lots question's number
        startTimer(dialogView);
        TextView exercise = dialogView.findViewById(R.id.exerciseMath);
        exercise.setText(exercises.getMathExercises().get(index).getQuestion());
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
                rb1.setText(exercises.getMathExercises().get(index).getAnswer());
                rb2.setText(exercises.getMathExercises().get(index).getWrongAnswers()[wrongAnswers[0]]);
                rb3.setText(exercises.getMathExercises().get(index).getWrongAnswers()[wrongAnswers[1]]);
                rb4.setText(exercises.getMathExercises().get(index).getWrongAnswers()[wrongAnswers[2]]);
                break;
            case 2:
                rb1.setText(exercises.getMathExercises().get(index).getWrongAnswers()[wrongAnswers[0]]);
                rb2.setText(exercises.getMathExercises().get(index).getAnswer());
                rb3.setText(exercises.getMathExercises().get(index).getWrongAnswers()[wrongAnswers[1]]);
                rb4.setText(exercises.getMathExercises().get(index).getWrongAnswers()[wrongAnswers[2]]);
                break;
            case 3:
                rb1.setText(exercises.getMathExercises().get(index).getWrongAnswers()[wrongAnswers[0]]);
                rb2.setText(exercises.getMathExercises().get(index).getWrongAnswers()[wrongAnswers[1]]);
                rb3.setText(exercises.getMathExercises().get(index).getAnswer());
                rb4.setText(exercises.getMathExercises().get(index).getWrongAnswers()[wrongAnswers[2]]);
                break;
            case 4:
                rb1.setText(exercises.getMathExercises().get(index).getWrongAnswers()[wrongAnswers[0]]);
                rb2.setText(exercises.getMathExercises().get(index).getWrongAnswers()[wrongAnswers[1]]);
                rb3.setText(exercises.getMathExercises().get(index).getWrongAnswers()[wrongAnswers[2]]);
                rb4.setText(exercises.getMathExercises().get(index).getAnswer());
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
                            answer = true;
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
                            answer = true;
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
                            answer = true;
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
                            answer = true;
                            dialog.dismiss();
                            Toast.makeText(PlayActivity.this, "correct :)", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            dialog.dismiss();
                            Toast.makeText(PlayActivity.this, "incorrect :(", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
                if (roundsCounter != 2)
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
                                if (!answer)
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void citiesQuestion()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this, R.style.CustomAlertDialog);
        View dialogView = getLayoutInflater().inflate(R.layout.cities_dialog, null);
        builder.setView(dialogView).setCancelable(false);
        final AlertDialog dialog = builder.show();
        Random random = new Random();
        int size = exercises.getCitiesExercises().size();
        final int index = random.nextInt(size); //draw lots question's number
        startTimer(dialogView);
        TextView exercise = dialogView.findViewById(R.id.exerciseCities);
        exercise.setText(exercises.getCitiesExercises().get(index).getQuestion());
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
                rb1.setText(exercises.getCitiesExercises().get(index).getAnswer());
                rb2.setText(exercises.getCitiesExercises().get(index).getWrongAnswers()[wrongAnswers[0]]);
                rb3.setText(exercises.getCitiesExercises().get(index).getWrongAnswers()[wrongAnswers[1]]);
                rb4.setText(exercises.getCitiesExercises().get(index).getWrongAnswers()[wrongAnswers[2]]);
                break;
            case 2:
                rb1.setText(exercises.getCitiesExercises().get(index).getWrongAnswers()[wrongAnswers[0]]);
                rb2.setText(exercises.getCitiesExercises().get(index).getAnswer());
                rb3.setText(exercises.getCitiesExercises().get(index).getWrongAnswers()[wrongAnswers[1]]);
                rb4.setText(exercises.getCitiesExercises().get(index).getWrongAnswers()[wrongAnswers[2]]);
                break;
            case 3:
                rb1.setText(exercises.getCitiesExercises().get(index).getWrongAnswers()[wrongAnswers[0]]);
                rb2.setText(exercises.getCitiesExercises().get(index).getWrongAnswers()[wrongAnswers[1]]);
                rb3.setText(exercises.getCitiesExercises().get(index).getAnswer());
                rb4.setText(exercises.getCitiesExercises().get(index).getWrongAnswers()[wrongAnswers[2]]);
                break;
            case 4:
                rb1.setText(exercises.getCitiesExercises().get(index).getWrongAnswers()[wrongAnswers[0]]);
                rb2.setText(exercises.getCitiesExercises().get(index).getWrongAnswers()[wrongAnswers[1]]);
                rb3.setText(exercises.getCitiesExercises().get(index).getWrongAnswers()[wrongAnswers[2]]);
                rb4.setText(exercises.getCitiesExercises().get(index).getAnswer());
                break;
        }
        Button answerBtn = dialogView.findViewById(R.id.citiesAnswerBtn);
        answerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (rightAnswer)
                {
                    case 1:
                        if (rb1.isChecked()) {
                            scoreCounter += scoreToAdd;
                            answer = true;
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
                            answer = true;
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
                            answer = true;
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
                            answer = true;
                            dialog.dismiss();
                            Toast.makeText(PlayActivity.this, "correct :)", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            dialog.dismiss();
                            Toast.makeText(PlayActivity.this, "incorrect :(", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
                if (roundsCounter != 2)
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
                                if (!answer)
                                    Toast.makeText(PlayActivity.this, "Sorry, you run out of time", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }.start();
            }
        }, timeLeftInMillis);
    }

    public void initArray()
    {
        String[] wrongMath = {"78", "30", "70"};
        String[] wrongCities = {"New York", "Rome", "Lisbon"};
        exercises.getMathExercises().add(new MathExercise("1+1", "2", wrongMath));
        exercises.getMathExercises().add(new MathExercise("2+2", "4", wrongMath));
        exercises.getMathExercises().add(new MathExercise("10+5", "15", wrongMath));
        exercises.getCitiesExercises().add(new CitiesExercise("Capital of Israel?", "Jerusalem", wrongCities));
        exercises.getCitiesExercises().add(new CitiesExercise("Capital of Spain?", "Madrid", wrongCities));
        exercises.getMathExercises().add(new MathExercise("7*8", "56", wrongMath));
    }

    public void showBonus()
    {
        bonus = true;
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer = MediaPlayer.create(this, R.raw.ta_da);
        AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this, R.style.BonusDialog);
        View dialogView = getLayoutInflater().inflate(R.layout.bonus_dialog, null);
        builder.setView(dialogView).setCancelable(false);
        final AlertDialog dialog = builder.show();
        mediaPlayer.start();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                //scoreCounter += 100;
                score.setText(getString(R.string.score) + " " + scoreCounter);
            }
        }, 3000);
        LinearLayout bonusLayout = findViewById(R.id.bonusLayout);
        bonusLayout.setVisibility(View.VISIBLE);
        spinBtn.setVisibility(View.INVISIBLE);
        final ObjectAnimator oa1 = ObjectAnimator.ofFloat(imageRoulette, "scaleX", 1f, 0f);
        final ObjectAnimator oa2 = ObjectAnimator.ofFloat(imageRoulette, "scaleX", 0f, 1f);
        oa1.setInterpolator(new DecelerateInterpolator());
        oa2.setInterpolator(new AccelerateDecelerateInterpolator());
        oa1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                imageRoulette.setImageResource(R.drawable.bonus_wheel);
                oa2.start();
            }
        });
        oa1.start();

        Button spinBonusBtn = findViewById(R.id.spinBonusBtn);
        spinBonusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                bonusLayout.setVisibility(View.INVISIBLE);
                spinBtn.setVisibility(View.VISIBLE);
            }
        });
        Button leaveBtn = findViewById(R.id.leaveBtn);
        leaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bonusLayout.setVisibility(View.INVISIBLE);
                spinBtn.setVisibility(View.VISIBLE);
                bonus = false;
                changeBack();
            }
        });
    }

    public void changeBack()
    {
        final ObjectAnimator oa1 = ObjectAnimator.ofFloat(imageRoulette, "scaleX", 1f, 0f);
        final ObjectAnimator oa2 = ObjectAnimator.ofFloat(imageRoulette, "scaleX", 0f, 1f);
        oa1.setInterpolator(new DecelerateInterpolator());
        oa2.setInterpolator(new AccelerateDecelerateInterpolator());
        oa1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                imageRoulette.setImageResource(R.drawable.rolutte_10);
                oa2.start();
            }
        });
        oa1.start();
    }

    public static String encodeToBase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("Name", getIntent().getStringExtra("Name"));
        editor.putInt("Score", scoreCounter);
        editor.putString("Pic", encodeToBase64(bitmap));
        editor.commit();
    }
}