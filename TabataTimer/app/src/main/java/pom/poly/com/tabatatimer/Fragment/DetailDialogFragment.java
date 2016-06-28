package pom.poly.com.tabatatimer.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pom.poly.com.tabatatimer.Adapter.DetailAdapter;
import pom.poly.com.tabatatimer.ContentProvider.Eventinf;
import pom.poly.com.tabatatimer.R;


public class DetailDialogFragment extends DialogFragment {
    private static String KEY = "DDF_KEY";
    @BindView(R.id.lvDetailDialog)
    ListView lvDetailDialog;
    private ArrayList<Eventinf> eventArray;

    public static DetailDialogFragment newInstance(List<Eventinf> eventinfs) {

        //change the eventinfs List to ArrayList
        ArrayList<Eventinf> eventArray = new ArrayList<>();
        eventArray.addAll(eventinfs);

        DetailDialogFragment dialog = new DetailDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(KEY, eventArray);
        dialog.setArguments(bundle);

        return dialog;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_dialog, container);
        ButterKnife.bind(this, view);

        Bundle arg = getArguments();
        eventArray = arg.getParcelableArrayList(KEY);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DetailAdapter adapter = new DetailAdapter(getContext(), eventArray);
        lvDetailDialog.setAdapter(adapter);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }
}
