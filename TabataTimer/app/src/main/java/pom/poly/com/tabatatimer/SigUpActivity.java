package pom.poly.com.tabatatimer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SigUpActivity extends AppCompatActivity {

    @BindView(R.id.edtUserID)
    EditText edtUserID;
    @BindView(R.id.edtPassW)
    EditText edtPassW;
    @BindView(R.id.edre_PassW)
    EditText edrePassW;
    @BindView(R.id.btVreate)
    Button btVreate;
    @BindView(R.id.tvSigup)
    TextView tvSigup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sig_up);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btVreate)
    public void onClick() {
        boolean canCreateAccount = validateForm(edtUserID.getText().toString(), edtPassW.getText().toString());
        if(canCreateAccount){
            //TODO create account
        }else {

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
            } else  {
                edtPassW.setError(null);
            }
        }

//to w password text view should match
        if(!edrePassW.getText().toString().equals(edtPassW.getText().toString())){
            String error=getString(R.string.sigupactivity_password_not_match_error);
            edrePassW.setError(error);
            edtPassW.setError(error);
            valid= false;
        }


        return valid;
    }
}
