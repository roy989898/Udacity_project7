package pom.poly.com.tabatatimer.ContentProvider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class UserDataHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "user.db";
    private static final int DATABASE_VERSION = 2;

    public UserDataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREAT_USERTABLE = "CREATE TABLE " + Contract.UserEntry.TABLE_NAME + "(" +
                Contract.UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                Contract.UserEntry.COLUMN_EMAIL + " TEXT NOT NULL, " +
                Contract.UserEntry.COLUMN_LIKENUMBER + " INTEGER NOT NULL, " +
                Contract.UserEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                Contract.UserEntry.COLUMN_PROFILE_LINK + " TEXT NOT NULL, " +
                Contract.UserEntry.COLUMN_TOTAL_TIME + " REAL NOT NULL, " +
                Contract.UserEntry.COLUMN_USERID + " TEXT NOT NULL) ";

        db.execSQL(SQL_CREAT_USERTABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Contract.UserEntry.TABLE_NAME);


    }
}
