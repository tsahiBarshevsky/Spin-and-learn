package tsahi.and.kostia.spinandlearn;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.github.paolorotolo.appintro.AppIntro;
import java.util.Locale;

public class WalkTroughActivity extends AppIntro {

    Class from;
    Boolean firstRun;

    GlobalVar global;

    String mainColor = "#C9FFC9";
    String secColor = "#A5A29B";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        global = ((GlobalVar) this.getApplication());

        global.setAppPaused(false);

        Bundle extra = getIntent().getExtras();

        from = (Class) extra.get("from");
        firstRun = getIntent().getBooleanExtra("firstRun", false);

        addSlide(com.amqtech.opensource.appintroexample.util.SampleSlide.newInstance(R.layout.slide00));
        addSlide(com.amqtech.opensource.appintroexample.util.SampleSlide.newInstance(R.layout.slide01));
        addSlide(com.amqtech.opensource.appintroexample.util.SampleSlide.newInstance(R.layout.slide02));
        addSlide(com.amqtech.opensource.appintroexample.util.SampleSlide.newInstance(R.layout.slide03));
        addSlide(com.amqtech.opensource.appintroexample.util.SampleSlide.newInstance(R.layout.slide04));
        addSlide(com.amqtech.opensource.appintroexample.util.SampleSlide.newInstance(R.layout.slide05));
        addSlide(com.amqtech.opensource.appintroexample.util.SampleSlide.newInstance(R.layout.slide06));
        addSlide(com.amqtech.opensource.appintroexample.util.SampleSlide.newInstance(R.layout.slide07));
        addSlide(com.amqtech.opensource.appintroexample.util.SampleSlide.newInstance(R.layout.slide08));
        addSlide(com.amqtech.opensource.appintroexample.util.SampleSlide.newInstance(R.layout.slide09));
        addSlide(com.amqtech.opensource.appintroexample.util.SampleSlide.newInstance(R.layout.slide10));

        setBarColor(Color.parseColor(mainColor));
        setSeparatorColor(Color.parseColor(secColor));

        if(firstRun){
            showSkipButton(false);
        }
        else {
            showSkipButton(true);
            setSkipText(getResources().getString(R.string.skip));
            setColorSkipButton(Color.BLACK);
        }
        setProgressButtonEnabled(true);

        setDoneText(getResources().getString(R.string.done));
        setColorDoneText(Color.BLACK);


        setNextArrowColor(Color.BLACK);
        setIndicatorColor(Color.BLACK,Color.DKGRAY);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        skipDialog();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Intent intent = new Intent(WalkTroughActivity.this, from);
        intent.putExtra("Name", getIntent().getStringExtra("Name"));
        startActivity(intent);    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }

    @Override
    public void onBackPressed() {
        if(firstRun){
            return;
        }
        skipDialog();
    }

    void skipDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(WalkTroughActivity.this, R.style.CustomAlertDialog);
        View dialogView = getLayoutInflater().inflate(R.layout.skip_tutorial_dialog, null);
        builder.setView(dialogView).setCancelable(false);
        final AlertDialog dialog = builder.show();
        Button okBtn = dialogView.findViewById(R.id.ok);
        okBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(WalkTroughActivity.this, from);
                intent.putExtra("Name", getIntent().getStringExtra("Name"));
                startActivity(intent);
            }
        });
        Button cancelBtn = dialogView.findViewById(R.id.cancle);
        cancelBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_anim);
        okBtn.startAnimation(animation);
        cancelBtn.startAnimation(animation);
        Typeface typeface = ResourcesCompat.getFont(this, R.font.stephia);;
        if (Locale.getDefault().toString().equals("iw_IL"))
        {
            typeface = ResourcesCompat.getFont(this, R.font.abraham);
        }
        else if(Locale.getDefault().toString().equals("ru_RU")){
            typeface = ResourcesCompat.getFont(this, R.font.wagnasty);
        }
            TextView textView = dialogView.findViewById(R.id.space);
            textView.setTypeface(typeface);
            cancelBtn.setTypeface(typeface);
            okBtn.setTypeface(typeface);

    }

    @Override
    protected void onPause() {
        super.onPause();
        global.setAppPaused(true);
        global.pauseMusic();
    }

    @Override
    protected void onResume() {
        super.onResume();
        global.setAppPaused(false);
        global.startMusic(this);
    }
}
