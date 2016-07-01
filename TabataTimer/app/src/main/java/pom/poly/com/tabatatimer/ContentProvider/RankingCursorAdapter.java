package pom.poly.com.tabatatimer.ContentProvider;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.service.voice.AlwaysOnHotwordDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
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
        View view = LayoutInflater.from(context).inflate(R.layout.ranking_cell, parent, false);
        ViewHolder vh=new ViewHolder(view);
        view.setTag(vh);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final ViewHolder vh= (ViewHolder) view.getTag();
       String name=cursor.getString(cursor.getColumnIndex(Contract.UserEntry.COLUMN_NAME));
        String profileLink=cursor.getString(cursor.getColumnIndex(Contract.UserEntry.COLUMN_PROFILE_LINK));
        long totalTime=cursor.getLong(cursor.getColumnIndex(Contract.UserEntry.COLUMN_TOTAL_TIME));
        vh.tvrankCellName.setText(name);
        vh.tvTotalTime.setText(totalTime+"");

//        load the profileLink picture
        Picasso picasso = Picasso.with(context);
        Target target=new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                vh.imProfile.setImageBitmap(bitmap);

            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        picasso.load(profileLink).into(target);
    }


    static class ViewHolder {
        @BindView(R.id.tvrankCell_name)
        TextView tvrankCellName;
        @BindView(R.id.imProfile)
        CircleImageView imProfile;
        @BindView(R.id.btTSupport)
        ImageButton btTSupport;
        @BindView(R.id.btMessage)
        ImageButton btMessage;
        @BindView(R.id.tvTotalTime)
        TextView tvTotalTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
