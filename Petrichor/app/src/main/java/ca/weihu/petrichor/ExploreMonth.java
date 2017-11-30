package ca.weihu.petrichor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ExploreMonth extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_explore_month);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        ListView list = (ListView) findViewById(R.id.highlightMonthList);

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

    public void onBtnBack(View view) {
        onBackPressed();
    }
    public void OnWeekButton(View view) {
        Intent in = new Intent(getApplicationContext(), ExploreWeek.class);
        startActivity(in);
    }
}
