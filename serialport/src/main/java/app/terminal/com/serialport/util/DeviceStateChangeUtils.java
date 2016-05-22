package app.terminal.com.serialport.util;

import android.hardware.usb.UsbDeviceConnection;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import app.terminal.com.serialport.driver.UsbSerialPort;

/**
 * Created by sly on 2016/5/18.
 */
public class DeviceStateChangeUtils {
    private final ExecutorService mExecutor = Executors.newSingleThreadExecutor();
    private SerialInputOutputManager serialInputOutputManager;
    private static DeviceStateChangeUtils deviceStateChangeUtils;
    private static UsbSerialPort serialPort;
    private int count = 0;

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
                public void onNewData(byte[] data) {
                    Log.i("DeviceStateChangeUtils", HexDump.toHexString(data));

                }

                @Override
                public void onRunError(Exception e) {
//                    Log.d(TAG, "Runner stopped.");
                }


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
