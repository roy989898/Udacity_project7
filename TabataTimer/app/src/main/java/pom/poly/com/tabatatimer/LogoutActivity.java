package pom.poly.com.tabatatimer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LogoutActivity extends AppCompatActivity {

    @BindView(R.id.btLogout)
    Button btLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btLogout)
    public void onClick() {
        //set the auto login false
        autoLoginFalse();
        //del the password and email
        delthesaveEmailAndPassAndProfileImgUrl();
        //go to the login page//
        Intent intent = new Intent(this, LoginActivity.class);
        //del the profile picture img//TODO
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void delthesaveEmailAndPassAndProfileImgUrl() {
        SharedPreferences preference = getSharedPreferences(getString(R.string.name_sharepreference),MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putString(getString(R.string.sharedpreference_email_key), "empty");
        editor.putString(getString(R.string.sharedPreference_password_key), "empty");
        editor.putString(getString(R.string.SharePreferenceDownloadLinkKey), "empty");
        editor.commit();

        Log.d("LogoutActivity","email "+preference.getString(getString(R.string.sharedpreference_email_key),"Uemail")+"");
        Log.d("LogoutActivity","password "+preference.getString(getString(R.string.sharedPreference_password_key),"Upassword")+"");
    }

    private void autoLoginFalse() {
        SharedPreferences preference = getSharedPreferences(getString(R.string.name_sharepreference),MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putBoolean(getString(R.string.sharedPreference_autologin_key), false);
        editor.commit();

        Log.d("LogoutActivity",preference.getBoolean(getString(R.string.sharedPreference_autologin_key),false)+"");
    }
}