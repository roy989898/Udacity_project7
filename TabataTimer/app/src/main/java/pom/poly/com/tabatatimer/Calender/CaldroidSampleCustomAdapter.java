package pom.poly.com.tabatatimer.Calender;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidGridAdapter;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import hirondelle.date4j.DateTime;
import pom.poly.com.tabatatimer.ContentProvider.Eventinf;
import pom.poly.com.tabatatimer.R;

public class CaldroidSampleCustomAdapter extends CaldroidGridAdapter {


    private final String YMD_FORMAT = "YYYY-MM-DD";


    public CaldroidSampleCustomAdapter(Context context, int month, int year,
                                       Map<String, Object> caldroidData,
                                       Map<String, Object> extraData) {
        super(context, month, year, caldroidData, extraData);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View cellView = convertView;


        // For reuse
        if (convertView == null) {
            cellView = inflater.inflate(R.layout.custom_cell, null);
        }

        int topPadding = cellView.getPaddingTop();
        int leftPadding = cellView.getPaddingLeft();
        int bottomPadding = cellView.getPaddingBottom();
        int rightPadding = cellView.getPaddingRight();

        TextView tv1 = (TextView) cellView.findViewById(R.id.tv1);
//        TextView tv2 = (TextView) cellView.findViewById(R.id.tv2);
        ImageView imgCell = (ImageView) cellView.findViewById(R.id.imgCell);


        //default is INVISIBLE
        imgCell.setVisibility(View.INVISIBLE);

        tv1.setTextColor(Color.BLACK);

        // Get dateTime of this cell
        final DateTime dateTime = this.datetimeList.get(position);
//        Log.d("adaptergetview", "getView at " + dateTime.toString());//TODO delte
        Resources resources = context.getResources();

        //TODO set  the tv2 has a icon
        /*first use dateTime to search in the Cursor,if has
        * than set is finish?if yes,have a goo icon*/
        // Set color of the dates in previous / next month
        if (dateTime.getMonth() != month) {
            tv1.setTextColor(resources
                    .getColor(com.caldroid.R.color.caldroid_darker_gray));
        }

        boolean shouldResetDiabledView = false;
        boolean shouldResetSelectedView = false;

        // Customize for disabled dates and date outside min/max dates
        if ((minDateTime != null && dateTime.lt(minDateTime))
                || (maxDateTime != null && dateTime.gt(maxDateTime))
                || (disableDates != null && disableDates.indexOf(dateTime) != -1)) {

            tv1.setTextColor(CaldroidFragment.disabledTextColor);
            if (CaldroidFragment.disabledBackgroundDrawable == -1) {
                cellView.setBackgroundResource(com.caldroid.R.drawable.disable_cell);
            } else {
                cellView.setBackgroundResource(CaldroidFragment.disabledBackgroundDrawable);
            }

			/*if (dateTime.equals(getToday())) {
                cellView.setBackgroundResource(com.caldroid.R.drawable.red_border_gray_bg);
			}*/

        } else {
            shouldResetDiabledView = true;
        }

        // Customize for selected dates
        if (selectedDates != null && selectedDates.indexOf(dateTime) != -1) {
            cellView.setBackgroundColor(resources
                    .getColor(com.caldroid.R.color.caldroid_sky_blue));

            tv1.setTextColor(Color.BLACK);

        } else {
            shouldResetSelectedView = true;
        }

        if (shouldResetDiabledView && shouldResetSelectedView) {

            // Customize for today
            if (dateTime.equals(getToday())) {
                cellView.setBackgroundResource(com.caldroid.R.drawable.red_border);
            } else {
                cellView.setBackgroundResource(R.drawable.cell_bg_yellow);
            }
        }


        /*String cellDattimeString = dateTime.format(YMD_FORMAT);
        String todayDateString = getToday().format(YMD_FORMAT);

        if (cellDattimeString.equals(todayDateString)) {
            cellView.setBackgroundResource(com.caldroid.R.drawable.red_border);
        }*/


        tv1.setText("" + dateTime.getDay());
//        tv2.setText("Hi");

        // Somehow after setBackgroundResource, the padding collapse.
        // This is to recover the padding
        cellView.setPadding(leftPadding, topPadding, rightPadding,
                bottomPadding);

        // Set custom color if required
        setCustomResources(dateTime, cellView, tv1);

        //check The database have the date,if have show the tv2

       /* String cellDateString=dateTime.format("YYYY-MM-DD");
        List<Eventinf> list = Eventinf.find(Eventinf.class, "date=?", cellDateString);
        if(!list.isEmpty()){//not empty,that mean that day has finish at least 1 time Tabata
            tv2.setVisibility(View.VISIBLE);
        }*/
        new checkTabataDateTask().execute(imgCell, dateTime);


        return cellView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();

    }

    private class checkTabataDateTask extends AsyncTask<Object, Void, Boolean> {
        View view;

        @Override
        protected Boolean doInBackground(Object... params) {

            Object view = params[0];
            Object datetime = params[1];


            if (view instanceof View && datetime instanceof DateTime) {
                String cellDateString = ((DateTime) datetime).format("YYYY-MM-DD");
                List<Eventinf> list = Eventinf.find(Eventinf.class, "date=?", cellDateString);
                this.view = (View) view;
                if (!list.isEmpty()) {//not empty,that mean that day has finish at least 1 time Tabata
                    return true;
                } else {
                    return false;
                }
            }

            return null;

        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if (aBoolean) {
                view.setVisibility(View.VISIBLE);
            } else {
                view.setVisibility(View.INVISIBLE);
            }

        }
    }
}
