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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HighlightList extends AppCompatActivity {

    private DatabaseReference dbRefUser;
    private DatabaseReference dbRefUserHighlights;
    private String thisEmail;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String userID;


    List<Highlight> highlights;
    ListView listViewHighlights;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_highlight_list);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        Bundle bundle = getIntent().getExtras();
        thisEmail = bundle.getString("userEmail");

        mAuth = FirebaseAuth.getInstance().getInstance();
        user = mAuth.getCurrentUser();
        userID = user.getUid();

        dbRefUser = Account.getDbRefUser();
        dbRefUserHighlights = Account.getDbRefUserHighlights();


        listViewHighlights = (ListView) findViewById(R.id.listViewHighlights);

        highlights = new ArrayList<>();
    }

    @Override
    protected void onStart() {

        super.onStart();

        Toast.makeText(HighlightList.this, "working", Toast.LENGTH_SHORT);

        dbRefUserHighlights.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // empty array containing highlights
                highlights.clear();

                // iterate through each child (each Highlight stored in the user's Account)_
                // and store in the emptied array
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    Highlight highlight = postSnapshot.getValue(Highlight.class);
                    highlights.add(highlight);
                }

                // create adapter
                HighlightListAdapter highlightsAdapter =
                        new HighlightListAdapter(HighlightList.this, highlights,
                                                    HighlightListAdapter.hListPurpose.WEEK, thisEmail,userID);
                listViewHighlights.setAdapter(highlightsAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(HighlightList.this, "Error retrieving highlights.",
                        Toast.LENGTH_SHORT);
            }
        });
    }
}
