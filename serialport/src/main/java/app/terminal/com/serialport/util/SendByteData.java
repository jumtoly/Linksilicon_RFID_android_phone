package app.terminal.com.serialport.util;

/**
 * Created by liu.yao on 2016/5/17.
 */
public class SendByteData {
    /**
     * 获取读卡器序列号
     */
    public final static byte[] SERIAL_NUMBER_BYTE = {0x55, 0x55, 0x00, 0x00, 0x00, 0x03, 0x0A, 0x01, 0x08};
    /**
     * 配置读卡器波特率
     */
//    public final static byte[] BAUD_RATE = {0x55, 0x55, 0x00, 0x00, 0x00, 0x04, (byte) 0xBD, 0x01, 0x06, (byte) 0xBE};
    public final static byte[] BAUD_RATE = {0x55, 0x55, 0x00, 0x00, 0x00, 0x04, (byte) 0xBD, 0x01, 0x00, 0x00};
    /**
     * 启动自动寻卡
     */
    public final static byte[] START_AUTO_FIND_CARD = {0x55, 0x55, 0x00, 0x00, 0x00, 0x03, 0x01, 0x01, 0x03};
    /**
     * 获取卡片信息
     */
    public final static byte[] GET_CARD_INFO = {0x55, 0x55, 0x00, 0x00, 0x00, 0x03, 0x01, 0x02, 0x00};
    /**
     * 关闭自动寻卡
     */
    public final static byte[] STOP_AUTO_FIND_CARD = {0x55, 0x55, 0x00, 0x00, 0x00, 0x03, 0x01, 0x03, 0x01};
    /**
     * 打开蜂鸣器
     */
    public final static byte[] START_BUZZER = {0x55, 0x55, 0x00, 0x00, 0x00, 0x03, 0x01, 0x04, 0x06};
    /**
     * 关闭蜂鸣器
     */
    public final static byte[] STOP_BUZZER = {0x55, 0x55, 0x00, 0x00, 0x00, 0x03, 0x01, 0x05, 0x07};
    /**
     * 关闭所有天线
     */
    public final static byte[] STOP_ALL_ANTENNA = {0x55, 0x55, 0x00, 0x00, 0x00, 0x04, 0x01, 0x06, 0x00, 0x03};

    /**
     * 读读卡芯片 EEPROM，地址范围为：0000-007F
     */
    public final static byte[] READ_EEPROM = {0x55, 0x55, 0x00, 0x00, 0x00, 0x06, 0x01, 0x07, 0x00, 0x30, 0x20, 0x10};
    /**
     * 写读卡芯片 EEPROM，安全地址范围为：0030-007F。访问其它地址可能引起系统异常
     */
    public final static byte[] WRITE_EEPROM = {0x55, 0x55, 0x00, 0x00, 0x00, 0x26, 0x01, 0x08, 0x00, 0x30, 0x20, 0x00, 0x58, 0x3F, 0x3F, 0x19, 0x13, 0x3F, 0x3B, 0x00, 0x73, 0x08, (byte) 0xAD, (byte) 0xFF, 0x1E, 0x41, 0x00, 0x00, 0x06, 0x03, 0x63, 0x63, 0x00, 0x00, 0x00, 0x00, 0x08, 0x07, 0x06, 0x0A, 0x02, 0x00, 0x00, 0x1B};

    /**
     * 手动寻卡
     */

    public final static byte[] MANUALLY_DETECTING_CARD = {0x55, 0x55, 0x00, 0x00, 0x00, 0x03, 0x01, 0x09, 0x0B};
    /**
     * 14443A询卡,唤醒询卡
     */

    public final static byte[] INQUIRY_CARD_14443A_WAKEUP = {0x55, 0x55, 0x00, 0x00, 0x00, 0x04, 0x02, 0x01, 0x52, 0x55};
    /**
     * 14443A询卡,标准询卡
     */

    public final static byte[] INQUIRY_CARD_14443A_STANDARD = {0x55, 0x55, 0x00, 0x00, 0x00, 0x04, 0x02, 0x01, 0x26, 0x55};


    /**
     * 14443A防冲突
     */

//    public final static byte[] ANTI_COLLISION_14443A = {0x55, 0x55, 0x00, 0x00, 0x00, 0x04, 0x02, 0x02, (byte) 0x93, (byte) 0x97};
    public final static byte[] ANTI_COLLISION_14443A = {0x55, 0x55, 0x00, 0x00, 0x00, 0x04, 0x02, 0x02, 0x00, 0x00};
    /**
     * 14443A选卡
     */
    public final static byte[] SELECT_CARD_14443A = {0x55, 0x55, 0x00, 0x00, 0x00, 0x04, 0x02, 0x03, (byte) 0x93, (byte) 0x96};
    /**
     * 14443A复合寻卡
     */

//    public final static byte[] COMPOSITE_DETECTING_CARD_14443A = {0x55, 0x55, 0x00, 0x00, 0x00, 0x03, 0x02, 0x04, 0x05};
    public final static byte[] COMPOSITE_DETECTING_CARD_14443A = {0x55, 0x55, 0x00, 0x00, 0x00, 0x03, 0x02, 0x00, 0x00, 0x00};
    /**
     * 14443A休眠
     */

    public final static byte[] DORMANCY_14443A = {0x55, 0x55, 0x00, 0x00, 0x00, 0x03, 0x02, 0x05, 0x04};
    /**
     * 密钥修改
     */
    public final static byte[] KEY_MODIFY = {0x55, 0x55, 0x00, 0x00, 0x00, 0x1e, 0x03, 0x07, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};

    /**
     * 控制字校验
     */
    public final static byte[] CHECK_CTRL_KEY = {0x55, 0x55, 0x00, 0x00, 0x00, 0x0d, 0x03, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
    /**
     * M1密钥验证
     */

//    public final static byte[] KEY_AUTHENTICATION_M1 = {0x55, 0x55, 0x00, 0x00, 0x00, 0x0D, 0x03, 0x01, 0x00, 0x02, 0x01, 0x60, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x6C};
    public final static byte[] KEY_AUTHENTICATION_M1 = {0x55, 0x55, 0x00, 0x00, 0x00, 0x0D, 0x03, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};


    /**
     * M1读块
     */

//    public final static byte[] READ_BLOCK_M1 = {0x55, 0x55, 0x00, 0x00, 0x00, 0x06, 0x03, 0x02, 0x00, 0x02, 0x01, 0x04};
    public final static byte[] READ_BLOCK_M1 = {0x55, 0x55, 0x00, 0x00, 0x00, 0x06, 0x03, 0x02, 0x00, 0x00, 0x00, 0x00};

    /**
     * M1写块
     */

//    public final static byte[] WRITE_BLOCK_M1 = {0x55, 0x55, 0x00, 0x00, 0x00, 0x16, 0x03, 0x03, 0x00, 0x02, 0x01, 0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38, 0x39, 0x41, 0x42, 0x43, 0x44, 0x45, 0x46, 0x13};
    public final static byte[] WRITE_BLOCK_M1 = {0x55, 0x55, 0x00, 0x00, 0x00, 0x16, 0x03, 0x03, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};

    /**
     * M1钱包初始化
     */

//    public final static byte[] WALLET_INITIALIZATION_M1 = {0x55, 0x55, 0x00, 0x00, 0x00, 0x0A, 0x03, 0x04, 0x00, 0x02, 0x02, 0x64, 0x00, 0x00, 0x00, 0x69};
    public final static byte[] WALLET_INITIALIZATION_M1 = {0x55, 0x55, 0x00, 0x00, 0x00, 0x0A, 0x03, 0x04, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
    /**
     * M1读钱包
     */
//    public final static byte[] PURSE_READ_M1 = {0x55, 0x55, 0x00, 0x00, 0x00, 0x06, 0x03, 0x05, 0x00, 0x02, 0x02, 0x00};
    public final static byte[] PURSE_READ_M1 = {0x55, 0x55, 0x00, 0x00, 0x00, 0x06, 0x03, 0x05, 0x00, 0x00, 0x00, 0x00};

    /**
     * M1钱包充值（增值）
     */
//    public final static byte[] WALLET_RECHARGE_M1 = {0x55, 0x55, 0x00, 0x00, 0x00, 0x0B, 0x03, 0x06, 0x01, 0x00, 0x02, 0x02, 0x64, 0x00, 0x00, 0x00, 0x6B};
    public final static byte[] WALLET_RECHARGE_M1 = {0x55, 0x55, 0x00, 0x00, 0x00, 0x0B, 0x03, 0x06, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};

    /**
     * M1钱包扣款（减值）
     */
//    public final static byte[] PURSE_DECREMENT_M1 = {0x55, 0x55, 0x00, 0x00, 0x00, 0x0B, 0x03, 0x06, 0x02, 0x00, 0x02, 0x02, 0x0A, 0x00, 0x00, 0x00, 0x06};
    public final static byte[] PURSE_DECREMENT_M1 = {0x55, 0x55, 0x00, 0x00, 0x00, 0x0B, 0x03, 0x06, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};

    /**
     * M1复合写块
     */
//    public final static byte[] COMPOSITE_WRITE_BLOCK_M1 = {0x55, 0x55, 0x00, 0x00, 0x00, 0x1E, 0x03, 0x07, 0x01, 0x00, 0x02, 0x01, 0x60, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x00, 0x11, 0x22, 0x33, 0x44, 0x55, 0x66, 0x77, (byte) 0x88, (byte) 0x99, (byte) 0xAA, (byte) 0xBB, (byte) 0xCC, (byte) 0xDD, (byte) 0xEE, (byte) 0xFF, 0x78};
    public final static byte[] COMPOSITE_WRITE_BLOCK_M1 = {0x55, 0x55, 0x00, 0x00, 0x00, 0x1E, 0x03, 0x07, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};

    /**
     * M1复合读块
     */
//    public final static byte[] COMPOSITE_READING_BLOCK_M1 = {0x55, 0x55, 0x00, 0x00, 0x00, 0x0E, 0x03, 0x07, 0x00, 0x00, 0x02, 0x01, 0x60, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x4E};
    public final static byte[] COMPOSITE_READING_BLOCK_M1 = {0x55, 0x55, 0x00, 0x00, 0x00, 0x0E, 0x03, 0x07, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
    /**
     * FMCOS激活
     */
    public final static byte[] FMCOS_ACTIVATION = {0x55, 0x55, 0x00, 0x00, 0x00, 0x04, 0x07, 0x01, 0x52, 0x50};
    /**
     * FMCOS指令之选择主目录
     */
    public final static byte[] SELECT_FMCOS_INSTRUCTION_HOME_DIRECTORY = {0x55, 0x55, 0x00, 0x00, 0x00, 0x0A, 0x07, 0x03, 0x00, (byte) 0xA4, 0x00, 0x00, 0x02, 0x3F, 0x00, (byte) 0x97};
    /**
     * FMCOS外部认证复合指令
     */
    public final static byte[] FMCOS_EXTERNAL_AUTHENTICATION_COMPOUND_INSTRUCTION = {0x55, 0x55, 0x00, 0x00, 0x00, 0x0D, 0x07, 0x04, 0x08, 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x06};
    /**
     * FMCOS停活
     */
    public final static byte[] FMCOS_STOP_LIVING = {0x55, 0x55, 0x00, 0x00, 0x00, 0x03, 0x07, 0x02, 0x06};
    /**
     * ICODE2 列举
     * 注意，与其它协议不同，此命令并非其它命令的先决条件。即，如果事先知道 UID，可以不必执行此命令，因执行其它命令时只需要 UID。
     */

    public final static byte[] ICODE2_LIST = {0x55, 0x55, 0x00, 0x00, 0x00, 0x03, 0x06, 0x01, 0x04};

    /**
     * ICODE2 选卡
     * 。注意，与其它协议不同，此命令并非是操作数据块的先决条件。 如果不执行此命令，用其它命令操作数据块时，参数中必须加入 UID，指定操作哪个卡片 如果执行了此命令，用其它命令操作数据块时，参数中不必加入 UID，表示卡片已被锁定
     */

    public final static byte[] ICODE2_ELECTION_CARD = {0x55, 0x55, 0x00, 0x00, 0x00, 0x0B, 0x06, 0x03, 0x17, 0x66, 0x4A, 0x36, 0x00, 0x01, 0x04, (byte) 0xE0, (byte) 0xE6};

    /**
     * ICODE2 获取系统信息
     */
    public final static byte[] ICODE2_GET_SYSTEM_INFORMATION = {0x55, 0x55, 0x00, 0x00, 0x00, 0x0C, 0x06, 0x0C, 0x02, 0x17, 0x66, 0x4A, 0x36, 0x00, 0x01, 0x04, (byte) 0xE0, (byte) 0xEC};


    /**
     * ICODE2 读块
     */

    public final static byte[] ICODE2_READ_BLOCK = {0x55, 0x55, 0x00, 0x00, 0x00, 0x0E, 0x06, 0x05, 0x02, 0x17, 0x66, 0x4A, 0x36, 0x00, 0x01, 0x04, (byte) 0xE0, 0x00, 0x01, (byte) 0xE6};

    /**
     * ICODE2 写块
     */

    public final static byte[] ICODE2_WRITE_BLOCK = {0x55, 0x55, 0x00, 0x00, 0x00, 0x16, 0x06, 0x06, 0x02, 0x17, 0x66, 0x4A, 0x36, 0x00, 0x01, 0x04, (byte) 0xE0, 0x00, 0x01, 0x12, 0x34, 0x56, 0x78, (byte) 0x9A, (byte) 0xBC, (byte) 0xDE, (byte) 0xF0, (byte) 0xFD};

    /**
     * ICODE2 写 AFI
     */

    public final static byte[] ICODE2_WRITE_AFI = {0x55, 0x55, 0x00, 0x00, 0x00, 0x0D, 0x06, 0x08, 0x02, 0x17, 0x66, 0x4A, 0x36, 0x00, 0x01, 0x04, (byte) 0xE0, 0x00, (byte) 0xE9};

    /**
     * ICODE2 写 DSFID
     */

    public final static byte[] ICODE2_WRITE_DSFID = {0x55, 0x55, 0x00, 0x00, 0x00, 0x0D, 0x06, 0x0A, 0x02, 0x17, 0x66, 0x4A, 0x36, 0x00, 0x01, 0x04, (byte) 0xE0, 0x00, (byte) 0xEB};

    /**
     * ICODE2 读块安全信息
     */

    public final static byte[] ICODE2_READ_BLOCK_SECURITY_INFORMATION = {0x55, 0x55, 0x00, 0x00, 0x00, 0x0E, 0x06, 0x0D, 0x02, 0x17, 0x66, 0x4A, 0x36, 0x00, 0x01, 0x04, (byte) 0xE0, 0x00, 0x01, (byte) 0xEE};

    /**
     * ICODE2 锁块。
     * 注意，此命令执行成功后，相应的块数据将无法再改写
     */

    public final static byte[] ICODE2_LOCKING_BLOCKS = {0x55, 0x55, 0x00, 0x00, 0x00, 0x0E, 0x06, 0x07, 0x02, 0x17, 0x66, 0x4A, 0x36, 0x00, 0x01, 0x04, (byte) 0xE0, 0x01, (byte) 0xE4};

    /**
     * ICODE2 锁 AFI。
     * 注意，此命令执行成功后，AFI 将无法再改写
     */

    public final static byte[] ICODE2_LOCK_AFI = {0x55, 0x55, 0x00, 0x00, 0x00, 0x0C, 0x06, 0x09, 0x02, 0x17, 0x66, 0x4A, 0x36, 0x00, 0x01, 0x04, (byte) 0xE0, (byte) 0xE9};
    /**
     * ICODE2 锁 DSFID。
     * 注意，此命令执行成功后，DSFID 将无法再改写
     */

    public final static byte[] ICODE2_LOCK_DSFID = {0x55, 0x55, 0x00, 0x00, 0x00, 0x0C, 0x06, 0x0B, 0x02, 0x17, 0x66, 0x4A, 0x36, 0x00, 0x01, 0x04, (byte) 0xE0, (byte) 0xE0};

    /**
     * ICODE2 静默。
     * 注意，此命令之后，对于当前卡片，“ICODE2 列举”命令将暂时失效
     */

    public final static byte[] ICODE2_SILENT = {0x55, 0x55, 0x00, 0x00, 0x00, 0x0B, 0x06, 0x02, 0x17, 0x66, 0x4A, 0x36, 0x00, 0x01, 0x04, (byte) 0xE0, (byte) 0xE7};

    /**
     * ICODE2 复位
     */

    public final static byte[] ICODE2_RESET = {0x55, 0x55, 0x00, 0x00, 0x00, 0x0C, 0x06, 0x04, 0x02, 0x17, 0x66, 0x4A, 0x36, 0x00, 0x01, 0x04, (byte) 0xE0, (byte) 0xE4};

    /**
     * 读取身份证序列号
     */

    public final static byte[] READ_ID_SERIAL_NUMBER = {0x55, 0x55, 0x00, 0x00, 0x00, 0x03, 0x08, 0x08, 0x03};

    /**
     * SRI512 询卡。
     * 此命令适合读卡器周围只有一张卡片
     */

    public final static byte[] SRI512_INQUIRY_CARD = {0x55, 0x55, 0x00, 0x00, 0x00, 0x03, 0x08, 0x0A, 0x01};
    /**
     * SRI512 防冲突 0。
     * 此命令在发生冲突后（读卡器周围有多张卡响应）首先使用
     */

    public final static byte[] SRI512_CONFLICT_PREVENTION_0 = {0x55, 0x55, 0x00, 0x00, 0x00, 0x03, 0x08, 0x0B, 0x00};
    /**
     * 超高频验证访问口令（用于获取对存储区进行读、写、锁定的权限
     */

    public final static byte[] UHF_ACCESS_PASSWORD_VERIFICATION = {0x55, 0x55, 0x00, 0x00, 0x00, 0x08, 0x05, 0x08, 0x50, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x55};
    /**
     * 超高频锁定内存
     */

    public final static byte[] UHF_LOCKED_MEMORY = {0x55, 0x55, 0x00, 0x00, 0x00, 0x08, 0x05, 0x07, 0x4C, 0x00, 0x00, 0x03, (byte) 0xFF, (byte) 0xBA};
    /**
     * 超高频损毁标签
     */

    public final static byte[] UHF_LABEL_DAMAGE = {0x55, 0x55, 0x00, 0x00, 0x00, 0x09, 0x05, 0x06, 0x4B, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x00, 0x41};
    /**
     * 超高频写用户存储区
     */

    public final static byte[] UHF_WRITE_USER_MEMORY = {0x55, 0x55, 0x00, 0x00, 0x00, 0x10, 0x05, 0x05, 0x57, 0x03, 0x00, 0x00, 0x04, (byte) 0x88, (byte) 0x99, (byte) 0xAA, (byte) 0xBB, (byte) 0xCC, (byte) 0xDD, (byte) 0xEE, (byte) 0xFF, 0x40};
    /**
     * 超高频读用户存储区
     */

    public final static byte[] UHF_READ_USER_MEMORY = {0x55, 0x55, 0x00, 0x00, 0x00, 0x08, 0x05, 0x04, 0x52, 0x03, 0x00, 0x00, 0x04, 0x5C};
    /**
     * UHF 单标签查询（此命令适用于识读范围内只有一张标签）
     */

    public final static byte[] UHF_SINGLE_LABEL_QUERY = {0x55, 0x55, 0x00, 0x00, 0x00, 0x04, 0x05, 0x03, 0x51, 0x53};
    /**
     * 读低频卡块号（只用于读内部有用户存储块的卡片，如 EM4205）
     */

    public final static byte[] LOW_FREQUENCY_CARD_READ_BLOCK_NUMBER = {0x55, 0x55, 0x00, 0x00, 0x00, 0x04, 0x09, 0x02, 0x06, 0x09};

    /**
     * 读低频卡序列号
     */

    public final static byte[] LOW_FREQUENCY_READ_CARD_SERIAL_NUMBER = {0x55, 0x55, 0x00, 0x00, 0x00, 0x03, 0x09, 0x01, 0x0B};
    /**
     * SRI512 休眠
     */

    public final static byte[] SRI512_SLEEP = {0x55, 0x55, 0x00, 0x00, 0x00, 0x03, 0x08, 0x13, 0x18};
    /**
     * SRI512 复位
     */

    public final static byte[] SRI512_RESET = {0x55, 0x55, 0x00, 0x00, 0x00, 0x03, 0x08, 0x12, 0x19};

    /**
     * SRI512 写块
     */

    public final static byte[] SRI512_WRITE_BLOCK = {0x55, 0x55, 0x00, 0x00, 0x00, 0x08, 0x08, 0x11, 0x07, 0x00, 0x00, 0x00, 0x00, 0x16};
    /**
     * SRI512 读块。
     */

    public final static byte[] SRI512_READ_BLOCK = {0x55, 0x55, 0x00, 0x00, 0x00, 0x04, 0x08, 0x10, 0x07, 0x1B};

    /**
     * SRI512 获取 UID。
     */

    public final static byte[] SRI512_GET_UID = {0x55, 0x55, 0x00, 0x00, 0x00, 0x03, 0x08, 0x0F, 0x04};

    /**
     * SRI512 选卡
     */

    public final static byte[] SRI512_ELECTION_CARD = {0x55, 0x55, 0x00, 0x00, 0x00, 0x04, 0x08, 0x0E, (byte) 0xE6, (byte) 0xE4};
    /**
     * SRI512 防冲突 N。此命令在发生冲突后使用时，放在“防冲突 0”之后使用，N 从 1 递增，至 到 F
     */

    public final static byte[] SRI512_ANTI_COLLISION_N = {0x55, 0x55, 0x00, 0x00, 0x00, 0x04, 0x08, 0x0C, 0x06, 0x06};


}
