package tsahi.and.kostia.spinandlearn;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.media.MediaPlayer;

public class GlobalVar extends Application {

    boolean AppPaused;
    boolean mute;

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
/*public MediaPlayer getBackgroundMusic()
    {
        return this.backgroundMusic;
    }

    public void startMusic(Context context)
    {
        backgroundMusic = new MediaPlayer();
        backgroundMusic = MediaPlayer.create(context, R.raw.background_music);
        backgroundMusic.start();
        backgroundMusic.setLooping(true);
    }

    public void stopMusic()
    {
        backgroundMusic.stop();
    }*/
}
