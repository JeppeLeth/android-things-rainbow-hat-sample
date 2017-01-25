package com.jleth.android.things.rainbowhatsamle.devices;


import android.view.KeyEvent;

import com.google.android.things.contrib.driver.button.Button;
import com.google.android.things.contrib.driver.button.ButtonInputDriver;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Buttons implements Closeable {

    public static final String BUTTON_A_GPIO_PIN = "BCM21";
    public static final String BUTTON_B_GPIO_PIN = "BCM20";
    public static final String BUTTON_C_GPIO_PIN = "BCM16";
    private List<ButtonInputDriver> buttonDrivers = new ArrayList<>();

    public Buttons() throws IOException {
        this(
                registerButtonDriver(Buttons.BUTTON_A_GPIO_PIN, KeyEvent.KEYCODE_A),
                registerButtonDriver(Buttons.BUTTON_B_GPIO_PIN, KeyEvent.KEYCODE_B),
                registerButtonDriver(Buttons.BUTTON_C_GPIO_PIN, KeyEvent.KEYCODE_C)
        );
    }

    public Buttons(ButtonInputDriver buttonA, ButtonInputDriver buttonB, ButtonInputDriver buttonC) throws IOException {
        buttonDrivers.add(buttonA);
        buttonDrivers.add(buttonB);
        buttonDrivers.add(buttonC);
    }

    private static ButtonInputDriver registerButtonDriver(String pin, int keycode) throws IOException {
        ButtonInputDriver driver = new ButtonInputDriver(pin, Button.LogicState.PRESSED_WHEN_LOW, keycode);
        driver.register();
        return driver;
    }

    @Override
    public void close() throws IOException {
        for (ButtonInputDriver buttonDriver : buttonDrivers) {
            buttonDriver.close();
        }
    }
}