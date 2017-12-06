package ca.weihu.petrichor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ExploreWeek extends AppCompatActivity {

    /*==============================================================================================
        V A R I A B L E S
    ==============================================================================================*/

    private DatabaseReference dbRefUser;
//    private DatabaseReference dbRefUserHighlights;

    private List<Highlight> highlights;
    private ListView listViewHighlights;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_week);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        ((TextView) findViewById(R.id.textViewWeek)).setText("Week " + Time.currentWeek());

        dbRefUser = Account.getDbRefUser();
//        dbRefUserHighlights = Account.getDbRefUserHighlights();


        listViewHighlights = (ListView) findViewById(R.id.listViewThisWeek);

        highlights = new ArrayList<>();
    }

    @Override
    protected void onStart() {

        super.onStart();


        Log.d("\n\n\nExploreWeek.java", "onStart Check");
        Toast.makeText(ExploreWeek.this, "working", Toast.LENGTH_SHORT);

        // when have time implement database structure to use startAt(...).limitToFirst(21)
        Account.getDbRefUserTPCs().child("weeks/" + Time.currentWeek() + "/highlights")
                .orderByValue().addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                highlights.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    Highlight highlight = postSnapshot.getValue(Highlight.class);

                    /*  show only highlights of this week
                        Note: would not have to use this for loop if Firebase was better implemented
                        but not enough time
                     */
                    if ( (Time.currentYear().compareTo(
                            Time.convertToYear(highlight.getId()) ) == 0)

                            && (Time.currentWeek().compareTo(
                                    Time.convertToWeek(highlight.getId())) == 0) ) {

                        highlights.add(highlight);
                    }
                }

                HighlightListAdapter highlightsAdapter =
                        new HighlightListAdapter(ExploreWeek.this, highlights,
                                                    HighlightListAdapter.hListPurpose.WEEK);

                listViewHighlights.setAdapter(highlightsAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ExploreWeek.this, "Error retrieving highlights.",
                        Toast.LENGTH_SHORT);
            }
        });
    }

    public void onBtnBack(View view) {
        onBackPressed();
    }


/*

        ListView list = findViewById(R.id.highlightWeekList);

        ArrayList<String> exploreWeek = new ArrayList<String>();

        String n1 = getIntent().getExtras().getString("Highlight 1");
        String n2 = getIntent().getExtras().getString("Highlight 2");
        String n3 = getIntent().getExtras().getString("Highlight 3");

        exploreWeek.add("Day 1" + "\n" + "\t" + n1 + "\n" + "\t" + n2 + "\n" +"\t" +n3);
        exploreWeek.add("Day 2");
        exploreWeek.add("Day 3");
        exploreWeek.add("Day 4");
        exploreWeek.add("Day 5");
        exploreWeek.add("Day 6");
        exploreWeek.add("Day 7");


// Now create adapter

        MyAdapter adapter = new MyAdapter(this, exploreWeek);

// NOw Set This Adapter to listview
        list.setAdapter(adapter);
*/
}
