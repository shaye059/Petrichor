package ca.weihu.petrichor;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Today extends AppCompatActivity {

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
    private DatabaseReference databaseUser;

    private DatabaseReference databaseHighlight;

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


    // C O N S T R U C T O R

    public Today() {
    }


    // M E T H O D S

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_today);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);


        // code to hide keyboard when relative layout is touched

        relLayout = (RelativeLayout) findViewById(R.id.todayRelLay);

        relLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                hideSystemUI();

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

        databaseUser = FirebaseDatabase.getInstance().getReference( user.getUid() );

        databaseHighlight = FirebaseDatabase.getInstance()
                .getReference( "accounts/" + userID + "/Highlight" );

//        databaseReference = FirebaseDatabase.getInstance().getReference();

        Log.d("\n\n---\n\nTODAY\n\n", "accounts/" + userID + "/Highlight");

        editTextH1 = (EditText) findViewById(R.id.editText);
        editTextH2 = (EditText) findViewById(R.id.editText3);
        editTextH3 = (EditText) findViewById(R.id.editText4);
        buttonSubmit1 = (Button) findViewById(R.id.button);
    }

    public void OnImageButton(View view) {
        Intent in = new Intent(getApplicationContext(), Friends.class);
        startActivity(in);
    }

    private void saveUserData() {

        // Firebase keys of the highlights
        String keyH1;
        String keyH2;
        String keyH3;


        // step 1 of 4: generate keyPrefix for highlight (suffix is h1, h2 or h3)

        DateFormat df = new SimpleDateFormat("yyyyMMwwddEEE");
        Date today = Calendar.getInstance().getTime();
        String keyPrefix = df.format(today);


        // step 2 of 4: saving the 3 highlights the user inputs

        String descriptionH1 = editTextH1.getText().toString().trim();
        String descriptionH2 = editTextH2.getText().toString().trim();
        String descriptionH3 = editTextH3.getText().toString().trim();


        // set 3 of 4: initiate keys

        keyH1 = keyPrefix + "h1";
        keyH2 = keyPrefix + "h2";
        keyH3 = keyPrefix + "h3";


        // step 4 of 4: create highlights and add to Firebase

        databaseHighlight.child(keyH1).setValue( new Highlight(keyH1, descriptionH1) );
        databaseHighlight.child(keyH2).setValue( new Highlight(keyH2, descriptionH2) );
        databaseHighlight.child(keyH3).setValue( new Highlight(keyH3, descriptionH3) );

        Toast.makeText(this, "Highlights saved.", Toast.LENGTH_LONG).show();


        Intent in = new Intent(getApplicationContext(), ExploreWeek.class);

        in.putExtra("Highlight 1", descriptionH1);
        in.putExtra("Highlight 2", descriptionH2);
        in.putExtra("Highlight 3", descriptionH3);
        startActivity(in);

        Intent in2 = new Intent(getApplicationContext(), ExploreVisitARandomDay.class);

        in2.putExtra("Highlight 1", descriptionH1);
        in2.putExtra("Highlight 2", descriptionH2);
        in2.putExtra("Highlight 3", descriptionH3);
        startActivity(in2);
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
}