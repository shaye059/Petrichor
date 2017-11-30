package ca.weihu.petrichor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.Query;

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

    /*
    Calendar cal = Calendar.getInstance();
    cal.setTime(mar.getEventDate());
    int day = cal.get(Calendar.DAY_OF_MONTH);
    int month = cal.get(Calendar.MONTH)
    int year = cal.get(Calendar.YEAR)
    int weekCounter;

     */

    private RelativeLayout relLayout = null;

    private Button buttonSubmit1;
    private EditText editTextH1;
    private EditText editTextH2;
    private EditText editTextH3;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = firebaseAuth.getCurrentUser();
    public String userid = user.getUid();
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference();
    private DatabaseReference ref2 = database.getReference("dailyhighlights");
    public String username = user.getEmail();
    public String highlight1, highlight2, highlight3;
    public StoreHighlightDay high;
    public int counter=1;
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    Date today = Calendar.getInstance().getTime();
    String reportDate = df.format(today);


    public Today() {

    }

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
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, AccountLogin.class));
        }

        editTextH1 = (EditText) findViewById(R.id.editText);
        editTextH2 = (EditText) findViewById(R.id.editText3);
        editTextH3 = (EditText) findViewById(R.id.editText4);
        buttonSubmit1 = (Button) findViewById(R.id.button);
    }

    public void OnImageButton(View view) {
        Intent in = new Intent(getApplicationContext(), Friends.class);
        startActivity(in);
    }

    public void saveUserData() {
        highlight1 = editTextH1.getText().toString().trim();
        highlight2 = editTextH2.getText().toString().trim();
        highlight3 = editTextH3.getText().toString().trim();

        StoreHighlightDay h1 = new StoreHighlightDay(highlight1);
        StoreHighlightDay h2 = new StoreHighlightDay(highlight2);
        StoreHighlightDay h3 = new StoreHighlightDay(highlight3);
        //Toast.makeText(this,username, Toast.LENGTH_LONG).show();
        //databaseReference.child("dailyhighlight");
        ref2.child((userid.toString())).child(reportDate).child((String.valueOf(counter))).push().setValue(h1);
        ref2.child((userid.toString())).child(reportDate).child((String.valueOf(counter))).push().setValue(h2);
        ref2.child((userid.toString())).child(reportDate).child((String.valueOf(counter))).push().setValue(h3);
        counter++;
    }

    public void onSubmitData(View view) {
        saveUserData();
        /*Query childq= ref2.child(userid).child(reportDate).orderByChild(counter).equalTo(counter);
        childq.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot single: dataSnapshot.getChildren()){
                    String high = single.getValue(StoreHighlightDay.class).toString();
                    editTextH1.setText(high);
                    editTextH2.setText(high);
                    editTextH3.setText(high);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
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