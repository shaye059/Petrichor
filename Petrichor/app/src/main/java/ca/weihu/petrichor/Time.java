package ca.weihu.petrichor;

import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A utility class.
 *
 * Created by Tri-An on December 02, 2017.
 */

public class Time {

    /*==============================================================================================
        V A R I A B L E S
    ==============================================================================================*/

    //String date;


    /*==============================================================================================
        M E T H O D S
    ==============================================================================================*/

    /*----------------------------------------------------------------------------------------------
        getting partial current date methods
    ----------------------------------------------------------------------------------------------*/

    /**
     *
     * @return today's date as a string; format is: yyyyMMwwddEEE
     */
    public static String dateOfToday() {
         return ( (new SimpleDateFormat("yyyyMMwwddEEE"))
                .format(Calendar.getInstance().getTime()) );
    }

    public static String currentYear() {
        return ( (new SimpleDateFormat("yyyy"))
                .format(Calendar.getInstance().getTime()) );
    }

    public static String currentMonth() {
        return ( (new SimpleDateFormat("MM"))
                .format(Calendar.getInstance().getTime()) );
    }

    public static String currentWeek() {
        return ( (new SimpleDateFormat("ww"))
                .format(Calendar.getInstance().getTime()) );
    }

    public static String currentDay() {
        return ( (new SimpleDateFormat("dd"))
                .format(Calendar.getInstance().getTime()) );
    }

    public static String currentDayOfWeek() {
        return ( (new SimpleDateFormat("EEE"))
                .format(Calendar.getInstance().getTime()) );
    }

    public static String currentMonthFullName() {

        String MM = (new SimpleDateFormat("MM")).format(Calendar.getInstance().getTime());

        switch (MM) {

            case "01":
                return "January";
            case "02":
                return "February";
            case "03":
                return "March";
            case "04":
                return "April";
            case "05":
                return "May";
            case "06":
                return "June";
            case "07":
                return "July";
            case "08":
                return "August";
            case "09":
                return "September";
            case "10":
                return "October";
            case "11":
                return "November";
            case "12":
                return "December";
            default:
                return "Not a highlight ID in format: yyyyMMwwddEEE";
        }
    }



    /*----------------------------------------------------------------------------------------------
        conversion methods
    ----------------------------------------------------------------------------------------------*/

    public static String convertToYear(String hID) {

        String year = Character.toString(hID.charAt(0))
                + Character.toString(hID.charAt(1))
                + Character.toString(hID.charAt(2))
                + Character.toString(hID.charAt(3));

        return year;
    }

    public static String convertToMonth(String hID) {

        String month = Character.toString(hID.charAt(4))
                + Character.toString(hID.charAt(5));

        return month;
    }

    public static String convertToMonthFullName(String hID) {

        String month = Character.toString(hID.charAt(4))
                + Character.toString(hID.charAt(5));

        switch (month) {

            case "01":
                return "January";
            case "02":
                return "February";
            case "03":
                return "March";
            case "04":
                return "April";
            case "05":
                return "May";
            case "06":
                return "June";
            case "07":
                return "July";
            case "08":
                return "August";
            case "09":
                return "September";
            case "10":
                return "October";
            case "11":
                return "November";
            case "12":
                return "December";
            default:
                return "Not a highlight ID in format: yyyyMMwwddEEE";
        }
    }

    public static String convertToMonthAbr(String hID) {

        String month = Character.toString(hID.charAt(4))
                + Character.toString(hID.charAt(5));

        switch (month) {

            case "01":
                return "Jan.";
            case "02":
                return "Feb.";
            case "03":
                return "Mar.";
            case "04":
                return "Apr.";
            case "05":
                return "May";
            case "06":
                return "Jun.";
            case "07":
                return "Jul.";
            case "08":
                return "Aug.";
            case "09":
                return "Sep.";
            case "10":
                return "Oct.";
            case "11":
                return "Nov.";
            case "12":
                return "Dec.";
            default:
                return "Not a highlight ID in format: yyyyMMwwddEEE";
        }
    }

    public static String convertToWeek(String hID) {

        String week = Character.toString(hID.charAt(6))
                + Character.toString(hID.charAt(7));

        return week;
    }

    public static String convertToDay(String hID) {

        String day = Character.toString(hID.charAt(8))
                + Character.toString(hID.charAt(9));

        return day;
    }

    public static String convertToDayOfWeekFullName(String hID) {

        String dayOfWeek = Character.toString(hID.charAt(10))
                + Character.toString(hID.charAt(11))
                + Character.toString(hID.charAt(12));

        switch (dayOfWeek) {

            case "Sun":
                return "Sunday";
            case "Mon":
                return "Monday";
            case "Tue":
                return "Tuesday";
            case "Wed":
                return "Wednesday";
            case "Thu":
                return "Thursday";
            case "Fri":
                return "Friday";
            case "Sat":
                return "Saturday";
            default:
                return "Not a highlight ID in format: yyyyMMwwddEEE";
        }
    }

    /**
     * returns the abbreviation of the day of the week given a highlight ID
     *
     * @param hID : ID of the highlight in the format: yyyyMMwwddEEE
     * @return
     */
    public static String convertToDayOfWeekAbr(String hID) {

        String dayOfWeek = Character.toString(hID.charAt(10))
                + Character.toString(hID.charAt(11))
                + Character.toString(hID.charAt(12))
                + ".";

        return dayOfWeek;
    }
}