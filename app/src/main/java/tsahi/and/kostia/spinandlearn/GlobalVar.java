package tsahi.and.kostia.spinandlearn;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.MediaPlayer;

public class GlobalVar extends Application {

    boolean AppPaused;
    boolean mute;
    MediaPlayer backgroundMusic;

    public GlobalVar(){
        setAppPaused(false);
    }

    public boolean isAppPaused() {
        return AppPaused;
    }

    public void setAppPaused(boolean appPaused) {
        AppPaused = appPaused;
    }

    public boolean isMute() {
        return mute;
    }

    public void setMute(boolean mute) {
        this.mute = mute;
    }

    public void initPlayer(Context context){
        backgroundMusic = new MediaPlayer();
        backgroundMusic = MediaPlayer.create(context, R.raw.background_music);
        backgroundMusic.setLooping(true);
        startMusic(context);
    }

    public void startMusic(Context context)
    {
        if(isAppPaused()){
            return;
        }
        SharedPreferences sharedPref = context.getSharedPreferences("sound", this.MODE_PRIVATE);
        setMute(sharedPref.getBoolean("mute", false));
        if(isMute()){
            return;
        }
        backgroundMusic.start();
    }

    public void pauseMusic()
    {
        if (backgroundMusic.isPlaying()) {
            backgroundMusic.pause();
        }
    }
}
