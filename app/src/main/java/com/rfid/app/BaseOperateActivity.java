package com.rfid.app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import app.terminal.com.serialport.driver.UsbSerialPort;

public class BaseOperateActivity extends AppCompatActivity {

    private static UsbSerialPort sPort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_operate);
    }

    static void show(Context context, UsbSerialPort port) {
        sPort = port;
        final Intent intent = new Intent(context, BaseOperateActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
        context.startActivity(intent);
    }
}
