package pom.poly.com.tabatatimer.Fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import pom.poly.com.tabatatimer.R;


public class SeetingFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
