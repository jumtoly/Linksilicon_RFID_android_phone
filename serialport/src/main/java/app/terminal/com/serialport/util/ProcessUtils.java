package app.terminal.com.serialport.util;

public class ProcessUtils {
    private final static char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static String byteArray2HexString(byte[] arrayByte) {
        StringBuilder result = new StringBuilder();
        for (byte b : arrayByte) {
            if (b < 0) {
                result.append(" ");
                result.append(HEX_DIGITS[(b >>> 4) & 0x0F]);
                result.append(HEX_DIGITS[b & 0x0F]);
            } else if (b >= 0 && b <= 9) {
                result.append(" ");
                result.append(HEX_DIGITS[0]);
                result.append(b);
            } else {
                result.append(" ");
                result.append(b);
            }
        }

        return result.toString();
    }

    public static String getCardType(byte byt) {
        switch (byt) {
            case 0:
                return "ULTRALIGHT"; //超轻卡
            case 2:
                return "S70";//Mifare S50卡
            case 4:
                return "S50";//Mifare S70卡
            case 6:
                return "IDENTIFICATE";    //身份证
            case 8:
                return "FM1208";//复旦CPU卡
            case 9:
                return "UHF";//超高频卡
            case 0x12:
                return "SRI512";//ST ISO14443B卡
            case 0x42:
                return "PLUS";//Mifare Plus卡
            case 0x44:
                return "D41";//Mifare DES CPU卡
            case (byte) 0xE4:
                return "ICODE2";//NXP ISO15693卡
            default:
                return "UNKNOWN_CARD";//其他类型卡片
        }
    }
}
