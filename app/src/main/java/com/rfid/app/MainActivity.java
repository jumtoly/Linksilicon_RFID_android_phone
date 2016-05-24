package com.rfid.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import app.terminal.com.serialport.inter.BroadcastIntface;
import app.terminal.com.serialport.util.CheckResponeData;
import app.terminal.com.serialport.util.HexDump;
import app.terminal.com.serialport.util.SerialPortEntity;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();


    private TextView mDumpTextView;
    private ScrollView mScrollView;
    private Spinner baudRateSpinner;
    private int baudRate;
    private String sendCmd;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(BroadcastIntface.GETREADERID_BROADCASTRECEIVER)) {
                byte[] responsedata = intent.getByteArrayExtra("RESPONSEDATA");
                byte[] sendData = intent.getByteArrayExtra("SENDDATA");
                updateReceivedData(sendData, responsedata);

            }
        }
    };

    private String getCardId(byte[] data) {
        byte[] result = new byte[4];
        if (data.length >= 12) {
            for (int i = 0; i < 4; i++) {
                result[i] = data[i + 7];
            }
            return HexDump.toHexString(result);
        }
        return "";

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        registerBoradcastReceiver();
        init();

    }

    private void registerBoradcastReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(BroadcastIntface.GETREADERID_BROADCASTRECEIVER);
        //注册广播
        registerReceiver(broadcastReceiver, myIntentFilter);
    }

    private void init() {
        mDumpTextView = (TextView) findViewById(R.id.consoleText);
        mScrollView = (ScrollView) findViewById(R.id.consoleScroller);
        baudRateSpinner = (Spinner) findViewById(R.id.baud_rate_spinner);
        final List<Integer> spinnerList = new ArrayList<>();
        spinnerList.add(115200);
        spinnerList.add(57600);
        spinnerList.add(38400);
        spinnerList.add(19200);
        spinnerList.add(9600);
        spinnerList.add(4800);
        spinnerList.add(2400);

        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerList);
        adapter.setDropDownViewResource(R.layout.drop_down_item);
        baudRateSpinner.setAdapter(adapter);
        baudRateSpinner.setSelection(4);
        baudRateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                baudRate = spinnerList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    /**
     * 获取序列号
     *
     * @param v
     */
    public void getSerialNumber(View v) {

        if (!BaseApp.instance().controlLinksilliconCardIntface.isReaderOpen()) {
            Toast.makeText(this, "请先打开读卡器串口", Toast.LENGTH_SHORT).show();
            SerialPortSettingsActivity.show(this);
        }
        BaseApp.instance().controlLinksilliconCardIntface.getReaderId(this);

    }

    /**
     * 设置波特率
     *
     * @param v
     */
    public void baudRateSetting(View v) {
        if (!BaseApp.instance().controlLinksilliconCardIntface.isReaderOpen()) {
            Toast.makeText(this, "请先打开读卡器串口", Toast.LENGTH_SHORT).show();
            SerialPortSettingsActivity.show(this);
        }
        BaseApp.instance().controlLinksilliconCardIntface.setBaudRate(this, baudRate);
    }

    /**
     * 串口设置
     *
     * @param v
     */
    public void serialPortSetting(View v) {
        SerialPortSettingsActivity.show(this);
    }

    /**
     * 基本操作
     *
     * @param v
     */
    public void basicOperation(View v) {
        BaseOperateActivity.show(this, SerialPortEntity.getInstance().getSerialPort());

    }


    /**
     * 卡片基本操作
     *
     * @param v
     */
    public void basicOperationCard(View v) {
        BasicOperationCardActivity.show(this);

    }

    /**
     * S50
     *
     * @param v
     */
    public void S50(View v) {
        S50CardOperateActivity.show(this);

    }

    /**
     * S70
     *
     * @param v
     */
    public void S70(View v) {
        S70CardOperateActivity.show(this);
    }

    /**
     * cpu
     *
     * @param v
     */
    public void cpu(View v) {
        CpuCardOperateActivity.show(this);
    }


    public void updateReceivedData(byte[] data, byte[] responsedata) {

        if (responsedata != null) {
            if (CheckResponeData.isOk(responsedata)) {
                StringBuilder msg = new StringBuilder();
                msg.append("发送命令：" + HexDump.toHexString(data) + System.getProperty("line.separator")).append("接收数据：" + HexDump.toHexString(responsedata)).append("执行命令成功").append(System.getProperty("line.separator") + System.getProperty("line.separator"));
                ;
                mDumpTextView.append(msg.toString());
                mScrollView.smoothScrollTo(0, mDumpTextView.getBottom());
                ((TextView) findViewById(R.id.serial_number)).setText(getCardId(responsedata));
            } else {
                StringBuilder msg = new StringBuilder();
                msg.append("发送命令：" + HexDump.toHexString(data) + System.getProperty("line.separator")).append("接收数据：" + HexDump.toHexString(responsedata)).append(CheckResponeData.getErrorInfo(responsedata) + System.getProperty("line.separator") + System.getProperty("line.separator"));
                mDumpTextView.append(msg.toString());
                mScrollView.smoothScrollTo(0, mDumpTextView.getBottom());
                ((TextView) findViewById(R.id.serial_number)).setText(getCardId(responsedata));
            }

        }


    }

    public void clearData(View v) {
        mDumpTextView.setText("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
