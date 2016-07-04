package pom.poly.com.tabatatimer.ContentProvider;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import butterknife.BindView;
import butterknife.ButterKnife;
import pom.poly.com.tabatatimer.Firebase.User;
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
        View view = LayoutInflater.from(context).inflate(R.layout.rank_cellv3, parent, false);
        ViewHolder vh = new ViewHolder(view);
        view.setTag(vh);

        return view;
    }

    @Override
    public void bindView(View view, Context context, final Cursor cursor) {
        final ViewHolder vh = (ViewHolder) view.getTag();
        String name = cursor.getString(cursor.getColumnIndex(Contract.UserEntry.COLUMN_NAME));
        String profileLink = cursor.getString(cursor.getColumnIndex(Contract.UserEntry.COLUMN_PROFILE_LINK));
        long totalTime = cursor.getLong(cursor.getColumnIndex(Contract.UserEntry.COLUMN_TOTAL_TIME));
        final int likeNumber = cursor.getInt(cursor.getColumnIndex(Contract.UserEntry.COLUMN_LIKENUMBER));
        String userID = cursor.getString(cursor.getColumnIndex(Contract.UserEntry.COLUMN_USERID));
        vh.userID = userID;
        Log.i("userID", userID);
        vh.tvName.setText(name);
        vh.txTotalTime.setText(totalTime + "");
        vh.txLikeNumber.setText(likeNumber + "");

//        load the profileLink picture
        vh.profilePicture.setImageURI(profileLink);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO like numer +1 at Firebase
                DatabaseReference baseRef = FirebaseDatabase.getInstance().getReference();


                DatabaseReference userREF = baseRef.child("Users").child(vh.userID);
                addoneToFireBaseIteam(userREF);
            }
        };
        vh.imageButton.setOnClickListener(clickListener);

    }

    private void addoneToFireBaseIteam(DatabaseReference userRef) {
        userRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                User user = mutableData.getValue(User.class);
                if (user == null) {
                    return Transaction.success(mutableData);
                }
                user.likeNumber = user.likeNumber + 1;

                mutableData.setValue(user);

                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                // Transaction completed
                Log.d("addoneToFireBaseIteam", "postTransaction:onComplete:" + databaseError);
            }
        });
    }


    static class ViewHolder {
        public String userID;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.profile_picture)
        SimpleDraweeView profilePicture;
        @BindView(R.id.imageButton)
        ImageButton imageButton;
        @BindView(R.id.txLikeNumber)
        TextView txLikeNumber;
        @BindView(R.id.txTotalTime)
        TextView txTotalTime;


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
