package com.rfid.app;

import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import app.terminal.com.serialport.driver.UsbSerialDriver;
import app.terminal.com.serialport.driver.UsbSerialPort;
import app.terminal.com.serialport.driver.UsbSerialProber;

public class MainActivity extends FragmentActivity {
    private final String TAG = MainActivity.class.getSimpleName();

    private UsbManager mUsbManager;
    private LinearLayout resultLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        init();

    }

    private void init() {
        resultLayout = (LinearLayout) findViewById(R.id.result_layout);
        mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        getSerialProber();
    }

    /**
     * 获取序列号
     *
     * @param v
     */
    public void getSerialNumber(View v) {

    }

    /**
     * 设置波特率
     *
     * @param v
     */
    public void baudRateSetting(View v) {

    }

    /**
     * 串口设置
     *
     * @param v
     */
    public void serialPortSetting(View v) {
        startActivity(new Intent(this, SerialPortSettingsActivity.class));
    }

    /**
     * 卡片基本操作
     *
     * @param v
     */
    public void basicOperation(View v) {

    }

    /**
     * S50
     *
     * @param v
     */
    public void S50(View v) {

    }

    /**
     * S70
     *
     * @param v
     */
    public void S70(View v) {

    }

    /**
     * cpu
     *
     * @param v
     */
    public void cpu(View v) {

    }


    public void getSerialProber() {
        new AsyncTask<Void, Void, List<UsbSerialPort>>() {
            @Override
            protected List<UsbSerialPort> doInBackground(Void... params) {
                Log.d(TAG, "Refreshing device list ...");
                SystemClock.sleep(1000);

                final List<UsbSerialDriver> drivers =
                        UsbSerialProber.getDefaultProber().findAllDrivers(mUsbManager);
                Log.d(TAG, "drivers:" + drivers.size());
                final List<UsbSerialPort> result = new ArrayList<UsbSerialPort>();
                for (final UsbSerialDriver driver : drivers) {
                    final List<UsbSerialPort> ports = driver.getPorts();
                    Log.d(TAG, String.format("+ %s: %s port%s",
                            driver, Integer.valueOf(ports.size()), ports.size() == 1 ? "" : "s"));
                    result.addAll(ports);
                }

                return result;
            }

            @Override
            protected void onPostExecute(List<UsbSerialPort> result) {

                Log.d(TAG, "Done refreshing, " + result.size() + " entries found.");
            }

        }.execute((Void) null);
    }
}
