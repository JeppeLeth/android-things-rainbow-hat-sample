package com.jleth.android.things.rainbowhatsamle;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyEvent;

import com.jleth.android.things.rainbowhatsamle.devices.Buttons;
import com.jleth.android.things.rainbowhatsamle.devices.Buzzer;
import com.jleth.android.things.rainbowhatsamle.devices.Display;
import com.jleth.android.things.rainbowhatsamle.devices.Leds;

import java.io.IOException;


/**
 * Created by jeppe on 01/20/2017.
 */
public class RainbowHATDemoActivity extends Activity {

    private static final String TAG = "RainbowHATDemoActivityJ";

    private final SparseArray<String> mMessages = new SparseArray<String>() {{
        put(KeyEvent.KEYCODE_A, "AHOY");
        put(KeyEvent.KEYCODE_B, "YARR");
        put(KeyEvent.KEYCODE_C, "GROG");
    }};

    private static final String DEFAULT_MESSAGE = "WJDK";

    private final double[] NOTES = {
            71, 71, 71, 71, 71, 71, 71, 64, 67, 71,
            69, 69, 69, 69, 69, 69, 69, 62, 66, 69,
            71, 71, 71, 71, 71, 71, 71, 73, 74, 77,
            74, 71, 69, 66, 64, 64};

    private final long[] TIMES = {
            300, 50, 50, 300, 50, 50, 300, 300, 300, 300,
            300, 50, 50, 300, 50, 50, 300, 300, 300, 300,
            300, 50, 50, 300, 50, 50, 300, 300, 300, 300,
            300, 300, 300, 300, 600, 600};

    private Buttons mButtons;
    private Buzzer mBuzzer;
    private Display mDisplay;
    private Leds mLeds;

    private int noteIndex = 0;
    private int timeIndex = 0;
    private int ledIndex = 0;

    private Handler playHandler = new Handler();
    private Runnable mPlaybackRunnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            mButtons = new Buttons();
            mBuzzer = new Buzzer();
            mDisplay = new Display();
            mLeds = new Leds();
            mDisplay.displayMessage(DEFAULT_MESSAGE);
        } catch (IOException e) {
            Log.e(TAG, "onCreate: ", e);
        }

        playMelodyWithLeds();

    }

    private void playMelodyWithLeds() {
        mPlaybackRunnable = new Runnable() {

            @Override
            public void run() {
                try {
                    mBuzzer.play(NOTES[noteIndex], (long) (TIMES[timeIndex] * 0.8));
                    mLeds.setLed(Leds.LEDS[ledIndex], true);
                    mLeds.setLed(Leds.LEDS[prevIndex(ledIndex, Leds.LEDS.length)], false);
                    if (noteIndex == NOTES.length - 1) {
                        mLeds.setLed(Leds.LEDS[ledIndex], false);
                        mBuzzer.close();
                        mDisplay.displayMessage(DEFAULT_MESSAGE);
                    } else {
                        playHandler.postDelayed(mPlaybackRunnable, TIMES[timeIndex]);
                        timeIndex++;
                        noteIndex++;
                        ledIndex = nextIndex(ledIndex, Leds.LEDS.length);
                    }
                } catch (IOException e) {
                    Log.e(TAG, "onCreate: ", e);
                }
            }


        };
        playHandler.post(mPlaybackRunnable);
    }

    private int prevIndex(int currIndex, int size) {
        int prevIndex = currIndex - 1;
        return prevIndex >= 0 ? prevIndex : size - 1;
    }

    private int nextIndex(int currIndex, int size) {
        int nextIndex = currIndex + 1;
        return nextIndex < size ? nextIndex : 0;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (mMessages.indexOfKey(event.getKeyCode()) >= 0) {
            String s = mMessages.get(event.getKeyCode());
            try {
                mDisplay.displayMessage(s);
            } catch (IOException e) {
                Log.e(TAG, "onKeyUp: ", e);
            }
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        playHandler.removeCallbacks(mPlaybackRunnable);
        try {
            mLeds.close();
            mButtons.close();
            mDisplay.close();
            mBuzzer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
