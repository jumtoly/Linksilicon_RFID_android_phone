package app.terminal.com.serialport.util;

/**
 * Created by sly on 2016/5/21.
 */
public class CardData {
    private byte[] writeData;//控制字
    private int cardType;//卡类型
    private int findAddrType;//寻址方式
    private byte sectorAddr;//扇区地址
    private byte blockAddr;//块地址
    private int keyType;//密钥类型
    private byte[] key;//密钥


    public byte[] getwriteData() {
        return writeData;
    }

    public void setwriteData(byte[] writeData) {
        this.writeData = writeData;
    }

    public int getCardType() {
        return cardType;
    }

    public void setCardType(int cardType) {
        this.cardType = cardType;
    }

    public int getFindAddrType() {
        return findAddrType;
    }

    public void setFindAddrType(int findAddrType) {
        this.findAddrType = findAddrType;
    }

    public byte getSectorAddr() {
        return sectorAddr;
    }

    public void setSectorAddr(byte sectorAddr) {
        this.sectorAddr = sectorAddr;
    }

    public byte getBlockAddr() {
        return blockAddr;
    }

    public void setBlockAddr(byte blockAddr) {
        this.blockAddr = blockAddr;
    }

    public int getKeyType() {
        return keyType;
    }

    public void setKeyType(int keyType) {
        this.keyType = keyType;
    }

    public byte[] getKey() {
        return key;
    }

    public void setKey(byte[] key) {
        this.key = key;
    }

    public CardData(int cardType, int findAddrType, byte sectorAddr, byte blockAddr, int keyType, byte[] key) {

        this.cardType = cardType;
        this.findAddrType = findAddrType;
        this.sectorAddr = sectorAddr;
        this.blockAddr = blockAddr;
        this.keyType = keyType;
        this.key = key;
    }

    public CardData(byte[] writeData, int cardType, int findAddrType, byte sectorAddr, byte blockAddr, int keyType, byte[] key) {
        this.writeData = writeData;
        this.cardType = cardType;
        this.findAddrType = findAddrType;
        this.sectorAddr = sectorAddr;
        this.blockAddr = blockAddr;
        this.keyType = keyType;
        this.key = key;
    }

    public CardData(int cardType, int findAddrType, byte sectorAddr, byte blockAddr) {
        this.cardType = cardType;
        this.findAddrType = findAddrType;
        this.sectorAddr = sectorAddr;
        this.blockAddr = blockAddr;
    }

    public CardData(byte[] writeData, int cardType, int findAddrType, byte sectorAddr, byte blockAddr) {
        this.writeData = writeData;
        this.cardType = cardType;
        this.findAddrType = findAddrType;
        this.sectorAddr = sectorAddr;
        this.blockAddr = blockAddr;
    }
}
