package com.rfid.app;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;

import app.terminal.com.serialport.driver.UsbSerialPort;
import app.terminal.com.serialport.inter.ControlLinksilliconCardIntface;
import app.terminal.com.serialport.util.SerialportControl;

public class SerialPortSettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    private final String TAG = SerialPortSettingsActivity.class.getSimpleName();
    private boolean isOpenTag = true;
    private ListPreference baudratesPreference;
    private ListPreference checkDigitsPreference;
    private ListPreference dataBitsPreference;
    private ListPreference stopBitsPreference;
    private static UsbSerialPort sPort;
    private int baudRate = 9600;
    private int dataBits = 8;
    private int stopBits = UsbSerialPort.STOPBITS_1;
    private int parity = UsbSerialPort.PARITY_NONE;
    private UsbManager mUsbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.serial_port_preferences);
        ListView listView = getListView();
        final Button submit = new Button(this);
        mUsbManager = BaseApp.instance().getUsbManager();
        submit.setText(BaseApp.instance().controlLinksilliconCardIntface.isReaderOpen() ? "关闭串口" : "打开串口");
        listView.addFooterView(submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!BaseApp.instance().controlLinksilliconCardIntface.isReaderOpen()) {
                    try {
                        if (BaseApp.instance().controlLinksilliconCardIntface.openReader(mUsbManager, baudRate, dataBits, stopBits, parity)) {
                            submit.setText("关闭串口");
                            Toast.makeText(SerialPortSettingsActivity.this, "Success opening device", Toast.LENGTH_SHORT).show();
                        } else {
                            submit.setText("打开串口");
                            Toast.makeText(SerialPortSettingsActivity.this, "Failed opening device", Toast.LENGTH_SHORT).show();
                        }

                    } catch (IOException e) {
                        if (isOpenTag) {
                            Log.d(TAG, "Error opening device:" + e.toString());
                        }
                        Toast.makeText(SerialPortSettingsActivity.this, "Error opening device", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    try {
                        BaseApp.instance().controlLinksilliconCardIntface.closeReader(mUsbManager);
                        submit.setText("打开串口");
                        Toast.makeText(SerialPortSettingsActivity.this, "Success closing device", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        if (isOpenTag) {
                            Log.d(TAG, "Error closing device:" + e.toString());
                        }
                        Toast.makeText(SerialPortSettingsActivity.this, "Error closing device", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);
        baudratesPreference = (ListPreference) findPreference("BAUD_RATE");
        baudratesPreference.setDefaultValue("9600");
        baudratesPreference.setSummary(baudratesPreference.getValue());
        baudratesPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                                                              public boolean onPreferenceChange(Preference preference, Object newValue) {
                                                                  preference.setSummary((String) newValue);
                                                                  return true;
                                                              }
                                                          }

        );
        checkDigitsPreference = (ListPreference)

                findPreference("CHECK_DIGIT");

        checkDigitsPreference.setDefaultValue("无检验");
        checkDigitsPreference.setSummary(checkDigitsPreference.getValue());
        checkDigitsPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener()

                                                            {
                                                                public boolean onPreferenceChange(Preference preference, Object newValue) {
                                                                    preference.setSummary((String) newValue);
                                                                    return true;
                                                                }
                                                            }

        );
        dataBitsPreference = (ListPreference)

                findPreference("DATA_BITS");

        dataBitsPreference.setDefaultValue("8");
        dataBitsPreference.setSummary(dataBitsPreference.getValue());
        dataBitsPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener()

                                                         {
                                                             public boolean onPreferenceChange(Preference preference, Object newValue) {
                                                                 preference.setSummary((String) newValue);
                                                                 return true;
                                                             }
                                                         }

        );

        stopBitsPreference = (ListPreference)

                findPreference("STOP_BITS");

        stopBitsPreference.setDefaultValue("1");
        stopBitsPreference.setSummary(stopBitsPreference.getValue());
        stopBitsPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener()

                                                         {
                                                             public boolean onPreferenceChange(Preference preference, Object newValue) {
                                                                 preference.setSummary((String) newValue);
                                                                 return true;
                                                             }
                                                         }

        );
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if ("BAUD_RATE".equals(key)) {
            baudRate = Integer.parseInt(baudratesPreference.getValue());
        }
        if ("CHECK_DIGIT".equals(key)) {
            if ("无检验".equals(checkDigitsPreference.getValue())) {
                parity = UsbSerialPort.PARITY_NONE;
            } else if ("奇检验".equals(checkDigitsPreference.getValue())) {
                parity = UsbSerialPort.PARITY_ODD;
            } else if ("偶检验".equals(checkDigitsPreference.getValue())) {
                parity = UsbSerialPort.PARITY_EVEN;
            }
        }
        if ("DATA_BITS".equals(key)) {
            dataBits = Integer.parseInt(dataBitsPreference.getValue());
        }
        if ("STOP_BITS".equals(key)) {
            if ("1".equals(stopBitsPreference.getValue())) {
                stopBits = UsbSerialPort.STOPBITS_1;
            } else if ("1.5".equals(stopBitsPreference.getValue())) {
                stopBits = UsbSerialPort.STOPBITS_1_5;
            } else {
                stopBits = UsbSerialPort.STOPBITS_2;
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
       /* try {
            controlLinksilliconCardIntface.initSerialPort(mUsbManager);
        } catch (IOException e) {
            Toast.makeText(SerialPortSettingsActivity.this, "failed init serial port.", Toast.LENGTH_SHORT).show();
        }*/
    }

    static void show(Context context) {
        final Intent intent = new Intent(context, SerialPortSettingsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
        context.startActivity(intent);
    }
}
