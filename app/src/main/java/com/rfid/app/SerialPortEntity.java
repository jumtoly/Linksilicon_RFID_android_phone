package com.rfid.app;

import app.terminal.com.serialport.driver.UsbSerialPort;

/**
 * Created by sly on 2016/5/20.
 */
public class SerialPortEntity {
    private static SerialPortEntity serialPortEntity;
    private UsbSerialPort serialPort;

    private SerialPortEntity() {

    }

    public static SerialPortEntity getInstance() {
        if (serialPortEntity == null) {
            serialPortEntity = new SerialPortEntity();
        }
        return serialPortEntity;
    }

    public UsbSerialPort getSerialPort() {
        return serialPort;
    }

    public void setSerialPort(UsbSerialPort serialPort) {
        this.serialPort = serialPort;
    }
}
