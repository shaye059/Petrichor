package ca.weihu.petrichor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class ExploreVisitARandomDay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_explore_visit_a_random_day);getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        ListView list = (ListView) findViewById(R.id.listView);

        String n1 = getIntent().getExtras().getString("Highlight 1");
        String n2 = getIntent().getExtras().getString("Highlight 2");
        String n3 = getIntent().getExtras().getString("Highlight 3");

        ArrayList<String> random = new ArrayList<String>();
        random.add(n1);
        random.add(n2);
        random.add(n3);


// Now create adapter

        MyAdapter adapter = new MyAdapter(this, random);

// NOw Set This Adapter to listview
        list.setAdapter(adapter);
    }

    public void onBtnBack(View view) {
        onBackPressed();
    }
}
