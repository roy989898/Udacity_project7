package pom.poly.com.tabatatimer.Service;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Intent;


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
