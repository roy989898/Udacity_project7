package pom.poly.com.tabatatimer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pom.poly.com.tabatatimer.ContentProvider.Eventinf;
import pom.poly.com.tabatatimer.R;

/**
 * Created by User on 28/6/2016.
 */
public class DetailAdapter extends ArrayAdapter<Eventinf> {

    private Context mcontext;
    private int KEY;

    public DetailAdapter(Context context, List<Eventinf> objects) {
        super(context, 0, objects);
        mcontext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.detaildialog_cell, parent,false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        Eventinf eventinf = getItem(position);
        vh.tvDetaildate.setText(eventinf.getDate());
        vh.tvDetailFinish.setText(eventinf.getFinish_time());
        vh.tvTotal.setText(eventinf.getTotalTime()+"");

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tvDetaildate)
        TextView tvDetaildate;
        @BindView(R.id.tvDetailFinish)
        TextView tvDetailFinish;
        @BindView(R.id.tvTotal)
        TextView tvTotal;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
