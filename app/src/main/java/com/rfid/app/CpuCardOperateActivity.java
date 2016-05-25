package com.rfid.app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import app.terminal.com.serialport.driver.UsbSerialPort;
import app.terminal.com.serialport.util.HexDump;

public class CpuCardOperateActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpu_card_operate);
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        android.view.WindowManager.LayoutParams p = getWindow().getAttributes();
        p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.8
        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.7
        getWindow().setAttributes(p);
    }

    /**
     * COS激活
     *
     * @param v
     */
    public void cosActivation(View v) {
        BaseApp.instance().controlLinksilliconCardIntface.cosActivation(this);
    }

    /**
     * COS停活
     *
     * @param v
     */
    public void cosStop(View v) {
        BaseApp.instance().controlLinksilliconCardIntface.cosActivation(this);
    }

    /**
     * 发送命令
     *
     * @param v
     */
    public void sendCmd(View v) {
        EditText cmd = (EditText) findViewById(R.id.cos_cmd_et);
        if (cmd.getText().toString().trim().length() % 2 != 0) {
            Toast.makeText(this, "请输入正确的COS命令", Toast.LENGTH_SHORT).show();
            return;
        }
        byte[] cmdByte = HexDump.hexStringToByteArray(cmd.getText().toString().trim());
        BaseApp.instance().controlLinksilliconCardIntface.sendCosCommand(this, cmdByte);
    }

    /**
     * 复合外部认证
     *
     * @param v
     */
    public void compoundExtAuth(View v) {
        EditText ketEt = (EditText) findViewById(R.id.cos_key_et);
        EditText ketLogoEt = (EditText) findViewById(R.id.cos_key_logo_et);
        if (ketEt.getText().toString().length() % 2 != 0 || ketLogoEt.getText().toString().trim().length() % 2 != 0) {
            Toast.makeText(this, "请输入正确的密钥或密钥标识号", Toast.LENGTH_SHORT).show();
            return;
        }
        byte[] ketByte = HexDump.hexStringToByteArray(ketEt.getText().toString().trim());
        byte[] ketLogoByte = HexDump.hexStringToByteArray(ketLogoEt.getText().toString().trim());
        BaseApp.instance().controlLinksilliconCardIntface.exauthentication(this, ketByte, ketLogoByte);

    }

    static void show(Context context) {
        final Intent intent = new Intent(context, CpuCardOperateActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
        context.startActivity(intent);
    }
}
