package ca.weihu.petrichor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ExploreTodayInThePast extends AppCompatActivity  {

    private List<Highlight> highlights;
    private ListView listViewHighlights;
    private DatabaseReference userRef;
    private String thisEmail;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_today_in_the_past);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        Bundle bundle = getIntent().getExtras();
        thisEmail = bundle.getString("userEmail");
        listViewHighlights = (ListView) findViewById(R.id.listViewTodayInThePast);
        mAuth = FirebaseAuth.getInstance().getInstance();
        user = mAuth.getCurrentUser();
        userID = user.getEmail();
        highlights = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();

        userRef = FirebaseDatabase.getInstance().getReference().child("Account");

        userRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot accountSnap : dataSnapshot.getChildren()) {

                    if (accountSnap.child("username").getValue().equals((thisEmail))) {

                        Highlight none;

                        if(thisEmail.equals(userID)) {
                            none = new Highlight(null, "Submit your highlights today and"
                                    + " next year this section won't be empty!");
                        }
                        else{
                            none = new Highlight(null, "Tell your friend to submit a highlight today and"
                                    + " next year this section won't be empty!");
                        }

                        highlights.clear();

                        try{
                        for (DataSnapshot postSnapshot : accountSnap.child("highlights").getChildren()) {

                            Highlight highlight = postSnapshot.getValue(Highlight.class);

                            // if not same year but same month and day... add to list
                            if ((Time.currentYear().compareTo(
                                    Time.convertToYear(highlight.getId())) != 0)

                                    && (Time.currentMonth().compareTo(
                                    Time.convertToMonth(highlight.getId())) == 0)

                                    && (Time.currentDay().compareTo(
                                    Time.convertToDay(highlight.getId())) == 0)) {

                                highlights.add(highlight);
                            }}}
                            catch(java.lang.IllegalArgumentException exception1){
                                highlights.add(none);
                            }

                        HighlightListAdapter highlightsAdapter =
                                new HighlightListAdapter(ExploreTodayInThePast.this, highlights,
                                        HighlightListAdapter.hListPurpose.SCROLLBACK, thisEmail,userID);

                        listViewHighlights.setAdapter(highlightsAdapter);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ExploreTodayInThePast.this,
                        "Error retrieving highlights.", Toast.LENGTH_SHORT);
            }
        });
    }

    public void onBtnBack(View view) {
        onBackPressed();
    }
}
