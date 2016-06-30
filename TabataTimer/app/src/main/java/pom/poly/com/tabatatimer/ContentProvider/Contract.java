package pom.poly.com.tabatatimer.ContentProvider;

import android.provider.BaseColumns;

/**
 * Created by User on 30/6/2016.
 */
public class Contract {

    public static final class UserEntry implements BaseColumns {
        public static final String TANLE_NAME="User";

        public static final String COLUMN_EMAIL="User_email";
        public static final String COLUMN_LIKENUMBER="Like_number";
        public static final String COLUMN_PROFILE_LINK="Profile_link";
        public static final String COLUMN_TOTAL_TIME="Total_time";
        public static final String COLUMN_USERID="User_id";
        public static final String COLUMN_NAME="User_name";
    }
}
