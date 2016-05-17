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
import android.widget.ScrollView;
import android.widget.TextView;

import com.rfid.app.utils.SendByteData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import app.terminal.com.serialport.driver.UsbSerialDriver;
import app.terminal.com.serialport.driver.UsbSerialPort;
import app.terminal.com.serialport.driver.UsbSerialProber;
import app.terminal.com.serialport.util.HexDump;
import app.terminal.com.serialport.util.SerialInputOutputManager;

public class MainActivity extends FragmentActivity {
    private final String TAG = MainActivity.class.getSimpleName();


    private final ExecutorService mExecutor = Executors.newSingleThreadExecutor();
    private TextView mDumpTextView;
    private ScrollView mScrollView;
    private UsbManager mUsbManager;
    private UsbSerialPort usbSerialPort;
    private SerialInputOutputManager serialInputOutputManager;
    private final SerialInputOutputManager.Listener mListener =
            new SerialInputOutputManager.Listener() {

                @Override
                public void onRunError(Exception e) {
                    Log.d(TAG, "Runner stopped.");
                }

                @Override
                public void onNewData(final byte[] data) {
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            MainActivity.this.updateReceivedData(data);
                        }
                    });
                }
            };


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
        getSerialProber();

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
        onDeviceStateChange(SendByteData.SERIAL_NUMBER_BYTE);

    }

    /**
     * 设置波特率
     *
     * @param v
     */
    public void baudRateSetting(View v) {
        onDeviceStateChange(SendByteData.BAUD_RATE);
    }

    /**
     * 串口设置
     *
     * @param v
     */
    public void serialPortSetting(View v) {
        SerialPortSettingsActivity.show(this, usbSerialPort);
    }

    /**
     * 基本操作
     *
     * @param v
     */
    public void basicOperation(View v) {
        BaseOperateActivity.show(this, usbSerialPort);

    }


    /**
     * 卡片基本操作
     *
     * @param v
     */
    public void basicOperationCard(View v) {
        BasicOperationCardActivity.show(this, usbSerialPort);

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

    private void stopIoManager() {
        if (serialInputOutputManager != null) {
            Log.i(TAG, "Stopping io manager ..");
            serialInputOutputManager.stop();
            serialInputOutputManager = null;
        }
    }

    private void startIoManager(byte[] btys) {
        if (usbSerialPort != null) {
            Log.i(TAG, "Starting io manager ..");
            serialInputOutputManager = new SerialInputOutputManager(usbSerialPort, mListener);
            serialInputOutputManager.writeAsync(btys);
            mExecutor.submit(serialInputOutputManager);
        }
    }

    private void onDeviceStateChange(byte[] btys) {
        stopIoManager();
        startIoManager(btys);
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
                if (result != null && result.size() > 0) {
                    usbSerialPort = result.get(0);
                }
                Log.d(TAG, "Done refreshing, " + result.size() + " entries found.");
            }

        }.execute((Void) null);
    }

    private void updateReceivedData(byte[] data) {
        final String message = "Read " + data.length + " bytes: \n"
                + HexDump.dumpHexString(data) + "\n\n";
        mDumpTextView.append(message);
        mScrollView.smoothScrollTo(0, mDumpTextView.getBottom());
    }
}
