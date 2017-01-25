package com.jleth.android.things.rainbowhatsamle.devices;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManagerService;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LedsTest {

    private PeripheralManagerService peripheralManagerService = mock(PeripheralManagerService.class);
    private Gpio redLedGpio = mock(Gpio.class);
    private Gpio greenLedGpio = mock(Gpio.class);
    private Gpio blueLedGpio = mock(Gpio.class);

    private Leds leds;

    @Before
    public void setUp() throws IOException {
        when(peripheralManagerService.openGpio(Leds.LED_RED_GPIO_PIN))
                .thenReturn(redLedGpio);
        when(peripheralManagerService.openGpio(Leds.LED_GREEN_GPIO_PIN))
                .thenReturn(greenLedGpio);
        when(peripheralManagerService.openGpio(Leds.LED_BLUE_GPIO_PIN))
                .thenReturn(blueLedGpio);

        leds = new Leds(peripheralManagerService);
    }

    @Test
    public void shouldSetupLeds() throws IOException {
        verify(redLedGpio).setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
        verify(greenLedGpio).setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
        verify(blueLedGpio).setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
    }

    @Test
    public void shouldLightLedOn() throws IOException {
        leds.setLed(Leds.LED_GREEN, true);

        verify(greenLedGpio).setValue(true);
    }

    @Test
    public void shouldToggleLed() throws IOException {
        when(greenLedGpio.getValue()).thenReturn(true);

        leds.toggleLed(Leds.LED_GREEN);

        verify(greenLedGpio).setValue(false);
    }

    @Test
    public void shouldCloseLeds() throws IOException {
        leds.close();

        verify(redLedGpio).close();
        verify(greenLedGpio).close();
        verify(blueLedGpio).close();
    }
}