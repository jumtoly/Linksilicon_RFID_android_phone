package com.rfid.app;

import android.content.Context;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import app.terminal.com.serialport.util.DeviceStateChangeUtils;
import app.terminal.com.serialport.util.SendByteData;

import app.terminal.com.serialport.driver.UsbSerialPort;
import app.terminal.com.serialport.util.HexDump;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();


    private TextView mDumpTextView;
    private ScrollView mScrollView;
    private UsbManager mUsbManager;
    private UsbSerialPort usbSerialPort;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        init();

    }


    private void init() {
        mDumpTextView = (TextView) findViewById(R.id.consoleText);
        mScrollView = (ScrollView) findViewById(R.id.consoleScroller);
        mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    /**
     * 获取序列号
     *
     * @param v
     */
    public void getSerialNumber(View v) {
        DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort()).onDeviceStateChange(SendByteData.SERIAL_NUMBER_BYTE);

    }

    /**
     * 设置波特率
     *
     * @param v
     */
    public void baudRateSetting(View v) {
        DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort()).onDeviceStateChange(SendByteData.BAUD_RATE);
    }

    /**
     * 串口设置
     *
     * @param v
     */
    public void serialPortSetting(View v) {
        SerialPortSettingsActivity.show(this);
    }

    /**
     * 基本操作
     *
     * @param v
     */
    public void basicOperation(View v) {
        BaseOperateActivity.show(this, SerialPortEntity.getInstance().getSerialPort());

    }


    /**
     * 卡片基本操作
     *
     * @param v
     */
    public void basicOperationCard(View v) {
        BasicOperationCardActivity.show(this, SerialPortEntity.getInstance().getSerialPort());

    }

    /**
     * S50
     *
     * @param v
     */
    public void S50(View v) {
        S50CardOperateActivity.show(this, SerialPortEntity.getInstance().getSerialPort());

    }

    /**
     * S70
     *
     * @param v
     */
    public void S70(View v) {
        S70CardOperateActivity.show(this, SerialPortEntity.getInstance().getSerialPort());
    }

    /**
     * cpu
     *
     * @param v
     */
    public void cpu(View v) {
        CpuCardOperateActivity.show(this, SerialPortEntity.getInstance().getSerialPort());
    }


    private void updateReceivedData(byte[] data) {
        final String message = "Read " + data.length + " bytes: \n"
                + HexDump.dumpHexString(data) + "\n\n";
        mDumpTextView.append(message);
        mScrollView.smoothScrollTo(0, mDumpTextView.getBottom());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
