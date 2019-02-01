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
    ImageView imageRoulette, bonusRoulette;
    TextView round, score, levelTV;
    CountDownTimer countDownTimer;
    String level, type;
    Button spinBtn;
    SharedPreferences sp;
    Bitmap bitmap;
    MediaPlayer mediaPlayer;

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

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
            bitmap = extras.getParcelable("user_pic");
        sp = getSharedPreferences("score detail", MODE_PRIVATE);
        exercisesContainer = new ExercisesContainer(this);
        levelTV = findViewById(R.id.level_TV);
        level = getIntent().getStringExtra("Level");
        switch (level)
        {
            case "Easy":
                scoreToAdd = 10;          //add 10-20 points for right answer
                scoreRange = 10;
                timeLeftInMillis = 60000; //1 minute
                temp = timeLeftInMillis;
                levelTV.setText(getString(R.string.level) + getString(R.string.easy));
                break;
            case "Medium":
                scoreToAdd = 20;          //add 20-50 points for right answer
                scoreRange = 30;
                timeLeftInMillis = 30000; //30 seconds
                temp = timeLeftInMillis;
                levelTV.setText(getString(R.string.level) + getString(R.string.medium));
                break;
            case "Hard":
                scoreToAdd = 50;          //add 50-100 points for right answer
                scoreRange = 50;
                timeLeftInMillis = 10000; //10 seconds
                temp = timeLeftInMillis;
                levelTV.setText(getString(R.string.level) + getString(R.string.hard));
                break;
        }

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
        else //bonus wheel spin
        {
            String string = String.valueOf((int)(((double)4)
                    - floor(((double)lngDegrees2) / (360.0d / ((double)4)))));
            int pos = Integer.parseInt(string);
            pos--;
            switch (pos)
            {
                case 0:
                    scoreCounter += 100;
                    score.setText(getString(R.string.score) + " " + scoreCounter);
                    rightAnswer();
                    Toast.makeText(PlayActivity.this, "Blue", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    scoreCounter =0;
                    score.setText(getString(R.string.score) + " " + scoreCounter);
                    wrongAnswer();
                    Toast.makeText(PlayActivity.this, "Green", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    if(scoreCounter < 100){
                        scoreCounter = 0;
                    }
                    else {
                        scoreCounter -= 100;
                    }
                    wrongAnswer();
                    score.setText(getString(R.string.score) + " " + scoreCounter);
                    Toast.makeText(PlayActivity.this, "Orange", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    scoreCounter *= 2;
                    rightAnswer();
                    score.setText(getString(R.string.score) + " " + scoreCounter);
                    Toast.makeText(PlayActivity.this, "Red", Toast.LENGTH_SHORT).show();
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

                                Toast.makeText(PlayActivity.this, "Sorry, you run out of time", Toast.LENGTH_SHORT).show();
                                mediaPlayer = new MediaPlayer();
                                mediaPlayer = MediaPlayer.create(PlayActivity.this, R.raw.out_of_time);
                                mediaPlayer.start();
                                round.setText(getString(R.string.round) + " " + roundsCounter);
                                score.setText(getString(R.string.score) + " " + scoreCounter);
                                strikes++;
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
        mediaPlayer = new MediaPlayer();
        mediaPlayer = MediaPlayer.create(this, R.raw.ta_da);
        AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this, R.style.BonusDialog);
        View dialogView = getLayoutInflater().inflate(R.layout.bonus_dialog, null);
        builder.setView(dialogView).setCancelable(false);
        dialog = builder.show();
        mediaPlayer.start();
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
        AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this, R.style.BonusDialog);
        View dialogView = getLayoutInflater().inflate(R.layout.right_answer_dialog, null);
        builder.setView(dialogView).setCancelable(false);
        final AlertDialog dialog = builder.show();
        mediaPlayer = new MediaPlayer();
        mediaPlayer = MediaPlayer.create(this, R.raw.right_answer);
        mediaPlayer.start();
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
        AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this, R.style.BonusDialog);
        View dialogView = getLayoutInflater().inflate(R.layout.wrong_answer_dialog, null);
        builder.setView(dialogView).setCancelable(false);
        final AlertDialog dialog = builder.show();
        mediaPlayer = new MediaPlayer();
        mediaPlayer = MediaPlayer.create(this, R.raw.wrong_answer);
        mediaPlayer.start();
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

        if(strikes >= 3) {
            Toast.makeText(this, "Strike out - Your score is: " + scoreCounter + " Animation with aplouds", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Finished game - Your score is: " + scoreCounter + " Animation with aplouds", Toast.LENGTH_LONG).show();

        }
        Intent intent = new Intent(PlayActivity.this, MainActivity.class);
        intent.putExtra("Name", getIntent().getStringExtra("Name"));
        startActivity(intent);
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

    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}