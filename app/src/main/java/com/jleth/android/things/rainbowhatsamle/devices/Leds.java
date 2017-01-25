package com.jleth.android.things.rainbowhatsamle.devices;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManagerService;

import java.io.Closeable;
import java.io.IOException;

public class Leds implements Closeable {


    public static final String LED_RED_GPIO_PIN = "BCM6";
    public static final String LED_GREEN_GPIO_PIN = "BCM19";
    public static final String LED_BLUE_GPIO_PIN = "BCM26";

    public static final int LED_RED = 0;
    public static final int LED_GREEN = 1;
    public static final int LED_BLUE = 2;
    public static final int[] LEDS = {LED_RED, LED_GREEN, LED_BLUE};

    private Gpio[] leds;

    private PeripheralManagerService mPeripheralManagerService;

    public Leds(PeripheralManagerService peripheralManagerService) throws IOException {
        mPeripheralManagerService = peripheralManagerService;
        leds = new Gpio[]{
                openGpio(mPeripheralManagerService, LED_RED_GPIO_PIN),
                openGpio(mPeripheralManagerService, LED_GREEN_GPIO_PIN),
                openGpio(mPeripheralManagerService, LED_BLUE_GPIO_PIN)
        };
    }

    public Leds() throws IOException {
        this(new PeripheralManagerService());
    }

    private Gpio openGpio(PeripheralManagerService service, String pin) throws IOException {
        Gpio led = service.openGpio(pin);
        led.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
        return led;
    }

    public void setLed(int led, boolean value) throws IOException {
        leds[led].setValue(value);
    }

    public void toggleLed(int led) throws IOException {
        boolean value = leds[led].getValue();
        setLed(led, !value);
    }

    @Override
    public void close() throws IOException {
        for (Gpio led : leds) {
            led.setValue(false);
            led.close();
        }
    }
}