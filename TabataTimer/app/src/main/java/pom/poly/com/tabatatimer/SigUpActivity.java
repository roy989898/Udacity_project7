package pom.poly.com.tabatatimer;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pom.poly.com.tabatatimer.Firebase.User;
import pom.poly.com.tabatatimer.Utility.Utility;

public class SigUpActivity extends AppCompatActivity {

    @BindView(R.id.edtUserID)
    EditText edtUserID;
    @BindView(R.id.edtPassW)
    EditText edtPassW;
    @BindView(R.id.edre_PassW)
    EditText edrePassW;
    @BindView(R.id.btVreate)
    Button btVreate;

    @BindView(R.id.tvAppname)
    TextView tvAppname;
    @BindView(R.id.tvLogin)
    TextView tvLogin;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String TAG = "SigUpActivity";
    private ProgressDialog progress;

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sig_up);
        ButterKnife.bind(this);

        Typeface font = Typeface.createFromAsset(getAssets(), "Billabong.ttf");
        tvAppname.setTypeface(font);

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

        //set the create account text
        String myString = "Already have an account?Log in!";

        tvLogin.setMovementMethod(LinkMovementMethod.getInstance());
        tvLogin.setText(myString, TextView.BufferType.SPANNABLE);
        Spannable mySpannable = (Spannable) tvLogin.getText();
        ClickableSpan myClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(SigUpActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        };
        mySpannable.setSpan(myClickableSpan, 24, 30, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    @OnClick(R.id.btVreate)
    public void onClick() {
        boolean canCreateAccount = validateForm(edtUserID.getText().toString(), edtPassW.getText().toString());
        if (canCreateAccount) {
            createAccount(edtUserID.getText().toString(), edrePassW.getText().toString());
        } else {

        }

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

//to w password text view should match
        if (!edrePassW.getText().toString().equals(edtPassW.getText().toString())) {
            String error = getString(R.string.sigupactivity_password_not_match_error);
            edrePassW.setError(error);
            edtPassW.setError(error);
            valid = false;
        }


        return valid;
    }

    private void createAccount(final String email, final String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm(email, password)) {
            return;
        }

        showProgressDialog();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        // [START_EXCLUDE]

                        //Finish create account

                        hideProgressDialog();
                        // [END_EXCLUDE]
                        if (!task.isSuccessful()) {
                            String authenticationFailed = getResources().getString(R.string.authenticationFailed);
                            Toast.makeText(SigUpActivity.this, authenticationFailed,
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            String success = getString(R.string.sigup_activity_sigup_success);

                            Utility.saveEmail(email, getApplicationContext());
                            Toast.makeText(getApplicationContext(), success, Toast.LENGTH_SHORT).show();

                            String uid = mAuth.getCurrentUser().getUid();
                            //to check is the use in Firebase dataBase?
                            DatabaseReference baseRef = FirebaseDatabase.getInstance().getReference();
                            DatabaseReference userRef = baseRef.child("Users").child(uid);
                            userRef.addListenerForSingleValueEvent(new ValueEventListener() { //start to check
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    User user = dataSnapshot.getValue(User.class);

                                    if (user == null) {//not in FireBase
                                        //create a user
                                        String email = edtUserID.getText().toString();
                                        String uid = mAuth.getCurrentUser().getUid();
                                        createAnewUseronFireBaseDatbase(email, uid);
                                    }

                                    //go back to login page to login
                                    Intent intent = new Intent(SigUpActivity.this, LoginActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });


                        }


                    }
                });
        // [END create_user_with_email]
    }

    private void createAnewUseronFireBaseDatbase(String email, String uid) {
        DatabaseReference baseRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRef = baseRef.child("Users").child(uid);
        User user = new User(email, 0, "empty", 0, uid, getString(R.string.preference_name_defaultvalue));
        userRef.setValue(user);

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
}
