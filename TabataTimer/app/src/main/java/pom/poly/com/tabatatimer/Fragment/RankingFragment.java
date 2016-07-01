package pom.poly.com.tabatatimer.Fragment;


import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pom.poly.com.tabatatimer.ContentProvider.Contract;
import pom.poly.com.tabatatimer.Firebase.User;
import pom.poly.com.tabatatimer.Preference.TimePickerPreference;
import pom.poly.com.tabatatimer.R;
import pom.poly.com.tabatatimer.Service.UserDownloadAndSaveServic;

/**
 * A simple {@link Fragment} subclass.
 */
public class RankingFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    final private int  MY_LODER_ID=101;


    @BindView(R.id.btCheck)
    Button btCheck;
    @BindView(R.id.tv)
    TextView tv;
    private DatabaseReference ref;

    public RankingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       getLoaderManager().initLoader(MY_LODER_ID,savedInstanceState,this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rank, container, false);
        ButterKnife.bind(this, view);




        return view;
    }

    @OnClick(R.id.btCheck)
    public void onClick() {

        /*Intent intent=new Intent(getActivity(), UserDownloadAndSaveServic.class);
        getActivity().startService(intent);*/





    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri= Contract.UserEntry.CONTENT_URI;
        return new CursorLoader(getContext(),uri,null,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data.moveToNext()){
            int count=data.getCount();
            Log.i("Loder",count+"");
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //TODO
    }
}
