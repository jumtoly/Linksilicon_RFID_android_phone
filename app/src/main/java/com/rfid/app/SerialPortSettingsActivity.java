package com.rfid.app;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.usb.UsbDeviceConnection;
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

public class SerialPortSettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private ListPreference baudratesPreference;
    private ListPreference checkDigitsPreference;
    private ListPreference dataBitsPreference;
    private ListPreference stopBitsPreference;
    private static UsbSerialPort sPort;
    private int baudRate = 9600;
    private int dataBits = 8;
    private int stopBits = UsbSerialPort.STOPBITS_1;
    private int parity = UsbSerialPort.PARITY_NONE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.serial_port_preferences);
        ListView listView = getListView();
        final Button submit = new Button(this);
        submit.setText("打开串口");
        submit.setTag(1);
        listView.addFooterView(submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sPort == null) {
                    Toast.makeText(SerialPortSettingsActivity.this, "No serial device.", Toast.LENGTH_SHORT).show();
                } else if (Integer.parseInt(submit.getTag().toString()) == 1) {
                    final UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);

                    UsbDeviceConnection connection = usbManager.openDevice(sPort.getDriver().getDevice());
                    if (connection == null) {
                        Toast.makeText(SerialPortSettingsActivity.this, "Opening device failed", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    try {
                        sPort.open(connection);
                        sPort.setParameters(baudRate, dataBits, stopBits, parity);
                    } catch (IOException e) {
                        Toast.makeText(SerialPortSettingsActivity.this, "Error opening device", Toast.LENGTH_SHORT).show();
                        submit.setText("打开串口");
                        submit.setTag(1);
                        try {
                            sPort.close();
                        } catch (IOException e2) {
                        }
                        sPort = null;
                        return;
                    }
                    Toast.makeText(SerialPortSettingsActivity.this, "Opening device success", Toast.LENGTH_SHORT).show();
                    submit.setText("关闭串口");
                    submit.setTag(2);
                } else if (Integer.parseInt(submit.getTag().toString()) == 2) {
                    try {
                        sPort.close();
                    } catch (IOException e) {
                        Toast.makeText(SerialPortSettingsActivity.this, "Error close device", Toast.LENGTH_SHORT).show();
                        submit.setText("关闭串口");
                        submit.setTag(2);
                    }
                    Toast.makeText(SerialPortSettingsActivity.this, "close device success", Toast.LENGTH_SHORT).show();
                    submit.setText("打开串口");
                    submit.setTag(1);
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
        });
        checkDigitsPreference = (ListPreference) findPreference("CHECK_DIGIT");
        checkDigitsPreference.setDefaultValue("无检验");
        checkDigitsPreference.setSummary(checkDigitsPreference.getValue());
        checkDigitsPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preference.setSummary((String) newValue);
                return true;
            }
        });
        dataBitsPreference = (ListPreference) findPreference("DATA_BITS");
        dataBitsPreference.setDefaultValue("8");
        dataBitsPreference.setSummary(dataBitsPreference.getValue());
        dataBitsPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preference.setSummary((String) newValue);
                return true;
            }
        });

        stopBitsPreference = (ListPreference) findPreference("STOP_BITS");
        stopBitsPreference.setDefaultValue("1");
        stopBitsPreference.setSummary(stopBitsPreference.getValue());
        stopBitsPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preference.setSummary((String) newValue);
                return true;
            }
        });
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
            stopBits = Integer.parseInt(stopBitsPreference.getValue());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    static void show(Context context, UsbSerialPort port) {
        sPort = port;
        final Intent intent = new Intent(context, SerialPortSettingsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
        context.startActivity(intent);
    }
}
