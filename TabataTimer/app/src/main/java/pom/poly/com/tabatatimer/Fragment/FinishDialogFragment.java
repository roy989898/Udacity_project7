package pom.poly.com.tabatatimer.Fragment;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.util.TimeUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.nio.charset.UnmappableCharacterException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pom.poly.com.tabatatimer.ContentProvider.Eventinf;
import pom.poly.com.tabatatimer.Firebase.User;
import pom.poly.com.tabatatimer.R;

/**
 * Created by User on 27/6/2016.
 */
public class FinishDialogFragment extends DialogFragment {


    private final static String BUNDLE_KEY = "KEY_GETEVENTIF";
    private static final String TAG = "in FinishDialogFragment";
    @BindView(R.id.btDialogCancel)
    Button btDialogCancel;
    @BindView(R.id.btDialogSave)
    Button btDialogSave;
    @BindView(R.id.tvfinish_time)
    TextView tvfinishTime;
    @BindView(R.id.tvTotal_time)
    TextView tvTotalTime;

    private Eventinf eventif;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private User user;
    private String email;
    private String useID;
    private DatabaseReference idREF;
    private String profilePURL;
    private String name;

    public FinishDialogFragment() {
    }

   /* public FinishDialogFragment newInstance(Eventinf eventif){
        this.eventif=eventif;


    }*/

    public static FinishDialogFragment newInstance(Eventinf eventif) {

        Bundle args = new Bundle();
        args.putParcelable(BUNDLE_KEY, eventif);

        FinishDialogFragment fragment = new FinishDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialogfragment, container);
        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        this.eventif = bundle.getParcelable(BUNDLE_KEY);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //set the font family
       /* Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "jinxed.ttf");
        tvFinish.setTypeface(font);
        tvFinish.setTextSize(50);*/

        tvfinishTime.setText(eventif.getFinish_time());
        tvTotalTime.setText(eventif.getTotalTime() + "");


        //for save to the firebase use
        profilePURL = getContext().getSharedPreferences(getString(R.string.name_sharepreference), Context.MODE_PRIVATE).getString(getString(R.string.SharePreferenceDownloadLinkKey), "");
        name = PreferenceManager.getDefaultSharedPreferences(getContext()).getString(getString(R.string.preference_name_key), getString(R.string.preference_name_defaultvalue));
    }

    @OnClick({R.id.btDialogCancel, R.id.btDialogSave})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btDialogCancel:
                dismiss();
                break;
            case R.id.btDialogSave:
                eventif.save();
                saveANDupdateFireBase();
                dismiss();
                break;
        }
    }

    private void saveANDupdateFireBase() {
        /*first chec is the use appear on the firebase
        * if no
        * hust save to the Firebase database
        * if yes
        * update children
        * update
        * 1name
        * 2profile picturle link
        * 3time*/

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        //get the email first
        email = mAuth.getCurrentUser().getEmail();
        useID = mAuth.getCurrentUser().getUid();

        //get the reference at the login user email
        idREF = mDatabase.child("Users").child(useID);
        //read it one times by listener

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);

                if (user == null) {
                    //create a new one

                    String id = mAuth.getCurrentUser().getProviderId();
                    long millionSecond = getTotalTabataTime();

                    User newuser = new User(email, 0, profilePURL, millionSecond, id, name);
                    idREF.setValue(newuser);
                } else {
                    //update  exiting one//
                    /*first chec is the use appear on the firebase
        * if no
        * hust save to the Firebase database
        * if yes
        * update children
        * update
        * 1name
        * 2
        * 3time*/
                    long millionSecond = getTotalTabataTime();
                    HashMap<String,Object> map=new HashMap<>();
                    map.put("totaltime",millionSecond);
                    map.put("userName",name);

                    idREF.updateChildren(map);





                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadsingle use by email:onCancelled", databaseError.toException());
            }
        };

        idREF.addListenerForSingleValueEvent(listener);

    }

    private long getTotalTabataTime(){//in million second
        List<Eventinf> eventList = Eventinf.listAll(Eventinf.class);
        long second=0;
        for (Eventinf event:eventList) {
            second+=event.getTotalTime();

        }

        long millionSecond = TimeUnit.SECONDS.toMillis(second);

        return millionSecond;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }
}
