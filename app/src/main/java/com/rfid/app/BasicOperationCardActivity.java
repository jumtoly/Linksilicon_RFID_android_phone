package com.rfid.app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import app.terminal.com.serialport.inter.ControlLinksilliconCardIntface;
import app.terminal.com.serialport.util.DeviceStateChangeUtils;
import app.terminal.com.serialport.util.SendByteData;

import app.terminal.com.serialport.driver.UsbSerialPort;
import app.terminal.com.serialport.util.SerialportControl;

public class BasicOperationCardActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_operation_card);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!BaseApp.instance().controlLinksilliconCardIntface.isReaderOpen()) {
            Toast.makeText(this, "请先打开读卡器串口", Toast.LENGTH_SHORT).show();
            SerialPortSettingsActivity.show(this);
            this.finish();
        }
    }

    static void show(Context context, UsbSerialPort port) {
        final Intent intent = new Intent(context, BasicOperationCardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
        context.startActivity(intent);
    }

    /**
     * 获取卡片信息
     *
     * @param v
     */
    public void getCardInfo(View v) {
        //TODO 没有写完整
        BaseApp.instance().controlLinksilliconCardIntface.getCardInfo();
    }

    /**
     * 标准询卡
     *
     * @param v
     */
    public void standardFindCard(View v) {
        BaseApp.instance().controlLinksilliconCardIntface.findCard();
    }

    /**
     * 唤醒询卡
     *
     * @param v
     */
    public void wakeUpFindCard(View v) {
        BaseApp.instance().controlLinksilliconCardIntface.wakeUp();
    }

    /**
     * 防冲突
     *
     * @param v
     */
    public void antiCollision(View v) {
        //TODO 第一次发送防冲突命令时，串联级别为93。若接收到的UID 以 88 开头，则需要在选卡 命令之后再次发送防冲突命令和选卡命令，
        // TODO 事先须将串联级别改为 95。依次类推。串联级别 共有三级：93，95，97。详情见ISO14443-3 第 6.4.3.2 节
//        DeviceStateChangeUtils.getInstence(sPort).onDeviceStateChange(SendByteData.ANTI_COLLISION_14443A);
    }

    /**
     * 复合寻卡
     *
     * @param v
     */
    public void complexFindCard(View v) {
        BaseApp.instance().controlLinksilliconCardIntface.composeFindCard();
    }

    /**
     * 选卡
     *
     * @param v
     */
    public void selectCard(View v) {
        //TODO 串联等级，待确认
        BaseApp.instance().controlLinksilliconCardIntface.selectCard(147);
    }

    /**
     * 暂停卡
     *
     * @param v
     */
    public void stopCard(View v) {
        BaseApp.instance().controlLinksilliconCardIntface.pauseCard();
    }


}
