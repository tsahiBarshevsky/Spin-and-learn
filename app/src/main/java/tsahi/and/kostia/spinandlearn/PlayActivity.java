package tsahi.and.kostia.spinandlearn;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Math.floor;

public class PlayActivity extends AppCompatActivity implements Animation.AnimationListener{

    int NUM_OF_ROUNDS = 10;

    int roundsCounter = 1, scoreCounter = 0, scoreToAdd, scoreRange;
    boolean blnButtonRotation = true, bonus, isFirstImage = true;
    int intNumber = 10;
    long lngDegrees = 0, lngDegrees2 = 0, timeLeftInMillis, temp;
    ExercisesContainer exercisesContainer;
    ImageView imageRoulette, bonusRoulette, heart1, heart2, heart3;
    TextView round, score;
    CountDownTimer countDownTimer;
    String level, type;
    Button spinBtn;
    SharedPreferences sp;
    Bitmap bitmap;
    MediaPlayer mediaPlayer;
    Animation animation;
    TextView mathAnswer;
    ArrayList<TextView> answer_blank;

    int blankIndex;

    float scale;
    float distanceRoulette ;
    float distanceBonus;

    int strikes;

    Exercises currentExercise;
    Timer timer;
    AlertDialog dialog;

    LinearLayout bonusLayout;

    Boolean isAppPaused = false;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        isAppPaused = false;
        Bundle extras = getIntent().getExtras();
        if (extras != null)
            bitmap = extras.getParcelable("user_pic");
        sp = getSharedPreferences("score detail", MODE_PRIVATE);
        exercisesContainer = new ExercisesContainer(this);
        level = getIntent().getStringExtra("Level");
        AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this, R.style.BonusDialog);
        View dialogView = getLayoutInflater().inflate(R.layout.level_dialog, null);
        builder.setView(dialogView).setCancelable(false);
        ImageView imageView = dialogView.findViewById(R.id.level_source);
        Animation scaleUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.level_scale_up);
        switch (level)
        {
            case "Easy":
                imageView.setImageResource(R.drawable.easy);
                scoreToAdd = 10;          //add 10-20 points for right answer
                scoreRange = 10;
                timeLeftInMillis = 60000; //1 minute
                temp = timeLeftInMillis;
                break;
            case "Medium":
                imageView.setImageResource(R.drawable.medium);
                scoreToAdd = 20;          //add 20-50 points for right answer
                scoreRange = 30;
                timeLeftInMillis = 30000; //30 seconds
                temp = timeLeftInMillis;
                break;
            case "Hard":
                imageView.setImageResource(R.drawable.hard);
                scoreToAdd = 50;          //add 50-100 points for right answer
                scoreRange = 50;
                timeLeftInMillis = 10000; //10 seconds
                temp = timeLeftInMillis;
                break;
        }
        imageView.startAnimation(scaleUp);
        final AlertDialog dialog = builder.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 3000);
        heart1 = findViewById(R.id.heart1);
        heart2 = findViewById(R.id.heart2);
        heart3 = findViewById(R.id.heart3);
        animation = AnimationUtils.loadAnimation(getApplicationContext() ,R.anim.button_anim);
        /*heart1.startAnimation(animation);
        heart2.startAnimation(animation);
        heart3.startAnimation(animation);*/
        strikes = 0;
        imageRoulette = findViewById(R.id.ImageView01);
        bonusRoulette = findViewById(R.id.ImageView02);
        bonusRoulette.setVisibility(View.INVISIBLE);
        scale = getApplicationContext().getResources().getDisplayMetrics().density;
        distanceRoulette = imageRoulette.getCameraDistance() * (scale + (scale / 3));
        distanceBonus = imageRoulette.getCameraDistance() * (scale + (scale / 3));
        imageRoulette.setCameraDistance(distanceRoulette);
        bonusRoulette.setCameraDistance(distanceBonus);
        round = findViewById(R.id.round);
        round.setText(getString(R.string.round) + " " + "1");
        score = findViewById(R.id.score);
        score.setText(getString(R.string.score) + " " + "0");
        spinBtn = findViewById(R.id.spinBtn);
        spinBtn.setOnClickListener(new spinWheelClickListener());
        Thread animations = new Thread(){
            public void run(){
                heart1.startAnimation(animation);
                heart2.startAnimation(animation);
                heart3.startAnimation(animation);
                spinBtn.startAnimation(animation);
            }
        };
        animations.start();
        //spinBtn.startAnimation(animation);
    }

    @Override
    public void onAnimationStart(Animation animation) {
        blnButtonRotation = false;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onAnimationEnd(Animation animation) {
        if (!bonus) {
            String string = String.valueOf((int) (((double) this.intNumber)
                    - floor(((double) this.lngDegrees) / (360.0d / ((double) this.intNumber)))));
            int pos = Integer.parseInt(string);
            pos--;
            blnButtonRotation = true;
            wordsQuestion();
            type = "words";
            /*switch (pos)
            {
                case 0:
                    type = "math";
                    mathQuestion();
                    break;
                case 1:
                    type = "words";
                    wordsQuestion();
                    break;
                case 2:
                    showBonus();
                    break;
                case 3:
                    type = "words";
                    wordsQuestion();
                    break;
                case 4:
                    type = "sentence";
                    sentenceQuestion();
                    break;
                case 5:
                    type = "cities";
                    citiesQuestion();
                    break;
                case 6:
                    type = "math";
                    mathQuestion();
                    break;
                case 7:
                    type = "words";
                    wordsQuestion();
                    break;
                case 8:
                    type = "sentence";
                    sentenceQuestion();
                    break;
                case 9:
                    type = "cities";
                    citiesQuestion();
                    break;
            }*/
        }
        else //bonus wheel spin
        {
            String string = String.valueOf((int)(((double)5)
                    - floor(((double)lngDegrees2) / (360.0d / ((double)5)))));
            int pos = Integer.parseInt(string);
            pos--;
            switch (pos)
            {
                case 0:
                    scoreCounter += 100;
                    score.setText(getString(R.string.score) + " " + scoreCounter);
                    endBonus(0);
                    break;
                case 1:
                    scoreCounter = 0;
                    score.setText(getString(R.string.score) + " " + scoreCounter);
                    endBonus(1);
                    break;
                case 2:
                    strikes--;
                    switch (strikes)
                    {
                        case 3:
                            heart1.startAnimation(animation);
                            heart1.setVisibility(View.VISIBLE);
                            break;
                        case 2:
                            heart2.startAnimation(animation);
                            heart2.setVisibility(View.VISIBLE);
                            break;
                        case 1:
                            heart3.startAnimation(animation);
                            heart3.setVisibility(View.VISIBLE);
                            break;
                    }
                    endBonus(2);
                    break;
                case 3:
                    if(scoreCounter < 100){
                        scoreCounter = 0;
                    }
                    else {
                        scoreCounter -= 100;
                    }
                    endBonus(3);
                    score.setText(getString(R.string.score) + " " + scoreCounter);
                    break;
                case 4:
                    scoreCounter *= 2;
                    endBonus(4);
                    score.setText(getString(R.string.score) + " " + scoreCounter);
                    break;
            }
            bonus = false;
            blnButtonRotation = true;
            boardFlip();
            if (roundsCounter > NUM_OF_ROUNDS) {
                endGame();
            }
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    public void endBonus(int result)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this, R.style.BonusDialog);
        View dialogView = getLayoutInflater().inflate(R.layout.bonus_result, null);
        builder.setView(dialogView).setCancelable(false);
        ImageView imageView = dialogView.findViewById(R.id.bonus_result);
        switch (result)
        {
            case 0:
                imageView.setImageResource(R.drawable.plus_100);
                break;
            case 1:
                imageView.setImageResource(R.drawable.zero_score);
                break;
            case 2:
                imageView.setImageResource(R.drawable.extra_life);
                break;
            case 3:
                imageView.setImageResource(R.drawable.minus_100);
                break;
            case 4:
                imageView.setImageResource(R.drawable.double_score);
                break;
        }
        final AlertDialog dialog = builder.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 3000);
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
            case "words":
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
    public void mathQuestion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this, R.style.CustomAlertDialog);
        View dialogView = getLayoutInflater().inflate(R.layout.math_dialog, null);
        builder.setView(dialogView).setCancelable(false);
        dialog = builder.show();

        currentExercise = exercisesContainer.getMathExercises().get(0);
        exercisesContainer.getMathExercises().remove(0);

        TextView question = dialogView.findViewById(R.id.exerciseMath);
        mathAnswer = dialogView.findViewById(R.id.answerMath);

        question.setText(currentExercise.getQuestion());

        Button answer_btn = dialogView.findViewById(R.id.mathAnswerBtn);
        TextView[] pad = {dialogView.findViewById(R.id.tv_m1),
                dialogView.findViewById(R.id.tv_m2),
                dialogView.findViewById(R.id.tv_m3),
                dialogView.findViewById(R.id.tv_m4),
                dialogView.findViewById(R.id.tv_m5),
                dialogView.findViewById(R.id.tv_m6),
                dialogView.findViewById(R.id.tv_m7),
                dialogView.findViewById(R.id.tv_m8),
                dialogView.findViewById(R.id.tv_m9),
                dialogView.findViewById(R.id.tv_m0),
                dialogView.findViewById(R.id.tv_mBack),
                dialogView.findViewById(R.id.tv_mClear)};

        timer = new Timer(); //timer round
        questionTimer();

        answer_btn.setOnClickListener(new answerBtnClickListener());

        for(int i=0;i<12;i++){
            pad[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String padString = ((TextView) v).getText().toString();
                    String tmp = mathAnswer.getText().toString();
                    if(getResources().getString(R.string.clr).equals(padString)){
                        mathAnswer.setText("");
                    }
                    else if(getResources().getString(R.string.del).equals(padString)){
                        tmp = mathAnswer.getText().toString();
                        if(tmp.length() != 0) {
                            tmp = tmp.substring(0, tmp.length() - 1);
                            mathAnswer.setText(tmp);
                        }
                    }
                    else if(padString.equals("0")){
                        if(tmp.length() != 0) {
                            tmp += padString;
                            mathAnswer.setText(tmp);
                        }
                    }
                    else {
                        tmp += padString;
                        mathAnswer.setText(tmp);
                    }
                }
            });
        }

        startTimer(dialogView);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void citiesQuestion()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this, R.style.CustomAlertDialog);
        View dialogView = getLayoutInflater().inflate(R.layout.cities_dialog, null);
        builder.setView(dialogView).setCancelable(false);
        dialog = builder.show();

        currentExercise = exercisesContainer.getCitiesExercises().get(0);
        exercisesContainer.getCitiesExercises().remove(0);

        TextView question = dialogView.findViewById(R.id.exerciseCities);
        ImageView flag = dialogView.findViewById(R.id.flagIMG);

        flag.setImageResource(currentExercise.getFlagID());

        final Button[] btn = {dialogView.findViewById(R.id.tv_c00),
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

        timer = new Timer(); //timer round
        questionTimer();

        for(int i=0;i<4;i++){
            btn[i].setOnClickListener(new answerBtnClickListener());
        }

        startTimer(dialogView);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sentenceQuestion()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this, R.style.CustomAlertDialog);
        View dialogView = getLayoutInflater().inflate(R.layout.cities_dialog, null);
        builder.setView(dialogView).setCancelable(false);
        dialog = builder.show();

        currentExercise = exercisesContainer.getSentenceExercises().get(0);
        exercisesContainer.getSentenceExercises().remove(0);

        TextView question = dialogView.findViewById(R.id.exerciseCities);

        final Button[] btn = {dialogView.findViewById(R.id.tv_c00),
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

        timer = new Timer(); //timer round
        questionTimer();

        for(int i=0;i<4;i++){
            btn[i].setOnClickListener(new answerBtnClickListener());
        }

        startTimer(dialogView);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void wordsQuestion()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this, R.style.CustomAlertDialog);
        View dialogView = getLayoutInflater().inflate(R.layout.complete_the_latters_dialog, null);
        builder.setView(dialogView).setCancelable(false);
        dialog = builder.show();

        currentExercise = exercisesContainer.getWordExercises().get(0);
        exercisesContainer.getWordExercises().remove(0);

        TextView definition = dialogView.findViewById(R.id.exerciseComplete);
        definition.setText(currentExercise.getDefinition());
        final String question = currentExercise.getQuestion().toString();

        final Button[] letter = {dialogView.findViewById(R.id.tv00),
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

        answer_blank = new ArrayList<>();
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

        timer = new Timer(); //timer round
        questionTimer();

        Button ans_btn = dialogView.findViewById(R.id.wordAnswerBtn);
        ans_btn.setOnClickListener(new answerBtnClickListener());

        startTimer(dialogView);
    }

    class answerBtnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String tmp = "";

            if(currentExercise instanceof MathExercise) {
                tmp = mathAnswer.getText().toString();
            }
            else if (currentExercise instanceof CitiesExercise || currentExercise instanceof SentenceExercise){
                tmp = ((TextView)v).getText().toString();
            }
            else if (currentExercise instanceof WordExercise){
                int j =0;
                String question = currentExercise.getQuestion();
                int question_size = question.length();
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
            }

            if (tmp.length() != 0) {
                if (tmp.equals(currentExercise.getAnswer())) {
                    rightAnswer();
                    scoreCounter += calcScore();
                } else {
                    wrongAnswer();
                    strikes++;
                    switch (strikes)
                    {
                        case 3:
                            heart1.clearAnimation();
                            heart1.setVisibility(View.INVISIBLE);
                            break;
                        case 2:
                            heart2.clearAnimation();
                            heart2.setVisibility(View.INVISIBLE);
                            break;
                        case 1:
                            heart3.clearAnimation();
                            heart3.setVisibility(View.INVISIBLE);
                            break;
                    }
                }

                dialog.dismiss();
                countDownTimer.cancel();
                timer.cancel();
                timeLeftInMillis = temp;
                round.setText(getString(R.string.round) + " " + roundsCounter);
                score.setText(getString(R.string.score) + " " + scoreCounter);
                if (roundsCounter > NUM_OF_ROUNDS || strikes >= 3) {
                    endGame();
                }
            }
        }
    }

    void questionTimer(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                dialog.dismiss();
                timer.cancel();
                new Thread() {
                    @Override
                    public void run() {
                        PlayActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this, R.style.BonusDialog);
                                View dialogView = getLayoutInflater().inflate(R.layout.time_is_up_dialog, null);
                                builder.setView(dialogView).setCancelable(false);
                                dialog = builder.show();
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        dialog.dismiss();
                                    }
                                }, 1500);
                                playSound(R.raw.out_of_time);
                                round.setText(getString(R.string.round) + " " + roundsCounter);
                                score.setText(getString(R.string.score) + " " + scoreCounter);
                                strikes++;
                                switch (strikes)
                                {
                                    case 3:
                                        heart1.clearAnimation();
                                        heart1.setVisibility(View.INVISIBLE);
                                        break;
                                    case 2:
                                        heart2.clearAnimation();
                                        heart2.setVisibility(View.INVISIBLE);
                                        break;
                                    case 1:
                                        heart3.clearAnimation();
                                        heart3.setVisibility(View.INVISIBLE);
                                        break;
                                }
                                if (roundsCounter > NUM_OF_ROUNDS || strikes >= 3) {
                                    endGame();
                                }

                            }
                        });
                    }
                }.start();
            }
        }, timeLeftInMillis);
    }

    public void showBonus()
    {
        bonus = true;
        AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this, R.style.BonusDialog);
        View dialogView = getLayoutInflater().inflate(R.layout.bonus_dialog, null);
        builder.setView(dialogView).setCancelable(false);
        dialog = builder.show();
        playSound(R.raw.ta_da);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                boardFlip();
                round.setText(getString(R.string.round) + " " + roundsCounter);
                bonusLayout = findViewById(R.id.bonusLayout);
                bonusLayout.setVisibility(View.VISIBLE);
                spinBtn.setVisibility(View.INVISIBLE);
                Button spinBonusBtn = findViewById(R.id.spinBonusBtn);
                spinBonusBtn.setOnClickListener(new spinWheelClickListener());
                Button leaveBtn = findViewById(R.id.leaveBtn);
                leaveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bonusLayout.setVisibility(View.INVISIBLE);
                        spinBtn.setVisibility(View.VISIBLE);
                        bonus = false;

                        boardFlip();
                    }
                });
            }
        }, 2000);
    }

    class spinWheelClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if (blnButtonRotation) {
                int btnId = ((Button) v).getId();
                int ran = new Random().nextInt(360) + 3600;
                long degrees;
                ImageView roulette;
                if (btnId == R.id.spinBtn) {
                    roundsCounter++;
                    degrees = lngDegrees;
                    roulette = imageRoulette;
                    lngDegrees = (degrees + ((long) ran)) % 360;
                }
                else if (btnId == R.id.spinBonusBtn) {
                    degrees = lngDegrees2;
                    roulette = bonusRoulette;
                    bonusLayout.setVisibility(View.INVISIBLE);
                    spinBtn.setVisibility(View.VISIBLE);
                    lngDegrees2 = (degrees + ((long) ran)) % 360;
                }
                else {
                    return;
                }
                RotateAnimation rotateAnimation = new RotateAnimation((float) degrees, (float)
                        (degrees + ((long) ran)), 1, 0.5f, 1, 0.5f);
                rotateAnimation.setDuration((long) ran);
                rotateAnimation.setFillAfter(true);
                rotateAnimation.setInterpolator(new DecelerateInterpolator());
                rotateAnimation.setAnimationListener(PlayActivity.this);
                roulette.setAnimation(rotateAnimation);
                roulette.startAnimation(rotateAnimation);
            }
        }
    }

    void boardFlip(){
        ImageView imgV1;
        ImageView imgV2;
        if(isFirstImage) {
            imgV1 = imageRoulette;
            imgV2 = bonusRoulette;
        }
        else{
            imgV2 = imageRoulette;
            imgV1 = bonusRoulette;
        }
        isFirstImage = !isFirstImage;
        imgV1.setRotationX(0);
        imgV1.animate().withLayer()
                .rotationY(90)
                .rotation(0)
                .setDuration(300)
                .withEndAction(
                        new Runnable() {
                            @Override public void run() {

                                imgV1.setVisibility(View.INVISIBLE);
                                imgV2.setVisibility(View.VISIBLE);

                                // second quarter turn
                                imgV2.setRotationY(-90);
                                imgV2.setRotationX(0);
                                imgV2.animate().withLayer()
                                        .rotationY(0)
                                        .setDuration(300)
                                        .start();
                            }
                        }
                ).start();
    }

    public void rightAnswer()
    {
        Random rand = new Random();
        int num = rand.nextInt(4) + 1;
        AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this, R.style.BonusDialog);
        View dialogView = getLayoutInflater().inflate(R.layout.right_answer_dialog, null);
        builder.setView(dialogView).setCancelable(false);
        ImageView imageView = dialogView.findViewById(R.id.right_answer_pic);
        switch (num)
        {
            case 1:
                imageView.setImageResource(R.drawable.right_answer);
                break;
            case 2:
                imageView.setImageResource(R.drawable.excellent);
                break;
            case 3:
                imageView.setImageResource(R.drawable.fantastic);
                break;
            case 4:
                imageView.setImageResource(R.drawable.great);
                break;
        }
        final AlertDialog dialog = builder.show();
        playSound(R.raw.right_answer);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 2000);
    }

    public void wrongAnswer()
    {
        Random rand = new Random();
        int num = rand.nextInt(4) + 1;
        AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this, R.style.BonusDialog);
        View dialogView = getLayoutInflater().inflate(R.layout.wrong_answer_dialog, null);
        builder.setView(dialogView).setCancelable(false);
        ImageView imageView = dialogView.findViewById(R.id.wrong_answer_pic);
        switch (num)
        {
            case 1:
                imageView.setImageResource(R.drawable.wrong_answer);
                break;
            case 2:
                imageView.setImageResource(R.drawable.mistake);
                break;
            case 3:
                imageView.setImageResource(R.drawable.not_answer);
                break;
            case 4:
                imageView.setImageResource(R.drawable.improve);
                break;
        }
        final AlertDialog dialog = builder.show();
        playSound(R.raw.wrong_answer);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 2000);
    }

    int calcScore(){
        double precent = (double)timeLeftInMillis/(double)temp;
        return scoreToAdd + (int)floor(precent*scoreRange);
    }

    void endGame(){
        SharedPreferences sharedPref = this.getSharedPreferences("gameData", this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Integer size = sharedPref.getInt("size", 0);
        UserInfo score = new UserInfo(bitmap, getIntent().getStringExtra("Name"), scoreCounter);
        size++;
        System.out.println(score.toString());
        editor.putString(size.toString(), score.toString());
        editor.putInt("size", size);
        editor.commit();
        int highScore = sharedPref.getInt("HighScore", 0);
        if(strikes >= 3) //end game by 3 strikes
        {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this, R.style.BonusDialog);
                    View dialogView = getLayoutInflater().inflate(R.layout.end_game_by_strikes_dialog, null);
                    builder.setView(dialogView).setCancelable(false);
                    TextView textView = dialogView.findViewById(R.id.score_strikes);
                    textView.setText(getResources().getString(R.string.your_score) + scoreCounter + ".");
                    dialog = builder.show();
                }
            }, 2000);
        }
        else //game over after 10 rounds
        {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this, R.style.BonusDialog);
                    View dialogView = getLayoutInflater().inflate(R.layout.game_over_by_victory_dialog, null);
                    builder.setView(dialogView).setCancelable(false);
                    TextView textView = dialogView.findViewById(R.id.score_vic);
                    textView.setText(getResources().getString(R.string.your_score) + scoreCounter + ".");
                    dialog = builder.show();
                    mediaPlayer = MediaPlayer.create(PlayActivity.this, R.raw.end_game_sound);
                    mediaPlayer.start();
                }
            }, 2000);
        }
        if (scoreCounter >= highScore) //new high score
        {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                    AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this, R.style.BonusDialog);
                    View dialogView = getLayoutInflater().inflate(R.layout.high_score_dialog, null);
                    builder.setView(dialogView).setCancelable(false);
                    builder.show();
                    mediaPlayer = MediaPlayer.create(PlayActivity.this, R.raw.yahoo);
                    mediaPlayer.start();
                }
            }, 7000);
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(PlayActivity.this, MainActivity.class);
                intent.putExtra("Name", getIntent().getStringExtra("Name"));
                startActivity(intent);
            }
        }, 10000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_change_player){
            AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this, R.style.CustomAlertDialog);
            View dialogView = getLayoutInflater().inflate(R.layout.stop_game_layout, null);
            builder.setView(dialogView).setCancelable(false);
            final AlertDialog dialog = builder.show();
            Button okBtn = dialogView.findViewById(R.id.ok);
            okBtn.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    Intent intent = new Intent(PlayActivity.this, FirstActivity.class);
                    startActivity(intent);
                }});
            Button cancelBtn = dialogView.findViewById(R.id.cancle);
            cancelBtn.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    dialog.dismiss();
                }});
        }
        else if(id == R.id.action_sound_toggle){

        }
        else if (id == R.id.action_how_to_play)
        {
            Intent intent = new Intent(PlayActivity.this, WalkTroughActivity.class);
            intent.putExtra("from", MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    void playSound(int rawID){
        if(isAppPaused){
            return;
        }
        mediaPlayer = new MediaPlayer();
        mediaPlayer = MediaPlayer.create(PlayActivity.this, rawID);
        mediaPlayer.start();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this, R.style.CustomAlertDialog);
        View dialogView = getLayoutInflater().inflate(R.layout.stop_game_layout, null);
        builder.setView(dialogView).setCancelable(false);
        final AlertDialog dialog = builder.show();
        Button okBtn = dialogView.findViewById(R.id.ok);
        okBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(PlayActivity.this, MainActivity.class);
                intent.putExtra("Name", getIntent().getStringExtra("Name"));
                startActivity(intent);
            }});
        Button cancelBtn = dialogView.findViewById(R.id.cancle);
        cancelBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }});
        okBtn.startAnimation(animation);
        cancelBtn.startAnimation(animation);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isAppPaused = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isAppPaused = false;
    }
}