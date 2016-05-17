package com.rfid.app;


import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;

public class SerialPortSettingsActivity extends AppCompatPreferenceActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.serial_port_preferences);
        final ListPreference baudrates = (ListPreference) findPreference("BAUD_RATE");
        baudrates.setDefaultValue("9600");
        baudrates.setSummary(baudrates.getValue());
        baudrates.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preference.setSummary((String) newValue);
                return true;
            }
        });
        final ListPreference checkDigits = (ListPreference) findPreference("CHECK_DIGIT");
        checkDigits.setDefaultValue("无检验");
        checkDigits.setSummary(checkDigits.getValue());
        checkDigits.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preference.setSummary((String) newValue);
                return true;
            }
        });
        final ListPreference dataBits = (ListPreference) findPreference("DATA_BITS");
        dataBits.setDefaultValue("8");
        dataBits.setSummary(dataBits.getValue());
        dataBits.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preference.setSummary((String) newValue);
                return true;
            }
        });

        final ListPreference stopBits = (ListPreference) findPreference("STOP_BITS");
        stopBits.setDefaultValue("1");
        stopBits.setSummary(stopBits.getValue());
        stopBits.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preference.setSummary((String) newValue);
                return true;
            }
        });
    }

}
