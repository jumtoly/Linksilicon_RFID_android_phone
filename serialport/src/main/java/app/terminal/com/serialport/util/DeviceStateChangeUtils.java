package app.terminal.com.serialport.util;

import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import app.terminal.com.serialport.driver.UsbSerialPort;
import app.terminal.com.serialport.inter.ResponeDataIntface;

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
    private ResponeDataIntface responeDataIntface;

    private DeviceStateChangeUtils() {

    }

    public static DeviceStateChangeUtils getInstence(UsbSerialPort serialPort) {
        DeviceStateChangeUtils.serialPort = serialPort;
        if (deviceStateChangeUtils == null) {
            deviceStateChangeUtils = new DeviceStateChangeUtils();
        }
        return deviceStateChangeUtils;
    }

    public void setResponeDataIntface(ResponeDataIntface responeDataIntface) {
        this.responeDataIntface = responeDataIntface;
    }

    private ResponeDataIntface getResponeDataIntface() {
        return responeDataIntface;
    }

    public static void setCurrentExeOperate(String operateStr) {
        currentOperateStr = operateStr;
    }

    private final SerialInputOutputManager.Listener mListener =
            new SerialInputOutputManager.Listener() {


                @Override
                public void onNewData(byte[] data) {
                    Log.i("DeviceStateChangeUtils", HexDump.toHexString(data));
                    if (count % 2 == 0) {
                        if (getResponeDataIntface() != null) {
                            getResponeDataIntface().sendData(data);
                        }
                        msgSb.append("发送命令:").append(HexDump.toHexString(data)).append(System.getProperty("line.separator"));
                    } else {
                        getResponeDataIntface().responseData(data);
                        msgSb.append("接收数据：").append(HexDump.toHexString(data));
                        if (CheckResponeData.isOk(data)) {
                            msgSb.append(currentOperateStr).append("执行成功").append(System.getProperty("line.separator"));
                        } else {
                            msgSb.append(CheckResponeData.getErrorInfo(data,0)).append(System.getProperty("line.separator"));
                        }
                    }


                }

                @Override
                public void onRunError(Exception e) {
                    Log.d("DeviceStateChangeUtils", "Runner stopped.");
                }


            };

    public void onDeviceStateChange(byte[] btys, ResponeDataIntface responeDataIntface) {
        stopIoManager();
        startIoManager(btys, responeDataIntface);
    }

    public void onDeviceStateChange(byte[] btys) {
        stopIoManager();
//        startIoManager(btys, responeDataIntface);
    }

    private void stopIoManager() {
        if (serialInputOutputManager != null) {
//            Log.i(TAG, "Stopping io manager ..");
            serialInputOutputManager.stop();
            serialInputOutputManager = null;
        }
    }

    private byte[] startIoManager(byte[] btys, ResponeDataIntface responeDataIntface) {
        final byte[][] bytes = {null};
        if (serialPort != null) {

//            Log.i(TAG, "Starting io manager ..");
            serialInputOutputManager = new SerialInputOutputManager(serialPort, responeDataIntface /*new ResponeDataIntface() {
                @Override
                public void responseData(byte[] data) {
                    Log.i("DeviceStateChangeUtils", "responseData：" + HexDump.toHexString(data));
                    bytes[0] = data;
                }

                @Override
                public void sendData(byte[] data) {
                    Log.i("DeviceStateChangeUtils", "sendData：" + HexDump.toHexString(data));
                }

                @Override
                public void onRunError(Exception e) {
                    Log.i("DeviceStateChangeUtils", "onRunError：" + e.toString());
                }
            }*/);
            serialInputOutputManager.writeAsync(btys);
            mExecutor.submit(serialInputOutputManager);
        }
        return bytes[0];
    }
}
