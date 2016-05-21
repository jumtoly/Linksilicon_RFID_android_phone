package com.rfid.app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import app.terminal.com.serialport.util.DeviceStateChangeUtils;
import app.terminal.com.serialport.util.SendByteData;

import app.terminal.com.serialport.driver.UsbSerialPort;

public class BaseOperateActivity extends AppCompatActivity {

    private static UsbSerialPort sPort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_operate);
    }

    /**
     * 手动寻卡
     *
     * @param v
     */
    public void manuallyDetectingCard(View v) {
        DeviceStateChangeUtils.getInstence(sPort).onDeviceStateChange(SendByteData.MANUALLY_DETECTING_CARD);

    }

    /**
     * 关闭所有天线
     *
     * @param v
     */
    public void closeAllAntenna(View v) {
        DeviceStateChangeUtils.getInstence(sPort).onDeviceStateChange(SendByteData.STOP_ALL_ANTENNA);

    }

    /**
     * 关闭蜂鸣器
     *
     * @param v
     */
    public void stopBuzzer(View v) {
        DeviceStateChangeUtils.getInstence(sPort).onDeviceStateChange(SendByteData.STOP_BUZZER);
    }

    /**
     * 开启蜂鸣器
     *
     * @param v
     */
    public void startBuzzer(View v) {
        DeviceStateChangeUtils.getInstence(sPort).onDeviceStateChange(SendByteData.START_BUZZER);
    }

    /**
     * 关闭自动寻卡
     *
     * @param v
     */
    public void closeAutoFindCard(View v) {
        DeviceStateChangeUtils.getInstence(sPort).onDeviceStateChange(SendByteData.STOP_AUTO_FIND_CARD);
    }

    /**
     * 开启自动寻卡
     *
     * @param v
     */
    public void startAutoFindCard(View v) {
        DeviceStateChangeUtils.getInstence(sPort).onDeviceStateChange(SendByteData.START_BUZZER);
    }

    static void show(Context context, UsbSerialPort port) {
        sPort = port;
        final Intent intent = new Intent(context, BaseOperateActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
        context.startActivity(intent);
    }

}
