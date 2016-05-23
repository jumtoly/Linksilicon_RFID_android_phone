package app.terminal.com.serialport.util;

/**
 * Created by liu.yao on 2016/5/23.
 */
public class CheckResponeData {

    public static boolean isOk(byte[] data) {
        if (data.length >= 7 && data[6] == 0) {
            return true;
        }
        return false;
    }

    public static String getErrorInfo(byte[] data) {
        String errorInfo = "";
        byte byt = 0;
        if (data.length >= 7 && data[6] == 0) {
            return "";
        } else {
            switch (data[6]) {


                case 0x3B:
                    errorInfo = "读卡器与卡片通信出错";
                    break;
                case 0x1D:
                    errorInfo = "读卡器与卡片通信超时";
                    break;
                case 0x19:
                    errorInfo = "位冲突";
                    break;
                case 0x0D:
                    errorInfo = "读卡器接收卡片数据异常";
                    break;
                case 0X0C:
                    errorInfo = "位计数器出错";
                    break;
                case 0X0B:
                    errorInfo = "M1卡读写操作前未经过密 钥认证 ";
                    break;
                case 0X08:
                    errorInfo = "M1卡写操作过程编码出错 ";
                    break;
                case 0X06:
                    errorInfo = "M1 卡密钥认证出错";
                    break;
                case 0X03:
                    errorInfo = "CRC 校验出错";
                    break;
                case 0X01:
                    errorInfo = "读卡器周围无卡片";
                    break;
                case 0X41:
                    errorInfo = "卡片不支持该命令";
                    break;
                case 0X40:
                    errorInfo = "未激活 COS 的情况下执行 COS 指令 ";
                    break;
                case 0X3F:
                    errorInfo = "连续两次激活 COS";
                    break;
                case 0X3E:
                    errorInfo = "命令参数错误";
                    break;
                case 0X3D:
                    errorInfo = "读卡器不支持该命令";
                    break;
                case 0X3C:
                    errorInfo = "读卡器异常";
                    break;
                case -1:
                    errorInfo = "未知错误";
                    break;

            }
        }
        return errorInfo;
    }

}
