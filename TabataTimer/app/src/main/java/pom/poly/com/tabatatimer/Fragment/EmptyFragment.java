package pom.poly.com.tabatatimer.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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
import pom.poly.com.tabatatimer.Firebase.User;
import pom.poly.com.tabatatimer.Preference.TimePickerPreference;
import pom.poly.com.tabatatimer.R;
import pom.poly.com.tabatatimer.Service.UserDownloadAndSaveServic;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmptyFragment extends Fragment {


    @BindView(R.id.btCheck)
    Button btCheck;
    @BindView(R.id.tv)
    TextView tv;
    private DatabaseReference ref;

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

        Intent intent=new Intent(getActivity(), UserDownloadAndSaveServic.class);
        getActivity().startService(intent);





    }


}
