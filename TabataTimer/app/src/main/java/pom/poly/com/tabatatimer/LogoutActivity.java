package pom.poly.com.tabatatimer;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import butterknife.ButterKnife;
import pom.poly.com.tabatatimer.ContentProvider.Eventinf;

public class LogoutActivity extends AppCompatActivity {

    private DialogInterface.OnClickListener listerner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
        ButterKnife.bind(this);
        createAndSjowDialog();


    }

    private void createAndSjowDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(getString(R.string.logout_activity_dialog_title));
        dialog.setMessage(getString(R.string.logout_activity_dialog_Message));
        listerner = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case AlertDialog.BUTTON_POSITIVE:
                        //go bac to setting pahe
                        //logout
                        deleteAllTheSetting();
                        deleteAllEveneinf();
                        //go to the login page//
                        Intent intent = new Intent(LogoutActivity.this, LoginActivity.class);
                        //del the profile picture img//
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                        FirebaseAuth.getInstance().signOut();
                        startActivity(intent);
                        break;
                    case AlertDialog.BUTTON_NEGATIVE:
                        finish();
                        break;


                }


            }
        };
        dialog.setPositiveButton(R.string.logout_activity_Logout_button_text, listerner);
        dialog.setNegativeButton(R.string.logout_activity_Cancel_button_text, listerner);
        dialog.create().show();
    }

    /*@OnClick(R.id.btLogout)
    public void onClick() {
        //del the password and email
        delthesaveEmailAndPassAndProfileImgUrl();
        deleteAllEveneinf();
        //go to the login page//
        Intent intent = new Intent(this, LoginActivity.class);
        //del the profile picture img//
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        FirebaseAuth.getInstance().signOut();
        startActivity(intent);
    }*/

    private void deleteAllTheSetting() {
        SharedPreferences preference = getSharedPreferences(getString(R.string.name_sharepreference), MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putString(getString(R.string.sharedpreference_email_key), "empty");
//        editor.putString(getString(R.string.sharedPreference_password_key), "empty");
        editor.putString(getString(R.string.SharePreferenceDownloadLinkKey), "empty");
        editor.commit();

        SharedPreferences defaultPreference = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor defaultEditor = defaultPreference.edit();
        //name
        defaultEditor.putString(getString(R.string.preference_name_key), getString(R.string.preference_name_defaultvalue));
//        Notification on/off
        defaultEditor.putBoolean(getString(R.string.preference_nof_key), Boolean.parseBoolean(getString(R.string.preference_nofe_defaultvalue)));
//        Notification time
        defaultEditor.putString(getString(R.string.preference_timePick_key), getString(R.string.preference_timePick_defaultvalue));
        defaultEditor.commit();


    }

    private void deleteAllEveneinf() {
        Eventinf.deleteAll(Eventinf.class);
    }


}
