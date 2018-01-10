package ca.weihu.petrichor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import java.util.Random;

public class ExploreVisitARandomDay extends AppCompatActivity {

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
        setContentView(R.layout.activity_explore_visit_a_random_day);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        mAuth = FirebaseAuth.getInstance().getInstance();
        user = mAuth.getCurrentUser();
        userID = user.getEmail();
        Bundle bundle = getIntent().getExtras();
        thisEmail = bundle.getString("userEmail");
        listViewHighlights = (ListView) findViewById(R.id.listViewVisitARandomDay);

        highlights = new ArrayList<>();
    }

    @Override
    protected void onStart() {

        super.onStart();

        roll();
    }

    /*  Note: inefficient code right now to retrieve a random day because it requires to go
        through the records twice (first to count number of highlights, second to choose one)...
        Implement more efficient code when time permits.
    */
    public void roll() {

        userRef = FirebaseDatabase.getInstance().getReference().child("Account");

        userRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot accountSnap : dataSnapshot.getChildren()) {

                    if (accountSnap.child("username").getValue().equals((thisEmail))) {
                        int counter = 0;

                        Random r = new Random();
                        int rNum;

                        highlights.clear();

                        try{
                            for (DataSnapshot postSnapshot : accountSnap.child("highlights").getChildren()) {
                            counter++;
                            Log.d("VisitARandomDay: 1", String.valueOf(counter));
                            }

                        rNum = r.nextInt(counter + 1 - 1) + 1;

                        counter = 0;

                        for (DataSnapshot postSnapshot : accountSnap.child("highlights").getChildren()) {

                            Highlight highlight = postSnapshot.getValue(Highlight.class);

                            counter++;

                            Log.d("VisitARandomDay: 2", String.valueOf(counter));

                            if (counter == rNum) {

                                highlights.add(highlight);
                            }
                        }}catch (java.lang.IllegalArgumentException exception){
                            Highlight none = new Highlight(null, "Sorry, we couldn't find any highlights :(");

                            highlights.add(none);
                        }
                        HighlightListAdapter highlightsAdapter =
                                new HighlightListAdapter(ExploreVisitARandomDay.this, highlights,
                                        HighlightListAdapter.hListPurpose.RANDOMDAY,thisEmail,userID);

                        listViewHighlights.setAdapter(highlightsAdapter);


                    }
                }
            }@Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ExploreVisitARandomDay.this,
                        "Error retrieving highlights.", Toast.LENGTH_SHORT);
            }
        });
    }

    public void onBtnReroll(View view) {
        roll();
    }

    public void onBtnBack(View view) {
        onBackPressed();
    }
}
