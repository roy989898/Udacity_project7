package pom.poly.com.tabatatimer.Utility;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import pom.poly.com.tabatatimer.R;


public class SoundLibrary {
    private SoundPool pool;
    private int startSound;
    private int endSound;

    public SoundLibrary(Context context) {
        pool = new SoundPool(3, AudioManager.STREAM_MUSIC, 5);
        startSound = pool.load(context, R.raw.startball, 1);
        endSound = pool.load(context, R.raw.endball, 1);
    }

    public void playStartSound() {
        pool.play(startSound, 1.0F, 1.0F, 0, 0, 1.0F);
    }

    public void playEndSound() {
        pool.play(endSound, 1.0F, 1.0F, 0, 0, 1.0F);
    }

}
