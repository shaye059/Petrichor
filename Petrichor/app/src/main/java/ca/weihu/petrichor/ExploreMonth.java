package ca.weihu.petrichor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

public class ExploreMonth extends AppCompatActivity {

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
        setContentView(R.layout.activity_explore_month);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        Bundle bundle = getIntent().getExtras();
        thisEmail = bundle.getString("userEmail");
        ((TextView) findViewById(R.id.textViewMonth)).setText(Time.currentMonthFullName());

        mAuth = FirebaseAuth.getInstance().getInstance();
        user = mAuth.getCurrentUser();
        userID = user.getUid();
        listViewHighlights = (ListView) findViewById(R.id.listViewThisMonth);

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

                    if (accountSnap.child("username").getValue().equals((thisEmail))) {
                        DataSnapshot highlightSnap = accountSnap.child("timePeriodCollections")
                                .child("months").child(String.valueOf(Time.currentMonth())).child("highlights");

                        highlights.clear();

                        for (DataSnapshot postSnapshot : highlightSnap.getChildren()) {

                            Highlight highlight = postSnapshot.getValue(Highlight.class);

                            //Show only highlights from this month
                            if ((Time.currentYear().compareTo(
                                    Time.convertToYear(highlight.getId())) == 0)

                                    && (Time.currentMonth().compareTo(
                                    Time.convertToMonth(highlight.getId())) == 0)) {

                                highlights.add(highlight);
                            }
                        }

                        HighlightListAdapter highlightsAdapter =
                                new HighlightListAdapter(ExploreMonth.this, highlights,
                                        HighlightListAdapter.hListPurpose.WEEK, thisEmail,userID);

                        listViewHighlights.setAdapter(highlightsAdapter);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ExploreMonth.this, "Error retrieving highlights.",
                        Toast.LENGTH_SHORT);
            }
        });

    }

    public void onBtnBack(View view) {
        onBackPressed();
    }

}
