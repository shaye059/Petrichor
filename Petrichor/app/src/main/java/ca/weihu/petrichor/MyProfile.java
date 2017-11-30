package ca.weihu.petrichor;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.*;
import com.google.firebase.database.ValueEventListener;

public class MyProfile extends AppCompatActivity {

    private TextView textView;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = mAuth.getCurrentUser();
    private String userID = user.getUid();
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference accountsRef = database.getReference("accounts");
    //private DatabaseReference nameRef = accountsRef.child(userID);
    private String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        username = user.getEmail();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);


        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);


        textView = (TextView) findViewById(R.id.emailView);

        updateName();
    }




    public void onUpdateBtn(View view) {
        //updateProfile();
        Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();

    }

    public void updateName(){

        textView.setText(username);
    }

    /*public void updateProfile(){
        textView = (EditText) findViewById(R.id.textView);
        final String name = textView.getText().toString().trim();

        accountsRef.child(userID).child("name").setValue(name);

        updateName();
    }*/

    public void onBtnBack(View view) {
        onBackPressed();
    }
}
