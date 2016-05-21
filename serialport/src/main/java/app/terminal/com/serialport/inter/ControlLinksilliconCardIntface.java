package app.terminal.com.serialport.inter;

import android.hardware.usb.UsbManager;

import java.io.IOException;

import app.terminal.com.serialport.util.CardInfo;
import app.terminal.com.serialport.util.KeyType;

/**
 * Created by sly on 2016/5/21.
 */
public interface ControlLinksilliconCardIntface {

    boolean initSerialPort(final UsbManager usbManager) throws IOException;


    // 导出的读卡器公共函数
    boolean openReader(UsbManager usbManager, int baud, int byteSize, int parity, int stop) throws IOException;

    boolean closeReader(UsbManager usbManager) throws IOException;

    boolean isReaderOpen();

    int GetReaderSendCmd(byte[] dest);

    int GetReaderRecvData(byte[] dest);

    boolean findCard();

    int wakeUp();

    boolean selectCard(int SeriesLevel);

    boolean conflict(CardInfo cardInfo);

    boolean getCardInfo(/*CardInfo cardInfo*/);

    boolean autoFindCard(boolean bOn);

    boolean buzzerOn(boolean bOn);

    boolean antennaOff();

    boolean manualCard(/*CardInfo pInfo*/);

    boolean GetReaderId(byte[] pReaderId, int[] pLen);

    boolean composeFindCard();

    boolean pauseCard();

    boolean SetBaudRate(int baud);

    boolean ReadReaderRegs(int Addr, byte RegNum, byte[] Dest);


    //M1卡操作相关函数
    boolean SendM1Command(byte[] m1Cmd, int cmdLen);

    boolean CheckKey(int card, boolean addrMode, int addr, int block, int keyType, byte[] key);

    boolean ReadBlock(boolean addrMode, int sector, int block, int cardType, byte[] data);

    boolean WriteBlock(boolean addrMode, int sector, int block, int cardType, byte[] data);

    boolean WalletInit(boolean addrMode, int sector, int block, int cardType, byte[] data);

    boolean ReadWallet(boolean addrMode, int sector, int block, int cardType, byte[] value);

    boolean WalletAdd(boolean addrMode, int sector, int block, int cardType, byte[] data);

    boolean WalletDec(boolean addrMode, int sector, int block, int cardType, byte[] data);

    boolean ComposeRead(boolean addrMode, int sector, int block, int cardType, byte[] data, KeyType keyType, char[] key);

    boolean ComposeWrite(boolean addrMode, int sector, int block, int cardType, byte[] data, KeyType keyType, char[] key);

    boolean ModifyKey(int sector, KeyType keyType, byte[] newKey, byte[] oldKeyA, byte[] oldKeyB);

    boolean ModifyControl(int sector, byte[] oldKeyA, byte[] oldKeyB, byte[] newCtrlWord);

    boolean ReadCtrlWord(int sector, byte[] key, byte[] data);

    boolean CheckCtrlKey(boolean aORb, int block, byte[] key, int order);


    //CPU卡相关函数
    boolean CosActivation();

    boolean CosDeactive();

    boolean SendCosCommand(byte[] cosCmd, int cmdLen);

    boolean Exauthentication(byte[] key, int keyLen, byte keyFlag);
}
