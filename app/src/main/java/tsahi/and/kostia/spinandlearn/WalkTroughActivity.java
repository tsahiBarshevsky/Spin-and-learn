package tsahi.and.kostia.spinandlearn;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.github.paolorotolo.appintro.model.SliderPage;

public class WalkTroughActivity extends AppIntro {

    Class from;
    Boolean firstRun;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extra = getIntent().getExtras();

        from = (Class) extra.get("from");
        firstRun = getIntent().getBooleanExtra("firstRun", false);

        SliderPage sliderPage[] = create_pages();
        for (int i = 0; i<7;i++){
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

    void skipDialog(){
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
            }});
        Button cancelBtn = dialogView.findViewById(R.id.cancle);
        cancelBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }});
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_anim);
        okBtn.startAnimation(animation);
        cancelBtn.startAnimation(animation);
    }

    SliderPage[] create_pages(){
        SliderPage[] ret = new SliderPage[1];
        SliderPage page;

        page = new SliderPage();
        page.setTitle(getResources().getString(R.string.app_name));
        page.setDescription(getResources().getString(R.string.japan));
        page.setImageDrawable(R.drawable.web_hi_res_512);
        page.setBgColor(Color.parseColor("#2cb42c"));
        ret[0] = page;



        return ret;
    }
}
