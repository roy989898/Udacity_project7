package pom.poly.com.tabatatimer.ContentProvider;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by User on 30/6/2016.
 */
public class Contract {

    public static final class UserEntry implements BaseColumns {

        //SQL part
        public static final String TABLE_NAME="UserTable";

        public static final String COLUMN_EMAIL="User_email";
        public static final String COLUMN_LIKENUMBER="Like_number";
        public static final String COLUMN_PROFILE_LINK="Profile_link";
        public static final String COLUMN_TOTAL_TIME="Total_time";
        public static final String COLUMN_USERID="User_id";
        public static final String COLUMN_NAME="User_name";

        //Content provider part
        public static final String CONTENT_AUTHORITY="pom.poly.com.tabatatimer";
        public static final Uri BASE_CONTENT_URI=Uri.parse("content://"+CONTENT_AUTHORITY);
        public static final String PATH_USER = "user";

        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH_USER).build();

        public static final String KEY_ID="keyID";

        public static final String CONTENT_TYPE= ContentResolver.CURSOR_ITEM_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+PATH_USER;//multi iteam
        public static final String CONTENT_ITEAM_TYPE=ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+PATH_USER;

        public static Uri buildUserWithID(String id){
           return CONTENT_URI.buildUpon().appendQueryParameter(KEY_ID,id).build();
        }

        public static Uri buildUserWithEmail(String email){
            return CONTENT_URI.buildUpon().appendPath(email).build();
        }
    }
}
