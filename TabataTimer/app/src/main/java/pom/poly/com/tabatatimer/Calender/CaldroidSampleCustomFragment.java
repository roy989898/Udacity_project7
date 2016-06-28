package pom.poly.com.tabatatimer.Calender;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidGridAdapter;

public class CaldroidSampleCustomFragment extends CaldroidFragment {

    private CaldroidSampleCustomAdapter adapter;

    @Override
    public CaldroidGridAdapter getNewDatesGridAdapter(int month, int year) {


         adapter = new CaldroidSampleCustomAdapter(getActivity(), month, year,
                getCaldroidData(), extraData);



        return adapter;
    }

    public void refreshAdapter(){
        adapter.notifyDataSetChanged();
    }

}
