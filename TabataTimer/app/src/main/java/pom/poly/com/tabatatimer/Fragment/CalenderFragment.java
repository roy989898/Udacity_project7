package pom.poly.com.tabatatimer.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pom.poly.com.tabatatimer.Calender.CaldroidSampleCustomFragment;
import pom.poly.com.tabatatimer.R;
import pom.poly.com.tabatatimer.Utility.Observer;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalenderFragment extends Fragment implements Observer{


    @BindView(R.id.btRefresh)
    Button btRefresh;
    private CaldroidSampleCustomFragment caldroidFragment;

    public CalenderFragment() {
        // Required empty public constructor
    }

    /*private void setCustomResourceForDates() {
        Calendar cal = Calendar.getInstance();

        // Min date is last 7 days
        cal.add(Calendar.DATE, -7);
        Date blueDate = cal.getTime();

        // Max date is next 7 days
        cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 7);
        Date greenDate = cal.getTime();

        if (caldroidFragment != null) {
            ColorDrawable blue = new ColorDrawable(getResources().getColor(R.color.blue));
            ColorDrawable green = new ColorDrawable(Color.GREEN);
            caldroidFragment.setBackgroundDrawableForDate(blue, blueDate);
            caldroidFragment.setBackgroundDrawableForDate(green, greenDate);
            caldroidFragment.setTextColorForDate(R.color.white, blueDate);
            caldroidFragment.setTextColorForDate(R.color.white, greenDate);

        }
    }*/

    private void setcaldroidArgument(CaldroidFragment caldroidFragment) {
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
        args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);
        args.putInt(CaldroidFragment.THEME_RESOURCE, R.style.CaldroidDefaultYellow);

        // Uncomment this to customize startDayOfWeek
        // args.putInt(CaldroidFragment.START_DAY_OF_WEEK,
        // CaldroidFragment.TUESDAY); // Tuesday

        // Uncomment this line to use Caldroid in compact mode
        // args.putBoolean(CaldroidFragment.SQUARE_TEXT_VIEW_CELL, false);

        // Uncomment this line to use dark theme
//            args.putInt(CaldroidFragment.THEME_RESOURCE, com.caldroid.R.style.CaldroidDefaultDark);

        caldroidFragment.setArguments(args);
    }

    private void createAndReplayCalenderFragment(Bundle savedInstanceState) {
        caldroidFragment = new CaldroidSampleCustomFragment();

        if (savedInstanceState != null) {
            caldroidFragment.restoreStatesFromKey(savedInstanceState,
                    "CALDROID_SAVED_STATE");
        }
        // If activity is created from fresh
        else {
            setcaldroidArgument(caldroidFragment);
        }

//        setCustomResourceForDates();

        // Attach to the activity
        FragmentTransaction t = getActivity().getSupportFragmentManager().beginTransaction();
        t.replace(R.id.clalender1, caldroidFragment);
        t.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_calender, container, false);

//        final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");

        // Setup caldroid fragment
        // **** If you want normal CaldroidFragment, use below line ****
        createAndReplayCalenderFragment(savedInstanceState);

        // Setup listener
        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {
               /* Toast.makeText(getContext(), formatter.format(date),
                        Toast.LENGTH_SHORT).show();*/
            }

            @Override
            public void onChangeMonth(int month, int year) {
                String text = "month: " + month + " year: " + year;
               /* Toast.makeText(getContext(), text,
                        Toast.LENGTH_SHORT).show();*/
            }

            @Override
            public void onLongClickDate(Date date, View view) {
               /* Toast.makeText(getContext(),
                        "Long click " + formatter.format(date),
                        Toast.LENGTH_SHORT).show();*/
            }

            @Override
            public void onCaldroidViewCreated() {
                if (caldroidFragment.getLeftArrowButton() != null) {
                  /*  Toast.makeText(getContext(),
                            "Caldroid view is created", Toast.LENGTH_SHORT)
                            .show();*/
                }
            }

        };

        // Setup Caldroid
        caldroidFragment.setCaldroidListener(listener);

        ButterKnife.bind(this, layout);
        return layout;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);

        if (caldroidFragment != null) {
            caldroidFragment.saveStatesToKey(outState, "CALDROID_SAVED_STATE");
        }


    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @OnClick(R.id.btRefresh)
    public void onClick() {

        createAndReplayCalenderFragment(null);
    }

    @Override
    public void update() {
        createAndReplayCalenderFragment(null);
    }
}
