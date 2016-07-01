package pom.poly.com.tabatatimer.ContentProvider;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.orm.SugarRecord;

import java.util.ArrayList;

import pom.poly.com.tabatatimer.Utility.Observer;


public class Eventinf extends SugarRecord implements Parcelable {
    /*The date
    The start time
    The finish time
    The action time
    The pause time
    The round
    */

    public static final Creator<Eventinf> CREATOR = new Creator<Eventinf>() {
        @Override
        public Eventinf createFromParcel(Parcel in) {
            return new Eventinf(in);
        }

        @Override
        public Eventinf[] newArray(int size) {
            return new Eventinf[size];
        }
    };
    private static ArrayList<Observer> observers = new ArrayList<>();
    private final String KEY_DATE = "keydate";
    private final String KEY_FINISH_TIME = "keyfinish";
    private final String KEY_ACTION = "keyaction";
    private final String KEY_PAUSE = "keypause";
    private final String KEY_ROUND = "keyround";
    String date;
    String finish_time;
    int action_time;
    int pause_time;
    int round;

    public Eventinf() {
    }

    public Eventinf(Parcel in) {
        Bundle val = in.readBundle();

        this.date = val.getString(KEY_DATE);
        this.finish_time = val.getString(KEY_FINISH_TIME);
        this.action_time = val.getInt(KEY_ACTION);
        this.pause_time = val.getInt(KEY_PAUSE);
        this.round = val.getInt(KEY_ROUND);
    }

    public Eventinf(int action_time, String date, String finish_time, int pause_time, int round) {
        this.action_time = action_time;
        this.date = date;
        this.finish_time = finish_time;
        this.pause_time = pause_time;
        this.round = round;
    }

    public static void registerObserver(Observer o) {
        removeALLObservers();
        observers.add(o);

    }

    public static void removeObservers(Observer o) {
        observers.remove(o);
    }

    public static void removeALLObservers() {
        observers.clear();
    }

    public static void notifyChanged() {
        try {
            for (Observer observer : observers) {
                observer.update();
            }
        } catch (Exception exp) {
            Log.d("Eventinf.notifyChanged", exp.toString());

        }

    }

    @Override
    public long save() {
        long i = super.save();
        try {
            notifyChanged();
        } catch (Exception e) {
            Log.d("Eventinf,save", e.toString());
        }

        return i;
    }

    @Override
    public boolean delete() {
        boolean b = super.delete();
        notifyChanged();
        return b;
    }


    public int getAction_time() {
        return action_time;
    }

    public void setAction_time(int action_time) {
        this.action_time = action_time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFinish_time() {
        return finish_time;
    }

    public void setFinish_time(String finish_time) {
        this.finish_time = finish_time;
    }

    public int getPause_time() {
        return pause_time;
    }

    public void setPause_time(int pause_time) {
        this.pause_time = pause_time;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Bundle val = new Bundle();
        val.putString(KEY_DATE, getDate());
        val.putString(KEY_FINISH_TIME, getFinish_time());
        val.putInt(KEY_ACTION, getAction_time());
        val.putInt(KEY_PAUSE, getPause_time());
        val.putInt(KEY_ROUND, getRound());

        dest.writeBundle(val);

    }

    public int getTotalTime() {
        int total = (getAction_time() + getPause_time()) * getRound();

        return total;
    }
}
