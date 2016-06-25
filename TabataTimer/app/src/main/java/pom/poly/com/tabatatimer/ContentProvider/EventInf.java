package pom.poly.com.tabatatimer.ContentProvider;

import com.orm.SugarRecord;


public class eventinf extends SugarRecord {
    /*The date
    The start time
    The finish time
    The action time
    The pause time
    The round
    */

    String date;
    String start_time;
    String finish_time;
    int action_time;
    int pause_time;
    int round;
    boolean finish;


    public eventinf() {
    }

    public eventinf(int action_time, String date, boolean finish, String finish_time, int pause_time, int round, String start_time) {
        this.action_time = action_time;
        this.date = date;
        this.finish = finish;
        this.finish_time = finish_time;
        this.pause_time = pause_time;
        this.round = round;
        this.start_time = start_time;
    }
}
