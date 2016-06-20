package pom.poly.com.tabatatimer.Preference;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import pom.poly.com.tabatatimer.R;

/**
 * Created by User on 20/6/2016.
 */
public class TimePickerPreference extends DialogPreference {
    private Context mcontext;


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
            int hour = timePicker.getCurrentHour();
            int minutes = timePicker.getCurrentMinute();
            Toast.makeText(mcontext, hour + ":" + minutes, Toast.LENGTH_SHORT).show();

        }
    }
}
