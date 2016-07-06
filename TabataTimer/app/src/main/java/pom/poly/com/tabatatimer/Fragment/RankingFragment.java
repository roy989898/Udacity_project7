package pom.poly.com.tabatatimer.Fragment;


import android.animation.Animator;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pom.poly.com.tabatatimer.ContentProvider.Contract;
import pom.poly.com.tabatatimer.ContentProvider.RankingCursorAdapter;
import pom.poly.com.tabatatimer.Firebase.Message;
import pom.poly.com.tabatatimer.R;
import pom.poly.com.tabatatimer.Utility.Utility;

/**
 * A simple {@link Fragment} subclass.
 */
public class RankingFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, RankingCursorAdapter.MessageHandler {

    final private int MY_LODER_ID = 101;
    @BindView(R.id.lv_ranking)
    ListView lvRanking;
    @BindView(R.id.tvEmptyMessageView)
    TextView tvEmptyMessageView;
    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.btSend)
    Button btSend;
    @BindView(R.id.btCancel)
    Button btCancel;
    @BindView(R.id.cardViewmessageBox)
    CardView cardViewmessageBox;


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

        lvRanking.setEmptyView(tvEmptyMessageView);

        cursorAdapter = new RankingCursorAdapter(getContext(), null);
        cursorAdapter.setHandler(this);

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

    @Override
    public void send(final String uid) {
        fadein();
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long datetime = Calendar.getInstance().getTimeInMillis();
                String fromID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                Message message = new Message(datetime, fromID, editText.getText().toString(), uid);
                Utility.sendMessage(message);

                fadeout();
            }
        });

    }

    @OnClick({R.id.btCancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btCancel:
                fadeout();
                break;
        }
    }

    private void fadein() {
        int mShortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);
        cardViewmessageBox.setAlpha(0f);
        cardViewmessageBox.setVisibility(View.VISIBLE);
        cardViewmessageBox.animate()
                .alpha(1f)
                .setDuration(mShortAnimationDuration)
                .setListener(null);

        editText.requestFocus();

    }

    private void fadeout() {
        int mShortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);
        //clear the message input
        editText.getText().clear();
        cardViewmessageBox.setAlpha(1f);
        cardViewmessageBox.animate()
                .alpha(0f)
                .setDuration(mShortAnimationDuration)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        cardViewmessageBox.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });

        //close the keyboard
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }
}
