package ca.weihu.petrichor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewFriend extends AppCompatActivity {
    private String thisEmail;
    private String thisName;
    private TextView friendName;
    private DatabaseReference userRef;
    private Account thisAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_friend);

        hideSystemUI();

        Bundle bundle = getIntent().getExtras();
        thisEmail = bundle.getString("userEmail");
        thisName = bundle.getString("userName");

        friendName = (TextView) findViewById(R.id.textViewFriend);




        userRef = FirebaseDatabase.getInstance().getReference().child("Account");


        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot accountSnap : dataSnapshot.getChildren()) {

                if(accountSnap.child("username").getValue().equals((thisEmail))){
                    thisAccount = accountSnap.getValue(Account.class);
                    friendName.setText(thisAccount.getname());
                }
            }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    public void onBtnHighlights(View view){
        Intent in = new Intent(getApplicationContext(),HighlightActivity.class);
        in.putExtra("userEmail",thisEmail );
        startActivity(in);
    }


    public void onBtnScrollBack(View view){
        Intent in = new Intent(getApplicationContext(),ScrollBack.class);
        in.putExtra("userEmail",thisEmail );
        startActivity(in);
    }

    public void onBtnRandomDay(View view){
        Intent in = new Intent(getApplicationContext(),ExploreVisitARandomDay.class);
        in.putExtra("userEmail",thisEmail );
        startActivity(in);
    }

    public void onBtnTodayInThePast(View view){
        Intent in = new Intent(getApplicationContext(),ExploreTodayInThePast.class);
        in.putExtra("userEmail",thisEmail );
        startActivity(in);
    }

    public void onBtnBack(View view) {
        onBackPressed();
    }



    // hides status bar and navbar
    private void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}
