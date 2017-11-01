package ca.weihu.petrichor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class  MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onToday(View view) {
//Application Context and Activity
        Intent intent = new Intent(getApplicationContext(), Today.class);
        startActivityForResult (intent,0);
    }

    public void onExplore(View view) {
//Application Context and Activity
        Intent intent = new Intent(getApplicationContext(), Explore.class);
        startActivityForResult (intent,0);
    }

}

