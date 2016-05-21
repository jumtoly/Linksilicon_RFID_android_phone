package app.terminal.com.serialport.util;

/**
 * Created by sly on 2016/5/21.
 */
public class CardInfo {
    private CardType cardType;
    private byte[] uid = new byte[32];

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public byte[] getUid() {
        return uid;
    }

    public void setUid(byte[] uid) {
        this.uid = uid;
    }
}
