package pom.poly.com.tabatatimer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import pom.poly.com.tabatatimer.ContentProvider.Eventinf;
import pom.poly.com.tabatatimer.R;

/**
 * Created by User on 28/6/2016.
 */
public class DetailAdapter extends ArrayAdapter<Eventinf> {

    private Context mcontext;

    public DetailAdapter(Context context, List<Eventinf> objects) {
        super(context, 0, objects);
        mcontext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.detaildialog_cell, parent);
        }

        return convertView;
    }
}
