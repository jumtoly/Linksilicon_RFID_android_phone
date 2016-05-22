package app.terminal.com.serialport.util;

/**
 * Created by sly on 2016/5/21.
 */
public interface CardType {
    int ULTRALIGHT = 10;//超轻卡 ULTRALIGHT=10
    int S50 = 11;          //Mifare S50卡
    int S70 = 12;          //Mifare S70卡
    int FM1208 = 13;            //复旦CPU卡
    int PLUS = 14;            //Mifare Plus卡
    int D41 = 15;          //Mifare DES CPU卡
    int IDENTIFICATE = 16;    //身份证
    int UHF = 17;          //超高频卡
    int ICODE2 = 18;            //NXP ISO15693卡
    int SRI512 = 19;        //ST ISO14443B卡
    int UNKNOWN_CARD = 20;    //其他类型卡片
}
