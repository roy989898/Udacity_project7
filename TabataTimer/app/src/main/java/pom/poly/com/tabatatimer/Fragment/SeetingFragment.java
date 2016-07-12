package pom.poly.com.tabatatimer.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import pom.poly.com.tabatatimer.R;
import pom.poly.com.tabatatimer.Utility.Utility;


public class SeetingFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        Preference namePreference = findPreference(getString(R.string.preference_name_key));
        Preference notificationtimePreference = findPreference(getString(R.string.preference_timePick_key));
        Preference notificationOnOffPreference = findPreference(getString(R.string.preference_nof_key));

        bindPreferenceChangeListener(namePreference);
        bindPreferenceChangeListener(notificationtimePreference);
        bindPreferenceChangeListener(notificationOnOffPreference);


    }

    private void bindPreferenceChangeListener(Preference preference) {
        preference.setOnPreferenceChangeListener(this);

        //trigger the onPreferenceChange
        if (preference.getKey().equals("pkeyNOF")) {
            onPreferenceChange(preference, PreferenceManager.getDefaultSharedPreferences(preference.getContext()).getBoolean(preference.getKey(), false));
        } else {
            onPreferenceChange(preference, PreferenceManager.getDefaultSharedPreferences(preference.getContext()).getString(preference.getKey(), ""));
        }

    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {

        String stringValue = null;
        if (!preference.getKey().equals("pkeyNOF")) {// switch no need to show the value in summary
            stringValue = newValue.toString();
            preference.setSummary(stringValue);
        }
        if (preference instanceof EditTextPreference) {
            //upload the new user name
            DatabaseReference baseref = FirebaseDatabase.getInstance().getReference();
            String uid = Utility.getUid();
            DatabaseReference userRef = baseref.child("Users").child(uid);
            HashMap<String, Object> map = new HashMap<>();
            map.put("userName", stringValue);
            userRef.updateChildren(map);
        }

        String key = preference.getKey();
        switch (key) {
            case "pkeyNOF":
                //the setting of on off change
                Boolean b= (Boolean) newValue;
                if(b){
//                    on
                    Log.d("convertStringtoCalcender", b+"");
//                    get the time setting
                    SharedPreferences defaultpreference = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    String timeString = defaultpreference.getString(getActivity().getString(R.string.preference_timePick_key), getActivity().getString(R.string.preference_timePick_defaultvalue));
                    Utility.setTheNotification(getActivity(),timeString);
                }else{
//                    off
                    Log.d("convertStringtoCalcender", b+"");
                    Utility.disableTheTheNotification(getActivity());
                }

                break;
            case "pleyTimPick":
                Log.d("convertStringtoCalcender", "in pleyTimPick ");
                //the setting of time cahnge
            {
                SharedPreferences defaultpreference = PreferenceManager.getDefaultSharedPreferences(getActivity());
                boolean switchOnOff = defaultpreference.getBoolean(getActivity().getString(R.string.preference_nof_key), false);
                if(switchOnOff){
//                    on
                    String timeString= (String) newValue;
                    Utility.setTheNotification(getActivity(),timeString);

                }
            }


                break;


        }
        return true;
    }


}
