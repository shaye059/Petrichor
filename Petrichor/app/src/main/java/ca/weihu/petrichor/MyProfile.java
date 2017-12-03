package ca.weihu.petrichor;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseUser;

import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.*;
import com.google.firebase.database.ValueEventListener;



public class MyProfile extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String userID;
    private DatabaseReference databaseAccounts;
    private DatabaseReference ref;
    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance().getInstance();
        user = mAuth.getCurrentUser();
        userID = user.getUid();
        setContentView(R.layout.activity_my_profile);
        databaseAccounts = FirebaseDatabase.getInstance().getReference();
        ref = databaseAccounts.child("Account");
        String userEmail;

        TextView textView = (TextView) findViewById(R.id.textViewEmail);
        textView.setText(user.getEmail());
        textView.invalidate();

        setContentView(R.layout.activity_my_profile);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        userEmail = user.getEmail();
        TextView emailView = (TextView) findViewById(R.id.textViewEmail);
        emailView.setText(userEmail);
        emailView.invalidate();
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

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable(){

            @Override
            public void run() {

                if (!account.getname().equals("")) {
                    nameView.setText(account.getname());
                    nameView.setCursorVisible(false);
                } else {
                    nameView.setText(null);
                }
            }
        });

    }

    public void onUpdateBtn(View view) {
        EditText nameView = (EditText) findViewById(R.id.nameEdit);
        final String name = nameView.getText().toString().trim();


        ref.child(userID).child("name").setValue(name);
        Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();

    }
    public void onBtnBack(View view) {
        onBackPressed();
    }
}
