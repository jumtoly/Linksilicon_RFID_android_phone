package com.rfid.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import app.terminal.com.serialport.inter.BroadcastIntface;
import app.terminal.com.serialport.util.CheckResponeData;
import app.terminal.com.serialport.util.CreateControl;
import app.terminal.com.serialport.util.HexDump;
import app.terminal.com.serialport.util.ModifyKey;

public class ModifyControlActivity extends AppCompatActivity {
    private static ModifyKey modifyKey;
    private String[] spinnerList = {"KeyA", "KeyB", "KeyA|B", "Never"};
    private ArrayAdapter<String> spinnerAdapter;
    private Spinner modify0Read;
    private Spinner modify1Read;
    private Spinner modify2Read;
    private Spinner modify3BRead;
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
    private StringBuffer ctrlWordInfoStr = new StringBuffer();
    private String currentCtrlWordStr;

    private String read3B;
    private String write3A;
    private String write3B;
    private String write3Ctrl;


    private String[] read = new String[3];
    private String[] write = new String[3];
    private String[] add = new String[3];
    private String[] dec = new String[3];


    private static ModifyControlActivity modifyControlActivity;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    public static ModifyControlActivity getInstance() {
        if (modifyControlActivity == null) {
            modifyControlActivity = new ModifyControlActivity();

        }
        return modifyControlActivity;
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(BroadcastIntface.GETREADERID_BROADCASTRECEIVER)) {
                byte[] responsedata = intent.getByteArrayExtra("RESPONSEDATA");


                Message msg = Message.obtain();
                msg.obj = responsedata;
                handler.sendMessage(msg);
            }

        }
    };

    private void registerBoradcastReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(BroadcastIntface.GETREADERID_BROADCASTRECEIVER);
        //注册广播
        registerReceiver(broadcastReceiver, myIntentFilter);
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.obj != null) {
                byte[] msgByte = (byte[]) msg.obj;
                StringBuffer sb = new StringBuffer();
                boolean isOk = CheckResponeData.isOk(msgByte);
                if (isOk) {
                    sb.append(msgByte[13]).append(" ").append(msgByte[14]).append(" ").append(msgByte[15]).append(" ").append(msgByte[16]);
                    currentCtrlWord.setText(sb.toString());
                } else {
                    currentCtrlWord.setText("00 00 00 00 00");
                }
                byte[] responsedata = new byte[128];

                for (int i = 0; i < msgByte.length; i++) {
                    responsedata[i] = msgByte[i];
                }
                setControl(responsedata);
                return true;
            } else {
                return false;
            }
        }
    });


    private void setControl(byte[] data) {

        byte[] order = new byte[2];
        byte[] blockorder = new byte[4];
        if (data.length >= 15) {
            order[0] = data[14];
            order[1] = data[15];
        }
        if ((order[0] & 0x10) >= 1)
            blockorder[0] += 4;
        if ((order[1] & 0x01) >= 1)
            blockorder[0] += 2;
        if ((order[1] & 0x10) >= 1)
            blockorder[0] += 1;

        if ((order[0] & 0x20) >= 1)
            blockorder[1] += 4;
        if ((order[1] & 0x02) >= 1)
            blockorder[1] += 2;
        if ((order[1] & 0x20) >= 1)
            blockorder[1] += 1;

        if ((order[0] & 0x40) >= 1)
            blockorder[2] += 4;
        if ((order[1] & 0x04) >= 1)
            blockorder[2] += 2;
        if ((order[1] & 0x40) >= 1)
            blockorder[2] += 1;

        if ((order[0] & 0x80) >= 1)
            blockorder[3] += 4;
        if ((order[1] & 0x08) >= 1)
            blockorder[3] += 2;
        if ((order[1] & 0x80) >= 1)
            blockorder[3] += 1;
        switch (blockorder[0]) {
            case 0: {
                modify0Read.setSelection(2);
                modify0Write.setSelection(2);
                modify0Add.setSelection(2);
                modify0Dec.setSelection(2);


                break;
            }
            case 1: {
                modify0Read.setSelection(2);
                modify0Write.setSelection(3);
                modify0Add.setSelection(3);
                modify0Dec.setSelection(2);

                break;
            }
            case 2: {
                modify0Read.setSelection(2);
                modify0Write.setSelection(3);
                modify0Add.setSelection(3);
                modify0Dec.setSelection(3);
                break;
            }
            case 3: {
                modify0Read.setSelection(1);
                modify0Write.setSelection(1);
                modify0Add.setSelection(3);
                modify0Dec.setSelection(3);
                break;
            }
            case 4: {
                modify0Read.setSelection(2);
                modify0Write.setSelection(1);
                modify0Add.setSelection(3);
                modify0Dec.setSelection(3);

                break;
            }
            case 5: {
                modify0Read.setSelection(1);
                modify0Write.setSelection(3);
                modify0Add.setSelection(3);
                modify0Dec.setSelection(3);

                break;
            }
            case 6: {
                modify0Read.setSelection(2);
                modify0Write.setSelection(1);
                modify0Add.setSelection(1);
                modify0Dec.setSelection(2);
                break;
            }
            case 7: {
                modify0Read.setSelection(3);
                modify0Write.setSelection(3);
                modify0Add.setSelection(3);
                modify0Dec.setSelection(3);
                break;
            }
        }

        switch (blockorder[1]) {
            case 0: {
                modify1Read.setSelection(2);
                modify1Write.setSelection(2);
                modify1Add.setSelection(2);
                modify1Dec.setSelection(2);


                break;
            }
            case 1: {
                modify1Read.setSelection(2);
                modify1Write.setSelection(3);
                modify1Add.setSelection(3);
                modify1Dec.setSelection(2);

                break;
            }
            case 2: {
                modify1Read.setSelection(2);
                modify1Write.setSelection(3);
                modify1Add.setSelection(3);
                modify1Dec.setSelection(3);
                break;
            }
            case 3: {
                modify1Read.setSelection(1);
                modify1Write.setSelection(1);
                modify1Add.setSelection(3);
                modify1Dec.setSelection(3);
                break;
            }
            case 4: {
                modify1Read.setSelection(2);
                modify1Write.setSelection(1);
                modify1Add.setSelection(3);
                modify1Dec.setSelection(3);

                break;
            }
            case 5: {
                modify1Read.setSelection(1);
                modify1Write.setSelection(3);
                modify1Add.setSelection(3);
                modify1Dec.setSelection(3);

                break;
            }
            case 6: {
                modify1Read.setSelection(2);
                modify1Write.setSelection(1);
                modify1Add.setSelection(1);
                modify1Dec.setSelection(2);
                break;
            }
            case 7: {
                modify1Read.setSelection(3);
                modify1Write.setSelection(3);
                modify1Add.setSelection(3);
                modify1Dec.setSelection(3);
                break;
            }
        }

        switch (blockorder[2]) {
            case 0: {
                modify2Read.setSelection(2);
                modify2Write.setSelection(2);
                modify2Add.setSelection(2);
                modify2Dec.setSelection(2);


                break;
            }
            case 1: {
                modify2Read.setSelection(2);
                modify2Write.setSelection(3);
                modify2Add.setSelection(3);
                modify2Dec.setSelection(2);

                break;
            }
            case 2: {
                modify2Read.setSelection(2);
                modify2Write.setSelection(3);
                modify2Add.setSelection(3);
                modify2Dec.setSelection(3);
                break;
            }
            case 3: {
                modify2Read.setSelection(1);
                modify2Write.setSelection(1);
                modify2Add.setSelection(3);
                modify2Dec.setSelection(3);
                break;
            }
            case 4: {
                modify2Read.setSelection(2);
                modify2Write.setSelection(1);
                modify2Add.setSelection(3);
                modify2Dec.setSelection(3);

                break;
            }
            case 5: {
                modify2Read.setSelection(1);
                modify2Write.setSelection(3);
                modify2Add.setSelection(3);
                modify2Dec.setSelection(3);

                break;
            }
            case 6: {
                modify2Read.setSelection(2);
                modify2Write.setSelection(1);
                modify2Add.setSelection(1);
                modify2Dec.setSelection(2);
                break;
            }
            case 7: {
                modify2Read.setSelection(3);
                modify2Write.setSelection(3);
                modify2Add.setSelection(3);
                modify2Dec.setSelection(3);
                break;
            }
        }

        switch (blockorder[3]) {
            case 0: {
                modify3AWrite.setSelection(0);
                modify3CtrlWrite.setSelection(3);
                modify3BRead.setSelection(0);
                modify3BWrite.setSelection(0);


                break;
            }
            case 1: {
                modify3AWrite.setSelection(0);
                modify3CtrlWrite.setSelection(0);
                modify3BRead.setSelection(0);
                modify3BWrite.setSelection(0);

                break;
            }
            case 2: {
                modify3AWrite.setSelection(3);
                modify3CtrlWrite.setSelection(3);
                modify3BRead.setSelection(0);
                modify3BWrite.setSelection(3);
                break;
            }
            case 3: {
                modify3AWrite.setSelection(1);
                modify3CtrlWrite.setSelection(1);
                modify3BRead.setSelection(3);
                modify3BWrite.setSelection(1);
                break;
            }
            case 4: {
                modify3AWrite.setSelection(1);
                modify3CtrlWrite.setSelection(3);
                modify3BRead.setSelection(3);
                modify3BWrite.setSelection(1);

                break;
            }
            case 5: {
                modify3AWrite.setSelection(3);
                modify3CtrlWrite.setSelection(1);
                modify3BRead.setSelection(3);
                modify3BWrite.setSelection(3);

                break;
            }
            case 6: {
                modify3AWrite.setSelection(3);
                modify3CtrlWrite.setSelection(3);
                modify3BRead.setSelection(3);
                modify3BWrite.setSelection(3);
                break;
            }
            case 7: {
                modify3AWrite.setSelection(3);
                modify3CtrlWrite.setSelection(3);
                modify3BRead.setSelection(3);
                modify3BWrite.setSelection(3);
                break;
            }
        }
        StringBuffer stemp = new StringBuffer();
        for (int i = 13; i < 17; i++) {
            String s = String.format("%02X ", data[i]);
            stemp.append(s);
        }
        currentCtrlWord.setText(stemp.toString());

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_control);
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.8
        p.width = (int) (d.getWidth() * 0.9); // 宽度设置为屏幕的0.7
        getWindow().setAttributes(p);
        findViews();
        registerBoradcastReceiver();
        initView();
        getCurrentCtrlWord();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    private void initView() {


        spinnerAdapter = new ModifyArrayAdapter(this, spinnerList);
        modify0Read.setAdapter(spinnerAdapter);
        modify1Read.setAdapter(spinnerAdapter);
        modify2Read.setAdapter(spinnerAdapter);
        modify3BRead.setAdapter(spinnerAdapter);
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
        ctrlWordInfo = (TextView) findViewById(R.id.generat_ctrl_word);
        currentCtrlWord = (TextView) findViewById(R.id.current_ctrl_word);
        modify0Read = (Spinner) findViewById(R.id.modify_control_0_read);
        modify1Read = (Spinner) findViewById(R.id.modify_control_1_read);
        modify2Read = (Spinner) findViewById(R.id.modify_control_2_read);
        modify3BRead = (Spinner) findViewById(R.id.modify_control_3B_read);
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
        modify0Read.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                read[0] = spinnerList[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        modify1Read.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                read[1] = spinnerList[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        modify2Read.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                read[2] = spinnerList[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        modify3BRead.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                read3B = spinnerList[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        modify0Write.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                write[0] = spinnerList[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        modify1Write.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                write[1] = spinnerList[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        modify2Write.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                write[2] = spinnerList[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        modify3AWrite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                write3A = spinnerList[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        modify3BWrite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                write3B = spinnerList[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        modify3CtrlWrite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                write3Ctrl = spinnerList[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        modify0Add.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                add[0] = spinnerList[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        modify1Add.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                add[1] = spinnerList[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        modify2Add.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                add[2] = spinnerList[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        modify0Dec.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dec[0] = spinnerList[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        modify1Dec.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dec[1] = spinnerList[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        modify2Dec.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dec[2] = spinnerList[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    static void show(Context context, ModifyKey modifyKey) {
        ModifyControlActivity.modifyKey = modifyKey;
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
        ctrlWordInfoStr.delete(0, ctrlWordInfoStr.length());
        byte[] str = {0x01, 0, 0, 0x69};
        for (int i = 0; i < 3; i++) {
            if (("KeyA|B".equals(read[i])) && ("KeyA|B".equals(write[i])) && ("KeyA|B".equals(add[i])) && ("KeyA|B".equals(dec[i]))) {
            } else if (("KeyA|B".equals(read[i])) && ("Never".equals(write[i])) && ("Never".equals(add[i])) && ("Never".equals(dec[i]))) {
                str[2] += (0x01 * str[0]);
            } else if (("KeyA|B".equals(read[i])) && ("KeyB".equals(write[i])) && ("Never".equals(add[i])) && ("Never".equals(dec[i]))) {
                str[1] += (0x10 * str[0]);
            } else if (("KeyA|B".equals(read[i])) && ("KeyB".equals(write[i])) && ("KeyB".equals(add[i])) && ("KeyA|B".equals(dec[i]))) {
                str[1] += (0x10 * str[0]);
                str[2] += (0x01 * str[0]);
            } else if (("KeyA|B".equals(read[i])) && ("Never".equals(write[i])) && ("Never".equals(add[i])) && ("KeyA|B".equals(dec[i]))) {
                str[2] += (0x10 * str[0]);
            } else if (("KeyB".equals(read[i])) && ("KeyB".equals(write[i])) && ("Never".equals(add[i])) && ("Never".equals(dec[i]))) {
                str[2] += (0x11 * str[0]);
            } else if (("KeyB".equals(read[i])) && ("Never".equals(write[i])) && ("Never".equals(add[i])) && ("Never".equals(dec[i]))) {
                str[1] += (0x10 * str[0]);
                str[2] += (0x10 * str[0]);
            } else if (("Never".equals(read[i])) && ("Never".equals(write[i])) && ("Never".equals(add[i])) && ("Never".equals(dec[i]))) {
                str[1] += (0x10 * str[0]);
                str[2] += (0x11 * str[0]);
            } else {
                Toast.makeText(this, "控制字无当前组合，请查阅相关文档！", Toast.LENGTH_SHORT).show();
                return;
            }
            str[0] = (byte) (str[0] << 1);
        }

        if (("KeyA".equals(write3A)) && ("Never".equals(write3Ctrl)) && ("KeyA".equals(read3B)) && ("KeyA".equals(write3B))) {
        } else if (("Never".equals(write3A)) && ("Never".equals(write3Ctrl)) && ("KeyA".equals(read3B)) && ("Never".equals(write3B))) {
            str[2] += 0x08;
        } else if (("KeyB".equals(write3A)) && ("Never".equals(write3Ctrl)) && ("Never".equals(read3B)) && ("KeyB".equals(write3B))) {
            str[1] += 0x80;
        } else if (("Never".equals(write3A)) && ("Never".equals(write3Ctrl)) && ("Never".equals(read3B)) && ("Never".equals(write3B))) {
            str[1] += 0x80;
            str[2] += 0x08;
        } else if (("KeyA".equals(write3A)) && ("KeyA".equals(write3Ctrl)) && ("KeyA".equals(read3B)) && ("KeyA".equals(write3B))) {
            str[2] += 0x80;
        } else if (("KeyB".equals(write3A)) && ("KeyB".equals(write3Ctrl)) && ("Never".equals(read3B)) && ("KeyB".equals(write3B))) {
            str[2] += 0x88;
        } else if (("Never".equals(write3A)) && ("KeyB".equals(write3Ctrl)) && ("Never".equals(read3B)) && ("Never".equals(write3B))) {
            str[1] += 0x80;
            str[2] += 0x80;
        } else if (("Never".equals(write3A)) && ("Never".equals(write3Ctrl)) && ("Never".equals(read3B)) && ("Never".equals(write3B))) {
            str[1] += 0x80;
            str[2] += 0x88;
        } else {
            Toast.makeText(this, "控制字无当前组合，请查阅相关文档！", Toast.LENGTH_SHORT).show();
            return;
        }
        str[1] += ((~str[2]) >> 4) & 0x0f;
        str[0] = (byte) ((((~str[1]) >> 4) & 0x0f) + (((~str[2]) << 4) & 0xf0));
        for (int i = 0; i < 4; i++) {
            String s = String.format("%02X", str[i]);
            ctrlWordInfoStr.append(s).append(" ");
        }

        ctrlWordInfo.setText(ctrlWordInfoStr.toString().substring(0, ctrlWordInfoStr.length() - 1));
    }

    /**
     * 确认修改
     *
     * @param v
     */
    public void okModify(View v) {
        if (currentCtrlWord.getText().toString().trim() == null || "".equals(currentCtrlWord.getText().toString().trim())) {
            Toast.makeText(this, "请先详细设置访问条件并生成控制字！", Toast.LENGTH_SHORT).show();
            return;
        }
        byte[] currentCtrlWordBytes = HexDump.hexStringToByteArray(this, currentCtrlWord.getText().toString().replaceAll("\\s*", ""));
        byte[] ctrlWordInfoBytes = HexDump.hexStringToByteArray(this, ctrlWordInfo.getText().toString().replaceAll("\\s*", ""));
        BaseApp.instance().controlLinksilliconCardIntface.modifyControl(this, modifyKey, currentCtrlWordBytes, ctrlWordInfoBytes);
        finish();
    }

    /**
     * 取消
     *
     * @param v
     */
    public void cnacelModify(View v) {
        finish();
    }

    public void getCurrentCtrlWord() {
        BaseApp.instance().controlLinksilliconCardIntface.readCtrlWord(this, modifyKey.getSector(), modifyKey.getaOldKey());
        SystemClock.sleep(200);
        if (CreateControl.getInstance().getOldctrl() == "") {
            currentCtrlWord.setText("00 00 00 00 00");
        } else {
            currentCtrlWord.setText(CreateControl.getInstance().getOldctrl());
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("ModifyControl Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
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


