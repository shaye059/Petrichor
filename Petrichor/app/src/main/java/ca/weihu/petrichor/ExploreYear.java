package ca.weihu.petrichor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class ExploreYear extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_explore_year);getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        ListView list = (ListView) findViewById(R.id.highlightYearList);

        ArrayList<String> exploreYear = new ArrayList<String>();

        exploreYear.add("Month 1");
        exploreYear.add("Month 2");
        exploreYear.add("Month 3");
        exploreYear.add("Month 4");
        exploreYear.add("Month 5");
        exploreYear.add("Month 6");
        exploreYear.add("Month 7");
        exploreYear.add("Month 8");
        exploreYear.add("Month 9");
        exploreYear.add("Month 10");
        exploreYear.add("Month 11");
        exploreYear.add("Month 12");

// Now create adapter

        MyAdapter adapter = new MyAdapter(this, exploreYear);

// NOw Set This Adapter to listview
        list.setAdapter(adapter);
    }

    public void onBtnBack(View view) {
        onBackPressed();
    }
}
