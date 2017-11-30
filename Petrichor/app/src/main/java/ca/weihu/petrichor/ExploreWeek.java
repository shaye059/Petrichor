package ca.weihu.petrichor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ExploreWeek extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_explore_week);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);


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
    }


    public void onBtnBack(View view) {
        onBackPressed();
    }
}
