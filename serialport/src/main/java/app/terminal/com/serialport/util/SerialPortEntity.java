package app.terminal.com.serialport.util;

import android.hardware.usb.UsbDeviceConnection;

import app.terminal.com.serialport.driver.UsbSerialPort;

/**
 * Created by sly on 2016/5/20.
 */
public class SerialPortEntity {
    private static SerialPortEntity serialPortEntity;
    private UsbSerialPort serialPort;
    private UsbDeviceConnection connection;

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

    public static SerialPortEntity getSerialPortEntity() {
        return serialPortEntity;
    }

    public static void setSerialPortEntity(SerialPortEntity serialPortEntity) {
        SerialPortEntity.serialPortEntity = serialPortEntity;
    }

    public UsbDeviceConnection getConnection() {
        return connection;
    }

    public void setConnection(UsbDeviceConnection connection) {
        this.connection = connection;
    }
}
