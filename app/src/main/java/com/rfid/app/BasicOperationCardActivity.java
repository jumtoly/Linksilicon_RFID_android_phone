package com.rfid.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import app.terminal.com.serialport.inter.BroadcastIntface;
import app.terminal.com.serialport.util.CheckResponeData;
import app.terminal.com.serialport.util.HexDump;

public class BasicOperationCardActivity extends AppCompatActivity {
    private TextView cardInfo;
    private BroadcastReceiver getCardInfoBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(BroadcastIntface.GETCARDDATA_BROADCASTRECEIVER)) {
                byte[] respondents = intent.getByteArrayExtra("RESPONSEDATA");
                byte[] sendData = intent.getByteArrayExtra("SENDDATA");
                updateReceivedData(sendData, respondents);

            }
        }
    };
    private BroadcastReceiver conflictBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(BroadcastIntface.CONFLICT_BROADCASTRECEIVER)) {

                byte[] respondents = intent.getByteArrayExtra("RESPONSEDATA");
                byte[] sendData = intent.getByteArrayExtra("SENDDATA");

                if (respondents[7] == 0x88) {
                    BaseApp.instance().controlLinksilliconCardIntface.selectCard(BasicOperationCardActivity.this, (byte) 0x95);
                    BaseApp.instance().controlLinksilliconCardIntface.conflict(BasicOperationCardActivity.this, (byte) 0x95);
                }
                Intent mIntent = new Intent(BroadcastIntface.GETREADERID_BROADCASTRECEIVER);
                mIntent.putExtra("SENDDATA", sendData);
                mIntent.putExtra("RESPONSEDATA", respondents);
                sendBroadcast(mIntent);


            }
        }
    };

    public void updateReceivedData(byte[] data, byte[] responsedata) {

        if (responsedata != null) {
            Intent mIntent = new Intent(BroadcastIntface.GETREADERID_BROADCASTRECEIVER);
            mIntent.putExtra("SENDDATA", data);
            mIntent.putExtra("RESPONSEDATA", responsedata);
            sendBroadcast(mIntent);
            if (CheckResponeData.isOk(responsedata)) {
                cardInfo.setText(getCardId(responsedata));
            } else {
                cardInfo.setText(getCardId(responsedata));
            }

        }


    }

    private String getCardId(byte[] data) {
        byte[] result = new byte[4];
        String cardType = "";
        if (data.length >= 13) {
            for (int i = 0; i < 4; i++) {
                result[i] = data[i + 8];
            }
            switch (data[7]) {
                case 0x04:
                    cardType = "S50卡";
                    break;
                case 0x02:
                    cardType = "S70卡";
                    break;
                case 0x08:
                    cardType = "CPU卡";
                    break;
                case 0x06:
                    cardType = "身份证";
                    break;
            }
            return HexDump.toHexString(result) + cardType;
        }
        return "";

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_operation_card);
        cardInfo = (TextView) findViewById(R.id.card_info);
        registerBoradcastReceiver();
    }

    private void registerBoradcastReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(BroadcastIntface.GETCARDDATA_BROADCASTRECEIVER);
        //注册广播
        registerReceiver(getCardInfoBroadcastReceiver, myIntentFilter);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BroadcastIntface.CONFLICT_BROADCASTRECEIVER);
        //注册广播
        registerReceiver(conflictBroadcastReceiver, intentFilter);
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

    static void show(Context context) {
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
        BaseApp.instance().controlLinksilliconCardIntface.getCardInfo(this);
    }

    /**
     * 标准询卡
     *
     * @param v
     */
    public void standardFindCard(View v) {
        BaseApp.instance().controlLinksilliconCardIntface.findCard(this);
    }

    /**
     * 唤醒询卡
     *
     * @param v
     */
    public void wakeUpFindCard(View v) {
        BaseApp.instance().controlLinksilliconCardIntface.wakeUp(this);
    }

    /**
     * 防冲突
     *
     * @param v
     */
    public void antiCollision(View v) {
        BaseApp.instance().controlLinksilliconCardIntface.conflict(this, (byte) 0x93);
    }

    /**
     * 复合寻卡
     *
     * @param v
     */
    public void complexFindCard(View v) {
        BaseApp.instance().controlLinksilliconCardIntface.composeFindCard(this);
    }

    /**
     * 选卡
     *
     * @param v
     */
    public void selectCard(View v) {
        //串联等级 0x93,0x95,0x97
        BaseApp.instance().controlLinksilliconCardIntface.selectCard(this, (byte) 0x93);
    }

    /**
     * 暂停卡
     *
     * @param v
     */
    public void stopCard(View v) {
        BaseApp.instance().controlLinksilliconCardIntface.pauseCard(this);
    }


}
