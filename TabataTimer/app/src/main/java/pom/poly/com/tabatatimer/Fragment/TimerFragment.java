package pom.poly.com.tabatatimer.Fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pom.poly.com.tabatatimer.R;
import pom.poly.com.tabatatimer.Utility.SoundLibrary;
import pom.poly.com.tabatatimer.View.myBallView;


public class TimerFragment extends Fragment {


    @BindView(R.id.ball1)
    myBallView ball1;
    @BindView(R.id.ball2)
    myBallView ball2;
    @BindView(R.id.ball3)
    myBallView ball3;
    @BindView(R.id.ball4)
    myBallView ball4;
    @BindView(R.id.ball5)
    myBallView ball5;
    @BindView(R.id.ball6)
    myBallView ball6;
    @BindView(R.id.ball7)
    myBallView ball7;
    @BindView(R.id.ball8)
    myBallView ball8;
    @BindView(R.id.tvPauseTimer)
    TextView tvPauseTimer;
    @BindView(R.id.tvActionTimer)
    TextView tvActionTimer;
    @BindView(R.id.btStart)
    Button btStart;
    @BindView(R.id.btReset)
    Button btReset;
    private myBallView[] ballArray;
    private boolean isStartButton = true;
    private boolean pauseTimerOn = true;
    private int pauseTimer = 0;
    private int actionTimer = 0;
    private int timerCount = 0;
    private Handler mHandler;
    private Timer timer;
    private SoundLibrary sound;
    private int pauseDeadline = 10;
    private int actionDeadLine = 20;
    private int countDeadLine = 8;


    public TimerFragment() {
        // Required empty public constructor


    }

    private myBallView[] initialBallArray() {
        myBallView[] ballArray = new myBallView[8];
        ballArray[0] = ball1;
        ballArray[1] = ball2;
        ballArray[2] = ball3;
        ballArray[3] = ball4;
        ballArray[4] = ball5;
        ballArray[5] = ball6;
        ballArray[6] = ball7;
        ballArray[7] = ball8;

        return ballArray;
    }

    @Override
    public void onResume() {
        super.onResume();
        restoreTimerandCount();
        showTheTimerandCount(pauseTimer, actionTimer, timerCount, isStartButton);
    }

    @Override
    public void onPause() {
        pauseAndSaveTimerandCount();
        super.onPause();

    }

    private void setTheBallOn(int index, myBallView[] ballArray) {
        if (index < ballArray.length && index >= 0) {
            for (myBallView ball : ballArray) {
                ball.setmSetOnOff(false); //set all off first
            }
            ballArray[index].setmSetOnOff(true); //set a specific on On
        } else {
            for (myBallView ball : ballArray) {
                ball.setmSetOnOff(false); //set all off
            }
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                if (msg.what == 1) {
                    Bundle bundle = msg.getData();
                    int pauseTime = bundle.getInt(getString(R.string.bundle_key_pause));
                    int actionTime = bundle.getInt(getString(R.string.bundle_key_action));
                    int count = bundle.getInt(getString(R.string.bundle_key_count));

                    showTheTimerandCount(pauseTime, actionTime, count, isStartButton);

                } else if (msg.what == 2) {
                    stopNadResetTimerandCount();
                    //TODO start  the Congratulation  Activity
                }
            }
        };

        sound = new SoundLibrary(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ime, container, false);
        ButterKnife.bind(this, view);
        ballArray = initialBallArray();
        return view;
    }

    @OnClick({R.id.btStart, R.id.btReset})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btStart:
                if (isStartButton) {
                    //start button
                    startTimerandCount();
                    setStartbuttonToPauseButton();
                } else {
                    //pause button
                    setPausePuttontoStartButton();
                    pauseAndSaveTimerandCount();
                }

                break;
            case R.id.btReset:
                stopNadResetTimerandCount();

                break;
        }
    }

    private void setStartbuttonToPauseButton() {
        isStartButton = false;
        btStart.setText(getString(R.string.bt_start_pause));
    }

    private void setPausePuttontoStartButton() {
        isStartButton = true;
        btStart.setText(getString(R.string.bt_start));
    }


    private void pauseAndSaveTimerandCount() {
        if (timer != null) {
            timer.cancel();

            timer = null;
        }
        saveTimerAndCount();

    }

    private void saveTimerAndCount() {
        SharedPreferences sharedPreference = getActivity().getSharedPreferences(getString(R.string.timer_fragment_sharedPreference_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putInt(getString(R.string.sharedpreferencekey_action), actionTimer);
        editor.putInt(getString(R.string.sharedpreferencekey_pause), pauseTimer);
        editor.putInt(getString(R.string.sharedpreferencekey_count), timerCount);
        editor.putBoolean(getString(R.string.sharedpreferencekey_isPauseTimerON), pauseTimerOn);
//        editor.putBoolean(getString(R.string.sharedpreferencekey_isStartButton), isStartButton);
        editor.commit();
    }

    private void restoreTimerandCount() {
        SharedPreferences sharedPreference = getActivity().getSharedPreferences(getString(R.string.timer_fragment_sharedPreference_name), Context.MODE_PRIVATE);
        actionTimer = sharedPreference.getInt(getString(R.string.sharedpreferencekey_action), 0);
        pauseTimer = sharedPreference.getInt(getString(R.string.sharedpreferencekey_pause), 0);
        timerCount = sharedPreference.getInt(getString(R.string.sharedpreferencekey_count), 0);
        pauseTimerOn = sharedPreference.getBoolean(getString(R.string.sharedpreferencekey_isPauseTimerON), true);


    }

    private void startTimerandCount() {
        if (timer == null) {
            timer = new Timer(true);
            timer.schedule(new myTimerTask(), 1000, 1000);
        }
    }

    private void stopNadResetTimerandCount() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        actionTimer = pauseTimer = timerCount = 0;
        isStartButton = true;
        showTheTimerandCount(pauseTimer, actionTimer, timerCount, isStartButton);
    }

    private void showTheTimerandCount(int pauseTimer, int ActionTimer, int count, Boolean isStartButton) {
        tvPauseTimer.setText(pauseTimer + "");
        tvActionTimer.setText(ActionTimer + "");
        setTheBallOn(count, ballArray);
        if (isStartButton) {
            setPausePuttontoStartButton();
        } else {
            setStartbuttonToPauseButton();
        }

    }

    private class myTimerTask extends TimerTask {

        @Override
        public void run() {


            if (pauseTimerOn) {
                pauseTimer++;
            } else {
                actionTimer++;
            }

            if (pauseTimer > pauseDeadline - 3 && pauseTimer <= pauseDeadline) {
                sound.playStartSound();
            }
            if (actionTimer == actionDeadLine) {
                sound.playEndSound();
            }

            if (pauseTimer > pauseDeadline) {
                pauseTimerOn = false;
                pauseTimer = 0;
            } else if (actionTimer > actionDeadLine) {
                pauseTimerOn = true;
                actionTimer = 0;
                timerCount++;
            }
            if (timerCount >= countDeadLine) {
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
                message.setData(databundle);
                message.what = 1;
                mHandler.sendMessage(message);
            }


            Log.d("Timer", "action " + actionTimer + " pause " + pauseTimer + " count " + timerCount);


        }
    }
}
