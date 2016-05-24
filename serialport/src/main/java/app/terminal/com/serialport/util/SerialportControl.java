package app.terminal.com.serialport.util;

import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.os.AsyncTask;
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
    public boolean closeReader(UsbManager usbManager) throws IOException {
       /* UsbDeviceConnection connection = usbManager.openDevice(SerialPortEntity.getInstance().getSerialPort().getDriver().getDevice());
        SerialPortEntity.getInstance().setConnection(connection);
        SerialPortEntity.getInstance().getSerialPort().open(connection);*/
        SerialPortEntity.getInstance().getSerialPort().close();
        isReaderOpen = false;
        return isReaderOpen;
    }

    @Override
    public boolean isReaderOpen() {
        return isReaderOpen;
    }

    @Override
    public int GetReaderSendCmd(byte[] dest) {
        return 0;
    }

    @Override
    public int GetReaderRecvData(byte[] dest) {
        return 0;
    }

    @Override
    public boolean findCard() {
        //TODO 命令不一样
        DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort()).onDeviceStateChange(SendByteData.INQUIRY_CARD_14443A_STANDARD);
        return false;
    }

    @Override
    public int wakeUp() {

        DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort()).onDeviceStateChange(SendByteData.INQUIRY_CARD_14443A_WAKEUP);
        return 0;
    }

    @Override
    public boolean selectCard(int SeriesLevel) {
        //TODO 命令不一样
        DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort()).onDeviceStateChange(SendByteData.SELECT_CARD_14443A);
        return false;
    }

    @Override
    public boolean conflict(CardInfo cardInfo) {
        //TODO 待确认
        return false;
    }

    @Override
    public boolean getCardInfo(/*CardInfo cardInfo*/) {
        DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort()).onDeviceStateChange(SendByteData.GET_CARD_INFO);
        return false;
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

        return false;
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
        return false;
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
        return false;
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
        return false;
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

        return false;
    }

    @Override
    public boolean composeFindCard() {
        DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort()).onDeviceStateChange(SendByteData.COMPOSITE_DETECTING_CARD_14443A);
        return true;
    }

    @Override
    public boolean pauseCard() {
        DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort()).onDeviceStateChange(SendByteData.DORMANCY_14443A);
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
    public boolean ReadReaderRegs(int Addr, byte RegNum, byte[] Dest) {
        return false;
    }

    @Override
    public boolean SendM1Command(byte[] m1Cmd, int cmdLen) {
        return false;
    }

    @Override
    public boolean checkKey(CardData cardData) {
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
        DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort()).onDeviceStateChange(authenticationM1);
        return true;
    }

    @Override
    public boolean readBlock(CardData cardData) {

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

        DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort()).onDeviceStateChange(readBlockM1);
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
    public boolean writeBlock(CardData cardData) {
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
        DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort()).onDeviceStateChange(writeblock);
        return true;
    }

    @Override
    public boolean walletInit(CardData cardData) {
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


        //TODO 如果不对，可能是没有进行大小端处理
       /* if(Host.c[0] == 0)
        {
            itemp = TRANSFER(itemp);
        }
        walletinit[11] = itemp % 256;
        walletinit[12] = itemp / 256;
        itemp -= (walletinit[11] + walletinit[12]*0x100);
        itemp = itemp/65536;
        walletinit[13] = itemp % 256;
        walletinit[14] = itemp / 256;*/
        walletInitializationM1[15] = CheckSum(walletInitializationM1, 16);
        DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort()).onDeviceStateChange(walletInitializationM1);
        return false;
    }

    @Override
    public boolean readWallet(CardData cardData) {
        byte[] purseReadM1 = SendByteData.PURSE_READ_M1;

        if (cardData.getFindAddrType() == FindAddrType.ABSOLUTE_ADDR) {
            purseReadM1[8] = 1;
            purseReadM1[9] = 0;
            purseReadM1[10] = cardData.getBlockAddr();
        } else {
            if ((cardData.getBlockAddr() >= 64) && (cardData.getCardType() == CardType.S50)) {
                //TODO 块地址设置错误
                return false;
            } else if ((cardData.getBlockAddr() >= 256) && (cardData.getCardType() == CardType.S70)) {
                //TODO 块地址设置错误
                return false;
            }
            purseReadM1[8] = 0;
            purseReadM1[9] = cardData.getSectorAddr();
            purseReadM1[10] = cardData.getBlockAddr();
        }

        if (CheckBlockAddr(purseReadM1[8], purseReadM1[9], purseReadM1[10])) {
            //TODO 钱包初始化错误
            return false;
        }


        purseReadM1[11] = CheckSum(purseReadM1, 12);
        DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort()).onDeviceStateChange(purseReadM1);
        return true;
    }

    @Override
    public boolean walletAdd(CardData cardData) {
        byte[] walletRechargeM1 = SendByteData.WALLET_RECHARGE_M1;

        if (cardData.getFindAddrType() == FindAddrType.ABSOLUTE_ADDR) {
            walletRechargeM1[9] = 1;
            walletRechargeM1[10] = 0;
            walletRechargeM1[11] = cardData.getBlockAddr();
        } else {
            if ((cardData.getBlockAddr() >= 64) && (cardData.getCardType() == CardType.S50)) {
                //TODO 块地址设置错误
                return false;
            } else if ((cardData.getBlockAddr() >= 256) && (cardData.getCardType() == CardType.S70)) {
                //TODO 块地址设置错误
                return false;
            }
            walletRechargeM1[9] = 0;
            walletRechargeM1[10] = cardData.getSectorAddr();
            walletRechargeM1[11] = cardData.getBlockAddr();
        }

        if (CheckBlockAddr(walletRechargeM1[9], walletRechargeM1[10], walletRechargeM1[11])) {
            //TODO 钱包初始化错误
            return false;
        }
        //TODO 如果不对，可能是没有进行大小端处理
       /* if(Host.c[0] == 0)
        {
            itemp = TRANSFER(itemp);
        }
        wallet[12] = itemp % 256;
        wallet[13] = itemp / 256;
        itemp = (itemp - (wallet[12] + wallet[13]*256));
        itemp = itemp/65536;
        wallet[14] = itemp % 65536;
        wallet[15] = itemp / 65536;
        wallet[8] = 0x01;
        wallet[16] = CheckSum(wallet,17);*/
        walletRechargeM1[16] = CheckSum(walletRechargeM1, 17);
        DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort()).onDeviceStateChange(walletRechargeM1);
        return true;
    }

    @Override
    public boolean walletDec(CardData cardData) {
        byte[] purseDecrementM1 = SendByteData.PURSE_DECREMENT_M1;
        if (cardData.getFindAddrType() == FindAddrType.ABSOLUTE_ADDR) {
            purseDecrementM1[9] = 1;
            purseDecrementM1[10] = 0;
            purseDecrementM1[11] = cardData.getBlockAddr();
        } else {
            if ((cardData.getBlockAddr() >= 64) && (cardData.getCardType() == CardType.S50)) {
                //TODO 块地址设置错误
                return false;
            } else if ((cardData.getBlockAddr() >= 256) && (cardData.getCardType() == CardType.S70)) {
                //TODO 块地址设置错误
                return false;
            }
            purseDecrementM1[9] = 0;
            purseDecrementM1[10] = cardData.getSectorAddr();
            purseDecrementM1[11] = cardData.getBlockAddr();
        }

        if (CheckBlockAddr(purseDecrementM1[9], purseDecrementM1[10], purseDecrementM1[11])) {
            //TODO 钱包初始化错误
            return false;
        }


        //TODO 如果不对，可能是没有进行大小端处理
        /*if(Host.c[0] == 0)
        {
            itemp = TRANSFER(itemp);
        }
        //sprintf((BYTE *)str ,"%x" ,itemp);
        wallet[12] = itemp % 256;
        wallet[13] = itemp / 256;
        itemp = (itemp - (wallet[12] + wallet[13]*256));
        itemp = itemp/65536;
        wallet[14] = itemp % 65536;
        wallet[15] = itemp / 65536;
        wallet[8] = 0x02;*/
        purseDecrementM1[16] = CheckSum(purseDecrementM1, 17);
        DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort()).onDeviceStateChange(purseDecrementM1);
        return true;
    }

    @Override
    public boolean composeRead(CardData cardData) {
        byte[] compositeReadBlockm1 = SendByteData.COMPOSITE_READING_BLOCK_M1;
        if (cardData.getFindAddrType() == FindAddrType.ABSOLUTE_ADDR) {
            compositeReadBlockm1[8] = 1;
            compositeReadBlockm1[9] = 0;
            compositeReadBlockm1[10] = cardData.getBlockAddr();
        } else {
            if ((cardData.getBlockAddr() >= 64) && (cardData.getCardType() == CardType.S50)) {
                //TODO 块地址设置错误
                return false;
            } else if ((cardData.getBlockAddr() >= 256) && (cardData.getCardType() == CardType.S70)) {
                //TODO 块地址设置错误
                return false;
            }
            compositeReadBlockm1[8] = 0;
            compositeReadBlockm1[9] = cardData.getSectorAddr();
            compositeReadBlockm1[10] = cardData.getBlockAddr();
        }
        if (cardData.getKeyType() == KeyType.KEY_A) {
            compositeReadBlockm1[11] = 0x60;
        } else if (cardData.getKeyType() == KeyType.KEY_B) {
            compositeReadBlockm1[11] = 0x61;
        }
        for (int i = 13, j = 0; i < 19; i++, j++) {
            compositeReadBlockm1[i] = cardData.getKey()[j];
        }
        compositeReadBlockm1[19] = CheckSum(compositeReadBlockm1, 20);
        DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort()).onDeviceStateChange(compositeReadBlockm1);
        return true;
    }

    @Override
    public boolean composeWrite(CardData cardData) {
        byte[] compositeWriteBlockM1 = SendByteData.COMPOSITE_WRITE_BLOCK_M1;
        if (cardData.getFindAddrType() == FindAddrType.ABSOLUTE_ADDR) {
            compositeWriteBlockM1[8] = 1;
            compositeWriteBlockM1[9] = 0;
            compositeWriteBlockM1[10] = cardData.getBlockAddr();
        } else {
            if ((cardData.getBlockAddr() >= 64) && (cardData.getCardType() == CardType.S50)) {
                //TODO 块地址设置错误
                return false;
            } else if ((cardData.getBlockAddr() >= 256) && (cardData.getCardType() == CardType.S70)) {
                //TODO 块地址设置错误
                return false;
            }
            compositeWriteBlockM1[8] = 0;
            compositeWriteBlockM1[9] = cardData.getSectorAddr();
            compositeWriteBlockM1[10] = cardData.getBlockAddr();
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
        if (CheckBlockAddr(compositeWriteBlockM1[9], compositeWriteBlockM1[10], compositeWriteBlockM1[11])) {
        /*if(!CheckWriteData(comwrite[17],comwrite[18],comwrite[19]))
        {
		AfxMessageBox(_T("控制字结构不正确！"),MB_ICONERROR|MB_OK);
		return;
		}
		CWarnDlg dlg;
		if(dlg.DoModal() != IDOK)
		{
			return;
		}*/
            //AfxMessageBox(_T("操作控制块请使用“修改控制块”功能！"),MB_ICONERROR|MB_OK);
            return false;
        }
        compositeWriteBlockM1[35] = CheckSum(compositeWriteBlockM1, 36);
        DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort()).onDeviceStateChange(compositeWriteBlockM1);
        return true;
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
    public boolean modifyKey(ModifyKey modifyKey) {
       /* byte[] keyModify = SendByteData.KEY_MODIFY;


        if (false == checkCtrlKey(true, modifyKey.getSector(), modifyKey.getaOldKey()*//*, MODIFYKEYCHECKA*//*))
            return false;
        SystemClock.sleep(50);
        if (false == CheckCtrlKey(false, modifyKey.getSector(), modifyKey.getbOldKey()*//*, MODIFYKEYCHECKB*//*))
            return false;
        SystemClock.sleep(50);

       bRet = ReadCtrlWord(sector, old_key_a, ctrl_word);
        if (bRet == FALSE)
            return FALSE;
        Sleep(50);
        modifykey[10] = sector;
        memset(recvbuf, 0, sizeof(recvbuf));

        if (sector >= 32) {
            modifykey[11] = 15;
        } else {
            modifykey[11] = 3;
        }

        //str.format("%s", (char*)iParam);
        BYTE order[ 2];
        BYTE ctrl = 0;
        for (int i = 0; i < 2; i++) {
            order[i] = ctrl_word[i + 1];
        }

        if (key_type == KEY_A) {
            modifykey[12] = 0x60;    //用密钥A写数据
            for (int i = 13, j = 0; i < 19; i++, j++) {
                modifykey[i] = old_key_a[j];
                modifykey[i + 6] = new_key[j];
            }

            for (int i = 25, j = 0; i < 29; i++, j++) {
                modifykey[i] = ctrl_word[j];            //4位控制字写入
            }

            for (int i = 29, j = 0; i < 35; i++, j++) {
                modifykey[i] = old_key_b[j];
            }
        } else if (key_type == KEY_B) {
            modifykey[12] = 0x61;    //用密钥B写数据

            for (int i = 13, j = 0; i < 19; i++, j++) {
                modifykey[i] = old_key_b[j];
                modifykey[i + 6] = old_key_a[j];
            }

            for (int i = 25, j = 0; i < 29; i++, j++) {
                modifykey[i] = ctrl_word[j];            //4位控制字写入
            }

            for (int i = 29, j = 0; i < 35; i++, j++) {
                modifykey[i] = new_key[j];
            }
        }


        modifykey[35] = CheckSum(modifykey, 36);*/
        return true;
    }


    /*  private boolean checkCtrlKey(boolean AorB, int block, byte[] key) {

          byte checkkey[] = SendByteData.CHECK_CTRL_KEY;
          checkkey[9] = (byte) block;
          if (block >= 32) {
              checkkey[10] = 15;
          } else {
              checkkey[10] = 3;
          }

          if (AorB) {
              checkkey[11] = 0x60;
          } else {
              checkkey[11] = 0x61;
          }

          for (int i = 12, j = 0; i < 18; i++, j++) {
              checkkey[i] = key[j];
          }

          checkkey[18] = CheckSum(checkkey, 19);
          DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort()).onDeviceStateChange(checkkey);
          return false;
      }*/
    @Override
    public boolean ModifyControl(int sector, byte[] oldKeyA, byte[] oldKeyB, byte[] newCtrlWord) {
        return false;
    }

    @Override
    public boolean ReadCtrlWord(int sector, byte[] key, byte[] data) {
        return false;
    }

    @Override
    public boolean CheckCtrlKey(boolean aORb, int block, byte[] key, int order) {
        return false;
    }

    @Override
    public boolean CosActivation() {
        return false;
    }

    @Override
    public boolean CosDeactive() {
        return false;
    }

    @Override
    public boolean SendCosCommand(byte[] cosCmd, int cmdLen) {
        return false;
    }

    @Override
    public boolean Exauthentication(byte[] key, int keyLen, byte keyFlag) {
        return false;
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
