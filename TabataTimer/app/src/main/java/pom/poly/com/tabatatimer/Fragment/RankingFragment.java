package pom.poly.com.tabatatimer.Fragment;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import pom.poly.com.tabatatimer.ContentProvider.Contract;
import pom.poly.com.tabatatimer.ContentProvider.RankingCursorAdapter;
import pom.poly.com.tabatatimer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RankingFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    final private int MY_LODER_ID = 101;
    @BindView(R.id.lv_ranking)
    ListView lvRanking;


    private DatabaseReference ref;
    private RankingCursorAdapter cursorAdapter;

    public RankingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rank, container, false);
        ButterKnife.bind(this, view);

        cursorAdapter = new RankingCursorAdapter(getContext(), null);

        getLoaderManager().initLoader(MY_LODER_ID, savedInstanceState, this);

        lvRanking.setAdapter(cursorAdapter);


        return view;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = Contract.UserEntry.CONTENT_URI;
        return new CursorLoader(getContext(), uri, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.moveToNext()) {
            int count = data.getCount();
            Log.d("getUserData", "onLoadFinished " + data.getCount() + "");
        }

//        mForecastAdapter.swapCursor(data);

        cursorAdapter.swapCursor(data);


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        cursorAdapter.swapCursor(null);

    }
}
