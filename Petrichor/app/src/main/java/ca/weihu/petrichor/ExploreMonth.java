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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ExploreMonth extends AppCompatActivity {

    private List<Highlight> highlights;
    private ListView listViewHighlights;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_month);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        ((TextView) findViewById(R.id.textViewMonth)).setText(Time.currentMonthFullName());


/*  ??? -T.N.
        ListView list = (ListView) findViewById(R.id.highlightMonthList);
*/

        listViewHighlights = (ListView) findViewById(R.id.listViewThisMonth);

        highlights = new ArrayList<>();
    }

    @Override
    protected void onStart() {

        super.onStart();

        Account.getDbRefUserTPCs().child("months/" + Time.currentMonth() + "/highlights")
                .orderByValue().addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                highlights.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    Highlight highlight = postSnapshot.getValue(Highlight.class);

                    // show only highlights of this month
                    if ( (Time.currentYear().compareTo(
                            Time.convertToYear(highlight.getId()) ) == 0)

                            && (Time.currentMonth().compareTo(
                            Time.convertToMonth(highlight.getId()) ) == 0) ) {

                        highlights.add(highlight);
                    }
                }

                HighlightListAdapter highlightsAdapter =
                        new HighlightListAdapter(ExploreMonth.this, highlights,
                                HighlightListAdapter.hListPurpose.MONTH);

                listViewHighlights.setAdapter(highlightsAdapter);
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


/*  ??? -T.N.
        ArrayList<String> exploreMonth = new ArrayList<String>();

        exploreMonth.add("Week 1");
        exploreMonth.add("Week 2");
        exploreMonth.add("Week 3");
        exploreMonth.add("Week 4");

// Now create adapter

        MyAdapter adapter = new MyAdapter(this, exploreMonth);

// NOw Set This Adapter to listview
        list.setAdapter(adapter);

    }


    public void OnWeekButton(View view) {
        Intent in = new Intent(getApplicationContext(), ExploreWeek.class);
        startActivity(in);
    }
*/
}
