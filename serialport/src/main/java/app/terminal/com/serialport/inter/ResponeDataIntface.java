package app.terminal.com.serialport.inter;

/**
 * Created by liu.yao on 2016/5/24.
 */
public interface ResponeDataIntface {
    void responseData(byte[] data);

    void sendData(byte[] data);

    void onRunError(Exception e);
}
