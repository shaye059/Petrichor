package ca.weihu.petrichor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Share extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_friends);
    }

    public void onTaggedMemories(View view) {
//Application Context and Activity
        Intent intent = new Intent(getApplicationContext(), TaggedMemories.class);
        startActivityForResult (intent,0);
    }

}
