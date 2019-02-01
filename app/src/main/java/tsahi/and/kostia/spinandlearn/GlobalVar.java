package tsahi.and.kostia.spinandlearn;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.media.MediaPlayer;

public class GlobalVar extends Application {
    private Resources gRes;
    //private MediaPlayer backgroundMusic;

    public Resources getgRes() {
        return gRes;
    }

    public void setgRes(Resources gRes) {
        this.gRes = gRes;
    }

    public int rndNum(int start, int end){
        return 0;
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
