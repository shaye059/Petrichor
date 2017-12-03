package ca.weihu.petrichor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by divya on 11/10/2017.
 */

public class Friends extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_friends);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);


        ListView list=(ListView)findViewById(R.id.listFriends);

        ArrayList<String> friends =new ArrayList<String>();

        friends.add("realmadrid@soccer.com");
        friends.add("ronaldo@realmadrid.com");
        friends.add("rodriguez@bayern.com");
        friends.add("cuadrado@juventus.com");
        friends.add("falcao@monaco.com");

// Now create adapter
        MyShareAdapter adapter=new MyShareAdapter(this, friends);

// NOw Set This Adapter to listview
        list.setAdapter(adapter);
    }


    public void onTaggedMemories(View view) {
        Intent in = new Intent(getApplicationContext(), MentionedHighlights.class);
        startActivity(in);
    }

    public void onBtnBack(View view) {
        onBackPressed();
    }

    public void onBtnAdd(View view){
        Intent intent = new Intent(getApplicationContext(), AddFriend.class);
        startActivity(intent);
    }
}
