package ca.weihu.petrichor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by divya on 11/10/2017.
 */

public class Share extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
    }
    public void onTaggedMemories(View view) {
        Intent in = new Intent(getApplicationContext(), SharedMemory.class);
        startActivity(in);
    }}
