package tsahi.and.kostia.spinandlearn;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.github.jinatonic.confetti.CommonConfetti;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static android.view.HapticFeedbackConstants.CONTEXT_CLICK;
import static java.lang.Math.floor;

public class PlayActivity extends AppCompatActivity implements Animation.AnimationListener{

    int NUM_OF_ROUNDS = 10;
    int roundsCounter = 1, scoreCounter = 0, scoreToAdd, scoreRange;
    boolean blnButtonRotation = true, bonus, isFirstImage = true;
    int intNumber = 10;
    long lngDegrees = 0, lngDegrees2 = 0, timeLeftInMillis, temp;
    ExercisesContainer exercisesContainer;
    ImageView imageRoulette, bonusRoulette;
    TextView round, score;
    CountDownTimer countDownTimer;
    String level, type;
    Button spinBtn;
    Bitmap bitmap;
    MediaPlayer mediaPlayer;
    Animation animation;
    TextView mathAnswer;
    ArrayList<TextView> answer_blank;
    int blankIndex, blankSize;
    float scale;
    float distanceRoulette ;
    float distanceBonus;
    int strikes;
    Exercises currentExercise;
    Timer timer;
    AlertDialog dialog;
    LinearLayout bonusLayout;
    GlobalVar global;
    ImageView[] hearts = new ImageView[6];
    View dialogView;
    Animation scaleUp;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        global = ((GlobalVar) this.getApplication());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        exercisesContainer = new ExercisesContainer(PlayActivity.this);
        global.setAppPaused(false);
        SharedPreferences sharedPref = this.getSharedPreferences("sound", this.MODE_PRIVATE);
        global.setMute(sharedPref.getBoolean("mute", false));
        Bundle extras = getIntent().getExtras();
        if (extras != null)
            bitmap = extras.getParcelable("user_pic");
        level = getIntent().getStringExtra("Level");
        AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this, R.style.BonusDialog);
        dialogView = getLayoutInflater().inflate(R.layout.level_dialog, null);
        builder.setView(dialogView).setCancelable(false);
        ImageView imageView = dialogView.findViewById(R.id.level_source);
        scaleUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.level_scale_up);
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
        animation = AnimationUtils.loadAnimation(getApplicationContext() ,R.anim.button_anim);
        hearts[0] = findViewById(R.id.heart1);
        hearts[1] = findViewById(R.id.heart2);
        hearts[2] = findViewById(R.id.heart3);
        hearts[3] = findViewById(R.id.heart4);
        hearts[4] = findViewById(R.id.heart5);
        hearts[5] = findViewById(R.id.heart6);
        strikes = 3;
        imageRoulette = findViewById(R.id.ImageView01);
        bonusRoulette = findViewById(R.id.ImageView02);
        bonusRoulette.setVisibility(View.INVISIBLE);
        scale = getApplicationContext().getResources().getDisplayMetrics().density;
        distanceRoulette = imageRoulette.getCameraDistance() * (scale + (scale / 3));
        distanceBonus = imageRoulette.getCameraDistance() * (scale + (scale / 3));
        imageRoulette.setCameraDistance(distanceRoulette);
        bonusRoulette.setCameraDistance(distanceBonus);
        round = findViewById(R.id.round);
        round.setText(getString(R.string.round) + " 1 " + getResources().getString(R.string.out_of) + " 10");
        score = findViewById(R.id.score);
        score.setText(getString(R.string.score) + " " + "0");
        spinBtn = findViewById(R.id.spinBtn);
        spinBtn.setOnClickListener(new spinWheelClickListener());
        Thread animations = new Thread(){
            public void run(){
                for(int i=0;i<3;i++){
                    hearts[i].setAnimation(animation);
                }
                spinBtn.startAnimation(animation);
            }
        };
        animations.start();
        Typeface typeface = ResourcesCompat.getFont(this, R.font.stephia);;
        if (Locale.getDefault().toString().equals("iw_IL"))
        {
            typeface = ResourcesCompat.getFont(this, R.font.abraham);
        }
        else if(Locale.getDefault().toString().equals("ru_RU")){
            typeface = ResourcesCompat.getFont(this, R.font.wagnasty);
        }
        round.setTypeface(typeface);
        score.setTypeface(typeface);
        spinBtn.setTypeface(typeface);
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
            if(pos != 2) {
                playSound(R.raw.clock);
                if(mediaPlayer != null) {
                    mediaPlayer.setLooping(true);
                }
            }
            switch (pos)
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
            }
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
                    strikes++;
                    heartsInvalidate();
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
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                spinBtn.setEnabled(true);
                //spinBtn.startAnimation(animation); --> bug
            }
        }, 500);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    public void endBonus(int result)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this, R.style.BonusDialog);
        dialogView = getLayoutInflater().inflate(R.layout.bonus_result, null);
        builder.setView(dialogView).setCancelable(false);
        ImageView imageView = dialogView.findViewById(R.id.bonus_result);
        switch (result)
        {
            case 0:
                imageView.setImageResource(R.drawable.plus_100);
                playSound(R.raw.yahoo);
                break;
            case 1:
                imageView.setImageResource(R.drawable.zero_score);
                playSound(R.raw.wrong_answer);
                break;
            case 2:
                imageView.setImageResource(R.drawable.extra_life);
                playSound(R.raw.yahoo);
                break;
            case 3:
                imageView.setImageResource(R.drawable.minus_100);
                playSound(R.raw.wrong_answer);
                break;
            case 4:
                imageView.setImageResource(R.drawable.double_score);
                playSound(R.raw.yahoo);
                break;
        }
        final AlertDialog dialog = builder.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                global.startMusic(PlayActivity.this);
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
        dialogView = getLayoutInflater().inflate(R.layout.math_dialog, null);
        builder.setView(dialogView).setCancelable(false);
        dialog = builder.show();
        currentExercise = new MathExercise();
        TextView question = dialogView.findViewById(R.id.exerciseMath);
        mathAnswer = dialogView.findViewById(R.id.answerMath);
        question.setText(currentExercise.getQuestion());
        Button answer_btn = dialogView.findViewById(R.id.mathAnswerBtn);
        TextView[] pad = {
                dialogView.findViewById(R.id.tv_m1),
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
            pad[i].setHapticFeedbackEnabled(true);
            pad[i].performHapticFeedback(CONTEXT_CLICK);
            pad[i].setOnClickListener(new mathBtnClickListener());
        }
        Typeface typeface = ResourcesCompat.getFont(this, R.font.stephia);;
        if (Locale.getDefault().toString().equals("iw_IL"))
        {
            typeface = ResourcesCompat.getFont(this, R.font.abraham);
        }
        else if(Locale.getDefault().toString().equals("ru_RU")){
            typeface = ResourcesCompat.getFont(this, R.font.wagnasty);
        }
            TextView time = dialogView.findViewById(R.id.timeMath);
            time.setTypeface(typeface);
            question.setTypeface(typeface);
            mathAnswer.setTypeface(typeface);
            for (int i=0;i<12;i++)
                pad[i].setTypeface(typeface);
            answer_btn.setTypeface(typeface);
        startTimer(dialogView);
    }

    class mathBtnClickListener implements View.OnClickListener{

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
                if(tmp.length() != 0 && tmp.length() <= 5) {
                    tmp += padString;
                    mathAnswer.setText(tmp);
                }
            }
            else {
                if(tmp.length() <= 5) {
                    tmp += padString;
                    mathAnswer.setText(tmp);
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void citiesQuestion()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this, R.style.CustomAlertDialog);
        dialogView = getLayoutInflater().inflate(R.layout.cities_dialog, null);
        builder.setView(dialogView).setCancelable(false);
        dialog = builder.show();
        currentExercise = exercisesContainer.getCitiesExercises().get(0);
        exercisesContainer.getCitiesExercises().remove(0);

        TextView question = dialogView.findViewById(R.id.exerciseCities);
        ImageView flag = dialogView.findViewById(R.id.flagIMG);

        flag.setImageResource(currentExercise.getFlagID());

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

            if (answers.get(tmp).length() > 10){
                btn[i].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                //btn[i].setHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics()));
                //btn[i].setWidth((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, getResources().getDisplayMetrics()));
            }
            btn[i].setText(answers.get(tmp));
            answers.set(tmp, "");
        }

        timer = new Timer(); //timer round
        questionTimer();

        for(int i=0;i<4;i++){
            btn[i].setOnClickListener(new answerBtnClickListener());
        }
        Typeface typeface = ResourcesCompat.getFont(this, R.font.stephia);;
        if (Locale.getDefault().toString().equals("iw_IL"))
        {
            typeface = ResourcesCompat.getFont(this, R.font.abraham);
        }
        else if(Locale.getDefault().toString().equals("ru_RU")){
            typeface = ResourcesCompat.getFont(this, R.font.wagnasty);
        }
            TextView timeCities = dialogView.findViewById(R.id.timeCities);
            timeCities.setTypeface(typeface);
            question.setTypeface(typeface);
            for (int i=0;i<4;i++)
                btn[i].setTypeface(typeface);
        startTimer(dialogView);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sentenceQuestion()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this, R.style.CustomAlertDialog);
        dialogView = getLayoutInflater().inflate(R.layout.cities_dialog, null);
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
            if (answers.get(tmp).length() > 10){
                btn[i].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
            }
            answers.set(tmp, "");
        }
        timer = new Timer(); //timer round
        questionTimer();
        for(int i=0;i<4;i++){
            btn[i].setOnClickListener(new answerBtnClickListener());
        }
        Typeface typeface = ResourcesCompat.getFont(this, R.font.stephia);;
        if (Locale.getDefault().toString().equals("iw_IL"))
        {
            typeface = ResourcesCompat.getFont(this, R.font.abraham);
        }
        else if(Locale.getDefault().toString().equals("ru_RU")){
            typeface = ResourcesCompat.getFont(this, R.font.wagnasty);
        }
            TextView timeCities = dialogView.findViewById(R.id.timeCities);
            timeCities.setTypeface(typeface);
            question.setTypeface(typeface);
            for (int i=0;i<4;i++)
                btn[i].setTypeface(typeface);
        startTimer(dialogView);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void wordsQuestion()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this, R.style.CustomAlertDialog);
        dialogView = getLayoutInflater().inflate(R.layout.complete_the_latters_dialog, null);
        builder.setView(dialogView).setCancelable(false);
        dialog = builder.show();
        currentExercise = exercisesContainer.getWordExercises().get(0);
        exercisesContainer.getWordExercises().remove(0);
        TextView definition = dialogView.findViewById(R.id.exerciseComplete);
        definition.setText(currentExercise.getDefinition());
        final String question = currentExercise.getQuestion().toString();
        final Button[] letter = {
                dialogView.findViewById(R.id.tv00),
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
                dialogView.findViewById(R.id.tv13)
        };
        ArrayList<Character> letterBank = currentExercise.getLetterBank();
        for(int i=0;i<14;i++){
            int tmp = (int)(Math.random()*14);
            while(letterBank.get(tmp).equals('0')){
                tmp = (int)(Math.random()*14);
            }
            letter[i].setText(letterBank.get(tmp).toString());
            letter[i].setHapticFeedbackEnabled(true);
            letter[i].performHapticFeedback(CONTEXT_CLICK);
            letterBank.set(tmp, '0');
        }
        answer_blank = new ArrayList<>();
        final LinearLayout answer_container = dialogView.findViewById(R.id.answerLayout);
        final int question_size = question.length();
        for(int i=0;i<question_size;i++){
            TextView tmp = new TextView(dialogView.getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.MATCH_PARENT, 1
            );
            params.setMargins(1,1,1,1);
            tmp.setLayoutParams(params);
            tmp.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            tmp.setGravity(Gravity.CENTER);
            tmp.setBackground(getResources().getDrawable(R.drawable.words_design));


            Typeface typeface = ResourcesCompat.getFont(this, R.font.stephia);;
            if (Locale.getDefault().toString().equals("iw_IL"))
            {
                typeface = ResourcesCompat.getFont(this, R.font.abraham);
            }
            else if(Locale.getDefault().toString().equals("ru_RU")){
                typeface = ResourcesCompat.getFont(this, R.font.wagnasty);
            }
            tmp.setTypeface(typeface);

            tmp.setText(((Character)question.charAt(i)).toString());
            if(question.charAt(i) == '_'){
                tmp.setBackground(getResources().getDrawable(R.drawable.word_design_answer));
                tmp.setHapticFeedbackEnabled(true);
                tmp.performHapticFeedback(CONTEXT_CLICK);
                answer_blank.add(tmp);
            }
            answer_container.addView(tmp);
        }
        blankIndex = 0;
        blankSize = answer_blank.size();
        for(int i=0;i<14;i++){
            letter[i].setOnClickListener(new letterBtnClickListener());
        }
        for(int i=0;i<blankSize;i++){
            answer_blank.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String tmp = ((TextView) v).getText().toString();
                    System.out.println(blankIndex);
                    if(!tmp.equals("_")){
                        ((TextView) v).setText("_");
                        for(int j=0;j<14;j++){
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
        Typeface typeface = ResourcesCompat.getFont(this, R.font.stephia);;
        if (Locale.getDefault().toString().equals("iw_IL"))
        {
            typeface = ResourcesCompat.getFont(this, R.font.abraham);
        }
        else if(Locale.getDefault().toString().equals("ru_RU")){
            typeface = ResourcesCompat.getFont(this, R.font.wagnasty);
        }
            TextView timeComplete = dialogView.findViewById(R.id.timeComplete);
            timeComplete.setTypeface(typeface);
            definition.setTypeface(typeface);
            ans_btn.setTypeface(typeface);
            for (int i=0;i<14;i++)
                letter[i].setTypeface(typeface);
        startTimer(dialogView);
    }

    class letterBtnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if(blankIndex < blankSize){
                for(int k = 0;k<blankSize;k++){
                    if(answer_blank.get(k).getText().equals("_")){
                        blankIndex = k;
                        k=blankSize;
                    }
                }
                String tmp = ((TextView) v).getText().toString();
                if(answer_blank.get(blankIndex).getText().equals("_")) {
                    answer_blank.get(blankIndex).setText(tmp);
                    v.setVisibility(View.INVISIBLE);
                }
            }
        }
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
                    strikes--;
                    heartsInvalidate();
                }

                dialog.dismiss();
                countDownTimer.cancel();
                timer.cancel();
                timeLeftInMillis = temp;
                if(roundsCounter <= NUM_OF_ROUNDS) {
                    round.setText(getString(R.string.round) + " " + roundsCounter + " " + getResources().getString(R.string.out_of) + " 10");
                }
                score.setText(getString(R.string.score) + " " + scoreCounter);
                if (roundsCounter > NUM_OF_ROUNDS || strikes == 0) {
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
                                dialogView = getLayoutInflater().inflate(R.layout.time_is_up_dialog, null);
                                builder.setView(dialogView).setCancelable(false);
                                dialog = builder.show();
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        dialog.dismiss();
                                        stopSound();
                                        global.startMusic(PlayActivity.this);
                                    }
                                }, 1500);
                                stopSound();
                                playSound(R.raw.out_of_time);
                                if(roundsCounter <= NUM_OF_ROUNDS) {
                                    round.setText(getString(R.string.round) + " " + roundsCounter + " " + getResources().getString(R.string.out_of) + " 10");
                                }
                                score.setText(getString(R.string.score) + " " + scoreCounter);
                                strikes--;
                                heartsInvalidate();
                                if (roundsCounter > NUM_OF_ROUNDS || strikes == 0) {
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
        dialogView = getLayoutInflater().inflate(R.layout.bonus_dialog, null);
        builder.setView(dialogView).setCancelable(false);
        dialog = builder.show();
        playSound(R.raw.ta_da);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                boardFlip();
                if(roundsCounter <= NUM_OF_ROUNDS) {
                    round.setText(getString(R.string.round) + " " + roundsCounter + " " + getResources().getString(R.string.out_of) + " 10");
                }
                bonusLayout = findViewById(R.id.bonusLayout);
                bonusLayout.setVisibility(View.VISIBLE);
                spinBtn.clearAnimation();
                spinBtn.setVisibility(View.INVISIBLE);
                Button spinBonusBtn = findViewById(R.id.spinBonusBtn);
                spinBonusBtn.setOnClickListener(new spinWheelClickListener());
                Button leaveBtn = findViewById(R.id.leaveBtn);
                leaveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        stopSound();
                        global.startMusic(PlayActivity.this);
                        bonusLayout.setVisibility(View.INVISIBLE);
                        spinBtn.setVisibility(View.VISIBLE);
                        spinBtn.startAnimation(animation);
                        spinBtn.setEnabled(true);
                        bonus = false;
                        boardFlip();
                    }
                });
                spinBonusBtn.startAnimation(animation);
                leaveBtn.startAnimation(animation);
                spinBtn.setEnabled(false);
                if(!global.isMusicMute()) {
                    playSound(R.raw.viva_las_vegas);
                    if (mediaPlayer != null) {
                        mediaPlayer.setLooping(true);
                    }
                }
                Typeface typeface = ResourcesCompat.getFont(PlayActivity.this, R.font.stephia);;
                if (Locale.getDefault().toString().equals("iw_IL"))
                {
                    typeface = ResourcesCompat.getFont(PlayActivity.this, R.font.abraham);
                }
                else if(Locale.getDefault().toString().equals("ru_RU")){
                    typeface = ResourcesCompat.getFont(PlayActivity.this, R.font.wagnasty);
                }
                    spinBonusBtn.setTypeface(typeface);
                    leaveBtn.setTypeface(typeface);
            }
        }, 2000);
    }

    class spinWheelClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (blnButtonRotation) {
                        int btnId = ((Button) v).getId();
                        long ran = new Random().nextInt(360) + 3600;
                        long degrees;
                        ImageView roulette;
                        if (btnId == R.id.spinBtn) {
                            roundsCounter++;
                            degrees = lngDegrees;
                            roulette = imageRoulette;
                            lngDegrees = (degrees + (ran)) % 360;
                            spinBtn.setEnabled(false);
                        } else if (btnId == R.id.spinBonusBtn) {
                            degrees = lngDegrees2;
                            roulette = bonusRoulette;
                            bonusLayout.setVisibility(View.INVISIBLE);
                            spinBtn.setVisibility(View.VISIBLE);
                            lngDegrees2 = (degrees + (ran)) % 360;
                        } else {
                            return;
                        }
                        RotateAnimation rotateAnimation = new RotateAnimation((float) degrees, (float)
                                (degrees + (ran)), 1, 0.5f, 1, 0.5f);
                        rotateAnimation.setDuration(ran);
                        rotateAnimation.setFillAfter(true);
                        rotateAnimation.setInterpolator(new DecelerateInterpolator());
                        rotateAnimation.setAnimationListener(PlayActivity.this);
                        roulette.setAnimation(rotateAnimation);
                        roulette.startAnimation(rotateAnimation);
                        stopSound();
                        global.pauseMusic();
                        playSound(R.raw.tic_tic_tic);
                    }
                }
            }, 100);
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
        dialogView = getLayoutInflater().inflate(R.layout.right_answer_dialog, null);
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
                stopSound();
                global.startMusic(PlayActivity.this);
                dialog.dismiss();

            }
        }, 2000);
    }

    public void wrongAnswer()
    {
        Random rand = new Random();
        int num = rand.nextInt(4) + 1;
        AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this, R.style.BonusDialog);
        dialogView = getLayoutInflater().inflate(R.layout.wrong_answer_dialog, null);
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
                stopSound();
                global.startMusic(PlayActivity.this);
                dialog.dismiss();
            }
        }, 3000);
    }

    int calcScore(){
        double percent = (double)timeLeftInMillis/(double)temp;
        return scoreToAdd + (int)floor(percent*scoreRange);
    }

    void endGame() {
        global.pauseMusic();
        SharedPreferences sharedPref = this.getSharedPreferences("gameData", this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Integer size = sharedPref.getInt("size", 0);
        playSound(R.raw.end_game_sound);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
        Date now = Calendar.getInstance().getTime();
        String sndLvl = "";
        if (level.equals("Easy")){
            sndLvl = getResources().getString(R.string.easy);
        }
        else if (level.equals("Medium")){
            sndLvl = getResources().getString(R.string.medium);
        }
        else if (level.equals("Hard")){
            sndLvl = getResources().getString(R.string.hard);
        }
        UserInfo score = new UserInfo(bitmap, getIntent().getStringExtra("Name"), scoreCounter, sndLvl, dateFormat.format(now), timeFormat.format(now));
        size++;
        editor.putString(size.toString(), score.toString());
        editor.putInt("size", size);
        editor.commit();
        int highScore = sharedPref.getInt("HighScore", 0);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this, R.style.BonusDialog);
                LinearLayout buttons;
                TextView textView;
                Button playAgain = null;
                Button mainMenu = null;
                Button nextLevel = null;
                if (strikes == 0) {
                    dialogView = getLayoutInflater().inflate(R.layout.end_game_by_strikes_dialog, null);
                    buttons = dialogView.findViewById(R.id.endGameButtons);
                    playAgain = dialogView.findViewById(R.id.playAgain);
                    mainMenu = dialogView.findViewById(R.id.mainMenu);
                    textView = dialogView.findViewById(R.id.score_strikes);
                    Typeface typeface = ResourcesCompat.getFont(PlayActivity.this, R.font.stephia);;
                    if (Locale.getDefault().toString().equals("iw_IL"))
                    {
                        typeface = ResourcesCompat.getFont(PlayActivity.this, R.font.abraham);
                    }
                    else if(Locale.getDefault().toString().equals("ru_RU")){
                        typeface = ResourcesCompat.getFont(PlayActivity.this, R.font.wagnasty);
                    }
                        textView.setTypeface(typeface);

                } else {
                    dialogView = getLayoutInflater().inflate(R.layout.game_over_by_victory_dialog, null);
                    textView = dialogView.findViewById(R.id.score_vic);
                    Typeface typeface = ResourcesCompat.getFont(PlayActivity.this, R.font.stephia);;
                    if (Locale.getDefault().toString().equals("iw_IL"))
                    {
                        typeface = ResourcesCompat.getFont(PlayActivity.this, R.font.abraham);
                    }
                    else if(Locale.getDefault().toString().equals("ru_RU")){
                        typeface = ResourcesCompat.getFont(PlayActivity.this, R.font.wagnasty);
                    }
                        textView.setTypeface(typeface);

                    if (level.equals("Easy") || level.equals("Medium")) {
                        buttons = dialogView.findViewById(R.id.easy_and_med_panel);
                        playAgain = dialogView.findViewById(R.id.playAgain);
                        mainMenu = dialogView.findViewById(R.id.mainMenu);
                        nextLevel = dialogView.findViewById(R.id.nextLevel);;
                    } else {
                        buttons = dialogView.findViewById(R.id.hardPanel);
                        playAgain = dialogView.findViewById(R.id.playAgainHard);
                        mainMenu = dialogView.findViewById(R.id.mainMenuHard);
                    }
                }
                builder.setView(dialogView).setCancelable(false);
                textView.setText(getResources().getString(R.string.your_score) + " " + scoreCounter + ".");
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        buttons.setVisibility(View.VISIBLE);
                        stopSound();
                        global.startMusic(PlayActivity.this);
                    }
                }, 2500);

                if (buttons.getId() == R.id.easy_and_med_panel) {
                    nextLevel = dialogView.findViewById(R.id.nextLevel);
                }
                playAgain.startAnimation(animation);
                mainMenu.startAnimation(animation);
                if (nextLevel != null) {
                    nextLevel.startAnimation(animation);
                    nextLevel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (level.equals("Easy")) {
                                level = "Medium";
                            } else if (level.equals("Medium")) {
                                level = "Hard";
                            }
                            Intent intent = new Intent(PlayActivity.this, PlayActivity.class);
                            intent.putExtra("Level", level);
                            intent.putExtra("Name", getIntent().getStringExtra("Name"));
                            Bundle extras = new Bundle();
                            extras.putParcelable("user_pic", bitmap);
                            intent.putExtras(extras);
                            startActivity(intent);
                        }
                    });
                }
                playAgain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(PlayActivity.this, PlayActivity.class);
                        intent.putExtra("Level", level);
                        intent.putExtra("Name", getIntent().getStringExtra("Name"));
                        Bundle extras = new Bundle();
                        extras.putParcelable("user_pic", bitmap);
                        intent.putExtras(extras);
                        startActivity(intent);
                    }
                });
                mainMenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(PlayActivity.this, MainActivity.class);
                        intent.putExtra("Name", getIntent().getStringExtra("Name"));
                        startActivity(intent);
                    }
                });
                Typeface typeface = ResourcesCompat.getFont(PlayActivity.this, R.font.stephia);;
                if (Locale.getDefault().toString().equals("iw_IL"))
                {
                    typeface = ResourcesCompat.getFont(PlayActivity.this, R.font.abraham);
                    playAgain.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
                    mainMenu.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
                    if (nextLevel != null)
                        nextLevel.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
                }
                else if(Locale.getDefault().toString().equals("ru_RU")){
                    typeface = ResourcesCompat.getFont(PlayActivity.this, R.font.wagnasty);
                }
                    playAgain.setTypeface(typeface);
                    mainMenu.setTypeface(typeface);
                    if (nextLevel != null)
                        nextLevel.setTypeface(typeface);
                dialog = builder.show();
            }
        }, 2000);
        if (scoreCounter >= highScore) //new high score
        {
            Handler scoreHandler = new Handler();
            scoreHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this, R.style.BonusDialog);
                    dialogView = getLayoutInflater().inflate(R.layout.high_score_dialog, null);
                    builder.setView(dialogView).setCancelable(false);
                    dialog = builder.show();
                    playSound(R.raw.yahoo);
                    Handler handler1 = new Handler();
                    handler1.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                        }
                    }, 2000);
                }
            }, 4000);
        }
        if(strikes > 0){
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    RelativeLayout container = findViewById(R.id.confetti);
                    CommonConfetti.rainingConfetti(container, new int[] { Color.CYAN, Color.MAGENTA, Color.YELLOW, Color.GREEN, Color.BLUE, Color.RED })
                            .infinite();
                }
            }, 2000);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.in_game_menu,menu);
        if(global.isMute()){
            MenuItem sound = menu.findItem(R.id.action_sound_toggle);
            sound.setTitle(getResources().getString(R.string.sound_on));
        }
        if(global.isMusicMute()){
            MenuItem music = menu.findItem(R.id.action_music_toggle);
            music.setTitle(getResources().getString(R.string.music_on));
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_change_player){
            AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this, R.style.CustomAlertDialog);
            dialogView = getLayoutInflater().inflate(R.layout.stop_game_layout, null);
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
            Typeface typeface = ResourcesCompat.getFont(PlayActivity.this, R.font.stephia);;
            if (Locale.getDefault().toString().equals("iw_IL"))
            {
                typeface = ResourcesCompat.getFont(PlayActivity.this, R.font.abraham);
            }
            else if(Locale.getDefault().toString().equals("ru_RU")){
                typeface = ResourcesCompat.getFont(PlayActivity.this, R.font.wagnasty);
            }
                TextView textView = dialogView.findViewById(R.id.space);
                textView.setTypeface(typeface);
                okBtn.setTypeface(typeface);
                cancelBtn.setTypeface(typeface);
        }
        else if(id == R.id.action_sound_toggle){
            SharedPreferences sharedPref = this.getSharedPreferences("sound", this.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            if(global.isMute()) {
                editor.putBoolean("mute", false);
                editor.commit();
                global.setMute(false);
                item.setTitle(getResources().getString(R.string.sound_off));
                global.startMusic(this);
            }
            else{
                editor.putBoolean("mute", true);
                editor.commit();
                global.setMute(true);
                item.setTitle(getResources().getString(R.string.sound_on));
                global.pauseMusic();
            }
        }
        else if(id == R.id.action_music_toggle) {
            SharedPreferences sharedPref = this.getSharedPreferences("sound", this.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            if(global.isMusicMute()) {
                editor.putBoolean("musicMute", false);
                editor.commit();
                global.setMusicMute(false);
                item.setTitle(getResources().getString(R.string.music_off));
                global.startMusic(this);
            }
            else{
                editor.putBoolean("musicMute", true);
                editor.commit();
                global.setMusicMute(true);
                item.setTitle(getResources().getString(R.string.music_on));
                global.pauseMusic();
            }
        }
        else if (id == R.id.action_how_to_play)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this, R.style.CustomAlertDialog);
            dialogView = getLayoutInflater().inflate(R.layout.stop_game_layout, null);
            builder.setView(dialogView).setCancelable(false);
            final AlertDialog dialog = builder.show();
            Button okBtn = dialogView.findViewById(R.id.ok);
            okBtn.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    Intent intent = new Intent(PlayActivity.this, WalkTroughActivity.class);
                    intent.putExtra("from", MainActivity.class);
                    startActivity(intent);
                }});
            Button cancelBtn = dialogView.findViewById(R.id.cancle);
            cancelBtn.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    dialog.dismiss();
                }});
            Typeface typeface = ResourcesCompat.getFont(PlayActivity.this, R.font.stephia);;
            if (Locale.getDefault().toString().equals("iw_IL"))
            {
                typeface = ResourcesCompat.getFont(PlayActivity.this, R.font.abraham);
            }
            else if(Locale.getDefault().toString().equals("ru_RU")){
                typeface = ResourcesCompat.getFont(PlayActivity.this, R.font.wagnasty);
            }
                TextView textView = dialogView.findViewById(R.id.space);
                textView.setTypeface(typeface);
                okBtn.setTypeface(typeface);
                cancelBtn.setTypeface(typeface);
        }
        return super.onOptionsItemSelected(item);
    }

    void playSound(int rawID){
        if(global.isAppPaused()){
            return;
        }
        SharedPreferences sharedPref = this.getSharedPreferences("sound", this.MODE_PRIVATE);
        global.setMute(sharedPref.getBoolean("mute", false));
        if(global.isMute()){
            return;
        }
        stopSound();
        mediaPlayer = new MediaPlayer();
        mediaPlayer = MediaPlayer.create(PlayActivity.this, rawID);
        mediaPlayer.start();
    }

    void stopSound(){
        if(mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.setLooping(false);
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }


    void heartsInvalidate(){
        if(strikes > 6){
            strikes = 6;
        }
        int i = 0;
        for(;i<strikes;i++){
            hearts[i].setVisibility(View.VISIBLE);
            hearts[i].setAnimation(animation);
        }
        for(;i<6;i++){
            hearts[i].setVisibility(View.INVISIBLE);
            hearts[i].clearAnimation();
        }
    }

    @Override
    public void onBackPressed() {
        if(!blnButtonRotation){
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this, R.style.CustomAlertDialog);
        dialogView = getLayoutInflater().inflate(R.layout.stop_game_layout, null);
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
        Typeface typeface = ResourcesCompat.getFont(PlayActivity.this, R.font.stephia);;
        if (Locale.getDefault().toString().equals("iw_IL"))
        {
            typeface = ResourcesCompat.getFont(PlayActivity.this, R.font.abraham);
        }
        else if(Locale.getDefault().toString().equals("ru_RU")){
            typeface = ResourcesCompat.getFont(PlayActivity.this, R.font.wagnasty);
        }
        TextView textView = dialogView.findViewById(R.id.space);
        textView.setTypeface(typeface);
        okBtn.setTypeface(typeface);
        cancelBtn.setTypeface(typeface);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
         global.setAppPaused(true);
         global.pauseMusic();
    }

    @Override
    protected void onResume() {
        super.onResume();
        global.setAppPaused(false);
        if(mediaPlayer != null){
            mediaPlayer.start();
        }
        else {
            global.startMusic(this);
        }
    }
}