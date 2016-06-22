package pom.poly.com.tabatatimer.Fragment;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import pom.poly.com.tabatatimer.R;


public class SeetingFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        Preference namePreference = findPreference(getString(R.string.preference_name_key));
        Preference notificationtimePreference = findPreference(getString(R.string.preference_timePick_key));

        bindPreferenceChangeListener(namePreference);
        bindPreferenceChangeListener(notificationtimePreference);


    }

    private void bindPreferenceChangeListener(Preference preference){
        preference.setOnPreferenceChangeListener(this);

        //trigger the onPreferenceChange
        onPreferenceChange(preference, PreferenceManager.getDefaultSharedPreferences(preference.getContext()).getString(preference.getKey(), ""));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String stringValue = newValue.toString();
        preference.setSummary(stringValue);
        return true;
    }
}
