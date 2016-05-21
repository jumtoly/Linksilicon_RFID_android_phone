package com.rfid.app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.terminal.com.serialport.util.DeviceStateChangeUtils;
import app.terminal.com.serialport.util.SendByteData;

import app.terminal.com.serialport.driver.UsbSerialPort;

public class S50CardOperateActivity extends AppCompatActivity {

    private static UsbSerialPort sPort;
    private EditText privateKeyEt;
    private EditText blockDataEt;
    private EditText moneyNumEt;
    private EditText aOldKeyEt;
    private EditText bOldKeyEt;
    private EditText aNewKeyEt;
    private EditText bNewKeyEt;
    private RadioGroup radioGroup;
    private RadioButton aRadioBtn;
    private RadioButton bRadioBtn;

    private Spinner findAddrWaySpinner;
    private Spinner sectorAddressSpinner;
    private Spinner blockAddressSpinner;
    private Spinner selectSectorSpinner;

    private List<String> findAddrWaySpinnerList;
    private ArrayAdapter<String> findAddrWaySpinnerAdapter;

    private List<Integer> sectorAddressSpinnerList;
    private ArrayAdapter<Integer> sectorAddressSpinnerAdapter;

    private List<Integer> blockAddressSpinnerList;
    private ArrayAdapter<Integer> blockAddressSpinnerAdapter;

    private List<Integer> selectSectorSpinnerList;
    private ArrayAdapter<Integer> selectSectorSpinnerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s50_card_operate);
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        android.view.WindowManager.LayoutParams p = getWindow().getAttributes();
        p.height = (int) (d.getHeight() * 0.8); // 高度设置为屏幕的0.8
        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.7
        getWindow().setAttributes(p);
        findViews();
        initData();
    }

    private void initData() {
        findAddrWaySpinnerList = new ArrayList<>();
        sectorAddressSpinnerList = new ArrayList<>();
        blockAddressSpinnerList = new ArrayList<>();
        selectSectorSpinnerList = new ArrayList<>();

        findAddrWaySpinnerList.add("绝对寻址");
        findAddrWaySpinnerList.add("相对寻址");
        for (int i = 0; i < 16; i++) {
            sectorAddressSpinnerList.add(i);
            selectSectorSpinnerList.add(i);
        }
        for (int i = 0; i < 4; i++) {
            blockAddressSpinnerList.add(i);
        }

        //适配器
        findAddrWaySpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, findAddrWaySpinnerList);
        sectorAddressSpinnerAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, sectorAddressSpinnerList);
        blockAddressSpinnerAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, blockAddressSpinnerList);
        selectSectorSpinnerAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, selectSectorSpinnerList);
        //设置样式
        findAddrWaySpinnerAdapter.setDropDownViewResource(R.layout.drop_down_item);
        sectorAddressSpinnerAdapter.setDropDownViewResource(R.layout.drop_down_item);
        blockAddressSpinnerAdapter.setDropDownViewResource(R.layout.drop_down_item);
        selectSectorSpinnerAdapter.setDropDownViewResource(R.layout.drop_down_item);
        //加载适配器
        findAddrWaySpinner.setAdapter(findAddrWaySpinnerAdapter);
        sectorAddressSpinner.setAdapter(sectorAddressSpinnerAdapter);
        blockAddressSpinner.setAdapter(blockAddressSpinnerAdapter);
        selectSectorSpinner.setAdapter(selectSectorSpinnerAdapter);

    }

    private void findViews() {
        privateKeyEt = (EditText) findViewById(R.id.s50_private_key);
        blockDataEt = (EditText) findViewById(R.id.s50_block_data);
        moneyNumEt = (EditText) findViewById(R.id.s50_money_num);
        aOldKeyEt = (EditText) findViewById(R.id.s50_a_old_key);
        bOldKeyEt = (EditText) findViewById(R.id.s50_b_old_key);
        aNewKeyEt = (EditText) findViewById(R.id.s50_a_new_key);
        bNewKeyEt = (EditText) findViewById(R.id.s50_b_new_key);
        radioGroup = (RadioGroup) findViewById(R.id.s50_key_type_radiogroup);
        aRadioBtn = (RadioButton) findViewById(R.id.s50_key_type_radiobtn_A);
        bRadioBtn = (RadioButton) findViewById(R.id.s50_key_type_radiobtn_B);
        findAddrWaySpinner = (Spinner) findViewById(R.id.s50_find_addr_way_spinner);
        sectorAddressSpinner = (Spinner) findViewById(R.id.s50_sector_address_spinner);
        blockAddressSpinner = (Spinner) findViewById(R.id.s50_block_address_spinner);
        selectSectorSpinner = (Spinner) findViewById(R.id.s50_select_sector_spinner);

        aRadioBtn.setChecked(true);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

            }
        });
        findAddrWaySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sectorAddressSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        blockAddressSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        selectSectorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    /**
     * 密钥验证
     *
     * @param v
     */
    public void s50KeyAuthentiation(View v) {
//        DeviceStateChangeUtils.getInstence(sPort).onDeviceStateChange(SendByteData.KEY_AUTHENTICATION_M1);
    }

    /**
     * 复合读块
     *
     * @param v
     */
    public void s50CompositeBlockRead(View v) {
//        DeviceStateChangeUtils.getInstence(sPort).onDeviceStateChange(SendByteData.COMPOSITE_READING_BLOCK_M1);
    }

    /**
     * 复合写块
     *
     * @param v
     */
    public void s50CompositeBlockWrite(View v) {
//        DeviceStateChangeUtils.getInstence(sPort).onDeviceStateChange(SendByteData.COMPOSITE_WRITE_BLOCK_M1);
    }

    /**
     * 读块
     *
     * @param v
     */
    public void s50ReadBlock(View v) {
//        DeviceStateChangeUtils.getInstence(sPort).onDeviceStateChange(SendByteData.READ_BLOCK_M1);
    }


    /**
     * 写块
     *
     * @param v
     */
    public void s50WriteBlock(View v) {
//        DeviceStateChangeUtils.getInstence(sPort).onDeviceStateChange(SendByteData.WRITE_BLOCK_M1);
    }

    /**
     * 钱包初始化
     *
     * @param v
     */
    public void s50InitWallet(View v) {
//        DeviceStateChangeUtils.getInstence(sPort).onDeviceStateChange(SendByteData.WALLET_INITIALIZATION_M1);
    }

    /**
     * 读钱包
     *
     * @param v
     */
    public void s50ReadWallet(View v) {
//        DeviceStateChangeUtils.getInstence(sPort).onDeviceStateChange(SendByteData.PURSE_READ_M1);
    }

    /**
     * 钱包增值
     *
     * @param v
     */
    public void s50AddedWallet(View v) {
//        DeviceStateChangeUtils.getInstence(sPort).onDeviceStateChange(SendByteData.WALLET_RECHARGE_M1);
    }

    /**
     * 钱包减值
     *
     * @param v
     */
    public void s50ImpairmentWallet(View v) {
//        DeviceStateChangeUtils.getInstence(sPort).onDeviceStateChange(SendByteData.PURSE_DECREMENT_M1);
    }

    /**
     * 修改控制字
     *
     * @param v
     */
    public void s50ModifyControlWord(View v) {

    }

    /**
     * 修改密钥
     *
     * @param v
     */
    public void s50ModifyPrivateKey(View v) {
//        DeviceStateChangeUtils.getInstence(sPort).onDeviceStateChange(SendByteData.COMPOSITE_DETECTING_CARD_14443A);
    }

    static void show(Context context, UsbSerialPort port) {
        sPort = port;
        final Intent intent = new Intent(context, S50CardOperateActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
        context.startActivity(intent);
    }
}
