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
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.github.paolorotolo.appintro.model.SliderPage;

import org.w3c.dom.Text;

import java.util.Locale;

public class WalkTroughActivity extends AppIntro {

    Class from;
    Boolean firstRun;
    final int NUM_OF_SLIDES = 11;

    GlobalVar global;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        global = ((GlobalVar) this.getApplication());

        global.setAppPaused(false);

        Bundle extra = getIntent().getExtras();

        from = (Class) extra.get("from");
        firstRun = getIntent().getBooleanExtra("firstRun", false);

        SliderPage sliderPage[] = create_pages();
        for (int i = 0; i<NUM_OF_SLIDES;i++){
            addSlide(AppIntroFragment.newInstance(sliderPage[i]));
        }

        setBarColor(Color.parseColor("#2cb42c"));
        setSeparatorColor(Color.parseColor("#e8e3d9"));

        if(firstRun){
            showSkipButton(false);
        }
        else {
            showSkipButton(true);
            setSkipText(getResources().getString(R.string.skip));
        }
        setProgressButtonEnabled(true);

        setDoneText(getResources().getString(R.string.done));


//        setVibrate(true);
//        setVibrateIntensity(30);
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
        if (Locale.getDefault().toString().equals("iw_IL")) {
            Typeface typeface = ResourcesCompat.getFont(this, R.font.dana);
            TextView textView = dialogView.findViewById(R.id.space);
            textView.setTypeface(typeface);
            cancelBtn.setTypeface(typeface);
            okBtn.setTypeface(typeface);
        }
    }

    SliderPage[] create_pages(){
        SliderPage[] ret = new SliderPage[NUM_OF_SLIDES];
        SliderPage page;

        page = new SliderPage();
        page.setTitle(getResources().getString(R.string.app_name));
        page.setDescription(getResources().getString(R.string.slide00));
        page.setImageDrawable(R.drawable.slide00);
        page.setBgColor(Color.parseColor("#2cb42c"));
        ret[0] = page;

        page = new SliderPage();
        page.setTitle(getResources().getString(R.string.title01));
        page.setDescription(getResources().getString(R.string.slide01));
        page.setImageDrawable(R.drawable.slide01);
        page.setBgColor(Color.parseColor("#2cb42c"));
        ret[1] = page;

        page = new SliderPage();
        page.setTitle(getResources().getString(R.string.title02));
        page.setDescription(getResources().getString(R.string.slide02));
        page.setImageDrawable(R.drawable.slide02);
        page.setBgColor(Color.parseColor("#2cb42c"));
        ret[2] = page;

        page = new SliderPage();
        page.setTitle(getResources().getString(R.string.title03));
        page.setDescription(getResources().getString(R.string.slide03));
        page.setImageDrawable(R.drawable.slide03);
        page.setBgColor(Color.parseColor("#2cb42c"));
        ret[3] = page;

        page = new SliderPage();
        page.setTitle(getResources().getString(R.string.title04));
        page.setDescription(getResources().getString(R.string.slide04));
        page.setImageDrawable(R.drawable.slide04);
        page.setBgColor(Color.parseColor("#2cb42c"));
        ret[4] = page;

        page = new SliderPage();
        page.setTitle(getResources().getString(R.string.title05));
        page.setDescription(getResources().getString(R.string.slide05));
        page.setImageDrawable(R.drawable.slide05);
        page.setBgColor(Color.parseColor("#2cb42c"));
        ret[5] = page;

        page = new SliderPage();
        page.setTitle(getResources().getString(R.string.title06));
        page.setDescription(getResources().getString(R.string.slide06));
        page.setImageDrawable(R.drawable.slide06);
        page.setBgColor(Color.parseColor("#2cb42c"));
        ret[6] = page;

        page = new SliderPage();
        page.setTitle(getResources().getString(R.string.title07));
        page.setDescription(getResources().getString(R.string.slide07));
        page.setImageDrawable(R.drawable.slide07);
        page.setBgColor(Color.parseColor("#2cb42c"));
        ret[7] = page;

        page = new SliderPage();
        page.setTitle(getResources().getString(R.string.title08));
        page.setDescription(getResources().getString(R.string.slide08));
        page.setImageDrawable(R.drawable.slide08);
        page.setBgColor(Color.parseColor("#2cb42c"));
        ret[8] = page;

        page = new SliderPage();
        page.setTitle(getResources().getString(R.string.title09));
        page.setDescription(getResources().getString(R.string.slide09));
        page.setImageDrawable(R.drawable.slide09);
        page.setBgColor(Color.parseColor("#2cb42c"));
        ret[9] = page;

        page = new SliderPage();
        page.setTitle(getResources().getString(R.string.title10));
        page.setDescription(getResources().getString(R.string.slide10));
        page.setImageDrawable(R.drawable.slide10);
        page.setBgColor(Color.parseColor("#2cb42c"));
        ret[10] = page;

        return ret;
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
