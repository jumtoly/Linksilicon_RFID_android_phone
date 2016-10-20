package com.rfid.app;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import app.terminal.com.serialport.util.CardData;
import app.terminal.com.serialport.util.CardType;
import app.terminal.com.serialport.util.CreateControl;
import app.terminal.com.serialport.util.FindAddrType;
import app.terminal.com.serialport.util.HexDump;
import app.terminal.com.serialport.util.KeyType;
import app.terminal.com.serialport.util.ModifyKey;

public class S70CardOperateActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s70_card_operate);
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
        for (int i = 0; i < 40; i++) {
            sectorAddressSpinnerList.add(i);

        }
        for (int i = 0; i < 64; i++) {
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
        privateKeyEt = (EditText) findViewById(R.id.s70_private_key);
        blockDataEt = (EditText) findViewById(R.id.s70_block_data);
        moneyNumEt = (EditText) findViewById(R.id.s70_money_num);
        aOldKeyEt = (EditText) findViewById(R.id.s70_a_old_key);
        bOldKeyEt = (EditText) findViewById(R.id.s70_b_old_key);
        aNewKeyEt = (EditText) findViewById(R.id.s70_a_new_key);
        bNewKeyEt = (EditText) findViewById(R.id.s70_b_new_key);
        radioGroup = (RadioGroup) findViewById(R.id.s70_key_type_radiogroup);
        aRadioBtn = (RadioButton) findViewById(R.id.s70_key_type_radiobtn_A);
        bRadioBtn = (RadioButton) findViewById(R.id.s70_key_type_radiobtn_B);
        findAddrWaySpinner = (Spinner) findViewById(R.id.s70_find_addr_way_spinner);
        sectorAddressSpinner = (Spinner) findViewById(R.id.s70_sector_address_spinner);
        blockAddressSpinner = (Spinner) findViewById(R.id.s70_block_address_spinner);
        selectSectorSpinner = (Spinner) findViewById(R.id.s70_select_sector_spinner);

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
                    sectorAddressSpinner.setEnabled(false);
                } else if (position == 1) {

                    findAddrType = FindAddrType.RELATIVE_ADDR;
                    sectorAddressSpinner.setEnabled(true);
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

    /**
     * 密钥验证
     *
     * @param v
     */
    public void s70KeyAuthentiation(View v) {
        byte[] key = HexDump.hexStringToByteArray(privateKeyEt.getText().toString().replaceAll("\\s*", ""));
        CardData cardData = new CardData(CardType.S70, findAddrType, sectorAddr, blockAddr, keyType, key);
        BaseApp.instance().controlLinksilliconCardIntface.checkKey(this, cardData);
    }

    /**
     * 复合读块
     *
     * @param v
     */
    public void s70CompositeBlockRead(View v) {
        byte[] key = HexDump.hexStringToByteArray(privateKeyEt.getText().toString().replaceAll("\\s*", ""));
        CardData cardData = new CardData(CardType.S70, findAddrType, sectorAddr, blockAddr, keyType, key);
        BaseApp.instance().controlLinksilliconCardIntface.composeRead(this, cardData);
    }

    /**
     * 复合写块
     *
     * @param v
     */
    public void s70CompositeBlockWrite(View v) {
        byte[] key = HexDump.hexStringToByteArray(privateKeyEt.getText().toString().replaceAll("\\s*", ""));
        byte[] writeData = HexDump.hexStringToByteArray(blockDataEt.getText().toString().replaceAll("\\s*", ""));
        CardData cardData = new CardData(writeData, CardType.S70, findAddrType, sectorAddr, blockAddr, keyType, key);
        BaseApp.instance().controlLinksilliconCardIntface.composeWrite(this, cardData);
    }

    /**
     * 读块
     *
     * @param v
     */
    public void s70ReadBlock(View v) {
        CardData cardData = new CardData(CardType.S70, findAddrType, sectorAddr, blockAddr);
        BaseApp.instance().controlLinksilliconCardIntface.readBlock(this, cardData);
    }


    /**
     * 写块
     *
     * @param v
     */
    public void s70WriteBlock(View v) {
        byte[] writeData = HexDump.hexStringToByteArray(blockDataEt.getText().toString().replaceAll("\\s*", ""));
        CardData cardData = new CardData(writeData, CardType.S70, findAddrType, sectorAddr, blockAddr);
        BaseApp.instance().controlLinksilliconCardIntface.writeBlock(this, cardData);
    }

    /**
     * 钱包初始化
     *
     * @param v
     */
    public void s70InitWallet(View v) {
        byte[] writeData = HexDump.hexStringToByteArray(moneyNumEt.getText().toString().replaceAll("\\s*", ""));
        if (findAddrWaySpinner.getSelectedItemPosition() == 0) {
            CardData cardData = new CardData(writeData, CardType.S70, FindAddrType.ABSOLUTE_ADDR, (byte) sectorAddressSpinner.getSelectedItem(), (byte) blockAddressSpinner.getSelectedItem());
            BaseApp.instance().controlLinksilliconCardIntface.walletInit(this, cardData);
        } else if (findAddrWaySpinner.getSelectedItemPosition() == 0) {
            CardData cardData = new CardData(writeData, CardType.S70, FindAddrType.RELATIVE_ADDR, (byte) sectorAddressSpinner.getSelectedItem(), (byte) blockAddressSpinner.getSelectedItem());
            BaseApp.instance().controlLinksilliconCardIntface.walletInit(this, cardData);
        }
    }

    /**
     * 读钱包
     *
     * @param v
     */
    public void s70ReadWallet(View v) {
        CardData cardData = new CardData(CardType.S70, findAddrType, sectorAddr, blockAddr);
        BaseApp.instance().controlLinksilliconCardIntface.readWallet(this, cardData);
    }

    /**
     * 钱包增值
     *
     * @param v
     */
    public void s70AddedWallet(View v) {
        byte[] writeData = HexDump.hexStringToByteArray(moneyNumEt.getText().toString().replaceAll("\\s*", ""));
        CardData cardData = new CardData(writeData, CardType.S70, findAddrType, sectorAddr, blockAddr);
        BaseApp.instance().controlLinksilliconCardIntface.walletAdd(this, cardData);
    }

    /**
     * 钱包减值
     *
     * @param v
     */
    public void s70ImpairmentWallet(View v) {
        byte[] writeData = HexDump.hexStringToByteArray(moneyNumEt.getText().toString().replaceAll("\\s*", ""));
        CardData cardData = new CardData(writeData, CardType.S70, findAddrType, sectorAddr, blockAddr);
        BaseApp.instance().controlLinksilliconCardIntface.walletDec(this, cardData);
    }

    /**
     * 修改控制字
     *
     * @param v
     */
    public void s70ModifyControlWord(View v) {
        byte[] aOldKey = HexDump.hexStringToByteArray(aOldKeyEt.getText().toString().replaceAll("\\s*", ""));
        byte[] bOldKey = HexDump.hexStringToByteArray(bOldKeyEt.getText().toString().replaceAll("\\s*", ""));
        byte[] aNewKey = HexDump.hexStringToByteArray(aNewKeyEt.getText().toString().replaceAll("\\s*", ""));
        byte[] bNewKey = HexDump.hexStringToByteArray(bNewKeyEt.getText().toString().replaceAll("\\s*", ""));
        ModifyKey modifyControlModifyKey = new ModifyKey(selectSector, aOldKey, bOldKey, aNewKey, bNewKey);
        if (CreateControl.getInstance().getNewctrl() == null || CreateControl.getInstance().getNewctrl().length() == 0) {
            Toast.makeText(this, "请先详细设置访问条件并生成控制字！", Toast.LENGTH_SHORT).show();
            ModifyControlActivity.show(this, modifyControlModifyKey);
            return;
        } else {
            if (BaseApp.instance().controlLinksilliconCardIntface.checkCtrlKey(this, true, modifyControlModifyKey.getSector(), modifyControlModifyKey.getaOldKey(), 26)) {//密钥A校验
                if (BaseApp.instance().controlLinksilliconCardIntface.checkCtrlKey(this, false, modifyControlModifyKey.getSector(), modifyControlModifyKey.getbOldKey(), 27)) {//密钥B校验
                    byte[] controlWord = HexDump.hexStringToByteArray(CreateControl.getInstance().getNewctrl());
                    BaseApp.instance().controlLinksilliconCardIntface.modifyControl(this, modifyControlModifyKey, controlWord, HexDump.hexStringToByteArray(CreateControl.getInstance().getOldctrl()));
                } else {
                    Toast.makeText(this, "密钥B验证失败", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(this, "密钥A验证失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 修改密钥
     *
     * @param v
     */
    public void s70ModifyPrivateKey(View v) {
        byte[] aOldKey = HexDump.hexStringToByteArray(aOldKeyEt.getText().toString().replaceAll("\\s*", ""));
        byte[] bOldKey = HexDump.hexStringToByteArray(bOldKeyEt.getText().toString().replaceAll("\\s*", ""));
        byte[] aNewKey = HexDump.hexStringToByteArray(aNewKeyEt.getText().toString().replaceAll("\\s*", ""));
        byte[] bNewKey = HexDump.hexStringToByteArray(bNewKeyEt.getText().toString().replaceAll("\\s*", ""));
        ModifyKey modifyKey = new ModifyKey(selectSector, aOldKey, bOldKey, aNewKey, bNewKey);
        if (BaseApp.instance().controlLinksilliconCardIntface.readCtrlWord(this, modifyKey.getSector(), modifyKey.getaOldKey())) {
            if (BaseApp.instance().controlLinksilliconCardIntface.modifyKey(this, selectSector, 0, aNewKey, aOldKey, bOldKey)) {
                if (!BaseApp.instance().controlLinksilliconCardIntface.modifyKey(this, selectSector, 1, bNewKey, aOldKey, bOldKey)) {
                    Toast.makeText(this, "密钥B修改失败", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "密钥A修改失败", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "读取控件字失败", Toast.LENGTH_SHORT).show();
        }
    }

    static void show(Context context) {
        final Intent intent = new Intent(context, S70CardOperateActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
        context.startActivity(intent);
    }
}
