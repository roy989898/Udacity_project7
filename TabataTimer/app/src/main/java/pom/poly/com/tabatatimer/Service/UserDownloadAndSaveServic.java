package pom.poly.com.tabatatimer.Service;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.provider.MediaStore;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
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
//        readAllFromFirebase();



    }





}
