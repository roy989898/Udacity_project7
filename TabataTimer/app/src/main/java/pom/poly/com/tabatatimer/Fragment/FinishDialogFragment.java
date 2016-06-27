package pom.poly.com.tabatatimer.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pom.poly.com.tabatatimer.ContentProvider.Eventinf;
import pom.poly.com.tabatatimer.R;

/**
 * Created by User on 27/6/2016.
 */
public class FinishDialogFragment extends DialogFragment {


    @BindView(R.id.btDialogCancel)
    Button btDialogCancel;
    @BindView(R.id.btDialogSave)
    Button btDialogSave;

    private Eventinf eventif;

    private final static String BUNDLE_KEY="KEY_GETEVENTIF";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialogfragment, container);
        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        this.eventif=bundle.getParcelable(BUNDLE_KEY);
        return view;
    }

   /* public FinishDialogFragment newInstance(Eventinf eventif){
        this.eventif=eventif;


    }*/

    public FinishDialogFragment() {
    }

    public static FinishDialogFragment newInstance(Eventinf eventif) {

        Bundle args = new Bundle();
        args.putParcelable(BUNDLE_KEY,eventif);

        FinishDialogFragment fragment = new FinishDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick({R.id.btDialogCancel, R.id.btDialogSave})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btDialogCancel:
                break;
            case R.id.btDialogSave:
                break;
        }
    }
}
