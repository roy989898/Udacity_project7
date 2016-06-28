package pom.poly.com.tabatatimer.Utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by User on 22/6/2016.
 */
public class Utility {

    static public  String getTodayDate() {
//        yyyy/MM/dd HH:mm:ss
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime()); //2014/08/06 16:00:22
    }
}
