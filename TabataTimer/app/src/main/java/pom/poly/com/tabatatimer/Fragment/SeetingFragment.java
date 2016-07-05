package pom.poly.com.tabatatimer.Fragment;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

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

    private void bindPreferenceChangeListener(Preference preference) {
        preference.setOnPreferenceChangeListener(this);

        //trigger the onPreferenceChange
        onPreferenceChange(preference, PreferenceManager.getDefaultSharedPreferences(preference.getContext()).getString(preference.getKey(), ""));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String stringValue = newValue.toString();
        preference.setSummary(stringValue);

        if (preference instanceof EditTextPreference) {
            //upload the new user name
            DatabaseReference baseref = FirebaseDatabase.getInstance().getReference();
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            DatabaseReference userRef = baseref.child("Users").child(uid);
            HashMap<String, Object> map = new HashMap<>();
            map.put("userName", stringValue);
            userRef.updateChildren(map);
        }
        return true;
    }
}
