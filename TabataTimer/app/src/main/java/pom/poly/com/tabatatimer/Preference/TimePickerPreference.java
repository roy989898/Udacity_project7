package pom.poly.com.tabatatimer.Preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;

import pom.poly.com.tabatatimer.R;

/**
 * Created by User on 20/6/2016.
 */
public class TimePickerPreference extends DialogPreference {
    private Context mcontext;
    private int hour = 0;
    private int minutes = 0;
    private String TAG = "TimePickerPreference";


    private TimePicker timePicker;

    public TimePickerPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTimePickerDialogUI();
        mcontext = context;
    }

    public TimePickerPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTimePickerDialogUI();
        mcontext = context;
    }

    @Override
    protected void onBindDialogView(View view) {
        timePicker = (TimePicker) view.findViewById(R.id.preferencetimePicker);
        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(minutes);
        super.onBindDialogView(view);
    }

    private void setTimePickerDialogUI() {
        setDialogLayoutResource(R.layout.timepicker_dialog);
        setPositiveButtonText(android.R.string.ok);
        setNegativeButtonText(android.R.string.cancel);
        setDialogIcon(null);

    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
        if (true) {
            hour = timePicker.getCurrentHour();
            minutes = timePicker.getCurrentMinute();
            String time = hour + ":" + minutes;
            //save the value
            persistString(time);

        }
        Log.d(TAG, "in onDialogClosed");
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        if (restorePersistedValue) {
            // Restore existing state

        } else {
            // Set default state from the XML attribute
            String mCurrentValue = (String) defaultValue;
            persistString(mCurrentValue);
        }
        String currentValue = this.getPersistedString(mcontext.getResources().getString(R.string.preference_timePick_defaultvalue));
        //set the lock to show
        hour = timeStringToInt(currentValue)[0];
        minutes = timeStringToInt(currentValue)[1];
        /*timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(minutes);*/
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return a.getString(index);
    }


    private String timeInttoString(int hour, int minutes) {
        String time = hour + ":" + minutes;
        return time;
    }

    private int[] timeStringToInt(String time) {
        String[] parts = time.split(":");
        int[] intArray = new int[2];
        intArray[0] = Integer.parseInt(parts[0]);
        intArray[1] = Integer.parseInt(parts[1]);
        return intArray;
    }

}
