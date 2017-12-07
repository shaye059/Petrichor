package ca.weihu.petrichor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
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

public class ExploreYear extends AppCompatActivity {

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

        setContentView(R.layout.activity_explore_year);getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        Bundle bundle = getIntent().getExtras();
        thisEmail = bundle.getString("userEmail");

        mAuth = FirebaseAuth.getInstance().getInstance();
        user = mAuth.getCurrentUser();
        userID = user.getEmail();

        ((TextView) findViewById(R.id.textViewYear)).setText(Time.currentYear());

        listViewHighlights = (ListView) findViewById(R.id.listViewThisYear);

        highlights = new ArrayList<>();
    }

    @Override
    protected void onStart() {

        super.onStart();

        userRef = FirebaseDatabase.getInstance().getReference().child("Account");


        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot accountSnap : dataSnapshot.getChildren()) {

                    if(accountSnap.child("username").getValue().equals((thisEmail))){
                        DataSnapshot highlightSnap = accountSnap.child("timePeriodCollections")
                                .child("years").child(String.valueOf(Time.currentYear())).child("highlights");

                        highlights.clear();

                        for (DataSnapshot postSnapshot : highlightSnap.getChildren()) {

                            Highlight highlight = postSnapshot.getValue(Highlight.class);


                            // show only highlights of this year
                            if ( (Time.currentYear().compareTo(
                                    Time.convertToYear(highlight.getId()) ) == 0) ) {

                                highlights.add(highlight);
                            }
                        }

                        HighlightListAdapter highlightsAdapter =
                                new HighlightListAdapter(ExploreYear.this, highlights,
                                        HighlightListAdapter.hListPurpose.YEAR, thisEmail,userID);

                        listViewHighlights.setAdapter(highlightsAdapter);
                    }
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ExploreYear.this, "Error retrieving highlights.",
                        Toast.LENGTH_SHORT);
            }
        });

    }

    public void onBtnBack(View view) {
        onBackPressed();
    }
}