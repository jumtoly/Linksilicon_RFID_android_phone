package com.rfid.app;

import android.app.Application;

import app.terminal.com.serialport.driver.UsbSerialPort;

/**
 * Created by sly on 2016/5/18.
 */
public class BaseApp extends Application {
    private static BaseApp instance;
    public UsbSerialPort usbSerialPort;

    public UsbSerialPort getUsbSerialPort() {
        return usbSerialPort;
    }

    public void setUsbSerialPort(UsbSerialPort usbSerialPort) {
        this.usbSerialPort = usbSerialPort;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static BaseApp instance() {

        return instance;
    }

}
