package com.jleth.android.things.rainbowhatsamle.devices;

import android.os.Handler;

import com.google.android.things.contrib.driver.pwmspeaker.Speaker;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class BuzzerTest {

    private Speaker speaker = mock(Speaker.class);
    private Handler stopHandler = mock(Handler.class);

    private Buzzer buzzer;

    @Before
    public void setUp() throws IOException {
        buzzer = new Buzzer(speaker, stopHandler);
    }

    @Test
    public void shouldPlayNote() throws IOException {
        buzzer.play(30.0);

        verify(speaker).play(30.0);
    }

    @Test
    public void shouldPlayNoteAndPostStopRunnable() throws IOException {
        buzzer.play(30.0, 100);

        verify(speaker).play(30.0);
        verify(stopHandler).postDelayed(any(Runnable.class), eq(100L));
    }

    @Test
    public void shouldStopPlayback() throws IOException {
        buzzer.stop();

        verify(speaker).stop();
    }

    @Test
    public void shouldCloseSpeaker() throws IOException {
        buzzer.close();

        verify(stopHandler).removeCallbacks(any(Runnable.class));
        verify(speaker).stop();
        verify(speaker).close();
    }
}