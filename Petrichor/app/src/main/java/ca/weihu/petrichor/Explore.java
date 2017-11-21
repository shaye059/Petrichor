package ca.weihu.petrichor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Explore extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    public void OnWeekButton(View view) {
        Intent in = new Intent(getApplicationContext(), Week.class);
        startActivity(in);
    }
    public void OnMonthButton(View view) {
        Intent in = new Intent(getApplicationContext(), Month.class);
        startActivity(in);
    }
    public void OnYearButton(View view) {
        Intent in = new Intent(getApplicationContext(), Year.class);
        startActivity(in);
    }
    public void OnSameDayInPastButton(View view) {
        Intent in = new Intent(getApplicationContext(), SameDayPastYear.class);
        startActivity(in);
    }
    public void OnRandomButton(View view) {
        Intent in = new Intent(getApplicationContext(), Random.class);
        startActivity(in);
    }

}
