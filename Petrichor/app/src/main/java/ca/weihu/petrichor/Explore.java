package ca.weihu.petrichor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Explore extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_explore);
    }

    public void OnWeekClick(View view) {
//Application Context and Activity
        Intent intent = new Intent(getApplicationContext(), Week.class);
        startActivityForResult (intent,0);
    }

    public void OnMonthClick(View view) {
//Application Context and Activity
        Intent intent = new Intent(getApplicationContext(), Month.class);
        startActivityForResult (intent,0);
    }

    public void OnYearClick(View view) {
//Application Context and Activity
        Intent intent = new Intent(getApplicationContext(), Year.class);
        startActivityForResult (intent,0);
    }

    public void OnRandomClick(View view) {
    //Application Context and Activity
        Intent intent = new Intent(getApplicationContext(), Random.class);
        startActivityForResult (intent,0);
    }

    public void onSameDayPastYearClick(View view) {
//Application Context and Activity
        Intent intent = new Intent(getApplicationContext(), SameDayPastYear.class);
        startActivityForResult (intent,0);
    }
}
