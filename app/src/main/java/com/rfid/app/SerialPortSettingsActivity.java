package com.rfid.app;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import app.terminal.com.serialport.util.StaticVar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import app.terminal.com.serialport.driver.UsbSerialDriver;
import app.terminal.com.serialport.driver.UsbSerialPort;
import app.terminal.com.serialport.driver.UsbSerialProber;

public class SerialPortSettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    private final String TAG = SerialPortSettingsActivity.class.getSimpleName();
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
        mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        submit.setText(StaticVar.getInstence().isSerialIsOpe() ? "关闭串口" : "打开串口");
        listView.addFooterView(submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sPort == null) {
                    Toast.makeText(SerialPortSettingsActivity.this, "No serial device.", Toast.LENGTH_SHORT).show();
                } else if (!StaticVar.getInstence().isSerialIsOpe()) {
                    final UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);

                    UsbDeviceConnection connection = usbManager.openDevice(sPort.getDriver().getDevice());
                    if (connection == null) {
                        Toast.makeText(SerialPortSettingsActivity.this, "Opening device failed", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    try {
                        sPort.open(connection);
                        sPort.setParameters(baudRate, dataBits, stopBits, parity);
                        StaticVar.getInstence().setSerialIsOpe(true);
                    } catch (IOException e) {
                        Toast.makeText(SerialPortSettingsActivity.this, "Error opening device", Toast.LENGTH_SHORT).show();
                        submit.setText("打开串口");
                        try {
                            sPort.close();
                        } catch (IOException e2) {
                        }
                        sPort = null;
                        return;
                    }
                    Toast.makeText(SerialPortSettingsActivity.this, "Opening device success", Toast.LENGTH_SHORT).show();
                    submit.setText("关闭串口");
                } else {
                    try {
                        sPort.close();
                        StaticVar.getInstence().setSerialIsOpe(false);
                    } catch (IOException e) {
                        Toast.makeText(SerialPortSettingsActivity.this, "Error close device", Toast.LENGTH_SHORT).show();
                        submit.setText("关闭串口");
                    }
                    Toast.makeText(SerialPortSettingsActivity.this, "close device success", Toast.LENGTH_SHORT).show();
                    submit.setText("打开串口");
                    StaticVar.getInstence().setSerialIsOpe(false);
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
        getSerialProber();

    }

    public void getSerialProber() {
        new AsyncTask<Void, Void, List<UsbSerialPort>>() {
            @Override
            protected List<UsbSerialPort> doInBackground(Void... params) {
                Log.d(TAG, "Refreshing device list ...");
                SystemClock.sleep(1000);

                final List<UsbSerialDriver> drivers =
                        UsbSerialProber.getDefaultProber().findAllDrivers(mUsbManager);
                Log.d(TAG, "drivers:" + drivers.size());
                final List<UsbSerialPort> result = new ArrayList<UsbSerialPort>();
                for (final UsbSerialDriver driver : drivers) {
                    final List<UsbSerialPort> ports = driver.getPorts();
                    Log.d(TAG, String.format("+ %s: %s port%s",
                            driver, Integer.valueOf(ports.size()), ports.size() == 1 ? "" : "s"));
                    result.addAll(ports);
                }

                return result;
            }

            @Override
            protected void onPostExecute(List<UsbSerialPort> result) {
                if (result != null && result.size() > 0) {
                    SerialPortEntity.getInstance().setSerialPort(result.get(0));
                    BaseApp baseApp = (BaseApp) getApplication();
                    baseApp.setUsbSerialPort(result.get(0));
                    sPort = result.get(0);
                }
                Log.d(TAG, "Done refreshing, " + result.size() + " entries found.");
            }

        }.execute((Void) null);
    }

    static void show(Context context) {
        final Intent intent = new Intent(context, SerialPortSettingsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
        context.startActivity(intent);
    }
}
