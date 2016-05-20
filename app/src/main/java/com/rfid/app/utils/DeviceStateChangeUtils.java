package com.rfid.app.utils;

import android.util.Log;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import app.terminal.com.serialport.driver.UsbSerialPort;
import app.terminal.com.serialport.util.SerialInputOutputManager;

/**
 * Created by sly on 2016/5/18.
 */
public class DeviceStateChangeUtils {
    private final ExecutorService mExecutor = Executors.newSingleThreadExecutor();
    private SerialInputOutputManager serialInputOutputManager;
    private static DeviceStateChangeUtils deviceStateChangeUtils;
    private static UsbSerialPort serialPort;

    private DeviceStateChangeUtils() {

    }

    public static DeviceStateChangeUtils getInstence(UsbSerialPort serialPort) {
        DeviceStateChangeUtils.serialPort = serialPort;
        if (deviceStateChangeUtils == null) {
            deviceStateChangeUtils = new DeviceStateChangeUtils();
        }
        return deviceStateChangeUtils;
    }


    private final SerialInputOutputManager.Listener mListener =
            new SerialInputOutputManager.Listener() {

                @Override
                public void onReqonseData(byte[] data) {
                    Log.i("DeviceStateChangeUtils", data + "");
                }

                @Override
                public void onRequestData(byte[] data) {
                    Log.i("DeviceStateChangeUtils", data + "");
                }

                @Override
                public void onRunError(Exception e) {
//                    Log.d(TAG, "Runner stopped.");
                }


                   /* MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            MainActivity.this.updateReceivedData(data);
                        }
                    });*/
            };

    public void onDeviceStateChange(byte[] btys) {
        stopIoManager();
        startIoManager(btys);
    }

    private void stopIoManager() {
        if (serialInputOutputManager != null) {
//            Log.i(TAG, "Stopping io manager ..");
            serialInputOutputManager.stop();
            serialInputOutputManager = null;
        }
    }

    private void startIoManager(byte[] btys) {
        if (serialPort != null) {
//            Log.i(TAG, "Starting io manager ..");
            serialInputOutputManager = new SerialInputOutputManager(serialPort, mListener);
            serialInputOutputManager.writeAsync(btys);
            mExecutor.submit(serialInputOutputManager);
        }
    }
}
