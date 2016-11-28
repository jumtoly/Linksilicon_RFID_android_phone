package app.terminal.com.serialport.util;

import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import app.terminal.com.serialport.driver.UsbSerialDriver;
import app.terminal.com.serialport.driver.UsbSerialPort;
import app.terminal.com.serialport.driver.UsbSerialProber;
import app.terminal.com.serialport.inter.BroadcastIntface;
import app.terminal.com.serialport.inter.ControlLinksilliconCardIntface;
import app.terminal.com.serialport.inter.ResponeDataIntface;

/**
 * Created by sly on 2016/5/21.
 */
public class SerialportControl implements ControlLinksilliconCardIntface {
    private static final String TAG = SerialportControl.class.getSimpleName();
    private UsbSerialPort usbSerialPort;
    private static boolean isReaderOpen;


    @Override
    public boolean openReader(UsbManager usbManager, int baudRate, int dataBits, int stopBits, int parity) throws IOException {
        if (SerialPortEntity.getInstance().getSerialPort() != null) {
            UsbDeviceConnection connection = usbManager.openDevice(SerialPortEntity.getInstance().getSerialPort().getDriver().getDevice());
            SerialPortEntity.getInstance().setConnection(connection);
            SerialPortEntity.getInstance().getSerialPort().open(connection);
            SerialPortEntity.getInstance().getSerialPort().setParameters(baudRate, dataBits, stopBits, parity);
            isReaderOpen = true;
        } else {
            isReaderOpen = false;

        }
        return isReaderOpen;
    }

    @Override
    public void closeReader(UsbManager usbManager) throws IOException {
       /* UsbDeviceConnection connection = usbManager.openDevice(SerialPortEntity.getInstance().getSerialPort().getDriver().getDevice());
        SerialPortEntity.getInstance().setConnection(connection);
        SerialPortEntity.getInstance().getSerialPort().open(connection);*/
        SerialPortEntity.getInstance().getSerialPort().close();
    }

    @Override
    public boolean isReaderOpen() {
        return isReaderOpen;
    }


    @Override
    public boolean findCard(final Context context) {
        DeviceStateChangeUtils stateChangeUtils = DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort());
        ResponeDataIntface responeDataIntface = new ResponeDataIntface() {
            Intent mIntent = new Intent(BroadcastIntface.GETREADERID_BROADCASTRECEIVER);

            @Override
            public void responseData(byte[] data) {
                Log.i(TAG, "responseData：" + HexDump.toHexString(data));
                mIntent.putExtra("RESPONSEDATA", data);
                context.sendBroadcast(mIntent);

            }

            @Override
            public void sendData(byte[] data) {
                Log.i(TAG, "sendData：" + HexDump.toHexString(data));
                mIntent.putExtra("SENDDATA", data);
            }

            @Override
            public void onRunError(Exception e) {
                Log.i(TAG, "onRunError：" + e.toString());
            }

        };
        stateChangeUtils.onDeviceStateChange(SendByteData.INQUIRY_CARD_14443A_STANDARD, responeDataIntface);
        return true;
    }

    @Override
    public int wakeUp(final Context context) {
        DeviceStateChangeUtils stateChangeUtils = DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort());
        ResponeDataIntface responeDataIntface = new ResponeDataIntface() {
            Intent mIntent = new Intent(BroadcastIntface.GETREADERID_BROADCASTRECEIVER);

            @Override
            public void responseData(byte[] data) {
                Log.i(TAG, "responseData：" + HexDump.toHexString(data));
                mIntent.putExtra("RESPONSEDATA", data);
                context.sendBroadcast(mIntent);

            }

            @Override
            public void sendData(byte[] data) {
                Log.i(TAG, "sendData：" + HexDump.toHexString(data));
                mIntent.putExtra("SENDDATA", data);
            }

            @Override
            public void onRunError(Exception e) {
                Log.i(TAG, "onRunError：" + e.toString());
            }

        };
        stateChangeUtils.onDeviceStateChange(SendByteData.INQUIRY_CARD_14443A_WAKEUP, responeDataIntface);
        return 0;
    }

    @Override
    public boolean selectCard(final Context context, byte seriesLevel) {
        byte[] selectcard = SendByteData.SELECT_CARD_14443A;
        selectcard[8] = seriesLevel;
        DeviceStateChangeUtils stateChangeUtils = DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort());
        ResponeDataIntface responeDataIntface = new ResponeDataIntface() {
            Intent mIntent = new Intent(BroadcastIntface.GETREADERID_BROADCASTRECEIVER);

            @Override
            public void responseData(byte[] data) {
                Log.i(TAG, "responseData：" + HexDump.toHexString(data));
                mIntent.putExtra("RESPONSEDATA", data);
                context.sendBroadcast(mIntent);

            }

            @Override
            public void sendData(byte[] data) {
                Log.i(TAG, "sendData：" + HexDump.toHexString(data));
                mIntent.putExtra("SENDDATA", data);
            }

            @Override
            public void onRunError(Exception e) {
                Log.i(TAG, "onRunError：" + e.toString());
            }

        };
        stateChangeUtils.onDeviceStateChange(selectcard, responeDataIntface);
        return true;
    }

    @Override
    public boolean conflict(final Context context, byte serieslevel) {
        DeviceStateChangeUtils stateChangeUtils = DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort());
        ResponeDataIntface responeDataIntface = new ResponeDataIntface() {
            Intent mIntent = new Intent(BroadcastIntface.CONFLICT_BROADCASTRECEIVER);

            @Override
            public void responseData(byte[] data) {
                Log.i(TAG, "responseData：" + HexDump.toHexString(data));
                mIntent.putExtra("RESPONSEDATA", data);
                context.sendBroadcast(mIntent);

            }

            @Override
            public void sendData(byte[] data) {
                Log.i(TAG, "sendData：" + HexDump.toHexString(data));
                mIntent.putExtra("SENDDATA", data);
            }

            @Override
            public void onRunError(Exception e) {
                Log.i(TAG, "onRunError：" + e.toString());
            }

        };
        byte[] collision14443a = SendByteData.ANTI_COLLISION_14443A;
        collision14443a[8] = (byte) serieslevel;
        stateChangeUtils.onDeviceStateChange(collision14443a, responeDataIntface);
        return true;
    }

    @Override
    public boolean getCardInfo(final Context context) {
        DeviceStateChangeUtils stateChangeUtils = DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort());
        ResponeDataIntface responeDataIntface = new ResponeDataIntface() {
            Intent mIntent = new Intent(BroadcastIntface.GETCARDDATA_BROADCASTRECEIVER);

            @Override
            public void responseData(byte[] data) {
                Log.i(TAG, "responseData：" + HexDump.toHexString(data));
                mIntent.putExtra("RESPONSEDATA", data);
                context.sendBroadcast(mIntent);

            }

            @Override
            public void sendData(byte[] data) {
                Log.i(TAG, "sendData：" + HexDump.toHexString(data));
                mIntent.putExtra("SENDDATA", data);
            }

            @Override
            public void onRunError(Exception e) {
                Log.i(TAG, "onRunError：" + e.toString());
            }

        };
        stateChangeUtils.onDeviceStateChange(SendByteData.GET_CARD_INFO, responeDataIntface);
        return true;
    }

    @Override
    public boolean autoFindCard(final Context context, boolean bOn) {
        DeviceStateChangeUtils stateChangeUtils = DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort());
        ResponeDataIntface responeDataIntface = new ResponeDataIntface() {
            Intent mIntent = new Intent(BroadcastIntface.GETREADERID_BROADCASTRECEIVER);

            @Override
            public void responseData(byte[] data) {
                Log.i(TAG, "responseData：" + HexDump.toHexString(data));
                mIntent.putExtra("RESPONSEDATA", data);
                context.sendBroadcast(mIntent);

            }

            @Override
            public void sendData(byte[] data) {
                Log.i(TAG, "sendData：" + HexDump.toHexString(data));
                mIntent.putExtra("SENDDATA", data);
            }

            @Override
            public void onRunError(Exception e) {
                Log.i(TAG, "onRunError：" + e.toString());
            }

        };
        if (bOn) {
            stateChangeUtils.onDeviceStateChange(SendByteData.START_AUTO_FIND_CARD, responeDataIntface);
        } else {
            stateChangeUtils.onDeviceStateChange(SendByteData.STOP_AUTO_FIND_CARD, responeDataIntface);
        }

        return true;
    }

    @Override
    public boolean buzzerOn(final Context context, boolean bOn) {
        DeviceStateChangeUtils stateChangeUtils = DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort());
        ResponeDataIntface responeDataIntface = new ResponeDataIntface() {
            Intent mIntent = new Intent(BroadcastIntface.GETREADERID_BROADCASTRECEIVER);

            @Override
            public void responseData(byte[] data) {
                Log.i(TAG, "responseData：" + HexDump.toHexString(data));
                mIntent.putExtra("RESPONSEDATA", data);
                context.sendBroadcast(mIntent);

            }

            @Override
            public void sendData(byte[] data) {
                Log.i(TAG, "sendData：" + HexDump.toHexString(data));
                mIntent.putExtra("SENDDATA", data);
            }

            @Override
            public void onRunError(Exception e) {
                Log.i(TAG, "onRunError：" + e.toString());
            }

        };
        if (bOn) {
            stateChangeUtils.onDeviceStateChange(SendByteData.START_BUZZER, responeDataIntface);
        } else {
            stateChangeUtils.onDeviceStateChange(SendByteData.STOP_BUZZER, responeDataIntface);

        }
        return true;
    }

    @Override
    public boolean antennaOff(final Context context) {
        DeviceStateChangeUtils stateChangeUtils = DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort());
        ResponeDataIntface responeDataIntface = new ResponeDataIntface() {
            Intent mIntent = new Intent(BroadcastIntface.GETREADERID_BROADCASTRECEIVER);

            @Override
            public void responseData(byte[] data) {
                Log.i(TAG, "responseData：" + HexDump.toHexString(data));
                mIntent.putExtra("RESPONSEDATA", data);
                context.sendBroadcast(mIntent);

            }

            @Override
            public void sendData(byte[] data) {
                Log.i(TAG, "sendData：" + HexDump.toHexString(data));
                mIntent.putExtra("SENDDATA", data);
            }

            @Override
            public void onRunError(Exception e) {
                Log.i(TAG, "onRunError：" + e.toString());
            }

        };
        stateChangeUtils.onDeviceStateChange(SendByteData.STOP_ALL_ANTENNA, responeDataIntface);
        return true;
    }

    @Override
    public boolean manualCard(final Context context) {
        DeviceStateChangeUtils stateChangeUtils = DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort());
        ResponeDataIntface responeDataIntface = new ResponeDataIntface() {
            Intent mIntent = new Intent(BroadcastIntface.GETREADERID_BROADCASTRECEIVER);

            @Override
            public void responseData(byte[] data) {
                Log.i(TAG, "responseData：" + HexDump.toHexString(data));
                mIntent.putExtra("RESPONSEDATA", data);
                context.sendBroadcast(mIntent);

            }

            @Override
            public void sendData(byte[] data) {
                Log.i(TAG, "sendData：" + HexDump.toHexString(data));
                mIntent.putExtra("SENDDATA", data);
            }

            @Override
            public void onRunError(Exception e) {
                Log.i(TAG, "onRunError：" + e.toString());
            }

        };
        stateChangeUtils.onDeviceStateChange(SendByteData.MANUALLY_DETECTING_CARD, responeDataIntface);
        return true;
    }

    @Override
    public boolean getReaderId(final Context context) {
        final boolean[] result = {false};
        DeviceStateChangeUtils stateChangeUtils = DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort());
        ResponeDataIntface responeDataIntface = new ResponeDataIntface() {
            Intent mIntent = new Intent(BroadcastIntface.GETREADERID_BROADCASTRECEIVER);

            @Override
            public void responseData(byte[] data) {
                Log.i(TAG, "responseData：" + HexDump.toHexString(data));
                mIntent.putExtra("RESPONSEDATA", data);
                context.sendBroadcast(mIntent);

            }

            @Override
            public void sendData(byte[] data) {
                Log.i(TAG, "sendData：" + HexDump.toHexString(data));
                mIntent.putExtra("SENDDATA", data);
            }

            @Override
            public void onRunError(Exception e) {
                Log.i(TAG, "onRunError：" + e.toString());
            }

        };
        stateChangeUtils.onDeviceStateChange(SendByteData.SERIAL_NUMBER_BYTE, responeDataIntface);

        return true;
    }

    @Override
    public boolean composeFindCard(final Context context) {
        DeviceStateChangeUtils stateChangeUtils = DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort());
        ResponeDataIntface responeDataIntface = new ResponeDataIntface() {
            Intent mIntent = new Intent(BroadcastIntface.GETREADERID_BROADCASTRECEIVER);

            @Override
            public void responseData(byte[] data) {
                Log.i(TAG, "responseData：" + HexDump.toHexString(data));
                mIntent.putExtra("RESPONSEDATA", data);
                context.sendBroadcast(mIntent);

            }

            @Override
            public void sendData(byte[] data) {
                Log.i(TAG, "sendData：" + HexDump.toHexString(data));
                mIntent.putExtra("SENDDATA", data);
            }

            @Override
            public void onRunError(Exception e) {
                Log.i(TAG, "onRunError：" + e.toString());
            }

        };
        stateChangeUtils.onDeviceStateChange(SendByteData.COMPOSITE_DETECTING_CARD_14443A, responeDataIntface);
        return true;
    }

    @Override
    public boolean pauseCard(final Context context) {
        DeviceStateChangeUtils stateChangeUtils = DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort());
        ResponeDataIntface responeDataIntface = new ResponeDataIntface() {
            Intent mIntent = new Intent(BroadcastIntface.GETREADERID_BROADCASTRECEIVER);

            @Override
            public void responseData(byte[] data) {
                Log.i(TAG, "responseData：" + HexDump.toHexString(data));
                mIntent.putExtra("RESPONSEDATA", data);
                context.sendBroadcast(mIntent);

            }

            @Override
            public void sendData(byte[] data) {
                Log.i(TAG, "sendData：" + HexDump.toHexString(data));
                mIntent.putExtra("SENDDATA", data);
            }

            @Override
            public void onRunError(Exception e) {
                Log.i(TAG, "onRunError：" + e.toString());
            }

        };
        stateChangeUtils.onDeviceStateChange(SendByteData.DORMANCY_14443A, responeDataIntface);
        return true;
    }

    @Override
    public boolean setBaudRate(final Context context, int baud) {
        byte[] baudRate = SendByteData.BAUD_RATE;
        switch (baud) {
            case 2400:
                baudRate[8] = 0x01;
                break;
            case 4800:
                baudRate[8] = 0x02;
                break;
            case 9600:
                baudRate[8] = 0x03;
                break;
            case 19200:
                baudRate[8] = 0x04;
                break;
            case 38400:
                baudRate[8] = 0x05;
                break;
            case 57600:
                baudRate[8] = 0x06;
                break;
            case 115200:
                baudRate[8] = 0x07;
                break;
        }
        ResponeDataIntface responeDataIntface = new ResponeDataIntface() {
            Intent mIntent = new Intent(BroadcastIntface.GETREADERID_BROADCASTRECEIVER);

            @Override
            public void responseData(byte[] data) {
                Log.i(TAG, "responseData：" + HexDump.toHexString(data));
                mIntent.putExtra("RESPONSEDATA", data);
                context.sendBroadcast(mIntent);

            }

            @Override
            public void sendData(byte[] data) {
                Log.i(TAG, "sendData：" + HexDump.toHexString(data));
                mIntent.putExtra("SENDDATA", data);
            }

            @Override
            public void onRunError(Exception e) {
                Log.i(TAG, "onRunError：" + e.toString());
            }
        };
        baudRate[9] = CheckSum(baudRate, 9);
        DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort()).onDeviceStateChange(baudRate, responeDataIntface);
        return true;
    }


    @Override
    public boolean checkKey(final Context context, CardData cardData) {
        byte[] authenticationM1 = SendByteData.KEY_AUTHENTICATION_M1;
        if (cardData.getFindAddrType() == FindAddrType.ABSOLUTE_ADDR) {
            authenticationM1[8] = 1;
            authenticationM1[9] = 0;
            authenticationM1[10] = cardData.getBlockAddr();
        } else {
            if ((cardData.getBlockAddr() >= 64) && (cardData.getCardType() == CardType.S50)) {
                //TODO 块地址设置错误
                return false;
            } else if ((cardData.getBlockAddr() >= 256) && (cardData.getCardType() == CardType.S70)) {
                //TODO 块地址设置错误
                return false;
            }
            authenticationM1[8] = 0;
            authenticationM1[9] = cardData.getSectorAddr();
            authenticationM1[10] = cardData.getBlockAddr();
        }
        if (cardData.getKeyType() == KeyType.KEY_A) {
            authenticationM1[11] = 0x60;
        } else if (cardData.getKeyType() == KeyType.KEY_B) {
            authenticationM1[11] = 0x61;
        }

        for (int i = 12, j = 0; i < 18; i++, j++) {
            authenticationM1[i] = cardData.getKey()[j];
        }
        DeviceStateChangeUtils stateChangeUtils = DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort());
        ResponeDataIntface responeDataIntface = new ResponeDataIntface() {
            Intent mIntent = new Intent(BroadcastIntface.GETREADERID_BROADCASTRECEIVER);

            @Override
            public void responseData(byte[] data) {
                Log.i(TAG, "responseData：" + HexDump.toHexString(data));
                mIntent.putExtra("RESPONSEDATA", data);
                context.sendBroadcast(mIntent);

            }

            @Override
            public void sendData(byte[] data) {
                Log.i(TAG, "sendData：" + HexDump.toHexString(data));
                mIntent.putExtra("SENDDATA", data);
            }

            @Override
            public void onRunError(Exception e) {
                Log.i(TAG, "onRunError：" + e.toString());
            }

        };
        stateChangeUtils.onDeviceStateChange(authenticationM1, responeDataIntface);
        return true;
    }

    @Override
    public boolean readBlock(final Context context, CardData cardData) {

        byte[] readBlockM1 = SendByteData.READ_BLOCK_M1;
        if (cardData.getFindAddrType() == FindAddrType.ABSOLUTE_ADDR) {
            readBlockM1[8] = 1;
            readBlockM1[9] = 0;
            readBlockM1[10] = cardData.getBlockAddr();
        } else {
            if ((cardData.getBlockAddr() >= 64) && (cardData.getCardType() == CardType.S50)) {
                //TODO 块地址设置错误
                return false;
            } else if ((cardData.getBlockAddr() >= 256) && (cardData.getCardType() == CardType.S70)) {
                //TODO 块地址设置错误
                return false;
            }
            readBlockM1[8] = 0;
            readBlockM1[9] = cardData.getSectorAddr();
            readBlockM1[10] = cardData.getBlockAddr();
        }
        readBlockM1[11] = CheckSum(readBlockM1, 12);
        DeviceStateChangeUtils stateChangeUtils = DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort());
        ResponeDataIntface responeDataIntface = new ResponeDataIntface() {
            Intent mIntent = new Intent(BroadcastIntface.GETREADERID_BROADCASTRECEIVER);

            @Override
            public void responseData(byte[] data) {
                Log.i(TAG, "responseData：" + HexDump.toHexString(data));
                mIntent.putExtra("RESPONSEDATA", data);
                context.sendBroadcast(mIntent);

            }

            @Override
            public void sendData(byte[] data) {
                Log.i(TAG, "sendData：" + HexDump.toHexString(data));
                mIntent.putExtra("SENDDATA", data);
            }

            @Override
            public void onRunError(Exception e) {
                Log.i(TAG, "onRunError：" + e.toString());
            }

        };
        stateChangeUtils.onDeviceStateChange(readBlockM1, responeDataIntface);
        return true;
    }

    byte CheckSum(byte str[], int len) {
        byte checksum = str[0];
        for (int i = 1; i < len; i++) {
            checksum ^= str[i];
        }
        return checksum;
    }

    @Override
    public boolean writeBlock(final Context context, CardData cardData) {
        byte[] writeblock = SendByteData.WRITE_BLOCK_M1;

        if (cardData.getFindAddrType() == FindAddrType.ABSOLUTE_ADDR) {
            writeblock[8] = 1;
            writeblock[9] = 0;
            writeblock[10] = cardData.getBlockAddr();
        } else {
            if ((cardData.getBlockAddr() >= 64) && (cardData.getCardType() == CardType.S50)) {
                //TODO 块地址设置错误
                return false;
            } else if ((cardData.getBlockAddr() >= 256) && (cardData.getCardType() == CardType.S70)) {
                //TODO 块地址设置错误
                return false;
            }
            writeblock[8] = 0;
            writeblock[9] = cardData.getSectorAddr();
            writeblock[10] = cardData.getBlockAddr();
        }

        for (int i = 11, j = 0; i < 27; i++, j++) {
            writeblock[i] = cardData.getwriteData()[j];
        }

        if (CheckBlockAddr(writeblock[8], writeblock[9], writeblock[10])) {
            //if(AfxMessageBox(_T("确定修改控制块？\r\n（如果数据不正确可能导致此扇区锁死）"),MB_ICONINFORMATION|MB_YESNO)==IDNO)
            return false;
        }

        writeblock[27] = CheckSum(writeblock, 28);
        DeviceStateChangeUtils stateChangeUtils = DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort());
        ResponeDataIntface responeDataIntface = new ResponeDataIntface() {
            Intent mIntent = new Intent(BroadcastIntface.GETREADERID_BROADCASTRECEIVER);

            @Override
            public void responseData(byte[] data) {
                Log.i(TAG, "responseData：" + HexDump.toHexString(data));
                mIntent.putExtra("RESPONSEDATA", data);
                context.sendBroadcast(mIntent);

            }

            @Override
            public void sendData(byte[] data) {
                Log.i(TAG, "sendData：" + HexDump.toHexString(data));
                mIntent.putExtra("SENDDATA", data);
            }

            @Override
            public void onRunError(Exception e) {
                Log.i(TAG, "onRunError：" + e.toString());
            }

        };
        stateChangeUtils.onDeviceStateChange(writeblock, responeDataIntface);
        return true;
    }

    @Override
    public boolean walletInit(final Context context, CardData cardData) {
        byte[] walletInitializationM1 = SendByteData.WALLET_INITIALIZATION_M1;

        if (cardData.getFindAddrType() == FindAddrType.ABSOLUTE_ADDR) {
            walletInitializationM1[8] = 1;
            walletInitializationM1[9] = 0;
            walletInitializationM1[10] = cardData.getBlockAddr();
        } else {
            if ((cardData.getBlockAddr() >= 64) && (cardData.getCardType() == CardType.S50)) {
                //TODO 块地址设置错误
                return false;
            } else if ((cardData.getBlockAddr() >= 256) && (cardData.getCardType() == CardType.S70)) {
                //TODO 块地址设置错误
                return false;
            }
            walletInitializationM1[8] = 0;
            walletInitializationM1[9] = cardData.getSectorAddr();
            walletInitializationM1[10] = cardData.getBlockAddr();
        }

        if (CheckBlockAddr(walletInitializationM1[8], walletInitializationM1[9], walletInitializationM1[10])) {
            //TODO 钱包初始化错误
            return false;
        }
        int itemp = HexDump.byte4ToInt(cardData.getWriteMoney());
        walletInitializationM1[11] = (byte) (itemp % 256);
        walletInitializationM1[12] = (byte) (itemp / 256);
        itemp -= (walletInitializationM1[11] + walletInitializationM1[12] * 0x100);
        itemp = itemp / 65536;
        walletInitializationM1[13] = (byte) (itemp % 256);
        walletInitializationM1[14] = (byte) (itemp / 256);
        walletInitializationM1[15] = CheckSum(walletInitializationM1, 16);
        DeviceStateChangeUtils stateChangeUtils = DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort());
        ResponeDataIntface responeDataIntface = new ResponeDataIntface() {
            Intent mIntent = new Intent(BroadcastIntface.GETREADERID_BROADCASTRECEIVER);

            @Override
            public void responseData(byte[] data) {
                Log.i(TAG, "responseData：" + HexDump.toHexString(data));
                mIntent.putExtra("RESPONSEDATA", data);
                context.sendBroadcast(mIntent);

            }

            @Override
            public void sendData(byte[] data) {
                Log.i(TAG, "sendData：" + HexDump.toHexString(data));
                mIntent.putExtra("SENDDATA", data);
            }

            @Override
            public void onRunError(Exception e) {
                Log.i(TAG, "onRunError：" + e.toString());
            }

        };
        stateChangeUtils.onDeviceStateChange(walletInitializationM1, responeDataIntface);
        return true;
    }

    @Override
    public String readWallet(final Context context, CardData cardData) {
        final boolean[] isOK = new boolean[1];
        byte[] purseReadM1 = SendByteData.PURSE_READ_M1;

        if (cardData.getFindAddrType() == FindAddrType.ABSOLUTE_ADDR) {
            purseReadM1[8] = 1;
            purseReadM1[9] = 0;
            purseReadM1[10] = cardData.getBlockAddr();
        } else {
            if ((cardData.getBlockAddr() >= 64) && (cardData.getCardType() == CardType.S50)) {
                //块地址设置错误
                return "";
            } else if ((cardData.getBlockAddr() >= 256) && (cardData.getCardType() == CardType.S70)) {
                //块地址设置错误
                return "";
            }
            purseReadM1[8] = 0;
            purseReadM1[9] = cardData.getSectorAddr();
            purseReadM1[10] = cardData.getBlockAddr();
        }

        if (CheckBlockAddr(purseReadM1[8], purseReadM1[9], purseReadM1[10])) {
            //钱包初始化错误
            return "";
        }

        final int[] result = {0};
        purseReadM1[11] = CheckSum(purseReadM1, 12);
        DeviceStateChangeUtils stateChangeUtils = DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort());
        ResponeDataIntface responeDataIntface = new ResponeDataIntface() {
            Intent mIntent = new Intent(BroadcastIntface.GETREADERID_BROADCASTRECEIVER);

            @Override
            public void responseData(byte[] data) {
                Log.i(TAG, "responseData：" + HexDump.toHexString(data));
                mIntent.putExtra("RESPONSEDATA", data);
                context.sendBroadcast(mIntent);

            }

            @Override
            public void sendData(byte[] data) {
                Log.i(TAG, "sendData：" + HexDump.toHexString(data));
                mIntent.putExtra("SENDDATA", data);
                result[0] = data[7] + data[8] * 0x100 + data[9] * 0x10000 + data[10] * 0x1000000;
            }

            @Override
            public void onRunError(Exception e) {
                Log.i(TAG, "onRunError：" + e.toString());
            }

        };
        stateChangeUtils.onDeviceStateChange(purseReadM1, responeDataIntface);
        SystemClock.sleep(200);

        return result[0] + "";
    }

    @Override
    public boolean walletAdd(final Context context, CardData cardData) {
        final boolean[] isOK = new boolean[1];
        byte[] walletRechargeM1 = SendByteData.WALLET_RECHARGE_M1;

        if (cardData.getFindAddrType() == FindAddrType.ABSOLUTE_ADDR) {
            walletRechargeM1[9] = 1;
            walletRechargeM1[10] = 0;
            walletRechargeM1[11] = cardData.getBlockAddr();
        } else {
            if ((cardData.getBlockAddr() >= 64) && (cardData.getCardType() == CardType.S50)) {
                //块地址设置错误
                return isOK[0] = false;
            } else if ((cardData.getBlockAddr() >= 256) && (cardData.getCardType() == CardType.S70)) {
                //块地址设置错误
                return isOK[0] = false;
            }
            walletRechargeM1[9] = 0;
            walletRechargeM1[10] = cardData.getSectorAddr();
            walletRechargeM1[11] = cardData.getBlockAddr();
        }

        if (CheckBlockAddr(walletRechargeM1[9], walletRechargeM1[10], walletRechargeM1[11])) {
            //TODO 钱包初始化错误
            return false;
        }
        int itemp = HexDump.byte4ToInt(cardData.getWriteMoney());
        walletRechargeM1[12] = (byte) (itemp % 256);
        walletRechargeM1[13] = (byte) (itemp / 256);
        itemp = (itemp - (walletRechargeM1[12] + walletRechargeM1[13] * 256));
        itemp = itemp / 65536;
        walletRechargeM1[14] = (byte) (itemp % 65536);
        walletRechargeM1[15] = (byte) (itemp / 65536);
        walletRechargeM1[8] = 0x01;
        walletRechargeM1[16] = CheckSum(walletRechargeM1, 17);
        DeviceStateChangeUtils stateChangeUtils = DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort());
        ResponeDataIntface responeDataIntface = new ResponeDataIntface() {
            Intent mIntent = new Intent(BroadcastIntface.GETREADERID_BROADCASTRECEIVER);

            @Override
            public void responseData(byte[] data) {
                Log.i(TAG, "responseData：" + HexDump.toHexString(data));
                isOK[0] = CheckResponeData.isOk(data);
                mIntent.putExtra("RESPONSEDATA", data);
                context.sendBroadcast(mIntent);

            }

            @Override
            public void sendData(byte[] data) {
                Log.i(TAG, "sendData：" + HexDump.toHexString(data));
                mIntent.putExtra("SENDDATA", data);
            }

            @Override
            public void onRunError(Exception e) {
                isOK[0] = false;
                Log.i(TAG, "onRunError：" + e.toString());
            }

        };
        stateChangeUtils.onDeviceStateChange(walletRechargeM1, responeDataIntface);
        SystemClock.sleep(200);
        return isOK[0];
    }

    @Override
    public boolean walletDec(final Context context, CardData cardData) {
        final boolean[] isOK = new boolean[1];
        byte[] purseDecrementM1 = SendByteData.PURSE_DECREMENT_M1;
        if (cardData.getFindAddrType() == FindAddrType.ABSOLUTE_ADDR) {
            purseDecrementM1[9] = 1;
            purseDecrementM1[10] = 0;
            purseDecrementM1[11] = cardData.getBlockAddr();
        } else {
            if ((cardData.getBlockAddr() >= 64) && (cardData.getCardType() == CardType.S50)) {
                //块地址设置错误
                return isOK[0] = false;
            } else if ((cardData.getBlockAddr() >= 64) && (cardData.getCardType() == CardType.S70)) {
                //块地址设置错误
                return isOK[0] = false;
            }
            purseDecrementM1[9] = 0;
            purseDecrementM1[10] = cardData.getSectorAddr();
            purseDecrementM1[11] = cardData.getBlockAddr();
        }

        if (CheckBlockAddr(purseDecrementM1[9], purseDecrementM1[10], purseDecrementM1[11])) {
            //钱包初始化错误
            return isOK[0] = false;
        }

        int itemp = HexDump.byte4ToInt(cardData.getWriteMoney());
        purseDecrementM1[12] = (byte) (itemp % 256);
        purseDecrementM1[13] = (byte) (itemp / 256);
        itemp = (itemp - (purseDecrementM1[12] + purseDecrementM1[13] * 256));
        itemp = itemp / 65536;
        purseDecrementM1[14] = (byte) (itemp % 65536);
        purseDecrementM1[15] = (byte) (itemp / 65536);
        purseDecrementM1[8] = 0x01;
        purseDecrementM1[16] = CheckSum(purseDecrementM1, 17);
        DeviceStateChangeUtils stateChangeUtils = DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort());
        ResponeDataIntface responeDataIntface = new ResponeDataIntface() {
            Intent mIntent = new Intent(BroadcastIntface.GETREADERID_BROADCASTRECEIVER);

            @Override
            public void responseData(byte[] data) {
                Log.i(TAG, "responseData：" + HexDump.toHexString(data));
                isOK[0] = CheckResponeData.isOk(data);
                mIntent.putExtra("RESPONSEDATA", data);
                context.sendBroadcast(mIntent);

            }

            @Override
            public void sendData(byte[] data) {
                Log.i(TAG, "sendData：" + HexDump.toHexString(data));
                mIntent.putExtra("SENDDATA", data);
            }

            @Override
            public void onRunError(Exception e) {
                isOK[0] = false;
                Log.i(TAG, "onRunError：" + e.toString());
            }

        };
        stateChangeUtils.onDeviceStateChange(purseDecrementM1, responeDataIntface);
        SystemClock.sleep(200);
        return isOK[0];
    }

    @Override
    public boolean composeRead(final Context context, CardData cardData) {
        final boolean[] isOK = new boolean[1];
        byte[] compositeReadBlockm1 = SendByteData.COMPOSITE_READING_BLOCK_M1;
        if (cardData.getFindAddrType() == FindAddrType.ABSOLUTE_ADDR) {
            compositeReadBlockm1[8] = 1;
            compositeReadBlockm1[9] = 0;
            compositeReadBlockm1[10] = cardData.getBlockAddr();
        } else {
            if ((cardData.getBlockAddr() >= 64) && (cardData.getCardType() == CardType.S50)) {
                //块地址设置错误
                return isOK[0] = false;
            } else if ((cardData.getBlockAddr() >= 256) && (cardData.getCardType() == CardType.S70)) {
                //块地址设置错误
                return isOK[0] = false;
            }
            compositeReadBlockm1[9] = 0;
            compositeReadBlockm1[10] = cardData.getSectorAddr();
            compositeReadBlockm1[11] = cardData.getBlockAddr();
        }
        if (cardData.getKeyType() == KeyType.KEY_A) {
            compositeReadBlockm1[12] = 0x60;
        } else if (cardData.getKeyType() == KeyType.KEY_B) {
            compositeReadBlockm1[12] = 0x61;
        }
        for (int i = 13, j = 0; i < 19; i++, j++) {
            compositeReadBlockm1[i] = cardData.getKey()[j];
        }
        compositeReadBlockm1[19] = CheckSum(compositeReadBlockm1, 20);
        DeviceStateChangeUtils stateChangeUtils = DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort());
        ResponeDataIntface responeDataIntface = new ResponeDataIntface() {
            Intent mIntent = new Intent(BroadcastIntface.GETREADERID_BROADCASTRECEIVER);

            @Override
            public void responseData(byte[] data) {
                Log.i(TAG, "responseData：" + HexDump.toHexString(data));
                isOK[0] = CheckResponeData.isOk(data);
                mIntent.putExtra("RESPONSEDATA", data);
                context.sendBroadcast(mIntent);

            }

            @Override
            public void sendData(byte[] data) {
                Log.i(TAG, "sendData：" + HexDump.toHexString(data));
                mIntent.putExtra("SENDDATA", data);
            }

            @Override
            public void onRunError(Exception e) {
                isOK[0] = false;
                Log.i(TAG, "onRunError：" + e.toString());
            }

        };
        stateChangeUtils.onDeviceStateChange(compositeReadBlockm1, responeDataIntface);
        SystemClock.sleep(200);
        return isOK[0];
    }

    @Override
    public boolean composeWrite(final Context context, CardData cardData) {
        final boolean[] isOK = new boolean[1];
        byte[] compositeWriteBlockM1 = SendByteData.COMPOSITE_WRITE_BLOCK_M1;
        if (cardData.getFindAddrType() == FindAddrType.ABSOLUTE_ADDR) {
            compositeWriteBlockM1[9] = 1;
            compositeWriteBlockM1[10] = 0;
            compositeWriteBlockM1[11] = cardData.getBlockAddr();
        } else {
            if ((cardData.getBlockAddr() >= 64) && (cardData.getCardType() == CardType.S50)) {
                //块地址设置错误
                return isOK[0] = false;
            } else if ((cardData.getBlockAddr() >= 256) && (cardData.getCardType() == CardType.S70)) {
                //块地址设置错误
                return isOK[0] = false;
            }
            compositeWriteBlockM1[9] = 0;
            compositeWriteBlockM1[10] = cardData.getSectorAddr();
            compositeWriteBlockM1[11] = cardData.getBlockAddr();
        }
        if (cardData.getKeyType() == KeyType.KEY_A) {
            compositeWriteBlockM1[12] = 0x60;
        } else if (cardData.getKeyType() == KeyType.KEY_B) {
            compositeWriteBlockM1[12] = 0x61;
        }

        for (int i = 13, j = 0; i < 19; i++, j++) {
            compositeWriteBlockM1[i] = cardData.getKey()[j];
        }

        for (int i = 19, j = 0; i < 35; i++, j++) {
            compositeWriteBlockM1[i] = cardData.getwriteData()[j];
        }
        DeviceStateChangeUtils stateChangeUtils = DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort());
        ResponeDataIntface responeDataIntface = new ResponeDataIntface() {
            Intent mIntent = new Intent(BroadcastIntface.GETREADERID_BROADCASTRECEIVER);

            @Override
            public void responseData(byte[] data) {
                Log.i(TAG, "responseData：" + HexDump.toHexString(data));
                isOK[0] = CheckResponeData.isOk(data);
                mIntent.putExtra("RESPONSEDATA", data);
                context.sendBroadcast(mIntent);

            }

            @Override
            public void sendData(byte[] data) {
                Log.i(TAG, "sendData：" + HexDump.toHexString(data));
                mIntent.putExtra("SENDDATA", data);
            }

            @Override
            public void onRunError(Exception e) {
                isOK[0] = false;
                Log.i(TAG, "onRunError：" + e.toString());
            }

        };
        compositeWriteBlockM1[35] = CheckSum(compositeWriteBlockM1, 36);
        stateChangeUtils.onDeviceStateChange(compositeWriteBlockM1, responeDataIntface);
        SystemClock.sleep(200);
        return isOK[0];
    }

    boolean CheckBlockAddr(byte findaddr, byte sectoraddr, byte blockaddr) {
        if (findaddr == 0) {
            if (blockaddr == 15) {
                return true;
            } else if ((sectoraddr < 32) && (blockaddr == 3)) {
                return true;
            }
        } else if (findaddr == 1) {
            if ((blockaddr < 128) && ((blockaddr + 1) % 4) == 0) {
                return true;
            } else if (((blockaddr + 1 - 128) % 16) == 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean modifyKey(final Context context, int sector, int keyType, byte[] newKey, byte[] oldKeyA, byte[] oldKeyB) {
        final boolean isOK[] = new boolean[1];
        byte[] modifykey = SendByteData.KEY_MODIFY;


        if (false == checkCtrlKey(context, true, sector, oldKeyA, 29)) {
            return false;
        }
        SystemClock.sleep(50);
        if (false == checkCtrlKey(context, false, sector, oldKeyB, 30)) {
            return false;
        }
        boolean bRet = readCtrlWord(context, sector, oldKeyA);
        if (bRet) {
            return false;
        }
        modifykey[10] = (byte) sector;

        if (sector >= 32) {
            modifykey[11] = 15;
        } else {
            modifykey[11] = 3;
        }

        //str.format("%s", (char*)iParam);
        byte[] order = new byte[2];
        byte ctrl = 0;
        for (int i = 0; i < 2; i++) {
            order[i] = HexDump.hexStringToByteArray(context, CreateControl.getInstance().getOldctrl())[i + 1];
        }

        if (keyType == KeyType.KEY_A) {
            modifykey[12] = 0x60;    //用密钥A写数据
            for (int i = 13, j = 0; i < 19; i++, j++) {
                modifykey[i] = oldKeyA[j];
                modifykey[i + 6] = newKey[j];
            }

            for (int i = 25, j = 0; i < 29; i++, j++) {
                modifykey[i] = HexDump.hexStringToByteArray(context, CreateControl.getInstance().getOldctrl())[j];            //4位控制字写入
            }

            for (int i = 29, j = 0; i < 35; i++, j++) {
                modifykey[i] = oldKeyB[j];
            }
        } else if (keyType == KeyType.KEY_B) {
            modifykey[12] = 0x61;    //用密钥B写数据

            for (int i = 13, j = 0; i < 19; i++, j++) {
                modifykey[i] = oldKeyB[j];
                modifykey[i + 6] = oldKeyA[j];
            }

            for (int i = 25, j = 0; i < 29; i++, j++) {
                modifykey[i] = HexDump.hexStringToByteArray(context, CreateControl.getInstance().getOldctrl())[j];            //4位控制字写入
            }

            for (int i = 29, j = 0; i < 35; i++, j++) {
                modifykey[i] = newKey[j];
            }
        }


        modifykey[35] = CheckSum(modifykey, 36);

        DeviceStateChangeUtils stateChangeUtils = DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort());
        ResponeDataIntface responeDataIntface = new ResponeDataIntface() {
            Intent mIntent = new Intent(BroadcastIntface.GETREADERID_BROADCASTRECEIVER);

            @Override
            public void responseData(byte[] data) {
                Log.i(TAG, "responseData：" + HexDump.toHexString(data));
                isOK[0] = CheckResponeData.isOk(data);
                mIntent.putExtra("RESPONSEDATA", data);
                context.sendBroadcast(mIntent);

            }

            @Override
            public void sendData(byte[] data) {
                Log.i(TAG, "sendData：" + HexDump.toHexString(data));
                mIntent.putExtra("SENDDATA", data);
            }

            @Override
            public void onRunError(Exception e) {
                isOK[0] = false;
                Log.i(TAG, "onRunError：" + e.toString());
            }

        };
        stateChangeUtils.onDeviceStateChange(modifykey, responeDataIntface);
        SystemClock.sleep(200);
        return isOK[0];
    }


    @Override
    public boolean modifyControl(final Context context, ModifyKey modifyKey, byte[] controlWord, byte[] oldControlWord) {
        final boolean isOK[] = new boolean[1];
        byte[] modify = new byte[36];
        modify[0] = 0x55;
        modify[1] = 0x55;
        modify[5] = 0x1e;
        modify[6] = 0x03;
        modify[7] = 0x07;
        modify[8] = 0x01;
        modify[10] = (byte) modifyKey.getSector();
        if (modifyKey.getSector() >= 32) {
            modify[11] = 15;
        } else {
            modify[11] = 3;
        }
        byte[] order = new byte[2];
        byte ctrl = 0;
        order[0] = oldControlWord[1];
        order[1] = oldControlWord[2];
        if ((order[0] & 0x80) >= 1)
            ctrl += 4;
        if ((order[1] & 0x08) >= 1)
            ctrl += 2;
        if ((order[1] & 0x80) >= 1)
            ctrl += 1;    //获取旧控制字并分析
        if (ctrl == 1) {
            modify[12] = 0x60;    //用密钥A写数据
            for (int i = 0; i < 6; i++) {
                modify[i + 13] = modifyKey.getaOldKey()[i];
            }
        } else if ((ctrl == 3) || (ctrl == 5)) {
            modify[12] = 0x61;    //用密钥B写数据
            for (int i = 0; i < 6; i++) {
                modify[i + 13] = modifyKey.getbOldKey()[i];
            }
        }
        for (int i = 0; i < 6; i++) {
            modify[i + 19] = modifyKey.getbOldKey()[i];
        }
        for (int i = 0; i < 4; i++) {
            modify[i + 25] = controlWord[i];
        }
        for (int i = 0; i < 6; i++) {
            modify[i + 29] = modifyKey.getbOldKey()[i];
        }
        modify[35] = CheckSum(modify, 36);
        DeviceStateChangeUtils stateChangeUtils = DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort());
        ResponeDataIntface responeDataIntface = new ResponeDataIntface() {
            Intent mIntent = new Intent(BroadcastIntface.GETREADERID_BROADCASTRECEIVER);

            @Override
            public void responseData(byte[] data) {
                Log.i(TAG, "responseData：" + HexDump.toHexString(data));
                isOK[0] = CheckResponeData.isOk(data);
                mIntent.putExtra("RESPONSEDATA", data);
                context.sendBroadcast(mIntent);

            }

            @Override
            public void sendData(byte[] data) {
                Log.i(TAG, "sendData：" + HexDump.toHexString(data));
                mIntent.putExtra("SENDDATA", data);
            }

            @Override
            public void onRunError(Exception e) {
                isOK[0] = false;
                Log.i(TAG, "onRunError：" + e.toString());
            }

        };
        stateChangeUtils.onDeviceStateChange(modify, responeDataIntface);
        SystemClock.sleep(200);
        return isOK[0];
    }

    @Override
    public boolean readCtrlWord(final Context context, int sector, byte[] key) {
        final boolean isOK[] = new boolean[1];
        byte[] readctrl = new byte[20];
        readctrl[0] = 0x55;
        readctrl[1] = 0x55;
        readctrl[5] = 0x0e;
        readctrl[6] = 0x03;
        readctrl[7] = 0x07;
        readctrl[10] = (byte) sector;
        if (sector >= 32) {
            readctrl[11] = 15;
        } else {
            readctrl[11] = 3;
        }
        if (key == null || key.length <= 0) {
            return false;
        } else {

            readctrl[12] = 0x60;
        }
        for (int i = 13, j = 0; i < 19; i++, j++) {
            readctrl[i] = key[j];
        }
        readctrl[19] = CheckSum(readctrl, 20);
        DeviceStateChangeUtils stateChangeUtils = DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort());
        ResponeDataIntface responeDataIntface = new ResponeDataIntface() {
            Intent mIntent = new Intent(BroadcastIntface.GETREADERID_BROADCASTRECEIVER);

            @Override
            public void responseData(byte[] data) {
                Log.i(TAG, "responseData：" + HexDump.toHexString(data));
                isOK[0] = true;
                mIntent.putExtra("RESPONSEDATA", data);
                context.sendBroadcast(mIntent);

            }

            @Override
            public void sendData(byte[] data) {
                Log.i(TAG, "sendData：" + HexDump.toHexString(data));
                mIntent.putExtra("SENDDATA", data);
            }

            @Override
            public void onRunError(Exception e) {
                isOK[0] = false;
                Log.i(TAG, "onRunError：" + e.toString());
            }

        };
        stateChangeUtils.onDeviceStateChange(readctrl, responeDataIntface);
        SystemClock.sleep(200);
        return isOK[0];
    }

    @Override
    public boolean checkCtrlKey(final Context context, boolean aORb, int block, byte[] key, final int order) {
        final boolean isOK[] = new boolean[1];
        byte[] checkKey = new byte[19];
        checkKey[0] = 0x55;
        checkKey[1] = 0x55;
        checkKey[5] = 0x0d;
        checkKey[6] = 0x03;
        checkKey[7] = 0x01;
        checkKey[9] = (byte) block;
        if (block >= 32) {
            checkKey[10] = 15;
        } else {
            checkKey[10] = 3;
        }

        if (aORb) {
            checkKey[11] = 0x60;
        } else {
            checkKey[11] = 0x61;
        }

        for (int i = 12, j = 0; i < 18; i++, j++) {
            checkKey[i] = key[j];
        }

        checkKey[18] = CheckSum(checkKey, 19);
        DeviceStateChangeUtils stateChangeUtils = DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort());
        ResponeDataIntface responeDataIntface = new ResponeDataIntface() {
            Intent mIntent = new Intent(BroadcastIntface.GETREADERID_BROADCASTRECEIVER);

            @Override
            public void responseData(byte[] data) {
                Log.i(TAG, "responseData：" + HexDump.toHexString(data));

                isOK[0] = CheckResponeData.isOk(data);
                mIntent.putExtra("CURRENT_ORDER", order);
                mIntent.putExtra("RESPONSEDATA", data);
                context.sendBroadcast(mIntent);

            }

            @Override
            public void sendData(byte[] data) {
                Log.i(TAG, "sendData：" + HexDump.toHexString(data));
                mIntent.putExtra("CURRENT_ORDER", order);
                mIntent.putExtra("SENDDATA", data);
            }

            @Override
            public void onRunError(Exception e) {
                isOK[0] = false;
                Log.i(TAG, "onRunError：" + e.toString());
            }

        };
        stateChangeUtils.onDeviceStateChange(checkKey, responeDataIntface);
        SystemClock.sleep(200);
        return isOK[0];
    }

    @Override
    public boolean cosActivation(final Context context) {
        final boolean[] isOK = new boolean[1];
        DeviceStateChangeUtils stateChangeUtils = DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort());
        ResponeDataIntface responeDataIntface = new ResponeDataIntface() {
            Intent mIntent = new Intent(BroadcastIntface.GETREADERID_BROADCASTRECEIVER);

            @Override
            public void responseData(byte[] data) {
                Log.i(TAG, "responseData：" + HexDump.toHexString(data));
                isOK[0] = CheckResponeData.isOk(data);
                mIntent.putExtra("RESPONSEDATA", data);
                context.sendBroadcast(mIntent);

            }

            @Override
            public void sendData(byte[] data) {
                Log.i(TAG, "sendData：" + HexDump.toHexString(data));
                mIntent.putExtra("SENDDATA", data);
            }

            @Override
            public void onRunError(Exception e) {
                isOK[0] = false;
                Log.i(TAG, "onRunError：" + e.toString());
            }

        };
        stateChangeUtils.onDeviceStateChange(SendByteData.FMCOS_ACTIVATION, responeDataIntface);
        SystemClock.sleep(200);
        return isOK[0];
    }

    @Override
    public boolean cosDeactive(final Context context) {
        final boolean[] isOK = new boolean[1];
        DeviceStateChangeUtils stateChangeUtils = DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort());
        ResponeDataIntface responeDataIntface = new ResponeDataIntface() {
            Intent mIntent = new Intent(BroadcastIntface.GETREADERID_BROADCASTRECEIVER);

            @Override
            public void responseData(byte[] data) {
                Log.i(TAG, "responseData：" + HexDump.toHexString(data));
                isOK[0] = CheckResponeData.isOk(data);
                mIntent.putExtra("RESPONSEDATA", data);
                context.sendBroadcast(mIntent);

            }

            @Override
            public void sendData(byte[] data) {
                Log.i(TAG, "sendData：" + HexDump.toHexString(data));
                mIntent.putExtra("SENDDATA", data);
            }

            @Override
            public void onRunError(Exception e) {
                isOK[0] = false;
                Log.i(TAG, "onRunError：" + e.toString());
            }

        };
        stateChangeUtils.onDeviceStateChange(SendByteData.FMCOS_STOP_LIVING, responeDataIntface);
        SystemClock.sleep(200);
        return isOK[0];
    }

    @Override
    public boolean sendCosCommand(final Context context, byte[] cosCmd) {
        final boolean[] isOK = new boolean[1];
        byte[] sendcommand = new byte[256];
        sendcommand[0] = 0x55;
        sendcommand[1] = 0x55;
        sendcommand[6] = 0x07;
        sendcommand[7] = 0x03;
        if (cosCmd == null || cosCmd.length < 0) {
            return false;
        } else if (cosCmd.length >= 90) {
            return false;
        }
        sendcommand[5] = (byte) (cosCmd.length + 3);
        sendcommand[8 + cosCmd.length] = CheckSum(sendcommand, 9 + cosCmd.length);
        for (int j = 0; j < cosCmd.length; j++) {
            sendcommand[j + 8] = cosCmd[j];
        }
        DeviceStateChangeUtils stateChangeUtils = DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort());
        ResponeDataIntface responeDataIntface = new ResponeDataIntface() {
            Intent mIntent = new Intent(BroadcastIntface.GETREADERID_BROADCASTRECEIVER);

            @Override
            public void responseData(byte[] data) {
                Log.i(TAG, "responseData：" + HexDump.toHexString(data));
                isOK[0] = CheckResponeData.isOk(data);
                mIntent.putExtra("RESPONSEDATA", data);
                context.sendBroadcast(mIntent);

            }

            @Override
            public void sendData(byte[] data) {
                Log.i(TAG, "sendData：" + HexDump.toHexString(data));
                mIntent.putExtra("SENDDATA", data);
            }

            @Override
            public void onRunError(Exception e) {
                isOK[0] = false;
                Log.i(TAG, "onRunError：" + e.toString());
            }

        };
        byte[] sendCmd = new byte[8 + cosCmd.length + 1];
        for (int i = 0; i < sendCmd.length; i++) {
            sendCmd[i] = sendcommand[i];
        }
        stateChangeUtils.onDeviceStateChange(sendCmd, responeDataIntface);
        SystemClock.sleep(200);
        return isOK[0];
    }

    @Override
    public boolean exauthentication(final Context context, byte[] key, byte[] keyFlag) {
        final boolean[] isOK = new boolean[1];
        byte[] ex = new byte[27];
        ex[0] = 0x55;
        ex[1] = 0x55;
        ex[5] = 0x13;
        ex[6] = 0x07;
        ex[7] = 0x04;
        ex[8] = 0x08;
        ex[10] = (byte) 0xff;
        ex[11] = (byte) 0xff;
        ex[12] = (byte) 0xff;
        ex[13] = (byte) 0xff;
        ex[14] = (byte) 0xff;
        ex[15] = (byte) 0xff;
        ex[16] = (byte) 0xff;
        ex[17] = (byte) 0xff;
        ex[18] = 0x06;
        if (key != null && key.length > 0) {
            ex[9] = (byte) key.length;
            ex[5] = (byte) (5 + ex[8]);
            ex[((int) ex[5]) + 5] = CheckSum(ex, ((int) ex[5]) + 6);

        }
        DeviceStateChangeUtils stateChangeUtils = DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort());
        ResponeDataIntface responeDataIntface = new ResponeDataIntface() {
            Intent mIntent = new Intent(BroadcastIntface.GETREADERID_BROADCASTRECEIVER);

            @Override
            public void responseData(byte[] data) {
                Log.i(TAG, "responseData：" + HexDump.toHexString(data));
                isOK[0] = CheckResponeData.isOk(data);
                mIntent.putExtra("RESPONSEDATA", data);
                context.sendBroadcast(mIntent);

            }

            @Override
            public void sendData(byte[] data) {
                Log.i(TAG, "sendData：" + HexDump.toHexString(data));
                mIntent.putExtra("SENDDATA", data);
            }

            @Override
            public void onRunError(Exception e) {
                isOK[0] = false;
                Log.i(TAG, "onRunError：" + e.toString());
            }

        };
        byte[] exeCmd = new byte[((int) ex[5]) + 5 + 1];
        for (int i = 0; i < exeCmd.length; i++) {
            exeCmd[i] = ex[i];
        }
        stateChangeUtils.onDeviceStateChange(exeCmd, responeDataIntface);
        SystemClock.sleep(200);
        return isOK[0];
    }

    @Override
    public boolean initSerialPort(final UsbManager usbManager) throws IOException {
        new AsyncTask<Void, Void, List<UsbSerialPort>>() {
            @Override
            protected List<UsbSerialPort> doInBackground(Void... params) {
//                SystemClock.sleep(1000);
                final List<UsbSerialDriver> drivers =
                        UsbSerialProber.getDefaultProber().findAllDrivers(usbManager);
                final List<UsbSerialPort> result = new ArrayList<UsbSerialPort>();
                for (final UsbSerialDriver driver : drivers) {
                    final List<UsbSerialPort> ports = driver.getPorts();
                    result.addAll(ports);
                }
                return result;
            }

            @Override
            protected void onPostExecute(List<UsbSerialPort> result) {
                if (result != null && result.size() > 0) {
                    SerialPortEntity.getInstance().setSerialPort(result.get(0));
                    usbSerialPort = result.get(0);
                    return;

                }
            }

        }.execute((Void) null);

        return SerialPortEntity.getInstance().getSerialPort() != null ? true : false;
    }


}
