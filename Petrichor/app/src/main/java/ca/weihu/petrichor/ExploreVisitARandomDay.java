package ca.weihu.petrichor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ExploreVisitARandomDay extends AppCompatActivity {

    private List<Highlight> highlights;
    private ListView listViewHighlights;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_visit_a_random_day);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        listViewHighlights = (ListView) findViewById(R.id.listViewVisitARandomDay);

        highlights = new ArrayList<>();
    }

    @Override
    protected void onStart() {

        super.onStart();

        /*  Note: inefficient code right now to retrieve a random day because it requires to go
            through the records twice (first to count number of highlights, second to choose one)...
            Implement more efficient code when time permits.
           */

        Account.getDbRefUserHighlights()
                .orderByValue().addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int counter = 0;

                Random r = new Random();
                int rNum;

                highlights.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    counter++;
                    Log.d("VisitARandomDay: 1", String.valueOf(counter));
                }

                rNum = r.nextInt(counter + 1 - 1) + 1;

                counter = 0;

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    Highlight highlight = postSnapshot.getValue(Highlight.class);

                    counter++;

                    Log.d("VisitARandomDay: 2", String.valueOf(counter));

                    if ( counter == rNum ) {

                        highlights.add(highlight);
                    }
                }

                HighlightListAdapter highlightsAdapter =
                        new HighlightListAdapter(ExploreVisitARandomDay.this, highlights,
                                HighlightListAdapter.hListPurpose.RANDOMDAY);

                listViewHighlights.setAdapter(highlightsAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ExploreVisitARandomDay.this,
                        "Error retrieving highlights.", Toast.LENGTH_SHORT);
            }
        });
    }

    public void onBtnBack(View view) {
        onBackPressed();
    }
}
