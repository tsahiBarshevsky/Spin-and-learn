package tsahi.and.kostia.spinandlearn;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.MediaPlayer;

public class GlobalVar extends Application {

    boolean AppPaused;
    boolean mute;
    boolean musicMute;
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

    public boolean isMusicMute() {
        return musicMute;
    }

    public void setMusicMute(boolean musicMute) {
        this.musicMute = musicMute;
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
        setMusicMute(sharedPref.getBoolean("musicMute", false));
        if(isMusicMute()){
            return;
        }
        if (backgroundMusic != null) {
            backgroundMusic.start();
        }
    }

    public void pauseMusic()
    {
        if (backgroundMusic != null && backgroundMusic.isPlaying()) {
            backgroundMusic.pause();
        }
    }

    public void changeMusic(int musicID, Context context){
        if (backgroundMusic != null) {
            backgroundMusic.setLooping(false);
            backgroundMusic.stop();
            backgroundMusic.reset();
            backgroundMusic.release();
            backgroundMusic = null;
            backgroundMusic = MediaPlayer.create(context, musicID);
            backgroundMusic.setLooping(true);
            startMusic(context);
        }
    }
}
