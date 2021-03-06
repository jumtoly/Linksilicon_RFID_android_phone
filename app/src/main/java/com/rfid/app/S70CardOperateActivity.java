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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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
    private ModifyKey modifyControlModifyKey;

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
        byte[] key = HexDump.hexStringToByteArray(this, privateKeyEt.getText().toString().replaceAll("\\s*", ""));
        CardData cardData = new CardData(CardType.S70, findAddrType, sectorAddr, blockAddr, keyType, key);
        BaseApp.instance().controlLinksilliconCardIntface.checkKey(this, cardData);
    }

    /**
     * 复合读块
     *
     * @param v
     */
    public void s70CompositeBlockRead(View v) {
        byte[] key = HexDump.hexStringToByteArray(this, privateKeyEt.getText().toString().replaceAll("\\s*", ""));
        CardData cardData = new CardData(CardType.S70, findAddrType, sectorAddr, blockAddr, keyType, key);
        BaseApp.instance().controlLinksilliconCardIntface.composeRead(this, cardData);
    }

    /**
     * 复合写块
     *
     * @param v
     */
    public void s70CompositeBlockWrite(View v) {
        byte[] key = HexDump.hexStringToByteArray(this, privateKeyEt.getText().toString().replaceAll("\\s*", ""));
        byte[] writeData = HexDump.hexStringToByteArray(this, blockDataEt.getText().toString().replaceAll("\\s*", ""));
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
        byte[] writeData = HexDump.hexStringToByteArray(this, blockDataEt.getText().toString().replaceAll("\\s*", ""));
        CardData cardData = new CardData(writeData, CardType.S70, findAddrType, sectorAddr, blockAddr);
        BaseApp.instance().controlLinksilliconCardIntface.writeBlock(this, cardData);
    }

    /**
     * 钱包初始化
     *
     * @param v
     */
    public void s70InitWallet(View v) {
        if (moneyNumEt.getText() == null || moneyNumEt.getText().equals(" ")) {
            Toast.makeText(this, "请输入钱包初始化金额", Toast.LENGTH_SHORT).show();
            return;
        }
        byte writeData;
        try {
            writeData = HexDump.getBytes(Integer.parseInt(moneyNumEt.getText().toString().replaceAll("\\s*", "")));
        } catch (NumberFormatException e) {
            Toast.makeText(this, "请输入钱包初始化整数金额", Toast.LENGTH_SHORT).show();
            return;
        }


        if (findAddrWaySpinner.getSelectedItemPosition() == 0) {
            CardData cardData = new CardData(writeData, CardType.S70, FindAddrType.ABSOLUTE_ADDR, Byte.valueOf(sectorAddressSpinner.getSelectedItem().toString()), Byte.valueOf(blockAddressSpinner.getSelectedItem().toString()));
            BaseApp.instance().controlLinksilliconCardIntface.walletInit(this, cardData);
        } else {
            CardData cardData = new CardData(writeData, CardType.S70, FindAddrType.RELATIVE_ADDR, Byte.valueOf(sectorAddressSpinner.getSelectedItem().toString()), Byte.valueOf(blockAddressSpinner.getSelectedItem().toString()));
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
        moneyNumEt.setText(BaseApp.instance().controlLinksilliconCardIntface.readWallet(this, cardData));
    }

    /**
     * 钱包增值
     *
     * @param v
     */
    public void s70AddedWallet(View v) {
        if (moneyNumEt.getText().toString().trim() == null || moneyNumEt.getText().toString().trim().equals("")) {
            Toast.makeText(this, "请输入钱包初始化金额", Toast.LENGTH_SHORT).show();
            return;
        }
        byte writeData;
        try {
            writeData = HexDump.getBytes(Integer.parseInt(moneyNumEt.getText().toString().replaceAll("\\s*", "")));
        } catch (NumberFormatException e) {
            Toast.makeText(this, "请输入钱包初始化整数金额", Toast.LENGTH_SHORT).show();
            return;
        }

        CardData cardData = new CardData(writeData, CardType.S70, findAddrType, sectorAddr, blockAddr);
        BaseApp.instance().controlLinksilliconCardIntface.walletAdd(this, cardData);
    }

    /**
     * 钱包减值
     *
     * @param v
     */
    public void s70ImpairmentWallet(View v) {

        if (moneyNumEt.getText() == null || moneyNumEt.getText().equals(" ")) {
            Toast.makeText(this, "请输入钱包初始化金额", Toast.LENGTH_SHORT).show();
            return;
        }
        byte writeData;
        try {
            writeData = HexDump.getBytes(Integer.parseInt(moneyNumEt.getText().toString().replaceAll("\\s*", "")));
        } catch (NumberFormatException e) {
            Toast.makeText(this, "请输入钱包初始化整数金额", Toast.LENGTH_SHORT).show();
            return;
        }
        CardData cardData = new CardData(writeData, CardType.S70, findAddrType, sectorAddr, blockAddr);
        BaseApp.instance().controlLinksilliconCardIntface.walletDec(this, cardData);
    }

    /**
     * 修改控制字
     *
     * @param v
     */
    public void s70ModifyControlWord(View v) {
        byte[] aOldKey = HexDump.hexStringToByteArray(this, aOldKeyEt.getText().toString().replaceAll("\\s*", ""));
        byte[] bOldKey = HexDump.hexStringToByteArray(this, bOldKeyEt.getText().toString().replaceAll("\\s*", ""));
        byte[] aNewKey = HexDump.hexStringToByteArray(this, aNewKeyEt.getText().toString().replaceAll("\\s*", ""));
        byte[] bNewKey = HexDump.hexStringToByteArray(this, bNewKeyEt.getText().toString().replaceAll("\\s*", ""));
        if (aOldKey == null || bOldKey == null || aNewKey == null || bNewKey == null) {
            return;
        }
        modifyControlModifyKey = new ModifyKey(selectSector, KeyType.KEY_A, aOldKey, bOldKey, aNewKey, bNewKey);
        ModifyControlActivity.show(this, modifyControlModifyKey);
      /*  if (CreateControl.getInstance().getNewctrl() == null || CreateControl.getInstance().getNewctrl().length() == 0) {
            Toast.makeText(this, "请先详细设置访问条件并生成控制字！", Toast.LENGTH_SHORT).show();
            ModifyControlActivity.show(this, modifyControlModifyKey);
            return;
        } else {
            if (BaseApp.instance().controlLinksilliconCardIntface.checkCtrlKey(this, true, modifyControlModifyKey.getSector(), modifyControlModifyKey.getaOldKey(), MODIFYCTRLCHECKA)) {
                if (BaseApp.instance().controlLinksilliconCardIntface.checkCtrlKey(this, false, modifyControlModifyKey.getSector(), modifyControlModifyKey.getbOldKey(), MODIFYCTRLCHECKB)) {
                    byte[] controlWord = HexDump.hexStringToByteArray(this, CreateControl.getInstance().getNewctrl());
                    BaseApp.instance().controlLinksilliconCardIntface.modifyControl(S50CardOperateActivity.this, modifyControlModifyKey, controlWord, HexDump.hexStringToByteArray(this, CreateControl.getInstance().getOldctrl()));
                } else {
                    Toast.makeText(this, "密钥B验证失败", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(this, "密钥A验证失败", Toast.LENGTH_SHORT).show();
            }
        }*/
    }

    /**
     * 修改密钥
     *
     * @param v
     */
    public void s70ModifyPrivateKey(View v) {
        byte[] aOldKey = HexDump.hexStringToByteArray(this, aOldKeyEt.getText().toString().replaceAll("\\s*", ""));
        byte[] bOldKey = HexDump.hexStringToByteArray(this, bOldKeyEt.getText().toString().replaceAll("\\s*", ""));
        byte[] aNewKey = HexDump.hexStringToByteArray(this, aNewKeyEt.getText().toString().replaceAll("\\s*", ""));
        byte[] bNewKey = HexDump.hexStringToByteArray(this, bNewKeyEt.getText().toString().replaceAll("\\s*", ""));
        if (aOldKey == null || bOldKey == null || aNewKey == null || bNewKey == null) {
            return;
        }
        boolean isAOk = BaseApp.instance().controlLinksilliconCardIntface.modifyKey(this, selectSector, KeyType.KEY_A, aNewKey, aOldKey, bOldKey);
        if (!isAOk) {
            String strKey = HexDump.toHexString(aNewKey);
            aOldKeyEt.setText(strKey);
        }
        aOldKey = HexDump.hexStringToByteArray(this, aOldKeyEt.getText().toString().replaceAll("\\s*", ""));
        boolean isBOk = BaseApp.instance().controlLinksilliconCardIntface.modifyKey(this, selectSector, KeyType.KEY_B, bNewKey, aOldKey, bOldKey);
        if (!isBOk) {
            String strKey = HexDump.toHexString(bNewKey);
            aOldKeyEt.setText(strKey);
        }
    }

    static void show(Context context) {
        final Intent intent = new Intent(context, S70CardOperateActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
        context.startActivity(intent);
    }
}
