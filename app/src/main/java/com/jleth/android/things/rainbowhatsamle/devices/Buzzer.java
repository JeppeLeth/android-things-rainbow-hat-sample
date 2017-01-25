package com.jleth.android.things.rainbowhatsamle.devices;

import android.os.Handler;

import com.google.android.things.contrib.driver.pwmspeaker.Speaker;

import java.io.Closeable;
import java.io.IOException;

public class Buzzer implements Closeable {

    public static final String SPEAKER_PWM_PIN = "PWM1";

    private Runnable stopRunnable;
    private Speaker speaker;
    private Handler stopHandler;

    public Buzzer() throws IOException {
        this(new Speaker(Buzzer.SPEAKER_PWM_PIN), new Handler());
    }

    public Buzzer(Speaker speaker, Handler stopHandler) throws IOException {
        this.speaker = speaker;
        this.stopHandler = stopHandler;
        stopRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    stop();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }


    public void play(double frequency) throws IOException {
        speaker.play(frequency);
    }

    public void play(double frequency, long duration) throws IOException {
        if (frequency != 0) {
            speaker.play(frequency);
        }
        stopHandler.postDelayed(stopRunnable, duration);
    }

    public void stop() throws IOException {
        speaker.stop();
    }

    @Override
    public void close() throws IOException {
        stopHandler.removeCallbacks(stopRunnable);
        speaker.stop();
        speaker.close();
    }
}