package com.jleth.android.things.rainbowhatsamle.devices;

import com.google.android.things.contrib.driver.ht16k33.AlphanumericDisplay;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

public class DisplayTest {

    private AlphanumericDisplay mockDisplay = mock(AlphanumericDisplay.class);

    private Display display;

    @Before public void setUp() throws IOException {
        display = new Display(mockDisplay);
    }

    @Test public void shouldSetupDisplay() throws IOException {
        verify(mockDisplay).setEnabled(true);
        verify(mockDisplay).clear();
    }

    @Test public void shouldDisplayMessage() throws IOException {
        display.displayMessage("Hello!");

        verify(mockDisplay).display("Hello!");
    }

    @Test public void shouldCloseDisplay() throws IOException {
        reset(mockDisplay);

        display.close();

        verify(mockDisplay).clear();
    }
}
