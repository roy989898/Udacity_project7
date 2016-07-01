package pom.poly.com.tabatatimer.ContentProvider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;


public class UserProvider extends ContentProvider {
    public final static int USER = 100;
    public final static int USERwithEMAIL = 101;
    public final static int USERwithID = 102;
    private UserDataHelper dbHelper;
    private UriMatcher uriMather;

    @Override
    public boolean onCreate() {
        dbHelper = new UserDataHelper(getContext());

        uriMather = initTheuriMatcher();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int matchInt = uriMather.match(uri);
        Cursor recursor = null;
        switch (matchInt) {
            case USER:
                String queryS = uri.getQuery();
                if (queryS != null) {
                    String id = uri.getQueryParameter(Contract.UserEntry.KEY_ID);
                    recursor = getUserDataWithUserID(db,id, projection, sortOrder);
                } else {

                    recursor = getUserData(db,projection, sortOrder);
                }

                break;

            case USERwithEMAIL:
                String email = uri.getLastPathSegment();
                recursor = getUserDatawithEmail(db,email, projection, sortOrder);
                break;
            case USERwithID:
                String _idString = uri.getLastPathSegment();
                long _id=Long.parseLong(_idString);
                recursor = getUserDatawithID(db,_id, projection, sortOrder);
                break;
        }
        recursor.setNotificationUri(getContext().getContentResolver(), uri);

        return recursor;
    }

    private Cursor getUserData(SQLiteDatabase db, String[] projection, String sortOrder) {
        Cursor cursor = db.query(Contract.UserEntry.TABLE_NAME, projection, null, null, null, null, sortOrder);
        return cursor;
    }

    private Cursor getUserDataWithUserID(SQLiteDatabase db,String userid, String[] projection, String sortOrder) {
        Cursor cursor = db.query(Contract.UserEntry.TABLE_NAME, projection, Contract.UserEntry.COLUMN_USERID + "=?", new String[]{userid}, null, null, sortOrder);
        return cursor;
    }

    private Cursor getUserDatawithEmail(SQLiteDatabase db,String email, String[] projection, String sortOrder) {
        Cursor cursor = db.query(Contract.UserEntry.TABLE_NAME, projection, Contract.UserEntry.COLUMN_EMAIL + "=?", new String[]{email}, null, null, sortOrder);
        return cursor;
    }

    private Cursor getUserDatawithID(SQLiteDatabase db,long _id, String[] projection, String sortOrder) {
        Cursor cursor = db.query(Contract.UserEntry.TABLE_NAME, projection, Contract.UserEntry._ID + "=?", new String[]{_id+""}, null, null, sortOrder);
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
            case USERwithID:
                return Contract.UserEntry.CONTENT_ITEAM_TYPE;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int matchInt = uriMather.match(uri);
        Uri returnUri=null;
        if (matchInt == USER) {
            long _id = db.insert(Contract.UserEntry.TABLE_NAME, null, values);
            if (_id > 0)
                returnUri = Contract.UserEntry.buildUserWithID(_id);
            else
                throw new android.database.SQLException("Failed to insert row into " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int matchInt = uriMather.match(uri);
        if (matchInt == USER) {
            int number = db.delete(Contract.UserEntry.TABLE_NAME, selection, selectionArgs);
            if (number > 0){
                getContext().getContentResolver().notifyChange(uri, null);
                return number;
            }
            else
                return 0;

        }else{
            throw new android.database.SQLException("Failed to delete " + uri);
        }

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int matchInt = uriMather.match(uri);
        if (matchInt == USER) {
            int number = db.update(Contract.UserEntry.TABLE_NAME, values, selection, selectionArgs);
            if (number > 0){
                getContext().getContentResolver().notifyChange(uri, null);
                return number;
            }
            else
                return number;

        }else{
            throw new android.database.SQLException("Failed to update " + uri);
        }

    }

    private UriMatcher initTheuriMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(Contract.UserEntry.CONTENT_AUTHORITY, Contract.UserEntry.PATH_USER, USER);
        matcher.addURI(Contract.UserEntry.CONTENT_AUTHORITY, Contract.UserEntry.PATH_USER + "/*", USERwithEMAIL);
        matcher.addURI(Contract.UserEntry.CONTENT_AUTHORITY, Contract.UserEntry.PATH_USER + "/#", USERwithID);
        return matcher;
    }
}
