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

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.*;


public class Today extends AppCompatActivity {

    private RelativeLayout relLayout = null;
    private DatabaseReference databaseReference;
    private Button buttonSubmit1;
    private EditText editTextH1;
    private EditText editTextH2;
    private EditText editTextH3;
    private FirebaseAuth firebaseAuth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_today);getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);


        // code to hide keyboard when relative layout is touched

        relLayout = (RelativeLayout) findViewById(R.id.todayRelLay);

        relLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event){

                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                hideSystemUI();

                return true;
            }
        });
        firebaseAuth=FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this, AccountLogin.class));
        }
        databaseReference = FirebaseDatabase.getInstance().getReference();

        editTextH1 = (EditText) findViewById(R.id.editText);
        editTextH2 = (EditText) findViewById(R.id.editText3);
        editTextH3 = (EditText) findViewById(R.id.editText4);
        buttonSubmit1 = (Button)findViewById(R.id.button);

        FirebaseUser user = firebaseAuth.getCurrentUser();
    }
    public void OnImageButton(View view) {
        Intent in = new Intent(getApplicationContext(), Share.class);
        startActivity(in);
    }
    private void saveUserData(){
        String highlight1 =editTextH1.getText().toString().trim();
        String highlight2 =editTextH2.getText().toString().trim();
        String highlight3 =editTextH3.getText().toString().trim();

        StoreHighlightDay highlights = new StoreHighlightDay(highlight1,highlight2,highlight3);

        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference.child(user.getUid()).push().setValue(highlights);
        Toast.makeText(this,"Information Saved...", Toast.LENGTH_LONG).show();
    }

    public void onSubmitData(View view){
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
