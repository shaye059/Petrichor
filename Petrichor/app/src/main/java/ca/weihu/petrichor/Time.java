package ca.weihu.petrichor;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A utility class.
 *
 * Created by Tri-An on December 02, 2017.
 */

public class Time {

    // C L A S S  V A R I A B L E S

    //String date;

    // C L A S S  M E T H O D S

    /**
     *
     * @return today's date as a string; format is: yyyyMMwwddEEE
     */
    public static String dateOfToday() {
         return ( (new SimpleDateFormat("yyyyMMwwddEEE"))
                .format(Calendar.getInstance().getTime()) );
    }

    public static String currentDayOfWeek() {
        return ( (new SimpleDateFormat("EEE"))
                .format(Calendar.getInstance().getTime()) );
    }

    public static String currentDay() {
        return ( (new SimpleDateFormat("dd"))
                .format(Calendar.getInstance().getTime()) );
    }

    public static String currentWeek() {
        return ( (new SimpleDateFormat("ww"))
                .format(Calendar.getInstance().getTime()) );
    }

    public static String currentMonth() {
        return ( (new SimpleDateFormat("MM"))
                .format(Calendar.getInstance().getTime()) );
    }

    public static String currentYear() {
        return ( (new SimpleDateFormat("yyyy"))
                .format(Calendar.getInstance().getTime()) );
    }
}