package pom.poly.com.tabatatimer.Fragment;


import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pom.poly.com.tabatatimer.ContentProvider.Eventinf;
import pom.poly.com.tabatatimer.R;
import pom.poly.com.tabatatimer.Utility.SoundLibrary;
import pom.poly.com.tabatatimer.Utility.Utility;
import pom.poly.com.tabatatimer.Widget.TotalTimeWidget;


public class TimerFragment extends Fragment {


    @BindView(R.id.tvState)
    TextView tvState;
    @BindView(R.id.tvTimer)
    TextView tvTimer;
    @BindView(R.id.tvCycle)
    TextView tvCycle;
    @BindView(R.id.tvTotalTime)
    TextView tvTotalTime;
    @BindView(R.id.btPlayNpause)
    ImageButton btPlayNpause;

    private int restTimerDeadline = 10;
    private int actionTimerDeadline = 20;
    private int cycletimerDeadline = 8;
    /*private int restTimerDeadline = 1;
    private int actionTimerDeadline = 1;   //TODO for test
    private int cycletimerDeadline = 1;*/

    private boolean pauseTimerOn = true;
    private int pauseTimer = 0;
    private int actionTimer = 0;
    private int totaltime = 0;
    private SoundLibrary sound;
    private int timerCount = 0;
    private Handler mHandler;
    private Timer timer;
    private boolean isPauseButton = false;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;


    public TimerFragment() {
        // Required empty public constructor


    }

    @Override
    public void onPause() {
        CancelkeepScreenOn();
        super.onPause();
        pauseandSavetheTime();

    }

    @Override
    public void onResume() {
        super.onResume();
        restoreTimerandCount();

    }

    private String breadThesecondtoMoinutesandSecond(int s) {
        if (s < 0) {
            throw new IllegalArgumentException("Duration must be greater than zero!");
        }

        long minutes = TimeUnit.SECONDS.toMinutes(s);
        s -= TimeUnit.MINUTES.toSeconds(minutes);
        long seconds = s;

        String timeString = String.format(Locale.US, "%02d:%02d", minutes, seconds);


        return timeString;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sound = new SoundLibrary(getContext());
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                if (msg.what == 1) {
                    Bundle bundle = msg.getData();
                    int pauseTime = bundle.getInt(getString(R.string.bundle_key_pause));
                    int actionTime = bundle.getInt(getString(R.string.bundle_key_action));
                    int count = bundle.getInt(getString(R.string.bundle_key_count));
                    int ttTime = bundle.getInt(getString(R.string.bundle_key_totaltime));
                    String totalTime = breadThesecondtoMoinutesandSecond(ttTime);
                    boolean pausetierON = bundle.getBoolean(getString(R.string.bundle_key_isPauseTimer));
                    tvCycle.setText(count + "");
                    tvTotalTime.setText(totalTime);
                    if (pausetierON) {
                        //chnage Timercolor to white,and show pauseTime
                        int white = getResources().getColor(R.color.fragment_time_normaltext_color);
                        tvState.setTextColor(white);
                        tvTimer.setTextColor(white);
                        tvState.setText(getString(R.string.timerFragment_rest));
                        String pauseString = breadThesecondtoMoinutesandSecond(pauseTime);
                        tvTimer.setText(pauseString + "");


                    } else {
                        //change the Timer color to AccentColor,and showa ctionTime
                        int accentColor = getResources().getColor(R.color.colorAccent);
                        tvState.setTextColor(accentColor);
                        tvTimer.setTextColor(accentColor);
                        tvState.setText(getString(R.string.timerFragment_action));
                        String actionString = breadThesecondtoMoinutesandSecond(actionTime);
                        tvTimer.setText(actionString + "");
                    }

                    //show the Action tomer

                } else if (msg.what == 2) {
//
                    stopAndResetTheTimer();
                    Eventinf eventinf = new Eventinf(actionTimerDeadline, getTodayDate(), getNowTime(), restTimerDeadline, cycletimerDeadline);
                    eventinf.save();
                    updatetoFirebase();
                    updatetheWidget();

                }
            }
        };

    }

    private void updatetheWidget() {
        Intent intent = new Intent();
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        AppWidgetManager manager = AppWidgetManager.getInstance(getContext());
        int[] ids = manager.getAppWidgetIds(new ComponentName(getContext(), TotalTimeWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        getActivity().sendBroadcast(intent);
    }

    private void updatetoFirebase() {

        long currentDateinMillis = Utility.getCurrentDateinMillis();

        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance().getReference();
        }

        if (mAuth == null) {
            mAuth = FirebaseAuth.getInstance();
        }


        FirebaseUser currentuser = mAuth.getCurrentUser();
        String useID = currentuser.getUid();



        //get the reference at the login user email
//        DatabaseReference idREF = mDatabase.child("Users").child(useID).child("totaltime");
//        idREF.setValue(getTotalTabataTime());
        DatabaseReference idREF = mDatabase.child("Users").child(useID);
        HashMap<String, Object> map = new HashMap<>();
        map.put("totaltime", Utility.getTotalTabataTime());
        map.put("lastupdateTime_totalTime", currentDateinMillis);
        idREF.updateChildren(map);
    }



   /* private void stopNadResetTimerandCount() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        actionTimer = pauseTimer = timerCount = 0;
        isStartButton = true;
        showTheTimerandCount(pauseTimer, actionTimer, timerCount, isStartButton);
    }*/


    private String getTodayDate() {
//        yyyy/MM/dd HH:mm:ss
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime()); //2014/08/06 16:00:22
    }

    private String getNowTime() {
//        yyyy/MM/dd HH:mm:ss
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.US);
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime()); //2014/08/06 16:00:22
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_imev2, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    private void saveTimerAndCount(int pauseTimer, int actionTimer, int totaltime, int timerCount, boolean isPauseTimerOn) {
        SharedPreferences sharedPreference = getActivity().getSharedPreferences(getString(R.string.timer_fragment_sharedPreference_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putInt(getString(R.string.sharedpreferencekey_action), actionTimer);
        editor.putInt(getString(R.string.sharedpreferencekey_pause), pauseTimer);
        editor.putInt(getString(R.string.sharedpreferencekey_count), timerCount);
        editor.putInt(getString(R.string.sharedpreferencekey_totaltime), totaltime);
        editor.putBoolean(getString(R.string.sharedpreferencekey_isPauseTimerON), isPauseTimerOn);
//        editor.putBoolean(getString(R.string.sharedpreferencekey_isStartButton), isStartButton);
        editor.commit();
    }

    private void restoreTimerandCount() {
        SharedPreferences sharedPreference = getActivity().getSharedPreferences(getString(R.string.timer_fragment_sharedPreference_name), Context.MODE_PRIVATE);
        actionTimer = sharedPreference.getInt(getString(R.string.sharedpreferencekey_action), 0);
        pauseTimer = sharedPreference.getInt(getString(R.string.sharedpreferencekey_pause), 0);
        timerCount = sharedPreference.getInt(getString(R.string.sharedpreferencekey_count), 0);
        pauseTimerOn = sharedPreference.getBoolean(getString(R.string.sharedpreferencekey_isPauseTimerON), true);
        totaltime = sharedPreference.getInt(getString(R.string.sharedpreferencekey_totaltime), 0);

        resetTheWholeTimer(pauseTimer, actionTimer, totaltime, timerCount, pauseTimerOn);


    }

    private void startTimerandCount() {

        if (timer == null) {
            timer = new Timer(true);
            timer.schedule(new myTimerTask(), 1000, 1000);
        }
    }

    private void StartbuttonToPauseButton() {
        btPlayNpause.setImageResource(R.drawable.ic_pause_white_48dp);
        isPauseButton = true;

    }

    private void PauseButtonToStartButton() {
        btPlayNpause.setImageResource(R.drawable.ic_play_arrow_white_48dp);
        isPauseButton = false;
    }

    private void keepScreenOn() {
        final PowerManager pm = (PowerManager) getActivity().getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
        mWakeLock.acquire();
    }

    private void CancelkeepScreenOn() {
        final PowerManager pm = (PowerManager) getActivity().getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");

        try {
            mWakeLock.release();
        }catch (Throwable th){}

    }

    @OnClick({R.id.btPlayNpause, R.id.btReset})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btPlayNpause:
                if (isPauseButton) {
                    //Pause button
                    PauseButtonToStartButton();
                    pauseandSavetheTime();

                    CancelkeepScreenOn();
                } else {
                    //Start putton
                    StartbuttonToPauseButton();
                    startTimerandCount();

                    keepScreenOn();


                }

                break;
            case R.id.btReset:
                stopAndResetTheTimer();
                CancelkeepScreenOn();
                break;
        }
    }

    private void stopAndResetTheTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        resetTheWholeTimer(0, 0, 0, 0, true);


    }

    private void resetTheWholeTimer(int pauseTimer, int actionTimer, int totaltime, int timerCount, boolean isPauseTimerOn) {

        this.pauseTimer = pauseTimer;
        this.actionTimer = actionTimer;
        this.totaltime = totaltime;
        this.timerCount = timerCount;
        this.pauseTimerOn = isPauseTimerOn;
        tvCycle.setText(timerCount + "");
        tvTotalTime.setText(breadThesecondtoMoinutesandSecond(totaltime));


        if (isPauseTimerOn) {
            int white = getResources().getColor(R.color.fragment_time_normaltext_color);
            tvState.setTextColor(white);
            tvTimer.setTextColor(white);
            tvState.setText(getString(R.string.timerFragment_rest));
            tvTimer.setText(breadThesecondtoMoinutesandSecond(pauseTimer));

        } else {
            int accentColor = getResources().getColor(R.color.colorAccent);
            tvState.setTextColor(accentColor);
            tvTimer.setTextColor(accentColor);
            tvState.setText(getString(R.string.timerFragment_action));
            tvTimer.setText(breadThesecondtoMoinutesandSecond(actionTimer));
        }
        isPauseButton = false;
        PauseButtonToStartButton();
        saveTimerAndCount(pauseTimer, actionTimer, totaltime, timerCount, isPauseTimerOn);
    }

    private void pauseandSavetheTime() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        saveTimerAndCount(pauseTimer, actionTimer, totaltime, timerCount, pauseTimerOn);


    }


    private class myTimerTask extends TimerTask {

        @Override
        public void run() {


            if (pauseTimerOn) {
                pauseTimer++;
                if (pauseTimer <= restTimerDeadline) {
                    totaltime++;
                }

            } else {
                actionTimer++;
                if (actionTimer <= actionTimerDeadline) {
                    totaltime++;
                }

            }


            if (pauseTimer > restTimerDeadline - 3 && pauseTimer <= restTimerDeadline) {
                sound.playStartSound();
            }
            if (actionTimer == actionTimerDeadline) {
                sound.playEndSound();
            }

            if (pauseTimer > restTimerDeadline) {
                pauseTimerOn = false;
                pauseTimer = 0;
            } else if (actionTimer > actionTimerDeadline) {
                pauseTimerOn = true;
                actionTimer = 0;
                timerCount++;
            }
            if (timerCount >= cycletimerDeadline) {
                //send message to the handler,tell ot to sop the timer and reset
                Message message = new Message();
                message.what = 2;
                mHandler.sendMessage(message);
            } else {
                Message message = new Message();
                Bundle databundle = new Bundle();
                databundle.putInt(getString(R.string.bundle_key_pause), pauseTimer);
                databundle.putInt(getString(R.string.bundle_key_action), actionTimer);
                databundle.putInt(getString(R.string.bundle_key_count), timerCount);
                databundle.putInt(getString(R.string.bundle_key_totaltime), totaltime);
                databundle.putBoolean(getString(R.string.bundle_key_isPauseTimer), pauseTimerOn);
                message.setData(databundle);
                message.what = 1;
                mHandler.sendMessage(message);
            }


            Log.d("Timer", "action " + actionTimer + " pause " + pauseTimer + " count " + timerCount + " total" + totaltime);


        }
    }
}
