package pom.poly.com.tabatatimer.ContentProvider;

import com.orm.SugarRecord;

/**
 * Created by User on 25/6/2016.
 */
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
    String action_time;
    String pause_time;
    String round;

    public eventinf() {
    }

    public eventinf(String action_time, String date, String finish_time, String pause_time, String round, String start_time) {
        this.action_time = action_time;
        this.date = date;
        this.finish_time = finish_time;
        this.pause_time = pause_time;
        this.round = round;
        this.start_time = start_time;
    }
}
