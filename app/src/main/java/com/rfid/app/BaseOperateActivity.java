package com.rfid.app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import app.terminal.com.serialport.inter.ControlLinksilliconCardIntface;
import app.terminal.com.serialport.util.DeviceStateChangeUtils;
import app.terminal.com.serialport.util.SendByteData;

import app.terminal.com.serialport.driver.UsbSerialPort;
import app.terminal.com.serialport.util.SerialportControl;

public class BaseOperateActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_operate);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!BaseApp.instance().controlLinksilliconCardIntface.isReaderOpen()) {
            Toast.makeText(this, "请先打开读卡器串口", Toast.LENGTH_SHORT).show();
            SerialPortSettingsActivity.show(this);
            this.finish();
        }
    }

    /**
     * 手动寻卡
     *
     * @param v
     */
    public void manuallyDetectingCard(View v) {
        BaseApp.instance().controlLinksilliconCardIntface.manualCard(this);
    }

    /**
     * 关闭所有天线
     *
     * @param v
     */
    public void closeAllAntenna(View v) {
        BaseApp.instance().controlLinksilliconCardIntface.antennaOff(this);
    }

    /**
     * 关闭蜂鸣器
     *
     * @param v
     */
    public void stopBuzzer(View v) {
        BaseApp.instance().controlLinksilliconCardIntface.buzzerOn(this, false);
    }

    /**
     * 开启蜂鸣器
     *
     * @param v
     */
    public void startBuzzer(View v) {
        BaseApp.instance().controlLinksilliconCardIntface.buzzerOn(this, true);
    }

    /**
     * 关闭自动寻卡
     *
     * @param v
     */
    public void closeAutoFindCard(View v) {
        BaseApp.instance().controlLinksilliconCardIntface.autoFindCard(this, false);
    }

    /**
     * 开启自动寻卡
     *
     * @param v
     */
    public void startAutoFindCard(View v) {
        BaseApp.instance().controlLinksilliconCardIntface.autoFindCard(this, true);
    }


    static void show(Context context, UsbSerialPort port) {
        final Intent intent = new Intent(context, BaseOperateActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
        context.startActivity(intent);
    }

}
