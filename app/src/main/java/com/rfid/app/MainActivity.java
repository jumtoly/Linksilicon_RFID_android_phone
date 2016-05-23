package com.rfid.app;

import android.content.Context;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.terminal.com.serialport.util.DeviceStateChangeUtils;
import app.terminal.com.serialport.util.SendByteData;

import app.terminal.com.serialport.driver.UsbSerialPort;
import app.terminal.com.serialport.util.HexDump;
import app.terminal.com.serialport.util.SerialPortEntity;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();


    private TextView mDumpTextView;
    private ScrollView mScrollView;
    private Spinner baudRateSpinner;
    private int baudRate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        init();

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
//        DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort()).onDeviceStateChange(SendByteData.SERIAL_NUMBER_BYTE);

    }

    /**
     * 设置波特率
     *
     * @param v
     */
    public void baudRateSetting(View v) {
//        DeviceStateChangeUtils.getInstence(SerialPortEntity.getInstance().getSerialPort()).onDeviceStateChange(SendByteData.BAUD_RATE);
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
        BasicOperationCardActivity.show(this, SerialPortEntity.getInstance().getSerialPort());

    }

    /**
     * S50
     *
     * @param v
     */
    public void S50(View v) {
        S50CardOperateActivity.show(this, SerialPortEntity.getInstance().getSerialPort());

    }

    /**
     * S70
     *
     * @param v
     */
    public void S70(View v) {
        S70CardOperateActivity.show(this, SerialPortEntity.getInstance().getSerialPort());
    }

    /**
     * cpu
     *
     * @param v
     */
    public void cpu(View v) {
        CpuCardOperateActivity.show(this, SerialPortEntity.getInstance().getSerialPort());
    }


    private void updateReceivedData(byte[] data) {
        final String message = "Read " + data.length + " bytes: \n"
                + HexDump.dumpHexString(data) + "\n\n";
        mDumpTextView.append(message);
        mScrollView.smoothScrollTo(0, mDumpTextView.getBottom());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
