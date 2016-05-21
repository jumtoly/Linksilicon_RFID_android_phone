package app.terminal.com.serialport.util;

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
import app.terminal.com.serialport.inter.ControlLinksilliconCardIntface;

/**
 * Created by sly on 2016/5/21.
 */
public class SerialportControl implements ControlLinksilliconCardIntface {
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
    public boolean autoFindCard(boolean bOn) {
        if (bOn) {
            DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort()).onDeviceStateChange(SendByteData.START_AUTO_FIND_CARD);
        } else {
            DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort()).onDeviceStateChange(SendByteData.STOP_AUTO_FIND_CARD);
        }

        return false;
    }

    @Override
    public boolean buzzerOn(boolean bOn) {
        if (bOn) {
            DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort()).onDeviceStateChange(SendByteData.START_BUZZER);
        } else {
            DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort()).onDeviceStateChange(SendByteData.STOP_BUZZER);

        }
        return false;
    }

    @Override
    public boolean antennaOff() {
        DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort()).onDeviceStateChange(SendByteData.STOP_ALL_ANTENNA);
        return false;
    }

    @Override
    public boolean manualCard(/*CardInfo pInfo*/) {
        DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort()).onDeviceStateChange(SendByteData.MANUALLY_DETECTING_CARD);
        return false;
    }

    @Override
    public boolean GetReaderId(byte[] pReaderId, int[] pLen) {
        return false;
    }

    @Override
    public boolean composeFindCard() {
        DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort()).onDeviceStateChange(SendByteData.COMPOSITE_DETECTING_CARD_14443A);
        return false;
    }

    @Override
    public boolean pauseCard() {
        DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort()).onDeviceStateChange(SendByteData.DORMANCY_14443A);
        return false;
    }

    @Override
    public boolean SetBaudRate(int baud) {
        return false;
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
    public boolean CheckKey(int card, boolean addrMode, int addr, int block, int keyType, byte[] key) {
        return false;
    }

    @Override
    public boolean ReadBlock(boolean addrMode, int sector, int block, int cardType, byte[] data) {
        return false;
    }

    @Override
    public boolean WriteBlock(boolean addrMode, int sector, int block, int cardType, byte[] data) {
        return false;
    }

    @Override
    public boolean WalletInit(boolean addrMode, int sector, int block, int cardType, byte[] data) {
        return false;
    }

    @Override
    public boolean ReadWallet(boolean addrMode, int sector, int block, int cardType, byte[] value) {
        return false;
    }

    @Override
    public boolean WalletAdd(boolean addrMode, int sector, int block, int cardType, byte[] data) {
        return false;
    }

    @Override
    public boolean WalletDec(boolean addrMode, int sector, int block, int cardType, byte[] data) {
        return false;
    }

    @Override
    public boolean ComposeRead(boolean addrMode, int sector, int block, int cardType, byte[] data, KeyType keyType, char[] key) {
        return false;
    }

    @Override
    public boolean ComposeWrite(boolean addrMode, int sector, int block, int cardType, byte[] data, KeyType keyType, char[] key) {
        return false;
    }

    @Override
    public boolean ModifyKey(int sector, KeyType keyType, byte[] newKey, byte[] oldKeyA, byte[] oldKeyB) {
        return false;
    }

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

    CardType GetCardType(byte type) {
        switch (type) {
            case 0:
                return CardType.ULTRALIGHT;
            case 2:
                return CardType.S70;
            case 4:
                return CardType.S50;
            case 6:
                return CardType.IDENTIFICATE;
            case 8:
                return CardType.FM1208;
            case 9:
                return CardType.UHF;
            case 0x12:
                return CardType.SRI512;
            case 0x42:
                return CardType.PLUS;
            case 0x44:
                return CardType.D41;
            case (byte) 0xE4:
                return CardType.ICODE2;
            default:
                return CardType.UNKNOWN_CARD;
        }
    }
}
