package ca.weihu.petrichor;

import android.content.Intent;
import android.os.Bundle;
import android.preference.EditTextPreference;
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

    private RelativeLayout relLayout = null;

    private Button buttonSubmit1;
    public EditText editTextH1, editTextH2, editTextH3;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = firebaseAuth.getCurrentUser();
    public String userid = user.getUid();
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference();
    public DatabaseReference ref2 = database.getReference("dailyhighlights");
    public String username = user.getEmail();
    public String highlight1= editTextH1.getText().toString().trim();
    public String highlight2= editTextH2.getText().toString().trim();
    public String highlight3= editTextH3.getText().toString().trim();
    public StoreHighlightDay high;
    DateFormat df = DateFormat.getDateInstance();
    String reportDate = (df.format(new Date())).toString();
    public int counter = 0;

    public Today() {

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
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

        //Firebase Stuff
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) { //check if user is logged in
            finish();
            startActivity(new Intent(this, AccountLogin.class));
        }

        editTextH1 = findViewById(R.id.editText);
        editTextH2 = findViewById(R.id.editText3);
        editTextH3 = findViewById(R.id.editText4);
        buttonSubmit1 = (Button) findViewById(R.id.button);
        test();
    }

    public void OnImageButton(View view) {
        Intent in = new Intent(getApplicationContext(), Friends.class);
        startActivity(in);
    }

    public void saveUserData(String high1, String high2, String high3) {

        String h1 = highlight1;
        String h2 = highlight2;
        String h3 = highlight3;

        ref2.child((userid.toString())).child(reportDate).child("Highlight 1").setValue(h1);
        ref2.child((userid.toString())).child(reportDate).child("Highlight 2").setValue(h2);
        ref2.child((userid.toString())).child(reportDate).child("Highlight 3").setValue(h3);
        Toast.makeText(this,"Information Saved", Toast.LENGTH_LONG).show();
    }

    public void onSubmitData(View view) {
        saveUserData(highlight1,highlight2,highlight3);
    }


    public void test(){
        ref2.child(userid).child(reportDate).addValueEventListener(new ValueEventListener() {

            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {

               String h1 = (String) dataSnapshot.child("Highlight 1").getValue();
               String h2 = (String) dataSnapshot.child("Highlight 2").getValue();
               String h3 = (String) dataSnapshot.child("Highlight 3").getValue();

               editTextH1 = findViewById(R.id.editText);
               editTextH2 = findViewById(R.id.editText3);
               editTextH3 = findViewById(R.id.editText4);
               editTextH1.setHint((CharSequence) h1);
               editTextH2.setHint((CharSequence) h2);
               editTextH3.setHint((CharSequence) h3);

                counter = counter+1;
                if (counter!=0&&((h1=="")||(h2=="")||(h3==""))){
                    
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"database fuckup", Toast.LENGTH_LONG).show();
            }
        });
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