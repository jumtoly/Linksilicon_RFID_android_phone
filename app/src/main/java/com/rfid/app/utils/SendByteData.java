package com.rfid.app.utils;

/**
 * Created by liu.yao on 2016/5/17.
 */
public class SendByteData {
    public final static byte[] SERIAL_NUMBER_BYTE = {0x55, 0x55, 0x00, 0x00, 0x00, 0x03, 0x0A, 0x01, 0x08};
    public final static byte[] BAUD_RATE = {0x55, 0x55, 0, 0, 0, 0x04, (byte) 0xBD, 0x01, 0x06, (byte) 0xBE};
}
