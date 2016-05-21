package app.terminal.com.serialport.util;

/**
 * Created by sly on 2016/5/21.
 */
public enum CardType {
    ULTRALIGHT,//超轻卡 ULTRALIGHT=10
    S50,            //Mifare S50卡
    S70,            //Mifare S70卡
    FM1208,            //复旦CPU卡
    PLUS,            //Mifare Plus卡
    D41,            //Mifare DES CPU卡
    IDENTIFICATE,    //身份证
    UHF,            //超高频卡
    ICODE2,            //NXP ISO15693卡
    SRI512,            //ST ISO14443B卡
    UNKNOWN_CARD;    //其他类型卡片
}
