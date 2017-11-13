package ca.weihu.petrichor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class  MainActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void OnTodayButton(View view) {
        Intent in = new Intent(getApplicationContext(), Today.class);
        startActivity(in);
    }

    public void OnExploreButton(View view) {
        Intent intent = new Intent(getApplicationContext(), Explore.class);
        startActivity(intent);
    }
}
