package pom.poly.com.tabatatimer.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import pom.poly.com.tabatatimer.R;
import pom.poly.com.tabatatimer.View.myBallView;


public class TimerFragment extends Fragment {


    @BindView(R.id.ball1)
    myBallView ball1;
    @BindView(R.id.ball2)
    myBallView ball2;
    @BindView(R.id.ball3)
    myBallView ball3;
    @BindView(R.id.ball4)
    myBallView ball4;
    @BindView(R.id.ball5)
    myBallView ball5;
    @BindView(R.id.ball6)
    myBallView ball6;
    @BindView(R.id.ball7)
    myBallView ball7;
    @BindView(R.id.ball8)
    myBallView ball8;



    public TimerFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    /*public static TimerFragment newInstance(String param1, String param2) {
        TimerFragment fragment = new TimerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ime, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

}
