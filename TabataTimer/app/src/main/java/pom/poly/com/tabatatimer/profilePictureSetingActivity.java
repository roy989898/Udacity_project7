package pom.poly.com.tabatatimer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class profilePictureSetingActivity extends AppCompatActivity {

    @BindView(R.id.imProfile)
    CircleImageView imProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_picture_seting);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.imProfile)
    public void onClick() {
        Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();
    }
}
