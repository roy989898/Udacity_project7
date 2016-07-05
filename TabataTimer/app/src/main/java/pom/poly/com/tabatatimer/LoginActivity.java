package pom.poly.com.tabatatimer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.tvAppname)
    TextView tvAppname;
    @BindView(R.id.edtUserID)
    EditText edtUserID;
    @BindView(R.id.edtPassW)
    EditText edtPassW;
    @BindView(R.id.btVreate)
    Button btVreate;
    @BindView(R.id.btLogin)
    Button btLogin;
    @BindView(R.id.tvSigup)
    TextView tvSigup;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String TAG = "LoginActivity";
    private ProgressDialog progress;
    private FirebaseUser currentuser;


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

        currentuser = mAuth.getCurrentUser();
        if (currentuser != null) {
            Log.d("getCurrentUser", currentuser.getEmail());
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (currentuser != null) {//already login
            goToTheMainActivity();
        } else {
            //need to login again
            Toast.makeText(this, "Need to login again", Toast.LENGTH_SHORT).show();//TODO  put in xml
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    private boolean isAEmail(String email) {

        String re1 = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";   // Email Address 1

        Pattern p = Pattern.compile(re1, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher m = p.matcher(email);

        return m.matches();
    }

    private boolean isaValidPassw(String password) {

//        String re1 = "[a-zA-Z0-6@~!]{6,}";
        String re1 = "[a-zA-Z0-9]{6,}";
        Pattern p = Pattern.compile(re1, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher m = p.matcher(password);

        return m.matches();
    }

    private boolean validateForm(String email, String password) {
        boolean valid = true;
        //TODO put all the String into xml

        //check the email

        if (TextUtils.isEmpty(email)) {
            String required = getResources().getString(R.string.emptyMessage);
            edtUserID.setError(required);
            valid = false;
        } else {
            edtUserID.setError(null);
            if (!isAEmail(email)) {
                String notaEmail = getResources().getString(R.string.notAemail);
                edtUserID.setError(notaEmail);
                valid = false;
            } else {
                edtUserID.setError(null);
            }
        }


        //check the password

        if (TextUtils.isEmpty(password)) {
            String required = getResources().getString(R.string.emptyMessage);
            edtPassW.setError(required);
            valid = false;
        } else {
            edtPassW.setError(null);
            if (!isaValidPassw(password)) {
                String passwordErrorMessage = getResources().getString(R.string.passwordErrorMessage);
                edtPassW.setError(passwordErrorMessage);
                valid = false;
            } else {
                edtPassW.setError(null);
            }
        }


        return valid;
    }

   

    private void saveEmail(String email) {
        SharedPreferences preference = getSharedPreferences(getString(R.string.name_sharepreference), MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putString(getString(R.string.sharedpreference_email_key), email);
        editor.commit();
    }





    /*private boolean canAutoLogin() {
        SharedPreferences preference = getSharedPreferences(getString(R.string.name_sharepreference),MODE_PRIVATE);
        return preference.getBoolean(getString(R.string.sharedPreference_autologin_key), false);

    }*/


    private void signIn(final String email, final String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm(email, password)) {
            return;
        }

        showProgressDialog();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.

                        //after login,ho to the Main Activity

                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail", task.getException());
                            String authenticationFailed = getResources().getString(R.string.authenticationFailed);
                            Toast.makeText(LoginActivity.this, authenticationFailed,
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            saveEmail(email);

                            goToTheMainActivity();
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]


                    }
                });
        // [END sign_in_with_email]
    }

    private void goToTheMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void showProgressDialog() {
        progress = ProgressDialog.show(this, "",
                "", true);
    }

    private void hideProgressDialog() {
        if (progress != null) {
            progress.dismiss();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginv2);
        ButterKnife.bind(this);

        //set the custom font Billabong to the App name show at the login page
        Typeface font = Typeface.createFromAsset(getAssets(), "Billabong.ttf");
        tvAppname.setTypeface(font);

        //set the buttons click listener
        btVreate.setOnClickListener(this);
        btLogin.setOnClickListener(this);

        //Firebase auth
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {


            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }

            }


        };

        //recover the saved email
        SharedPreferences preference = getSharedPreferences(getString(R.string.name_sharepreference), MODE_PRIVATE);
        String email = preference.getString(getString(R.string.sharedpreference_email_key), "");
        if (email.equals("empty")) {
            edtUserID.setText("");
        } else {
            edtUserID.setText(email);
        }


        /*if (canAutoLogin()) {

            String password = preference.getString(getString(R.string.sharedPreference_password_key), "");
            Log.d("LoginActivity",canAutoLogin()+" "+email+" "+password);
            signIn(email, password);
        }*/
        //Set the clickable sigup text

        String myString = "Do no have an account?Sign up!";

        tvSigup.setMovementMethod(LinkMovementMethod.getInstance());
        tvSigup.setText(myString, TextView.BufferType.SPANNABLE);
        Spannable mySpannable = (Spannable)tvSigup.getText();
        ClickableSpan myClickableSpan = new ClickableSpan()
        {
            @Override
            public void onClick(View widget) {
                Intent intent=new Intent(LoginActivity.this,SigUpActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        };
        mySpannable.setSpan(myClickableSpan, 22, 28 + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btLogin:
                signIn(edtUserID.getText().toString(), edtPassW.getText().toString());
                break;

            case R.id.btVreate:
//                createAccount(edtUserID.getText().toString(), edtPassW.getText().toString());
                break;
        }
    }
}
