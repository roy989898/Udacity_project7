package pom.poly.com.tabatatimer.ContentProvider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.content.CursorLoader;


public class UserProvider extends ContentProvider {
    public final static int USER = 100;
    public final static int USERwithEMAIL = 101;
    private UserDataHelper dbHelper;
    private UriMatcher uriMather;
    private SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        dbHelper = new UserDataHelper(getContext());
         db = dbHelper.getWritableDatabase();
        uriMather = initTheuriMatcher();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int matchInt = uriMather.match(uri);
        Cursor recursor=null;
        switch (matchInt){
            case USER:
                String queryS=uri.getQuery();
                if(queryS!=null){
                    recursor=getUserData(projection,sortOrder);
                }else{
                    String id = uri.getQueryParameter(Contract.UserEntry.KEY_ID);
                    recursor=getUserDataWithID(id,projection,sortOrder);

                }

                break;

            case USERwithEMAIL:
                String email = uri.getLastPathSegment();
                recursor=getUserDatawithEmail(email,projection,sortOrder);
                break;
        }
        recursor.setNotificationUri(getContext().getContentResolver(), uri);

        return recursor;
    }

    private Cursor getUserData(String[] projection,String sortOrder){
        Cursor cursor = db.query(Contract.UserEntry.TABLE_NAME, projection, null, null, null, null, sortOrder);
        return cursor;
    }

    private Cursor getUserDataWithID(String id,String[] projection,String sortOrder){
        Cursor cursor = db.query(Contract.UserEntry.TABLE_NAME, projection, Contract.UserEntry.COLUMN_USERID+"=", new String[]{id}, null, null, sortOrder);
        return cursor;
    }

    private Cursor getUserDatawithEmail(String email,String[] projection,String sortOrder){
        Cursor cursor = db.query(Contract.UserEntry.TABLE_NAME, projection, Contract.UserEntry.COLUMN_EMAIL+"=", new String[]{email}, null, null, sortOrder);
        return cursor;
    }



    @Nullable
    @Override
    public String getType(Uri uri) {
        int match = uriMather.match(uri);
        switch (match) {
            case USER:
                return Contract.UserEntry.CONTENT_TYPE;

            case USERwithEMAIL:
                return Contract.UserEntry.CONTENT_ITEAM_TYPE;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    private UriMatcher initTheuriMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(Contract.UserEntry.CONTENT_AUTHORITY, Contract.UserEntry.PATH_USER, USER);
        matcher.addURI(Contract.UserEntry.CONTENT_AUTHORITY, Contract.UserEntry.PATH_USER + "/*", USERwithEMAIL);
        return matcher;
    }
}
