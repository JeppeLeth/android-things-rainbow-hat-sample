
package com.jleth.android.things.rainbowhatsamle.devices;


import com.google.android.things.contrib.driver.button.ButtonInputDriver;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ButtonsTest {

    private ButtonInputDriver buttonADriver = mock(ButtonInputDriver.class);
    private ButtonInputDriver buttonBDriver = mock(ButtonInputDriver.class);
    private ButtonInputDriver buttonCDriver = mock(ButtonInputDriver.class);

    private Buttons buttons;

    @Before
    public void setUp() throws IOException {
        buttons = new Buttons(buttonADriver, buttonBDriver, buttonCDriver);
    }

    @Test
    public void shouldCloseAllDrivers() throws IOException {
        buttons.close();

        verify(buttonADriver).close();
        verify(buttonBDriver).close();
        verify(buttonCDriver).close();
    }
}