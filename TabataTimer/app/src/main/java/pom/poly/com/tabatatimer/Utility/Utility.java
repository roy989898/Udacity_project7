package pom.poly.com.tabatatimer.Utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pom.poly.com.tabatatimer.R;

/**
 * Created by User on 22/6/2016.
 */
public class Utility {

    static public String getTodayDate() {
//        yyyy/MM/dd HH:mm:ss
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime()); //2014/08/06 16:00:22
    }

    static public long getCurrentDateinMillis(){
        Calendar calcender = Calendar.getInstance();
        return calcender.getTimeInMillis();
    }

    static public boolean isAEmail(String email) {

        String re1 = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";   // Email Address 1

        Pattern p = Pattern.compile(re1, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher m = p.matcher(email);

        return m.matches();
    }

    static public boolean isaValidPassw(String password) {

//        String re1 = "[a-zA-Z0-6@~!]{6,}";
        String re1 = "[a-zA-Z0-9]{6,}";
        Pattern p = Pattern.compile(re1, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher m = p.matcher(password);

        return m.matches();
    }

    static public boolean validateForm(String email, String password) {
        boolean valid = true;
        //check the email

        if (TextUtils.isEmpty(email)) {
            /*String required = context.getResources().getString(R.string.emptyMessage);
            edtUserID.setError(required);*/
            valid = false;
        } else {
//            edtUserID.setError(null);
            if (!isAEmail(email)) {
                /*String notaEmail = getResources().getString(R.string.notAemail);
                edtUserID.setError(notaEmail);*/
                valid = false;
            } else {
//                edtUserID.setError(null);
            }
        }


        //check the password

        if (TextUtils.isEmpty(password)) {
           /* String required = getResources().getString(R.string.emptyMessage);
            edtPassW.setError(required);*/
            valid = false;
        } else {
//            edtPassW.setError(null);
            if (!isaValidPassw(password)) {
                /*String passwordErrorMessage = getResources().getString(R.string.passwordErrorMessage);
                edtPassW.setError(passwordErrorMessage);*/
                valid = false;
            } else {
//                edtPassW.setError(null);
            }
        }


        return valid;
    }

    static public void saveEmail(String email, Context context) {
        SharedPreferences preference = context.getSharedPreferences(context.getString(R.string.name_sharepreference), context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putString(context.getString(R.string.sharedpreference_email_key), email);
        editor.commit();
    }

    static public void saveTheLinkinSP(String downloadUrl, Context context) {
        SharedPreferences preference = context.getSharedPreferences(context.getString(R.string.name_sharepreference), context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putString(context.getString(R.string.SharePreferenceDownloadLinkKey), downloadUrl);
        editor.commit();
    }


    public static void sendMessage(pom.poly.com.tabatatimer.Firebase.Message message) {
        //upload to fireBase
        DatabaseReference baseRef = FirebaseDatabase.getInstance().getReference();
        baseRef.child("Messages").child(message.toID).push().setValue(message);
    }

    public static String formatDate(long datetime){
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(datetime);
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);

        return String.format("%d-%02d-%02d",year,month,day);




    }




}
