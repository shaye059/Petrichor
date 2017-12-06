package ca.weihu.petrichor;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Today extends AppCompatActivity {

    /*==============================================================================================
        V A R I A B L E S
    ==============================================================================================*/

    TimePeriod day;
    boolean updateDatabase;
    boolean isWeek;
    boolean isMonth;
    boolean isYear;
    String [] memories = new String[2];
    /*
    Calendar cal = Calendar.getInstance();
    cal.setTime(mar.getEventDate());
    int day = cal.get(Calendar.DAY_OF_MONTH);
    int month = cal.get(Calendar.MONTH)
    int year = cal.get(Calendar.YEAR)
    int weekCounter;

     */

    private RelativeLayout relLayout = null;

    // database current user node
    private DatabaseReference dbRefUser;

    private DatabaseReference dbRefHighlight;

    private DatabaseReference databaseTimePeriod;

    private DatabaseReference databaseHighlightCollection;
/*    private DatabaseReference databaseTimePeriodCollection;
    private DatabaseReference databaseDayInThePastCollection;
    private DatabaseReference databaseRandomCollection;
    private DatabaseReference databaseSharedCollection;*/

    private FirebaseAuth firebaseAuth;

    private EditText editTextH1;
    private EditText editTextH2;
    private EditText editTextH3;
    private Button buttonSubmit1;

//    private DatabaseReference databaseReference;


    /*==============================================================================================
        C O N S T R U C T O R S
    ==============================================================================================*/

    public Today() {
    }


    /*==============================================================================================
        A C T I V I T Y  L I F E C Y C L E  M E T H O D S
    ==============================================================================================*/

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        displayEnteredHighlights();

        // code to hide keyboard when relative layout is touched

        relLayout = (RelativeLayout) findViewById(R.id.todayRelLay);

        relLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                // this code crashes sometimes... temporary: try and catch
                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                    hideSystemUI();

                } catch (Exception e) {
                }
                    
                return true;
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

        // if user is not logged in
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, AccountLogin.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();
        String userID = user.getUid();

        dbRefUser = FirebaseDatabase.getInstance().getReference( user.getUid() );

        dbRefHighlight = FirebaseDatabase.getInstance()
                .getReference( "Account/" + userID + "/highlights" );

//        databaseReference = FirebaseDatabase.getInstance().getReference();

        Log.d("\n\n---\n\nTODAY\n\n", "Account/" + userID + "/highlights");

        editTextH1 = (EditText) findViewById(R.id.editTextHighlightOne);
        editTextH2 = (EditText) findViewById(R.id.editTextHighlightTwo);
        editTextH3 = (EditText) findViewById(R.id.editTextHighlightThree);
        buttonSubmit1 = (Button) findViewById(R.id.btnSubmit);
    }


    /*==============================================================================================
        A C T I V I T Y  M E T H O D S
    ==============================================================================================*/

    public void OnImageButton(View view) {
        Intent in = new Intent(getApplicationContext(), Friends.class);
        startActivity(in);
    }

    public void onBtnEraseH1(View view) {
        eraseH(editTextH1, "h1");
    }
    public void onBtnEraseH2(View view) {
        eraseH(editTextH2, "h2");
    }
    public void onBtnEraseH3(View view) {
        eraseH(editTextH3, "h3");
    }

    private void eraseH(final EditText eT, final String h) {

        final String hDescription = eT.getText().toString().trim();

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Do you really want to erase this highlight?");
        dialogBuilder.setMessage(hDescription);

        dialogBuilder.setNegativeButton("No", null);

        dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int n) {
                if (hDescription.compareTo("") > 0) {
                    eraseHighlight(Time.dateOfToday() + h);
                } else {
                    Toast.makeText(getApplicationContext(), "No highlight to be erased.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    public void onSubmitData(View view) {
        saveUserData();
    }

    public void onBtnBack(View view) {
        onBackPressed();
    }


    // P R E V E N T I N G  E X I T I N G  F U L L S C R E E N

    // checks if window has focus
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        super.onWindowFocusChanged(hasFocus);

        // When the window gains focus, hide the system UI.
        if (hasFocus) {
            hideSystemUI();

            // When the window loses focus (e.g. the action overflow is shown),
            // cancel any pending hide action.
        } else {
            hideSystemUI();
        }
    }

    // hides status bar and navbar
    private void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }


    /*==============================================================================================
        D A T A B A S E - R E L A T E D  M E T H O D S
    ==============================================================================================*/

    private void eraseHighlight( String keyH ) {

        TimePeriodCollection.eraseFromDbHighlights( Account.getDbRefUserHighlights().child(keyH) );

        TimePeriodCollection.eraseFromDbTPCs( Time.currentYear(), Time.currentMonth(),
                                              Time.currentWeek(), Time.currentDay(), keyH );

        if ( (String.valueOf(keyH.charAt(13)) + String.valueOf(keyH.charAt(14)))
                .compareTo("h1") == 0 ) {
            editTextH1.setText("");
        } else if ( (String.valueOf(keyH.charAt(13)) + String.valueOf(keyH.charAt(14)))
                .compareTo("h2") == 0 ) {
            editTextH2.setText("");
        } else {
            editTextH3.setText("");
        }

        Toast.makeText(this, "Highlight erased.", Toast.LENGTH_SHORT).show();
    }

    private void saveUserData() {

        // Firebase keys of the highlights
        String keyH1;
        String keyH2;
        String keyH3;


        // step 1 of 5: generate keyPrefix for highlight (suffix is h1, h2 or h3)

        DateFormat df = new SimpleDateFormat("yyyyMMwwddEEE");
        Date today = Calendar.getInstance().getTime();
        String keyPrefix = df.format(today);


        // step 2 of 5: saving the 3 highlights the user inputs

        String descriptionH1 = editTextH1.getText().toString().trim();
        String descriptionH2 = editTextH2.getText().toString().trim();
        String descriptionH3 = editTextH3.getText().toString().trim();


        // set 3 of 5: initiate keys

        keyH1 = keyPrefix + "h1";
        keyH2 = keyPrefix + "h2";
        keyH3 = keyPrefix + "h3";


        // (steps 4 of 5 and 5 of 5 are in addHighlightsToDb(...) method)

        if (descriptionH1.compareTo("") > 0) {
            addHighlightsToDb(keyH1, descriptionH1);
        }
        if (descriptionH2.compareTo("") > 0) {
            addHighlightsToDb(keyH2, descriptionH2);
        }
        if (descriptionH3.compareTo("") > 0) {
            addHighlightsToDb(keyH3, descriptionH3);
        }
    }


    /**
     *
     * @param key : the key of the individual highlight
     * @param description
     */
    private void addHighlightsToDb(String key, String description) {

        // step 4 of 5: create highlights and add to Firebase

        Highlight h = new Highlight(key, description);

        dbRefHighlight.child(key).setValue( h );


        // step 5 of 5: attach time labels (and add to collections?)
        //dbRefHighlight.child(keyH1 + "/HighlightCollection");

        // 5.1 adding to specific highlight in db

        TimePeriodCollection.addToDbHighlights(dbRefHighlight, key);

        // 5.2 adding to timePeriodsCollection dbRef (for querying by TimeCollectionPeriod)

        TimePeriodCollection.addToDbTPCs(Time.currentYear(), Time.currentMonth(),
                Time.currentWeek(), Time.currentDay(), key, h);

        Toast.makeText(this, "Daily Highlights Submitted.", Toast.LENGTH_SHORT).show();
    }


// @whoever's code: this implementation needs improvement... uncomment when implemented. -T.N.
        /*Intent in = new Intent(getApplicationContext(), ExploreWeek.class);

        in.putExtra("Highlight 1", descriptionH1);
        in.putExtra("Highlight 2", descriptionH2);
        in.putExtra("Highlight 3", descriptionH3);
        startActivity(in);

        Intent in2 = new Intent(getApplicationContext(), ExploreVisitARandomDay.class);

        in2.putExtra("Highlight 1", descriptionH1);
        in2.putExtra("Highlight 2", descriptionH2);
        in2.putExtra("Highlight 3", descriptionH3);
        startActivity(in2);*/


    private void displayEnteredHighlights() {

        final String h1 = Time.dateOfToday() + "h1";
        final String h2 = Time.dateOfToday() + "h2";
        final String h3 = Time.dateOfToday() + "h3";

        final EditText h1Text = (EditText) findViewById(R.id.editTextHighlightOne);
        final EditText h2Text = (EditText) findViewById(R.id.editTextHighlightTwo);
        final EditText h3Text = (EditText) findViewById(R.id.editTextHighlightThree);

        Account.getDbRefUserHighlights().child("/").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    if (postSnapshot.getKey().compareTo(h1) == 0) {
                        h1Text.setText(postSnapshot.getValue(Highlight.class).getDescription());
                    } else if (postSnapshot.getKey().compareTo(h2) == 0) {
                        h2Text.setText(postSnapshot.getValue(Highlight.class).getDescription());
                    } else if (postSnapshot.getKey().compareTo(h3) == 0) {
                        h3Text.setText(postSnapshot.getValue(Highlight.class).getDescription());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}