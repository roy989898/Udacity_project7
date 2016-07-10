package pom.poly.com.tabatatimer.BrodcastReceiver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import java.util.Calendar;

import pom.poly.com.tabatatimer.MainActivity;
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
        Calendar nowCalcender = Calendar.getInstance();
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
        String timeString = preference.getString(context.getString(R.string.preference_timePick_key), context.getString(R.string.preference_timePick_defaultvalue));


        //on
        Calendar cal = Utility.convertStringtoCalcender(timeString);
        Log.d("NotificationReceiver", "receive before check the time");
        if (nowCalcender.get(Calendar.HOUR_OF_DAY) == cal.get(Calendar.HOUR_OF_DAY) && nowCalcender.get(Calendar.MINUTE) == cal.get(Calendar.MINUTE)) {
            Log.d("NotificationReceiver", "receive");
            //TODO
            createNotification(context);
        }


    }

    private void createNotification(Context context) {
        //TODO move the string to xml
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Tabata Timer")
                        .setContentText("It is time to do tabata")
                        .setAutoCancel(true)
                        .setSound(alarmSound);
// Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(context, MainActivity.class);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
// Adds the back stack for the Intent (but not the Intent itself)
//        stackBuilder.addParentStack(ResultActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(0, mBuilder.build());
    }
}
