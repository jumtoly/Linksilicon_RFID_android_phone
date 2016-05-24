package app.terminal.com.serialport.inter;

import android.content.Context;
import android.hardware.usb.UsbManager;

import java.io.IOException;

import app.terminal.com.serialport.util.CardData;
import app.terminal.com.serialport.util.CardInfo;
import app.terminal.com.serialport.util.ModifyKey;

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

    boolean getReaderId(Context context);

    boolean composeFindCard();

    boolean pauseCard();

    boolean setBaudRate(int baud);

    boolean ReadReaderRegs(int Addr, byte RegNum, byte[] Dest);


    //M1卡操作相关函数
    boolean SendM1Command(byte[] m1Cmd, int cmdLen);

    boolean checkKey(CardData cardData);

    boolean readBlock(CardData cardData);

    boolean writeBlock(CardData cardData);

    boolean walletInit(CardData cardData);

    boolean readWallet(CardData cardData);

    boolean walletAdd(CardData cardData);

    boolean walletDec(CardData cardData);

    boolean composeRead(CardData cardData);

    boolean composeWrite(CardData cardData);

    boolean modifyKey(ModifyKey modifyKey);

    boolean ModifyControl(int sector, byte[] oldKeyA, byte[] oldKeyB, byte[] newCtrlWord);

    boolean ReadCtrlWord(int sector, byte[] key, byte[] data);

    boolean CheckCtrlKey(boolean aORb, int block, byte[] key, int order);


    //CPU卡相关函数
    boolean CosActivation();

    boolean CosDeactive();

    boolean SendCosCommand(byte[] cosCmd, int cmdLen);

    boolean Exauthentication(byte[] key, int keyLen, byte keyFlag);
}
