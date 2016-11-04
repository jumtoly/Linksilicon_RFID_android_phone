package app.terminal.com.serialport.util;

/**
 * Created by sly on 2016/5/22.
 */
public class ModifyKey {
    private int sector;//密钥扇区
    private int keyType;//密钥类型 a密钥或b密钥
    private byte[] aOldKey;//A旧密钥
    private byte[] bOldKey;//B旧密钥
    private byte[] aNewKey;//A新密钥
    private byte[] bNewKey;//B新密钥

    public int getSector() {
        return sector;
    }

    public void setSector(int sector) {
        this.sector = sector;
    }


    public byte[] getaOldKey() {
        return aOldKey;
    }

    public void setaOldKey(byte[] aOldKey) {
        this.aOldKey = aOldKey;
    }

    public byte[] getbOldKey() {
        return bOldKey;
    }

    public void setbOldKey(byte[] bOldKey) {
        this.bOldKey = bOldKey;
    }

    public byte[] getaNewKey() {
        return aNewKey;
    }

    public void setaNewKey(byte[] aNewKey) {
        this.aNewKey = aNewKey;
    }

    public byte[] getbNewKey() {
        return bNewKey;
    }

    public void setbNewKey(byte[] bNewKey) {
        this.bNewKey = bNewKey;
    }

    public ModifyKey(int sector, int keyType, byte[] aOldKey, byte[] bOldKey, byte[] aNewKey, byte[] bNewKey) {
        this.keyType = keyType;
        this.sector = sector;
        this.aOldKey = aOldKey;
        this.bOldKey = bOldKey;
        this.aNewKey = aNewKey;
        this.bNewKey = bNewKey;
    }
}
