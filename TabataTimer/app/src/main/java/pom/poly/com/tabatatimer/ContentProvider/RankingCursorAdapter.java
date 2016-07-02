package pom.poly.com.tabatatimer.ContentProvider;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import pom.poly.com.tabatatimer.R;

/**
 * Created by User on 1/7/2016.
 */
public class RankingCursorAdapter extends CursorAdapter {
    public RankingCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.rank_cellv2, parent, false);
        ViewHolder vh = new ViewHolder(view);
        view.setTag(vh);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final ViewHolder vh = (ViewHolder) view.getTag();
        String name = cursor.getString(cursor.getColumnIndex(Contract.UserEntry.COLUMN_NAME));
        String profileLink = cursor.getString(cursor.getColumnIndex(Contract.UserEntry.COLUMN_PROFILE_LINK));
        long totalTime = cursor.getLong(cursor.getColumnIndex(Contract.UserEntry.COLUMN_TOTAL_TIME));
        vh.tvName.setText(name);
        vh.txTotalTime.setText(totalTime + "");

//        load the profileLink picture
        vh.profilePicture.setImageURI(profileLink);

    }


    static class ViewHolder {
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.profile_picture)
        SimpleDraweeView profilePicture;
        @BindView(R.id.txTotalTime)
        TextView txTotalTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
