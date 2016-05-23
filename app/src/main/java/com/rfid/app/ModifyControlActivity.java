package com.rfid.app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.terminal.com.serialport.driver.UsbSerialPort;

public class ModifyControlActivity extends AppCompatActivity {
    private ArrayAdapter<String> spinnerAdapter;
    private Spinner modify0Read;
    private Spinner modify1Read;
    private Spinner modify2Read;
    private Spinner modify3ARead;
    private Spinner modify3BRead;
    private Spinner modify3CtrlRead;
    private Spinner modify0Write;
    private Spinner modify1Write;
    private Spinner modify2Write;
    private Spinner modify3AWrite;
    private Spinner modify3BWrite;
    private Spinner modify3CtrlWrite;
    private Spinner modify0Add;
    private Spinner modify1Add;
    private Spinner modify2Add;
    private Spinner modify0Dec;
    private Spinner modify1Dec;
    private Spinner modify2Dec;
    private TextView ctrlWordInfo;
    private TextView currentCtrlWord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_control);
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        android.view.WindowManager.LayoutParams p = getWindow().getAttributes();
        p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.8
        p.width = (int) (d.getWidth() * 0.9); // 宽度设置为屏幕的0.7
        getWindow().setAttributes(p);
        findViews();
        initView();
    }

    private void initView() {
        String[] spinnerList = {"KeyA", "KeyB", "KeyA|KeyB", "Never"};

        spinnerAdapter = new ModifyArrayAdapter(this, spinnerList);
//        spinnerAdapter.setDropDownViewResource(R.layout.drop_down_item);
        modify0Read.setAdapter(spinnerAdapter);
        modify1Read.setAdapter(spinnerAdapter);
        modify2Read.setAdapter(spinnerAdapter);
        modify3ARead.setAdapter(spinnerAdapter);
        modify3BRead.setAdapter(spinnerAdapter);
        modify3CtrlRead.setAdapter(spinnerAdapter);
        modify0Write.setAdapter(spinnerAdapter);
        modify1Write.setAdapter(spinnerAdapter);
        modify2Write.setAdapter(spinnerAdapter);
        modify3AWrite.setAdapter(spinnerAdapter);
        modify3BWrite.setAdapter(spinnerAdapter);
        modify3CtrlWrite.setAdapter(spinnerAdapter);
        modify0Add.setAdapter(spinnerAdapter);
        modify1Add.setAdapter(spinnerAdapter);
        modify2Add.setAdapter(spinnerAdapter);
        modify0Dec.setAdapter(spinnerAdapter);
        modify1Dec.setAdapter(spinnerAdapter);
        modify2Dec.setAdapter(spinnerAdapter);
    }

    private void findViews() {
        modify0Read = (Spinner) findViewById(R.id.modify_control_0_read);
        modify1Read = (Spinner) findViewById(R.id.modify_control_1_read);
        modify2Read = (Spinner) findViewById(R.id.modify_control_2_read);
        modify3ARead = (Spinner) findViewById(R.id.modify_control_3A_read);
        modify3BRead = (Spinner) findViewById(R.id.modify_control_3B_read);
        modify3CtrlRead = (Spinner) findViewById(R.id.modify_control_3ctrl_read);
        modify0Write = (Spinner) findViewById(R.id.modify_control_0_write);
        modify1Write = (Spinner) findViewById(R.id.modify_control_1_write);
        modify2Write = (Spinner) findViewById(R.id.modify_control_2_write);
        modify3AWrite = (Spinner) findViewById(R.id.modify_control_3A_write);
        modify3BWrite = (Spinner) findViewById(R.id.modify_control_3B_write);
        modify3CtrlWrite = (Spinner) findViewById(R.id.modify_control_3ctrl_write);
        modify0Add = (Spinner) findViewById(R.id.modify_control_0_add);
        modify1Add = (Spinner) findViewById(R.id.modify_control_1_add);
        modify2Add = (Spinner) findViewById(R.id.modify_control_2_add);
        modify0Dec = (Spinner) findViewById(R.id.modify_control_0_dec);
        modify1Dec = (Spinner) findViewById(R.id.modify_control_1_dec);
        modify2Dec = (Spinner) findViewById(R.id.modify_control_2_dec);
    }

    static void show(Context context) {
        final Intent intent = new Intent(context, ModifyControlActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
        context.startActivity(intent);
    }

    /**
     * 生成控制字
     *
     * @param v
     */
    public void generatCtrlWord(View v) {

    }

    /**
     * 确认修改
     *
     * @param v
     */
    public void okModify(View v) {

    }

    /**
     * 取消
     *
     * @param v
     */
    public void cnacelModify(View v) {

    }

    private class ModifyArrayAdapter extends ArrayAdapter<String> {

        private Context mContext;
        private String[] mStringArray;


        public ModifyArrayAdapter(Context context, String[] objects) {
            super(context, android.R.layout.simple_spinner_item, objects);
            mContext = context;
            mStringArray = objects;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                convertView = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
            }
            TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
            tv.setText(mStringArray[position]);
            tv.setTextSize(8f);
            return convertView;
        }
    }
}


