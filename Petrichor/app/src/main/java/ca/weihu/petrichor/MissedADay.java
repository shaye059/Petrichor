package ca.weihu.petrichor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * MissedADay.java
 *
 * This class allows the user to submit a highlight in the past (up to 9 months prior--possibly a
 * limiting feature of the built-in classes?). The user must indicate which of the three highlights
 * s/he is submitting.
 *
 * Features:
 * - user input validation
 * - creation of a custom key for a Highlight to be stored in the database
 *
 * NOTE: As of now, the user can add a highlight in the future as well...
 *
 * Created by Tri-An on December 03, 2017.
 */
public class MissedADay extends AppCompatActivity {

    /*==============================================================================================
        V A R I A B L E S
    ==============================================================================================*/

    private EditText editTextYYYY;
    private EditText editTextMM;
    private EditText editTextDD;

    private CheckBox checkBoxH1;
    private CheckBox checkBoxH2;
    private CheckBox checkBoxH3;

    private EditText hDescription;

    DatabaseReference dbRefHighlight;


    /*==============================================================================================
        M E T H O D S
    ==============================================================================================*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missed_a_day);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        editTextYYYY = (EditText) findViewById(R.id.editTextYear);
        editTextMM = (EditText) findViewById(R.id.editTextMonth);
        editTextDD = (EditText) findViewById(R.id.editTextDay);

        checkBoxH1 = (CheckBox) findViewById(R.id.checkBoxH1);
        checkBoxH2 = (CheckBox) findViewById(R.id.checkBoxH2);
        checkBoxH3 = (CheckBox) findViewById(R.id.checkBoxH3);

        hDescription = (EditText) findViewById(R.id.editTextHDescription);

        dbRefHighlight = Account.getDbRefUser().child("highlights");
    }


    public void onBtnSubmit(View view) {

        String description;

        String strYYYY;
        String strMM;
        String strDD;

        int intYYYY;
        int intMM;
        int intDD;

        String dateHKey;
        String weekNumber;
        String dayOfWeek;

        DateFormat df;
        Date d;
        Calendar cal;

        Highlight h;


        description = hDescription.getText().toString().trim();

        strYYYY = editTextYYYY.getText().toString().trim();
        strMM = editTextMM.getText().toString().trim();
        strDD = editTextDD.getText().toString().trim();


        /*------------------------------------------------------------------------------------------
            Initial Validation Checks
        ------------------------------------------------------------------------------------------*/

        // check for valid input: format (number of characters)
        if ( strYYYY.length() != 4 && strMM.length() != 2 && strDD.length() != 2 ) {

            Toast.makeText(this, "Please enter a valid date.",
                    Toast.LENGTH_SHORT).show();
            return;
        }


        // check for valid input: format (integer characters)
        try {

            intYYYY = Integer.parseInt(editTextYYYY.getText().toString().trim());
            intMM = Integer.parseInt(editTextMM.getText().toString().trim());
            intDD = Integer.parseInt(editTextDD.getText().toString().trim());

        } catch (Exception e) {

            Toast.makeText(this, "Please enter a valid date.",
                    Toast.LENGTH_SHORT).show();
            return;
        }


        /*------------------------------------------------------------------------------------------
            Generate Key (date) for New Highlight and Additional Validation Checks
        ------------------------------------------------------------------------------------------*/

        dateHKey = strYYYY + strMM + strDD;

        try {

            df = new SimpleDateFormat("yyyyMMdd");
            d = df.parse(dateHKey);
            cal = Calendar.getInstance();
            cal.setTime(d);

            weekNumber = String.valueOf(cal.get(Calendar.WEEK_OF_YEAR));
            dateHKey = new StringBuffer(dateHKey).insert(6, weekNumber).toString();

            dayOfWeek = String.valueOf(cal.get(Calendar.DAY_OF_WEEK));

            switch (dayOfWeek) {

                case "1":
                    dayOfWeek = "Sun";
                    break;
                case "2":
                    dayOfWeek = "Mon";
                    break;
                case "3":
                    dayOfWeek = "Tue";
                    break;
                case "4":
                    dayOfWeek = "Wed";
                    break;
                case "5":
                    dayOfWeek = "Thu";
                    break;
                case "6":
                    dayOfWeek = "Fri";
                    break;
                case "7":
                    dayOfWeek = "Sat";
                    break;
                default:
                    Toast.makeText(this, "Please enter a valid date.",
                            Toast.LENGTH_SHORT).show();
                    return;
            }

            dateHKey = new StringBuffer(dateHKey).insert(10, dayOfWeek).toString();

        } catch (Exception e) {

            Toast.makeText(this, "Please enter a valid date.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // make sure only one box is checked
        if ( checkBoxH1.isChecked() && !checkBoxH2.isChecked() && !checkBoxH3.isChecked() ) {

            dateHKey += "h1";

        } else if ( !checkBoxH1.isChecked() && checkBoxH2.isChecked() && !checkBoxH3.isChecked() ) {

            dateHKey += "h2";

        } else if ( !checkBoxH1.isChecked() && !checkBoxH2.isChecked() && checkBoxH3.isChecked() ) {

            dateHKey += "h3";

        } else {
            Toast.makeText(this, "Please check only one box.",
                    Toast.LENGTH_SHORT).show();
            return;
        }


        /*------------------------------------------------------------------------------------------
            Checking to see if highlight date key already exists (if so ask user: overwrite?)
            NOTE: Implement this when I have time. -T.N.
        ------------------------------------------------------------------------------------------*/


        /*------------------------------------------------------------------------------------------
            Adding to Database
        ------------------------------------------------------------------------------------------*/

        h = new Highlight(dateHKey, description);

        dbRefHighlight.child(dateHKey).setValue(h);

        TimePeriodCollection.addToDbHighlights(dbRefHighlight, dbRefHighlight.child(dateHKey).getKey());

        TimePeriodCollection.addToDbTPCs(strYYYY, strMM, weekNumber, strDD, dateHKey, h);

        Toast.makeText(this, "Highlight Submitted.",
                Toast.LENGTH_SHORT).show();
        return;
    }

    /*  only allow one box to be checked at a time (implemented after the exception handlers for
        resolving more than 1 CheckBox checked */
    public void onCheckBox1Clicked(View view) {
        if (checkBoxH2.isChecked()) {
            checkBoxH2.toggle();
        }
        if (checkBoxH3.isChecked()) {
            checkBoxH3.toggle();
        }
    }
    public void onCheckBox2Clicked(View view) {
        if (checkBoxH1.isChecked()) {
            checkBoxH1.toggle();
        }
        if (checkBoxH3.isChecked()) {
            checkBoxH3.toggle();
        }
    }
    public void onCheckBox3Clicked(View view) {
        if (checkBoxH1.isChecked()) {
            checkBoxH1.toggle();
        }
        if (checkBoxH2.isChecked()) {
            checkBoxH2.toggle();
        }
    }

    public void onBtnBack(View view) {
        onBackPressed();
    }
}