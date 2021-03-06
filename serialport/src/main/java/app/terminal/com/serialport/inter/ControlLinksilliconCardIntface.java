package app.terminal.com.serialport.inter;

import android.content.Context;
import android.hardware.usb.UsbManager;

import java.io.IOException;

import app.terminal.com.serialport.util.CardData;
import app.terminal.com.serialport.util.CardInfo;
import app.terminal.com.serialport.util.CreateControl;
import app.terminal.com.serialport.util.ModifyKey;

/**
 * Created by sly on 2016/5/21.
 */
public interface ControlLinksilliconCardIntface {

    boolean initSerialPort(final UsbManager usbManager) throws IOException;


    // 导出的读卡器公共函数
    boolean openReader(UsbManager usbManager, int baud, int byteSize, int parity, int stop) throws IOException;

    void closeReader(UsbManager usbManager) throws IOException;

    boolean isReaderOpen();


    boolean findCard(Context context);

    int wakeUp(Context context);

    boolean selectCard(Context context, byte SeriesLevel);

    boolean conflict(Context context, byte serieslevel);

    boolean getCardInfo(Context context);

    boolean autoFindCard(Context context, boolean bOn);

    boolean buzzerOn(Context context, boolean bOn);

    boolean antennaOff(Context context);

    boolean manualCard(Context context);

    boolean getReaderId(Context context);

    boolean composeFindCard(Context context);

    boolean pauseCard(Context context);

    boolean setBaudRate(Context context, int baud);



    //M1卡操作相关函数

    boolean checkKey(Context context, CardData cardData);

    boolean readBlock(Context context, CardData cardData);

    boolean writeBlock(Context context, CardData cardData);

    boolean walletInit(Context context, CardData cardData);

    String readWallet(Context context, CardData cardData);

    boolean walletAdd(Context context, CardData cardData);

    boolean walletDec(Context context, CardData cardData);

    boolean composeRead(Context context, CardData cardData);

    boolean composeWrite(Context context, CardData cardData);

    boolean modifyKey(Context context, int sector, int keyType, byte[] newKey, byte[] oldKeyA, byte[] oldKeyB);

    boolean modifyControl(Context context, ModifyKey modifyKey, byte[] controlWord, byte[] oldControlWord);

    boolean readCtrlWord(Context context, int sector, byte[] key);

    boolean checkCtrlKey(Context context, boolean aORb, int block, byte[] key, int order);


    //CPU卡相关函数
    boolean cosActivation(Context context);

    boolean cosDeactive(Context context);

    boolean sendCosCommand(Context context, byte[] cosCmd);

    boolean exauthentication(Context context, byte[] key, byte[] keyFlag);
}
