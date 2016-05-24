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
        if (data.length >= 7 && data[6] == 0) {
            return "";
        } else if (data.length >= 7) {
            switch (data[6]) {


                case 0x3B:
                    return "读卡器与卡片通信出错";
                case 0x1D:
                    return "读卡器与卡片通信超时";
                case 0x19:
                    return "位冲突";
                case 0x0D:
                    return "读卡器接收卡片数据异常";
                case 0X0C:
                    return "位计数器出错";
                case 0X0B:
                    return "M1卡读写操作前未经过密 钥认证 ";
                case 0X08:
                    return "M1卡写操作过程编码出错 ";
                case 0X06:
                    return "M1 卡密钥认证出错";
                case 0X03:
                    return "CRC 校验出错";
                case 0X01:
                    return "读卡器周围无卡片";
                case 0X41:
                    return "卡片不支持该命令";
                case 0X40:
                    return "未激活 COS 的情况下执行 COS 指令 ";
                case 0X3F:
                    return "连续两次激活 COS";
                case 0X3E:
                    return "命令参数错误";
                case 0X3D:
                    return "读卡器不支持该命令";
                case 0X3C:
                    return "读卡器异常";
                case -1:
                    return "未知错误";

            }

        }
        return "";
    }

}
