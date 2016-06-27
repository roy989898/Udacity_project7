package pom.poly.com.tabatatimer.Fragment;


import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pom.poly.com.tabatatimer.ContentProvider.Eventinf;
import pom.poly.com.tabatatimer.R;

/**
 * Created by User on 27/6/2016.
 */
public class FinishDialogFragment extends DialogFragment {


    private final static String BUNDLE_KEY = "KEY_GETEVENTIF";
    @BindView(R.id.btDialogCancel)
    Button btDialogCancel;
    @BindView(R.id.btDialogSave)
    Button btDialogSave;
    @BindView(R.id.tvfinish_time)
    TextView tvfinishTime;
    @BindView(R.id.tvTotal_time)
    TextView tvTotalTime;

    private Eventinf eventif;

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
    }

    @OnClick({R.id.btDialogCancel, R.id.btDialogSave})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btDialogCancel:
                dismiss();
                break;
            case R.id.btDialogSave:
                eventif.save();
                dismiss();
                break;
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }
}
