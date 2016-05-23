package app.terminal.com.serialport.util;

import android.content.Context;
import android.hardware.usb.UsbDeviceConnection;
import android.util.Log;
import android.widget.Toast;

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
    private static String currentOperateStr;
    private int count = 0;
    private StringBuilder msgSb = new StringBuilder();

    private DeviceStateChangeUtils() {

    }

    public static DeviceStateChangeUtils getInstence(UsbSerialPort serialPort) {
        DeviceStateChangeUtils.serialPort = serialPort;
        if (deviceStateChangeUtils == null) {
            deviceStateChangeUtils = new DeviceStateChangeUtils();
        }
        return deviceStateChangeUtils;
    }

    public static void setCurrentExeOperate(String operateStr) {
        currentOperateStr = operateStr;
    }

    private final SerialInputOutputManager.Listener mListener =
            new SerialInputOutputManager.Listener() {


                @Override
                public void onNewData(byte[] data) {
                    if (count % 2 == 0) {
                        msgSb.append("发送命令:").append(HexDump.toHexString(data)).append(System.getProperty("line.separator"));
                    } else {
                        msgSb.append("接收数据：").append(HexDump.toHexString(data));
                        if (CheckResponeData.isOk(data)) {
                            msgSb.append(currentOperateStr).append("执行成功").append(System.getProperty("line.separator"));
                        } else {
                            msgSb.append(CheckResponeData.getErrorInfo(data)).append(System.getProperty("line.separator"));
                        }
                    }
                    count++;
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
