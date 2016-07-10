package pom.poly.com.tabatatimer.BrodcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Calendar;

import pom.poly.com.tabatatimer.R;
import pom.poly.com.tabatatimer.Utility.Utility;

public class NotificationReceiver extends BroadcastReceiver {
    public NotificationReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
//        prevent the trigger at the wrong time
        Calendar nowCalcender=Calendar.getInstance();
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
        String timeString = preference.getString(context.getString(R.string.preference_timePick_key), context.getString(R.string.preference_timePick_defaultvalue));


        //on
        Calendar cal = Utility.convertStringtoCalcender(timeString);

        if(nowCalcender.get(Calendar.HOUR_OF_DAY)==cal.get(Calendar.HOUR_OF_DAY)&&nowCalcender.get(Calendar.MINUTE)==cal.get(Calendar.MINUTE)){
            Log.d("NotificationReceiver", "receive");
        }

    }
}
