package pom.poly.com.tabatatimer.BrodcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import pom.poly.com.tabatatimer.R;
import pom.poly.com.tabatatimer.Utility.Utility;

public class BootedReceiver extends BroadcastReceiver {
    public BootedReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Log.d("NotificationReceiver", "booted receive");

        SharedPreferences defaultpreference = PreferenceManager.getDefaultSharedPreferences(context);
        boolean switchOnOff = defaultpreference.getBoolean(context.getString(R.string.preference_nof_key), false);
        if(switchOnOff){
//                    on
            String timeString = defaultpreference.getString(context.getString(R.string.preference_timePick_key), context.getString(R.string.preference_timePick_defaultvalue));
            Utility.setTheNotification(context,timeString);

        }
    }
}
