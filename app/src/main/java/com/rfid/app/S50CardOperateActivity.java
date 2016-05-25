package com.rfid.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import app.terminal.com.serialport.driver.UsbSerialPort;
import app.terminal.com.serialport.inter.BroadcastIntface;
import app.terminal.com.serialport.util.CardData;
import app.terminal.com.serialport.util.CardType;
import app.terminal.com.serialport.util.CreateControl;
import app.terminal.com.serialport.util.FindAddrType;
import app.terminal.com.serialport.util.HexDump;
import app.terminal.com.serialport.util.KeyType;
import app.terminal.com.serialport.util.ModifyKey;

public class S50CardOperateActivity extends AppCompatActivity {
    private static final int MODIFYCTRLCHECKA = 26;
    private static final int MODIFYCTRLCHECKB = 27;

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

    private byte sectorAddr = 0;
    private byte blockAddr = 0;
    private int findAddrType = FindAddrType.ABSOLUTE_ADDR;
    private int keyType = KeyType.KEY_A;
    private byte selectSector = 0;
    private ModifyKey modifyControlModifyKey;
    private int count = 0;
    private BroadcastReceiver checkKeyBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (action.equals(BroadcastIntface.CHECKCTRLKEY_BROADCASTRECEIVER)) {
                if (count == 0) {
                    BaseApp.instance().controlLinksilliconCardIntface.checkCtrlKey(S50CardOperateActivity.this, false, modifyControlModifyKey.getSector(), modifyControlModifyKey.getbOldKey(), MODIFYCTRLCHECKB);
                    count++;
                } else {
                    BaseApp.instance().controlLinksilliconCardIntface.readCtrlWord(S50CardOperateActivity.this, modifyControlModifyKey.getSector(), modifyControlModifyKey.getaOldKey(), false);
                    count = 0;
                }
            }
        }
    };
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(BroadcastIntface.GETCONTROL_BROADCASTRECEIVER)) {

                byte[] respondents = intent.getByteArrayExtra("RESPONSEDATA");
                byte[] sendData = intent.getByteArrayExtra("SENDDATA");
                if (!intent.getBooleanExtra("ISREADCTRLWORD", false)) {
                    byte[] controlWord = HexDump.hexStringToByteArray(CreateControl.getInstance().getNewctrl());
                    BaseApp.instance().controlLinksilliconCardIntface.modifyControl(S50CardOperateActivity.this, modifyControlModifyKey, controlWord, respondents);
                }
                Intent mIntent = new Intent(BroadcastIntface.GETREADERID_BROADCASTRECEIVER);
                mIntent.putExtra("SENDDATA", sendData);
                mIntent.putExtra("RESPONSEDATA", respondents);
                sendBroadcast(mIntent);

                Intent intent1 = new Intent(BroadcastIntface.GETCURRENTCTRLKEY_BROADCASTRECEIVER);
                intent1.putExtra("SENDDATA", sendData);
                intent1.putExtra("RESPONSEDATA", respondents);
                sendBroadcast(intent1);


            }
        }
    };


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
        registerBoradcastReceiver();
        findViews();
        initData();

    }

    private void registerBoradcastReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(BroadcastIntface.GETCONTROL_BROADCASTRECEIVER);
        //注册广播
        registerReceiver(broadcastReceiver, myIntentFilter);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BroadcastIntface.CHECKCTRLKEY_BROADCASTRECEIVER);
        //注册广播
        registerReceiver(checkKeyBroadcastReceiver, intentFilter);

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
                if (aRadioBtn.getId() == checkedId) {
                    keyType = KeyType.KEY_A;
                } else {
                    keyType = KeyType.KEY_B;
                }
            }
        });
        findAddrWaySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    findAddrType = FindAddrType.ABSOLUTE_ADDR;
                    sectorAddressSpinner.setEnabled(true);
                } else if (position == 1) {

                    findAddrType = FindAddrType.RELATIVE_ADDR;
                    sectorAddressSpinner.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sectorAddressSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sectorAddr = (byte) position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        blockAddressSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                blockAddr = (byte) position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        selectSectorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectSector = (byte) position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


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

    /**
     * 密钥验证
     *
     * @param v
     */
    public void s50KeyAuthentiation(View v) {
        byte[] key = HexDump.hexStringToByteArray(privateKeyEt.getText().toString().replaceAll("\\s*", ""));
        CardData cardData = new CardData(CardType.S50, findAddrType, sectorAddr, blockAddr, keyType, key);
        BaseApp.instance().controlLinksilliconCardIntface.checkKey(this, cardData);
    }

    /**
     * 复合读块
     *
     * @param v
     */
    public void s50CompositeBlockRead(View v) {
        byte[] key = HexDump.hexStringToByteArray(privateKeyEt.getText().toString().replaceAll("\\s*", ""));
        CardData cardData = new CardData(CardType.S50, findAddrType, sectorAddr, blockAddr, keyType, key);
        BaseApp.instance().controlLinksilliconCardIntface.composeRead(this, cardData);
    }

    /**
     * 复合写块
     *
     * @param v
     */
    public void s50CompositeBlockWrite(View v) {
        byte[] key = HexDump.hexStringToByteArray(privateKeyEt.getText().toString().replaceAll("\\s*", ""));
        byte[] writeData = HexDump.hexStringToByteArray(blockDataEt.getText().toString().replaceAll("\\s*", ""));
        CardData cardData = new CardData(writeData, CardType.S50, findAddrType, sectorAddr, blockAddr, keyType, key);
        BaseApp.instance().controlLinksilliconCardIntface.composeWrite(this, cardData);
    }

    /**
     * 读块
     *
     * @param v
     */
    public void s50ReadBlock(View v) {
        CardData cardData = new CardData(CardType.S50, findAddrType, sectorAddr, blockAddr);
        BaseApp.instance().controlLinksilliconCardIntface.readBlock(this, cardData);
    }


    /**
     * 写块
     *
     * @param v
     */
    public void s50WriteBlock(View v) {
        byte[] writeData = HexDump.hexStringToByteArray(blockDataEt.getText().toString().replaceAll("\\s*", ""));
        CardData cardData = new CardData(writeData, CardType.S50, findAddrType, sectorAddr, blockAddr);
        BaseApp.instance().controlLinksilliconCardIntface.writeBlock(this, cardData);
    }

    /**
     * 钱包初始化
     *
     * @param v
     */
    public void s50InitWallet(View v) {
        byte[] writeData = HexDump.hexStringToByteArray(moneyNumEt.getText().toString().replaceAll("\\s*", ""));
        CardData cardData = new CardData(writeData, CardType.S50, findAddrType, sectorAddr, blockAddr);
        BaseApp.instance().controlLinksilliconCardIntface.walletInit(this, cardData);
    }

    /**
     * 读钱包
     *
     * @param v
     */
    public void s50ReadWallet(View v) {
        CardData cardData = new CardData(CardType.S50, findAddrType, sectorAddr, blockAddr);
        BaseApp.instance().controlLinksilliconCardIntface.readWallet(this, cardData);
    }

    /**
     * 钱包增值
     *
     * @param v
     */
    public void s50AddedWallet(View v) {
        byte[] writeData = HexDump.hexStringToByteArray(moneyNumEt.getText().toString().replaceAll("\\s*", ""));
        CardData cardData = new CardData(writeData, CardType.S50, findAddrType, sectorAddr, blockAddr);
        BaseApp.instance().controlLinksilliconCardIntface.walletAdd(this, cardData);
    }

    /**
     * 钱包减值
     *
     * @param v
     */
    public void s50ImpairmentWallet(View v) {
        byte[] writeData = HexDump.hexStringToByteArray(moneyNumEt.getText().toString().replaceAll("\\s*", ""));
        CardData cardData = new CardData(writeData, CardType.S50, findAddrType, sectorAddr, blockAddr);
        BaseApp.instance().controlLinksilliconCardIntface.walletDec(this, cardData);
    }

    /**
     * 修改控制字
     *
     * @param v
     */
    public void s50ModifyControlWord(View v) {
        byte[] aOldKey = HexDump.hexStringToByteArray(aOldKeyEt.getText().toString().replaceAll("\\s*", ""));
        byte[] bOldKey = HexDump.hexStringToByteArray(bOldKeyEt.getText().toString().replaceAll("\\s*", ""));
        byte[] aNewKey = HexDump.hexStringToByteArray(aNewKeyEt.getText().toString().replaceAll("\\s*", ""));
        byte[] bNewKey = HexDump.hexStringToByteArray(bNewKeyEt.getText().toString().replaceAll("\\s*", ""));
        modifyControlModifyKey = new ModifyKey(selectSector, aOldKey, bOldKey, aNewKey, bNewKey);
        if (CreateControl.getInstance().getNewctrl() == null || CreateControl.getInstance().getNewctrl().length() == 0) {
            Toast.makeText(this, "请先详细设置访问条件并生成控制字！", Toast.LENGTH_SHORT).show();
            ModifyControlActivity.show(this, modifyControlModifyKey);
            return;
        } else {
            BaseApp.instance().controlLinksilliconCardIntface.checkCtrlKey(this, true, modifyControlModifyKey.getSector(), modifyControlModifyKey.getaOldKey(), MODIFYCTRLCHECKA);

        }

    }

    /**
     * 修改密钥
     *
     * @param v
     */
    public void s50ModifyPrivateKey(View v) {
        byte[] aOldKey = HexDump.hexStringToByteArray(aOldKeyEt.getText().toString().replaceAll("\\s*", ""));
        byte[] bOldKey = HexDump.hexStringToByteArray(bOldKeyEt.getText().toString().replaceAll("\\s*", ""));
        byte[] aNewKey = HexDump.hexStringToByteArray(aNewKeyEt.getText().toString().replaceAll("\\s*", ""));
        byte[] bNewKey = HexDump.hexStringToByteArray(bNewKeyEt.getText().toString().replaceAll("\\s*", ""));
        ModifyKey modifyKey = new ModifyKey(selectSector, aOldKey, bOldKey, aNewKey, bNewKey);
//        BaseApp.instance().controlLinksilliconCardIntface.readCtrlWord(S50CardOperateActivity.this, modifyControlModifyKey.getSector(), modifyControlModifyKey.getaOldKey(), false);
//        SystemClock.sleep(200);
        BaseApp.instance().controlLinksilliconCardIntface.modifyKey(this, selectSector, 0, aNewKey, aOldKey, bOldKey);
        SystemClock.sleep(200);
        BaseApp.instance().controlLinksilliconCardIntface.modifyKey(this, selectSector, 1, bNewKey, aOldKey, bOldKey);
    }

    static void show(Context context) {
        final Intent intent = new Intent(context, S50CardOperateActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
        context.startActivity(intent);
    }
}
