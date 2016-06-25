package pom.poly.com.tabatatimer.ContentProvider;

import android.content.ContentResolver;
import android.net.Uri;

/**
 * Created by User on 25/6/2016.
 */
public class Contract {
    /*Eventinf (DIR)
content://pom.poly.com.tabatatimer/eventinf

Eventinf with Date(DIR)
content://pom.poly.com.tabatatimer/eventinf/[date]
*/

    public static final String CONTENT_AUTHORITY = "pom.poly.com.tabatatimer";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_EVENT = "eventinf";

    public static final Uri CONTENT_URI =
            BASE_CONTENT_URI.buildUpon().appendPath(PATH_EVENT).build();  //build uri:  "content://pom.poly.com.tabatatimer/eventinf"

    public static final String CONTENT_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_EVENT;  //define the type , multiple iteam
    public static final String CONTENT_ITEM_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_EVENT;  //single iteam


}
