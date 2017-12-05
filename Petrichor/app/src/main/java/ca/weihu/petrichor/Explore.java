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

    public void onBtnThisWeek(View view) {
        Intent in = new Intent(getApplicationContext(), ExploreWeek.class);
        startActivity(in);
    }
    public void onBtnThisMonth(View view) {
        Intent in = new Intent(getApplicationContext(), ExploreMonth.class);
        startActivity(in);
    }
    public void onBtnThisYear(View view) {
        Intent in = new Intent(getApplicationContext(), ExploreYear.class);
        startActivity(in);
    }
    public void onBtnScrollBack(View view) {
        Intent in = new Intent(getApplicationContext(), ScrollBack.class);
        startActivity(in);
    }
    public void onBtnTodayInThePast(View view) {
        Intent in = new Intent(getApplicationContext(), ExploreTodayInThePast.class);
        startActivity(in);
    }
    public void onRandomButton(View view) {
        Intent in = new Intent(getApplicationContext(), ExploreVisitARandomDay.class);
        startActivity(in);
    }
    public void onBtnBack(View view) {
        onBackPressed();
    }







    public void onTimePeriodButton(View view) {
        Intent in = new Intent(getApplicationContext(), TimePeriodActivity.class);
        startActivity(in);
    }
    public void OnHighlightsButton(View view) {
        Intent in = new Intent(getApplicationContext(), HighlightActivity.class);
        startActivity(in);
    }
    public void OnPastButton(View view) {
        Intent in = new Intent(getApplicationContext(), Past.class);
        startActivity(in);
    }
    public void OnSameDayInPastButton(View view) {
        Intent in = new Intent(getApplicationContext(), ExploreTodayInThePast.class);
        startActivity(in);
    }


    public void onTaggedMemories(View view) {
    }
}
