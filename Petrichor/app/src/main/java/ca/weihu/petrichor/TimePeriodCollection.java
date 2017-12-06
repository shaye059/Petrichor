package ca.weihu.petrichor;

import com.google.firebase.database.DatabaseReference;

/**
 * Created by Tri-An on November 28, 2017.
 */

public class TimePeriodCollection implements HighlightCollection {

    TimePeriod[] timePeriods;

/*    @Override
    public Highlight[] returnHighlight() {
        return new Highlight[0];
    }*/


    /*----------------------------------------------------------------------------------------------
        dbRef: Account/user-key/highlights/highlight
    ----------------------------------------------------------------------------------------------*/

    /**
     * sets the TimePeriod for a highlight
     * @param dbRefHighlight : DatabaseReference of the highlight
     * @param key : highlight's key
     */
    public static void addToDbHighlights(DatabaseReference dbRefHighlight, String key) {

        TimePeriod tp1;
        TimePeriod tp2;
        TimePeriod tp3;
        TimePeriod tp4;

        // create time period

        tp1 = new TimePeriod( TimePeriod.TimePeriodLabel.YEAR,
                Integer.parseInt(Time.currentYear()), null);

        tp2 = new TimePeriod( TimePeriod.TimePeriodLabel.MONTH,
                Integer.parseInt(Time.currentMonth()), tp1);

        tp3 = new TimePeriod( TimePeriod.TimePeriodLabel.WEEK,
                Integer.parseInt(Time.currentWeek()), tp2);

        tp4 = new TimePeriod( TimePeriod.TimePeriodLabel.DAY,
                Integer.parseInt(Time.currentDay()), tp3);

        // add to DB

        dbRefHighlight.child(key + "/timePeriods/TimePeriodYYYY").setValue(tp1);

        dbRefHighlight.child(key + "/timePeriods/TimePeriodYYYY/TimePeriodMM").setValue(tp2);

        dbRefHighlight.child(key + "/timePeriods/TimePeriodYYYY/TimePeriodMM/TimePeriodWW")
                .setValue(tp3);

        dbRefHighlight.child(key +
                "/timePeriods/TimePeriodYYYY/TimePeriodMM/TimePeriodWW/TimePeriodDD").setValue(tp4);

        // add to TimePeriodCollection

        dbRefHighlight.child(key + "/timePeriods/TimePeriodYYYY/timePeriodCollections/years/"
                + Time.currentYear()).setValue(true);

        dbRefHighlight.child(key + "/timePeriods/TimePeriodYYYY/timePeriodCollections/months/"
                + Time.currentMonth()).setValue(true);

        dbRefHighlight.child(key + "/timePeriods/TimePeriodYYYY/timePeriodCollections/weeks/"
                + Time.currentWeek()).setValue(true);

        dbRefHighlight.child(key + "/timePeriods/TimePeriodYYYY/timePeriodCollections/days/"
                + Time.currentDay()).setValue(true);
    }

    public static void eraseFromDbHighlights(DatabaseReference dbRefHighlight) {
        dbRefHighlight.removeValue();
    }


    /*----------------------------------------------------------------------------------------------
        dbRef: Account/user-key/timePeriodCollections/t/n/highlights/highlight
            where   t = days, months, weeks or years
                    n = t value
    ----------------------------------------------------------------------------------------------*/

    /**
     * adds a highlight to a user dbRef TimePeriodCollection
     *
     * @param key : highlight's key
     * @param highlight : highlight
     */
    public static void addToDbTPCs(String yyyy, String mm, String ww, String dd,
                                   String key, Highlight highlight) {

        DatabaseReference dbRefUser = Account.getDbRefUser();

        dbRefUser.child("timePeriodCollections/years/" + yyyy + "/highlights/" + key)
                .setValue(highlight);

        dbRefUser.child("timePeriodCollections/months/" + mm + "/highlights/" + key)
                .setValue(highlight);

        dbRefUser.child("timePeriodCollections/weeks/" + ww + "/highlights/" + key)
                .setValue(highlight);

        dbRefUser.child("timePeriodCollections/days/" + dd + "/highlights/" + key)
                .setValue(highlight);
    }

    public static void eraseFromDbTPCs(String yyyy, String mm, String ww, String dd, String key) {
        DatabaseReference dbRefUser = Account.getDbRefUser();

        dbRefUser.child("timePeriodCollections/years/" + yyyy + "/highlights/" + key)
                .removeValue();

        dbRefUser.child("timePeriodCollections/months/" + mm + "/highlights/" + key)
                .removeValue();

        dbRefUser.child("timePeriodCollections/weeks/" + ww + "/highlights/" + key)
                .removeValue();

        dbRefUser.child("timePeriodCollections/days/" + dd + "/highlights/" + key)
                .removeValue();
    }
}
