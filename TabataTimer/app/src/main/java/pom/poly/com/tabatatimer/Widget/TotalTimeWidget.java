package pom.poly.com.tabatatimer.Widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import java.util.Locale;

import pom.poly.com.tabatatimer.R;
import pom.poly.com.tabatatimer.Utility.Utility;

/**
 * Implementation of App Widget functionality.
 */
public class TotalTimeWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        //get the total time
        long totalTime = Utility.getTotalTabataTime();
        //make the milsecond to hour, monutes,second
        long[] timeArray = Utility.getDurationBreakdown(totalTime);
        String timeShow = String.format( Locale.US,"%02d : %02d : %02d", timeArray[0], timeArray[1], timeArray[2]);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.total_time_widget);
        views.setTextViewText(R.id.appwidget_text, timeShow);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

