package pom.poly.com.tabatatimer.Service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import pom.poly.com.tabatatimer.Firebase.User;


public class UserDownloadAndSaveServic extends IntentService {


    public UserDownloadAndSaveServic() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
        ValueEventListener eventLister=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot ds:children) {
                    User user = ds.getValue(User.class);
                    Log.i("EmptyFragment",user.userName);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("read users", "The read failed: " + databaseError.getMessage());
            }
        };
        ref.addListenerForSingleValueEvent(eventLister);//read the use data


    }


}
