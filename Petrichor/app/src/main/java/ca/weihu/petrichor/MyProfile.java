package ca.weihu.petrichor;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.google.firebase.auth.FirebaseUser;

import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.*;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;


public class MyProfile extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String userID;
    private DatabaseReference databaseAccounts;
    private DatabaseReference ref;
    private Account account;
    private EditText editText;
    private RelativeLayout relLayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance().getInstance();
        user = mAuth.getCurrentUser();
        userID = user.getUid();
        setContentView(R.layout.activity_my_profile);
        databaseAccounts = FirebaseDatabase.getInstance().getReference();
        ref = databaseAccounts.child("Account");

        setContentView(R.layout.activity_my_profile);
        editText = (EditText)findViewById(R.id.nameEdit);
        relLayout = (RelativeLayout) findViewById(R.id.my_profile_layout);

        relLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                editText.setCursorVisible(false);

                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                hideSystemUI();

                return true;
            }
        });

        // Update the EditText so it won't popup Android's own keyboard, since I have my own.
        editText.setCursorVisible(false);
        editText.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.onTouchEvent(event);
                editText.setCursorVisible(true);
                return true;
            }
        });
    }

    public void onStart(){
        super.onStart();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
                updateProfile();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
    @Override
    protected void onRestart() {
        super.onRestart();
        hideSystemUI();
    }

    private void showData(DataSnapshot  datasnapshot){
        Iterable<DataSnapshot> children = datasnapshot.getChildren();

        for(DataSnapshot accountC : children) {
            if (accountC.getKey().equals(userID)) {
                account = accountC.getValue(Account.class);
            }
        }

    }


    public void updateProfile() {

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        final TextView nameView = (TextView) findViewById(R.id.nameEdit);
        final TextView emailView = (TextView) findViewById(R.id.textViewEmail);
        final TextView friendsView = (TextView) findViewById(R.id.textViewFriends);


        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable(){

            @Override
            public void run() {

                if (!account.getname().equals("")) {
                    nameView.setText(account.getname());
                    emailView.setText(account.getusername());
                    friendsView.setText(String.valueOf(account.getnumfriends()));
                } else {
                    nameView.setText(null);
                    emailView.setText(account.getusername());
                    friendsView.setText(String.valueOf(account.getnumfriends()));
                }
            }
        });

    }

    /*Method that updates the name of the viewer in the database to the name entered by the user in
    the edit text. Refreshes the activity to remove the cursor again. Displays a toast notifying
    the user that the name has been updated.*/
    public void onUpdateBtn(View view) {
        EditText nameView = (EditText) findViewById(R.id.nameEdit);
        final String name = nameView.getText().toString().trim();
        ref.child(userID).child("name").setValue(name);
        editText.setCursorVisible(false);
        Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();

    }
    public void onBtnBack(View view) {
        onBackPressed();
    }

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
