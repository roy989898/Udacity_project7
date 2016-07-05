package pom.poly.com.tabatatimer.Fragment;


import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import pom.poly.com.tabatatimer.Calender.CaldroidSampleCustomFragment;
import pom.poly.com.tabatatimer.ContentProvider.Eventinf;
import pom.poly.com.tabatatimer.R;
import pom.poly.com.tabatatimer.Utility.Observer;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalenderFragment extends Fragment implements Observer {

    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

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

        FragmentActivity activity = getActivity();

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
//        FragmentTransaction t = activity.getSupportFragmentManager().beginTransaction();

        FragmentTransaction t = getChildFragmentManager().beginTransaction();

          t.replace(R.id.clalender1, caldroidFragment);


        t.commit();

        // Setup listener
        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {

                final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                new showDetailINFTask().execute(formatter.format(date)); //to show the detail information dialog,if have detail information
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

        // Setup the click action
        caldroidFragment.setCaldroidListener(listener);
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
    public void onPause() {
        super.onPause();
        Eventinf.registerObserver(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void update() {
        createAndReplayCalenderFragment(null);
    }

    private class showDetailINFTask extends AsyncTask<String, Void, List<Eventinf>> {

        @Override
        protected List<Eventinf> doInBackground(String... params) {
            List<Eventinf> list;
            if (params.length > 0) {
                list = Eventinf.find(Eventinf.class, "date=?", params[0]);
            } else {
                return null;
            }

            if (!list.isEmpty()) {
                return list;
            } else {
                return null;
            }


        }

        @Override
        protected void onPostExecute(List<Eventinf> eventinfs) {
            //if eventinfs is null,that mean no event at this date
            if (eventinfs != null) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DetailDialogFragment ddf=DetailDialogFragment.newInstance(eventinfs);

                ddf.show(fm,"detailDialogFragment");

            }
            //TODO  show the  selected date detail
            super.onPostExecute(eventinfs);

        }
    }
}
