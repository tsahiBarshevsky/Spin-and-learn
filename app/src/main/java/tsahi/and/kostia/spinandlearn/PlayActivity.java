package tsahi.and.kostia.spinandlearn;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

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

    TextView mathAnswer;

    int blankIndex;

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
                /*case 2:
                    showBonus();
                    break;*/
                case 3:
                    type = "cities";
                    citiesQuestion();
                    break;
                case 4:
                    type = "sentence";
                    sentenceQuestion();
                    break;
                case 5:
                    type = "words";
                    wordsQuestion();
                    break;
                case 6:
                    type = "math";
                    mathQuestion();
                    break;
                case 7:
                    type = "cities";
                    citiesQuestion();
                    break;
                case 8:
                    type = "sentence";
                    sentenceQuestion();
                    break;
                case 9:
                    type = "words";
                    wordsQuestion();
                    break;
            }
        }
        else //bonus wheel spin - need to check this
        {
            String string = String.valueOf((int)(((double)4)
                    - Math.floor(((double)lngDegrees) / (360.0d / ((double)4)))));
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
        if (roundsCounter > 50)
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
            case "word":
                timerText = v.findViewById(R.id.timeComplete);
                break;
            case "sentence":
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

        final MathExercise currentExercise = exercises.getMathExercises().get(0);
        exercises.getMathExercises().remove(0);

        TextView question = dialogView.findViewById(R.id.exerciseMath);
        mathAnswer = dialogView.findViewById(R.id.answerMath);

        question.setText(currentExercise.getQuestion());

        Button answer_btn = dialogView.findViewById(R.id.mathAnswerBtn);
        TextView pad1 = dialogView.findViewById(R.id.tv_m1);
        TextView pad2 = dialogView.findViewById(R.id.tv_m2);
        TextView pad3 = dialogView.findViewById(R.id.tv_m3);
        TextView pad4 = dialogView.findViewById(R.id.tv_m4);
        TextView pad5 = dialogView.findViewById(R.id.tv_m5);
        TextView pad6 = dialogView.findViewById(R.id.tv_m6);
        TextView pad7 = dialogView.findViewById(R.id.tv_m7);
        TextView pad8 = dialogView.findViewById(R.id.tv_m8);
        TextView pad9 = dialogView.findViewById(R.id.tv_m9);
        TextView pad0 = dialogView.findViewById(R.id.tv_m0);
        TextView padBS = dialogView.findViewById(R.id.tv_mBack);
        TextView padC = dialogView.findViewById(R.id.tv_mClear);

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
                                if (!answer) {
                                    Toast.makeText(PlayActivity.this, "Sorry, you run out of time", Toast.LENGTH_SHORT).show();
                                    round.setText(getString(R.string.round) + " " + roundsCounter);
                                    score.setText(getString(R.string.score) + " " + scoreCounter);
                                }
                            }
                        });
                    }
                }.start();
            }
        }, timeLeftInMillis);

        answer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmp = mathAnswer.getText().toString();
                if(tmp.length()!=0) {
                    if (tmp.equals(currentExercise.getAnswer())) {
                        Toast.makeText(PlayActivity.this, "correct :)", Toast.LENGTH_SHORT).show();
                        scoreCounter += scoreToAdd;
                    } else {
                        Toast.makeText(PlayActivity.this, "incorrect :(", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                    countDownTimer.cancel();
                    timer.cancel();
                    timeLeftInMillis = temp;
                    round.setText(getString(R.string.round) + " " + roundsCounter);
                    score.setText(getString(R.string.score) + " " + scoreCounter);
                }
            }
        });

        MathButtonClickListener mathButtonClickListener = new MathButtonClickListener();
        pad0.setOnClickListener(mathButtonClickListener);
        pad1.setOnClickListener(mathButtonClickListener);
        pad2.setOnClickListener(mathButtonClickListener);
        pad3.setOnClickListener(mathButtonClickListener);
        pad4.setOnClickListener(mathButtonClickListener);
        pad5.setOnClickListener(mathButtonClickListener);
        pad6.setOnClickListener(mathButtonClickListener);
        pad7.setOnClickListener(mathButtonClickListener);
        pad8.setOnClickListener(mathButtonClickListener);
        pad9.setOnClickListener(mathButtonClickListener);
        padBS.setOnClickListener(mathButtonClickListener);
        padC.setOnClickListener(mathButtonClickListener);

        startTimer(dialogView);
//
//        Random random = new Random();
//        int size = exercises.getMathExercises().size();
//        final int index = random.nextInt(size); //draw lots question's number
//        startTimer(dialogView);
//        TextView exercise = dialogView.findViewById(R.id.exerciseMath);
//        exercise.setText(exercises.getMathExercises().get(index).getQuestion());
//        /*final RadioButton rb1 = dialogView.findViewById(R.id.rb1);
//        final RadioButton rb2 = dialogView.findViewById(R.id.rb2);
//        final RadioButton rb3 = dialogView.findViewById(R.id.rb3);
//        final RadioButton rb4 = dialogView.findViewById(R.id.rb4);*/
//
//        /*final int rightAnswer = random.nextInt(4) + 1;
//        List<Integer> digits = new ArrayList<>();// = IntStream.range(0,3).boxed().collect(Collectors.toList());
//        digits.add((int)(Math.random()*3));
//        for (int i =1;i<3;i++){
//            int tmp = (int)(Math.random()*3);
//            while (digits.contains(tmp)){
//                tmp = (int)(Math.random()*3);
//            }
//            digits.add(tmp);
//        }
//        System.out.println(digits.toString());
//        Collections.shuffle(digits);
//        int wrongAnswers[] = new int[3];
//        for (int i=0;i<3;i++)
//            wrongAnswers[i] = digits.get(i);
//        /*int[] wrongAnswers = new int[3];
//        for (int i=0;i<3;i++)
//            wrongAnswers[i] = random.nextInt(3);*/
//        //final int wrongAnswer = random.nextInt(3);
//        /*switch (rightAnswer)
//        {
//            case 1:
//                rb1.setText(exercises.getMathExercises().get(index).getAnswer());
//                rb2.setText(exercises.getMathExercises().get(index).getWrongAnswers().get(wrongAnswers[0]));
//                rb3.setText(exercises.getMathExercises().get(index).getWrongAnswers().get(wrongAnswers[1]));
//                rb4.setText(exercises.getMathExercises().get(index).getWrongAnswers().get(wrongAnswers[2]));
//                break;
//            case 2:
//                rb1.setText(exercises.getMathExercises().get(index).getWrongAnswers().get(wrongAnswers[0]));
//                rb2.setText(exercises.getMathExercises().get(index).getAnswer());
//                rb3.setText(exercises.getMathExercises().get(index).getWrongAnswers().get(wrongAnswers[1]));
//                rb4.setText(exercises.getMathExercises().get(index).getWrongAnswers().get(wrongAnswers[2]));
//                break;
//            case 3:
//                rb1.setText(exercises.getMathExercises().get(index).getWrongAnswers().get(wrongAnswers[0]));
//                rb2.setText(exercises.getMathExercises().get(index).getWrongAnswers().get(wrongAnswers[1]));
//                rb3.setText(exercises.getMathExercises().get(index).getAnswer());
//                rb4.setText(exercises.getMathExercises().get(index).getWrongAnswers().get(wrongAnswers[2]));
//                break;
//            case 4:
//                rb1.setText(exercises.getMathExercises().get(index).getWrongAnswers().get(wrongAnswers[0]));
//                rb2.setText(exercises.getMathExercises().get(index).getWrongAnswers().get(wrongAnswers[1]));
//                rb3.setText(exercises.getMathExercises().get(index).getWrongAnswers().get(wrongAnswers[2]));
//                rb4.setText(exercises.getMathExercises().get(index).getAnswer());
//                break;
//        }*/
//        Button answerBtn = dialogView.findViewById(R.id.mathAnswerBtn);
//        answerBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switch (rightAnswer)
//                {
//                    case 1:
//                        if (rb1.isChecked()) {
//                            scoreCounter += scoreToAdd;
//                            answer = true;
//                            dialog.dismiss();
//                            Toast.makeText(PlayActivity.this, "correct :)", Toast.LENGTH_SHORT).show();
//                        }
//                        else {
//                            dialog.dismiss();
//                            Toast.makeText(PlayActivity.this, "incorrect :(", Toast.LENGTH_SHORT).show();
//                        }
//                        break;
//                    case 2:
//                        if (rb2.isChecked()) {
//                            scoreCounter += scoreToAdd;
//                            answer = true;
//                            dialog.dismiss();
//                            Toast.makeText(PlayActivity.this, "correct :)", Toast.LENGTH_SHORT).show();
//                        }
//                        else {
//                            dialog.dismiss();
//                            Toast.makeText(PlayActivity.this, "incorrect :(", Toast.LENGTH_SHORT).show();
//                        }
//                        break;
//                    case 3:
//                        if (rb3.isChecked()) {
//                            scoreCounter += scoreToAdd;
//                            answer = true;
//                            dialog.dismiss();
//                            Toast.makeText(PlayActivity.this, "correct :)", Toast.LENGTH_SHORT).show();
//                        }
//                        else {
//                            dialog.dismiss();
//                            Toast.makeText(PlayActivity.this, "incorrect :(", Toast.LENGTH_SHORT).show();
//                        }
//                        break;
//                    case 4:
//                        if (rb4.isChecked()) {
//                            scoreCounter += scoreToAdd;
//                            answer = true;
//                            dialog.dismiss();
//                            Toast.makeText(PlayActivity.this, "correct :)", Toast.LENGTH_SHORT).show();
//                        }
//                        else {
//                            dialog.dismiss();
//                            Toast.makeText(PlayActivity.this, "incorrect :(", Toast.LENGTH_SHORT).show();
//                        }
//                        break;
//                }
//                if (roundsCounter != 2)
//                    round.setText(getString(R.string.round) + " " + roundsCounter);
//                score.setText(getString(R.string.score) + " " + scoreCounter);
//                countDownTimer.cancel();
//                timeLeftInMillis = temp;
//            }
//        });


    }

    private class MathButtonClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            String btnString = ((TextView) v).getText().toString();
            String tmp;
            switch (btnString){
                case "0":
                    tmp = mathAnswer.getText().toString();
                    tmp+="0";
                    mathAnswer.setText(tmp);
                    break;
                case "1":
                    tmp = mathAnswer.getText().toString();
                    tmp+="1";
                    mathAnswer.setText(tmp);
                    break;
                case "2":
                    tmp = mathAnswer.getText().toString();
                    tmp+="2";
                    mathAnswer.setText(tmp);
                    break;
                case "3":
                    tmp = mathAnswer.getText().toString();
                    tmp+="3";
                    mathAnswer.setText(tmp);
                    break;
                case "4":
                    tmp = mathAnswer.getText().toString();
                    tmp+="4";
                    mathAnswer.setText(tmp);
                    break;
                case "5":
                    tmp = mathAnswer.getText().toString();
                    tmp+="5";
                    mathAnswer.setText(tmp);
                    break;
                case "6":
                    tmp = mathAnswer.getText().toString();
                    tmp+="6";
                    mathAnswer.setText(tmp);
                    break;
                case "7":
                    tmp = mathAnswer.getText().toString();
                    tmp+="7";
                    mathAnswer.setText(tmp);
                    break;
                case "8":
                    tmp = mathAnswer.getText().toString();
                    tmp+="8";
                    mathAnswer.setText(tmp);
                    break;
                case "9":
                    tmp = mathAnswer.getText().toString();
                    tmp+="9";
                    mathAnswer.setText(tmp);
                    break;
                case "C":
                    mathAnswer.setText("");
                    break;
                case "b":
                    tmp = mathAnswer.getText().toString();
                    if(tmp.length() != 0) {
                        tmp = tmp.substring(0, tmp.length() - 1);
                        mathAnswer.setText(tmp);
                    }
                    break;
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void citiesQuestion()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this, R.style.CustomAlertDialog);
        View dialogView = getLayoutInflater().inflate(R.layout.cities_dialog, null);
        builder.setView(dialogView).setCancelable(false);
        final AlertDialog dialog = builder.show();

        final CitiesExercise currentExercise = exercises.getCitiesExercises().get(0);
        exercises.getCitiesExercises().remove(0);

        TextView question = dialogView.findViewById(R.id.exerciseCities);

        final TextView[] btn = {dialogView.findViewById(R.id.tv_c00),
                dialogView.findViewById(R.id.tv_c01),
                dialogView.findViewById(R.id.tv_c10),
                dialogView.findViewById(R.id.tv_c11)};

        question.setText(currentExercise.getQuestion());

        ArrayList<String> answers = currentExercise.getWrongAnswers();
        answers.add(currentExercise.getAnswer());

        for(int i=0;i<4;i++){
            int tmp = (int)(Math.random()*4);
            while(answers.get(tmp).length() == 0){
                tmp = (int)(Math.random()*4);
            }
            btn[i].setText(answers.get(tmp));
            answers.set(tmp, "");
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
                                if (!answer) {
                                    Toast.makeText(PlayActivity.this, "Sorry, you run out of time", Toast.LENGTH_SHORT).show();
                                    round.setText(getString(R.string.round) + " " + roundsCounter);
                                    score.setText(getString(R.string.score) + " " + scoreCounter);
                                }
                            }
                        });
                    }
                }.start();
            }
        }, timeLeftInMillis);

        for(int i=0;i<4;i++){
            btn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(((TextView)v).getText().equals(currentExercise.getAnswer())){
                        Toast.makeText(PlayActivity.this, "correct :)", Toast.LENGTH_SHORT).show();
                        scoreCounter += scoreToAdd;
                    }
                    else{
                        Toast.makeText(PlayActivity.this, "incorrect :(", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                    countDownTimer.cancel();
                    timer.cancel();
                    timeLeftInMillis = temp;
                    round.setText(getString(R.string.round) + " " + roundsCounter);
                    score.setText(getString(R.string.score) + " " + scoreCounter);
                }
            });
        }

        startTimer(dialogView);

//        Random random = new Random();
//        int size = exercises.getCitiesExercises().size();
//        final int index = random.nextInt(size); //draw lots question's number
//        startTimer(dialogView);
//        TextView exercise = dialogView.findViewById(R.id.exerciseCities);
//        exercise.setText(exercises.getCitiesExercises().get(index).getQuestion());
//        /*final RadioButton rb1 = dialogView.findViewById(R.id.rb1);
//        final RadioButton rb2 = dialogView.findViewById(R.id.rb2);
//        final RadioButton rb3 = dialogView.findViewById(R.id.rb3);
//        final RadioButton rb4 = dialogView.findViewById(R.id.rb4);
//
//        final int rightAnswer = random.nextInt(4) + 1;
//        List<Integer> digits = new ArrayList<>();// = IntStream.range(0,3).boxed().collect(Collectors.toList());
//        digits.add((int)(Math.random()*3));
//        for (int i =1;i<3;i++){
//            int tmp = (int)(Math.random()*3);
//            while (digits.contains(tmp)){
//                tmp = (int)(Math.random()*3);
//            }
//            digits.add(tmp);
//        }
//        System.out.println(digits.toString());
//        Collections.shuffle(digits);
//        int wrongAnswers[] = new int[3];
//        for (int i=0;i<3;i++)
//            wrongAnswers[i] = digits.get(i);
//        *//*int[] wrongAnswers = new int[3];
//        for (int i=0;i<3;i++)
//            wrongAnswers[i] = random.nextInt(3);*//*
//        //final int wrongAnswer = random.nextInt(3);
//        switch (rightAnswer)
//        {
//            case 1:
//                rb1.setText(exercises.getCitiesExercises().get(index).getAnswer());
//                rb2.setText(exercises.getCitiesExercises().get(index).getWrongAnswers()[wrongAnswers[0]]);
//                rb3.setText(exercises.getCitiesExercises().get(index).getWrongAnswers()[wrongAnswers[1]]);
//                rb4.setText(exercises.getCitiesExercises().get(index).getWrongAnswers()[wrongAnswers[2]]);
//                break;
//            case 2:
//                rb1.setText(exercises.getCitiesExercises().get(index).getWrongAnswers()[wrongAnswers[0]]);
//                rb2.setText(exercises.getCitiesExercises().get(index).getAnswer());
//                rb3.setText(exercises.getCitiesExercises().get(index).getWrongAnswers()[wrongAnswers[1]]);
//                rb4.setText(exercises.getCitiesExercises().get(index).getWrongAnswers()[wrongAnswers[2]]);
//                break;
//            case 3:
//                rb1.setText(exercises.getCitiesExercises().get(index).getWrongAnswers()[wrongAnswers[0]]);
//                rb2.setText(exercises.getCitiesExercises().get(index).getWrongAnswers()[wrongAnswers[1]]);
//                rb3.setText(exercises.getCitiesExercises().get(index).getAnswer());
//                rb4.setText(exercises.getCitiesExercises().get(index).getWrongAnswers()[wrongAnswers[2]]);
//                break;
//            case 4:
//                rb1.setText(exercises.getCitiesExercises().get(index).getWrongAnswers()[wrongAnswers[0]]);
//                rb2.setText(exercises.getCitiesExercises().get(index).getWrongAnswers()[wrongAnswers[1]]);
//                rb3.setText(exercises.getCitiesExercises().get(index).getWrongAnswers()[wrongAnswers[2]]);
//                rb4.setText(exercises.getCitiesExercises().get(index).getAnswer());
//                break;
//        }*/
//        /*Button answerBtn = dialogView.findViewById(R.id.citiesAnswerBtn);
//        answerBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switch (rightAnswer)
//                {
//                    case 1:
//                        if (rb1.isChecked()) {
//                            scoreCounter += scoreToAdd;
//                            answer = true;
//                            dialog.dismiss();
//                            Toast.makeText(PlayActivity.this, "correct :)", Toast.LENGTH_SHORT).show();
//                        }
//                        else {
//                            dialog.dismiss();
//                            Toast.makeText(PlayActivity.this, "incorrect :(", Toast.LENGTH_SHORT).show();
//                        }
//                        break;
//                    case 2:
//                        if (rb2.isChecked()) {
//                            scoreCounter += scoreToAdd;
//                            answer = true;
//                            dialog.dismiss();
//                            Toast.makeText(PlayActivity.this, "correct :)", Toast.LENGTH_SHORT).show();
//                        }
//                        else {
//                            dialog.dismiss();
//                            Toast.makeText(PlayActivity.this, "incorrect :(", Toast.LENGTH_SHORT).show();
//                        }
//                        break;
//                    case 3:
//                        if (rb3.isChecked()) {
//                            scoreCounter += scoreToAdd;
//                            answer = true;
//                            dialog.dismiss();
//                            Toast.makeText(PlayActivity.this, "correct :)", Toast.LENGTH_SHORT).show();
//                        }
//                        else {
//                            dialog.dismiss();
//                            Toast.makeText(PlayActivity.this, "incorrect :(", Toast.LENGTH_SHORT).show();
//                        }
//                        break;
//                    case 4:
//                        if (rb4.isChecked()) {
//                            scoreCounter += scoreToAdd;
//                            answer = true;
//                            dialog.dismiss();
//                            Toast.makeText(PlayActivity.this, "correct :)", Toast.LENGTH_SHORT).show();
//                        }
//                        else {
//                            dialog.dismiss();
//                            Toast.makeText(PlayActivity.this, "incorrect :(", Toast.LENGTH_SHORT).show();
//                        }
//                        break;
//                }
//                if (roundsCounter != 2)
//                    round.setText(getString(R.string.round) + " " + roundsCounter);
//                score.setText(getString(R.string.score) + " " + scoreCounter);
//                countDownTimer.cancel();
//                timeLeftInMillis = temp;
//            }
//        });*/


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sentenceQuestion()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this, R.style.CustomAlertDialog);
        View dialogView = getLayoutInflater().inflate(R.layout.cities_dialog, null);
        builder.setView(dialogView).setCancelable(false);
        final AlertDialog dialog = builder.show();

        final SentenceExercise currentExercise = exercises.getSentenceExercises().get(0);
        exercises.getSentenceExercises().remove(0);

        TextView question = dialogView.findViewById(R.id.exerciseCities);

        final TextView[] btn = {dialogView.findViewById(R.id.tv_c00),
                dialogView.findViewById(R.id.tv_c01),
                dialogView.findViewById(R.id.tv_c10),
                dialogView.findViewById(R.id.tv_c11)};

        question.setText(currentExercise.getQuestion());

        ArrayList<String> answers = currentExercise.getWrongAnswers();
        answers.add(currentExercise.getAnswer());

        for(int i=0;i<4;i++){
            int tmp = (int)(Math.random()*4);
            while(answers.get(tmp).length() == 0){
                tmp = (int)(Math.random()*4);
            }
            btn[i].setText(answers.get(tmp));
            answers.set(tmp, "");
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
                                if (!answer) {
                                    Toast.makeText(PlayActivity.this, "Sorry, you run out of time", Toast.LENGTH_SHORT).show();
                                    round.setText(getString(R.string.round) + " " + roundsCounter);
                                    score.setText(getString(R.string.score) + " " + scoreCounter);
                                }
                            }
                        });
                    }
                }.start();
            }
        }, timeLeftInMillis);

        for(int i=0;i<4;i++){
            btn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(((TextView)v).getText().equals(currentExercise.getAnswer())){
                        Toast.makeText(PlayActivity.this, "correct :)", Toast.LENGTH_SHORT).show();
                        scoreCounter += scoreToAdd;
                    }
                    else{
                        Toast.makeText(PlayActivity.this, "incorrect :(", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                    countDownTimer.cancel();
                    timer.cancel();
                    timeLeftInMillis = temp;
                    round.setText(getString(R.string.round) + " " + roundsCounter);
                    score.setText(getString(R.string.score) + " " + scoreCounter);
                }
            });
        }

        startTimer(dialogView);



    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void wordsQuestion()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this, R.style.CustomAlertDialog);
        View dialogView = getLayoutInflater().inflate(R.layout.complete_the_latters_dialog, null);
        builder.setView(dialogView).setCancelable(false);
        final AlertDialog dialog = builder.show();

        final WordExercise currentExercise = exercises.getWordExercises().get(0);
        exercises.getWordExercises().remove(0);

        TextView definition = dialogView.findViewById(R.id.exerciseComplete);
        definition.setText(currentExercise.getDefinition());
        final String question = currentExercise.getQuestion().toString();

        final TextView[] letter = {dialogView.findViewById(R.id.tv00),
          dialogView.findViewById(R.id.tv01),
          dialogView.findViewById(R.id.tv02),
          dialogView.findViewById(R.id.tv03),
          dialogView.findViewById(R.id.tv04),
          dialogView.findViewById(R.id.tv05),
          dialogView.findViewById(R.id.tv06),
          dialogView.findViewById(R.id.tv07),
          dialogView.findViewById(R.id.tv08),
          dialogView.findViewById(R.id.tv09),
          dialogView.findViewById(R.id.tv10),
          dialogView.findViewById(R.id.tv11),
          dialogView.findViewById(R.id.tv12),
          dialogView.findViewById(R.id.tv13),
          dialogView.findViewById(R.id.tv14),
          dialogView.findViewById(R.id.tv15),
          dialogView.findViewById(R.id.tv16),
          dialogView.findViewById(R.id.tv17),
          dialogView.findViewById(R.id.tv18),
          dialogView.findViewById(R.id.tv19)};

        ArrayList<Character> letterBank = currentExercise.getLetterBank();

        for(int i=0;i<20;i++){
            int tmp = (int)(Math.random()*20);
            while(letterBank.get(tmp).equals('0')){
                tmp = (int)(Math.random()*20);
            }
            letter[i].setText(letterBank.get(tmp).toString());
            letterBank.set(tmp, '0');
        }

        final ArrayList<TextView> answer_blank = new ArrayList<>();
        final LinearLayout answer_container = dialogView.findViewById(R.id.answerLayout);
        final int question_size = question.length();
        for(int i=0;i<question_size;i++){
            TextView tmp = new TextView(dialogView.getContext());
            tmp.setTextSize(30);
            tmp.setTextColor(Color.BLACK);
            tmp.setPadding(5,0,5,0);
            tmp.setText(((Character)question.charAt(i)).toString());
            if(question.charAt(i) == '_'){
                answer_blank.add(tmp);
            }
            answer_container.addView(tmp);
        }

        blankIndex = 0;
        final int blankSize = answer_blank.size();

        for(int i=0;i<20;i++){
            letter[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(blankIndex < blankSize){
                        String tmp = ((TextView) v).getText().toString();
                        answer_blank.get(blankIndex++).setText(tmp);
                        v.setVisibility(View.INVISIBLE);
                    }
                }
            });
        }

        for(int i=0;i<blankSize;i++){
            answer_blank.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String tmp = ((TextView) v).getText().toString();
                    if(!tmp.equals("_")){
                        ((TextView) v).setText("_");
                        for(int j=0;j<20;j++){
                            if(letter[j].getVisibility() == View.INVISIBLE && letter[j].getText().equals(tmp)){
                                letter[j].setVisibility(View.VISIBLE);
                                for(int k = 0;k<blankSize;k++){
                                    if(answer_blank.get(k).getText().equals("_")){
                                        blankIndex = k;
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            });
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
                                if (!answer) {
                                    Toast.makeText(PlayActivity.this, "Sorry, you run out of time", Toast.LENGTH_SHORT).show();
                                    round.setText(getString(R.string.round) + " " + roundsCounter);
                                    score.setText(getString(R.string.score) + " " + scoreCounter);
                                }
                            }
                        });
                    }
                }.start();
            }
        }, timeLeftInMillis);

        Button ans_btn = dialogView.findViewById(R.id.wordAnswerBtn);
        ans_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmp = "";
                int j =0;
                for(int i=0;i<question_size;i++) {
                    if(question.charAt(i) == '_'){
                        if(answer_blank.get(j).getText().equals("_")){
                            return;
                        }
                        tmp+= answer_blank.get(j++).getText();
                    }
                    else{
                        tmp+=question.charAt(i);
                    }
                }
                if (tmp.equals(currentExercise.getAnswer())) {
                    Toast.makeText(PlayActivity.this, "correct :)", Toast.LENGTH_SHORT).show();
                    scoreCounter += scoreToAdd;
                } else {
                    Toast.makeText(PlayActivity.this, "incorrect :(", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
                countDownTimer.cancel();
                timer.cancel();
                timeLeftInMillis = temp;
                round.setText(getString(R.string.round) + " " + roundsCounter);
                score.setText(getString(R.string.score) + " " + scoreCounter);
            }
        });

        startTimer(dialogView);



    }

    public void initArray()
    {
        exercises.getMathExercises().add(new MathExercise("32 + 17", "49"));
        exercises.getMathExercises().add(new MathExercise("67 - 52", "15"));
        exercises.getMathExercises().add(new MathExercise("12 x 5", "60"));
        exercises.getMathExercises().add(new MathExercise("72 / 9", "8"));
        exercises.getMathExercises().add(new MathExercise("23 x 3", "69"));
        exercises.getMathExercises().add(new MathExercise("128 / 32", "4"));
        exercises.getMathExercises().add(new MathExercise("59 + 62", "121"));
        exercises.getMathExercises().add(new MathExercise("236 - 140", "96"));
        exercises.getMathExercises().add(new MathExercise("2 ^ 5", "32"));
        exercises.getMathExercises().add(new MathExercise("5 ^ 3", "125"));

        exercises.getCitiesExercises().add(new CitiesExercise(this,getResources().getString(R.string.capital_of) + " " + getResources().getString(R.string.israel) + "?", getResources().getString(R.string.jerusalem)));
        exercises.getCitiesExercises().add(new CitiesExercise(this,getResources().getString(R.string.capital_of) + " " + getResources().getString(R.string.spain) + "?", getResources().getString(R.string.madrid)));
        exercises.getCitiesExercises().add(new CitiesExercise(this,getResources().getString(R.string.capital_of) + " " + getResources().getString(R.string.england) + "?", getResources().getString(R.string.london)));
        exercises.getCitiesExercises().add(new CitiesExercise(this,getResources().getString(R.string.capital_of) + " " + getResources().getString(R.string.usa) + "?", getResources().getString(R.string.washington)));
        exercises.getCitiesExercises().add(new CitiesExercise(this,getResources().getString(R.string.capital_of) + " " + getResources().getString(R.string.france) + "?", getResources().getString(R.string.paris)));
        exercises.getCitiesExercises().add(new CitiesExercise(this,getResources().getString(R.string.capital_of) + " " + getResources().getString(R.string.russsia) + "?", getResources().getString(R.string.moscow)));
        exercises.getCitiesExercises().add(new CitiesExercise(this,getResources().getString(R.string.capital_of) + " " + getResources().getString(R.string.ukraine) + "?", getResources().getString(R.string.kiev)));
        exercises.getCitiesExercises().add(new CitiesExercise(this,getResources().getString(R.string.capital_of) + " " + getResources().getString(R.string.japan) + "?", getResources().getString(R.string.tokyo)));
        exercises.getCitiesExercises().add(new CitiesExercise(this,getResources().getString(R.string.capital_of) + " " + getResources().getString(R.string.south_africa) + "?", getResources().getString(R.string.capetown)));
        exercises.getCitiesExercises().add(new CitiesExercise(this,getResources().getString(R.string.capital_of) + " " + getResources().getString(R.string.germany) + "?", getResources().getString(R.string.berlin)));
        exercises.getCitiesExercises().add(new CitiesExercise(this,getResources().getString(R.string.capital_of) + " " + getResources().getString(R.string.argentina) + "?", getResources().getString(R.string.buenos_aires)));
        exercises.getCitiesExercises().add(new CitiesExercise(this,getResources().getString(R.string.capital_of) + " " + getResources().getString(R.string.peru) + "?", getResources().getString(R.string.lima)));
        exercises.getCitiesExercises().add(new CitiesExercise(this,getResources().getString(R.string.capital_of) + " " + getResources().getString(R.string.egypt) + "?", getResources().getString(R.string.cairo)));
        exercises.getCitiesExercises().add(new CitiesExercise(this,getResources().getString(R.string.capital_of) + " " + getResources().getString(R.string.saudi_arabia) + "?", getResources().getString(R.string.riahd)));
        exercises.getCitiesExercises().add(new CitiesExercise(this,getResources().getString(R.string.capital_of) + " " + getResources().getString(R.string.lebanon) + "?", getResources().getString(R.string.beiruth)));
        exercises.getCitiesExercises().add(new CitiesExercise(this,getResources().getString(R.string.capital_of) + " " + getResources().getString(R.string.siriya) + "?", getResources().getString(R.string.damascus)));

        exercises.getWordExercises().add(new WordExercise(this,getResources().getString(R.string.encylopedia_definition), getResources().getString(R.string.encyclopedia)));
        exercises.getWordExercises().add(new WordExercise(this,getResources().getString(R.string.bacteria_definition), getResources().getString(R.string.bacteria)));
        exercises.getWordExercises().add(new WordExercise(this,getResources().getString(R.string.allergy_definition), getResources().getString(R.string.allergy)));
        exercises.getWordExercises().add(new WordExercise(this,getResources().getString(R.string.ballerina_definition), getResources().getString(R.string.ballerina)));
        exercises.getWordExercises().add(new WordExercise(this,getResources().getString(R.string.boomernag_definition), getResources().getString(R.string.boomernag)));
        exercises.getWordExercises().add(new WordExercise(this,getResources().getString(R.string.camouflage_definition), getResources().getString(R.string.camouflage)));
        exercises.getWordExercises().add(new WordExercise(this,getResources().getString(R.string.magazine_definition), getResources().getString(R.string.magazine)));
        exercises.getWordExercises().add(new WordExercise(this,getResources().getString(R.string.archive_definition), getResources().getString(R.string.archive)));
        exercises.getWordExercises().add(new WordExercise(this,getResources().getString(R.string.feather_definition), getResources().getString(R.string.feather)));
        exercises.getWordExercises().add(new WordExercise(this,getResources().getString(R.string.success_definition), getResources().getString(R.string.success)));

        exercises.getSentenceExercises().add(new SentenceExercise(this,getResources().getString(R.string.tooteth_missing), getResources().getString(R.string.tooteth)));
        exercises.getSentenceExercises().add(new SentenceExercise(this,getResources().getString(R.string.smoke_missing), getResources().getString(R.string.smoke)));
        exercises.getSentenceExercises().add(new SentenceExercise(this,getResources().getString(R.string.book_missing), getResources().getString(R.string.book)));
        exercises.getSentenceExercises().add(new SentenceExercise(this,getResources().getString(R.string.thousand_missing), getResources().getString(R.string.thousand)));
        exercises.getSentenceExercises().add(new SentenceExercise(this,getResources().getString(R.string.hand_missing), getResources().getString(R.string.hand)));
        exercises.getSentenceExercises().add(new SentenceExercise(this,getResources().getString(R.string.practice_missing), getResources().getString(R.string.practice)));
        exercises.getSentenceExercises().add(new SentenceExercise(this,getResources().getString(R.string.loves_missing), getResources().getString(R.string.loves)));
        exercises.getSentenceExercises().add(new SentenceExercise(this,getResources().getString(R.string.sight_missing), getResources().getString(R.string.sight)));
        exercises.getSentenceExercises().add(new SentenceExercise(this,getResources().getString(R.string.grasp_missing), getResources().getString(R.string.grasp)));

        Collections.shuffle(exercises.getCitiesExercises());
        Collections.shuffle(exercises.getMathExercises());
        Collections.shuffle(exercises.getSentenceExercises());
        Collections.shuffle(exercises.getWordExercises());
    }

    /*public void showBonus()
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
    }*/

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
        if(image != null) {
            immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] b = baos.toByteArray();
            String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
            return imageEncoded;
        }
        return null;
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