package com.jleth.android.things.rainbowhatsamle.devices;

import com.google.android.things.contrib.driver.ht16k33.AlphanumericDisplay;

import java.io.Closeable;
import java.io.IOException;

public final class Display implements Closeable {
    private final AlphanumericDisplay display;
    private static final String DISPLAY_I2C_BUS = "I2C1";

    public Display(AlphanumericDisplay display) throws IOException {
        this.display = display;
        this.display.setEnabled(true);
        this.display.clear();
    }

    public Display() throws IOException {
        this(new AlphanumericDisplay(getDISPLAY_I2C_BUS()));
    }

    public final void displayMessage(String message) throws IOException {
        this.display.display(message);
    }

    public void close() throws IOException {
        this.display.clear();
        this.display.setEnabled(false);
        this.display.close();
    }

    public static String getDISPLAY_I2C_BUS() {
        return DISPLAY_I2C_BUS;
    }

}
