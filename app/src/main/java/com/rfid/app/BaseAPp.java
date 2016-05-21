package com.rfid.app;

import android.app.Application;
import android.content.Context;
import android.hardware.usb.UsbManager;
import android.widget.Toast;

import java.io.IOException;

import app.terminal.com.serialport.inter.ControlLinksilliconCardIntface;
import app.terminal.com.serialport.util.SerialportControl;

/**
 * Created by sly on 2016/5/18.
 */
public class BaseApp extends Application {
    private ControlLinksilliconCardIntface controlLinksilliconCardIntface = new SerialportControl();
    private static BaseApp instance;
    private UsbManager usbManager;


    @Override
    public void onCreate() {
        super.onCreate();
        usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        try {
            controlLinksilliconCardIntface.initSerialPort(usbManager);
        } catch (IOException e) {
            Toast.makeText(this, "failed init serial port.", Toast.LENGTH_SHORT).show();
        }
        instance = this;
    }

    public UsbManager getUsbManager() {
        return usbManager;
    }

    public static BaseApp instance() {

        return instance;
    }

}
