package pom.poly.com.tabatatimer.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pom.poly.com.tabatatimer.ContentProvider.Eventinf;
import pom.poly.com.tabatatimer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmptyFragment extends Fragment {


    @BindView(R.id.btCheck)
    Button btCheck;

    public EmptyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mpty, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btCheck)
    public void onClick() {

//        yyyy/MM/dd HH:mm:ss
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        String dateString = dateFormat.format(cal.getTime()); //2014/08/06 16:00:22
//mptyFragment
        List<Eventinf> list = Eventinf.find(Eventinf.class, "date=?", dateString);

        for (Eventinf inf : list) {
            Log.d("mptyFragment",inf.getDate()+" "+inf.getFinish_time());
            Log.d("mptyFragment",inf.getDate().equals(getTodayDate())+"");

        }


    }

    private String getTodayDate() {
//        yyyy/MM/dd HH:mm:ss
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime()); //2014/08/06 16:00:22
    }
}
