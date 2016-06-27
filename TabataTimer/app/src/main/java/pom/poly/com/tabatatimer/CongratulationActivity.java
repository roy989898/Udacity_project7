package pom.poly.com.tabatatimer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import pom.poly.com.tabatatimer.ContentProvider.Eventinf;

public class CongratulationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congratulation);

        Intent intent = getIntent();
        Eventinf eventinf = intent.getParcelableExtra(getString(R.string.timerFragment_CongratulationBundleKey));
        Log.d("CongratulationActivity", "Date " + eventinf.getDate());
        Log.d("CongratulationActivity", "Finish " + eventinf.getFinish_time());
    }
}
