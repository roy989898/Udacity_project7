package pom.poly.com.tabatatimer.Service;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import pom.poly.com.tabatatimer.ContentProvider.Contract;
import pom.poly.com.tabatatimer.Firebase.User;


public class UserDownloadAndSaveServic extends IntentService {


    private ContentResolver resolver;

    public UserDownloadAndSaveServic() {
        super("MyIntentService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        resolver=getContentResolver();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
        ValueEventListener eventLister=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot ds:children) {
                    User user = ds.getValue(User.class);
                    Log.i("EmptyFragment",user.userName);
                    insertUserData(resolver,user);


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("read users", "The read failed: " + databaseError.getMessage());
            }
        };
        ref.addListenerForSingleValueEvent(eventLister);//read the use data


    }

    private void insertUserData(ContentResolver resolver, User user){
        ContentValues values=new ContentValues();
        values.put(Contract.UserEntry.COLUMN_EMAIL,user.email);
        values.put(Contract.UserEntry.COLUMN_LIKENUMBER,user.likeNumber);
        values.put(Contract.UserEntry.COLUMN_PROFILE_LINK,user.profileLink);
        values.put(Contract.UserEntry.COLUMN_TOTAL_TIME,user.totaltime);
        values.put(Contract.UserEntry.COLUMN_USERID,user.userID);
        values.put(Contract.UserEntry.COLUMN_NAME,user.userName);

        resolver.insert(Contract.UserEntry.CONTENT_URI,values);
    }


}
