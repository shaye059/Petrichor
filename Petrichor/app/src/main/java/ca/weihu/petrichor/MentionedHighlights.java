package ca.weihu.petrichor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class MentionedHighlights extends AppCompatActivity implements HighlightCollection{

/*    @Override
    public Highlight[] returnHighlight() {
        return new Highlight[0];
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mentionedhighlights);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        ListView list=(ListView)findViewById(R.id.listMentionedHighlights);

        ArrayList<String> mentionedHighlight =new ArrayList<String>();

        mentionedHighlight.add("bear@bear.ca - Went Shopping");
        mentionedHighlight.add("ronaldo@soccer.com - Played Soccer");
        mentionedHighlight.add("rodriguez@bayern.com - Won Champions League");

// Now create adapter

        MyDeleteAdapter adapter=new MyDeleteAdapter(this, mentionedHighlight);

// NOw Set This Adapter to listview
        list.setAdapter(adapter);


    } public void onBtnBack(View view) {
        onBackPressed();
    }

}