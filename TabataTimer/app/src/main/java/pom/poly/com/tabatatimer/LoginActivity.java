package pom.poly.com.tabatatimer;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        //set the custom font Billabong to the App name show at the login page
        Typeface font = Typeface.createFromAsset(getAssets(), "Billabong.ttf");

        tvAppname.setTypeface(font);



    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, "click!", Toast.LENGTH_SHORT).show();
    }
}
