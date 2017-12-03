package ca.weihu.petrichor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Home extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);


        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    public void OnTodayButton(View view) {
        Intent in = new Intent(getApplicationContext(), Today.class);
        startActivity(in);
    }

    public void OnExploreButton(View view) {
        Intent intent = new Intent(getApplicationContext(), Explore.class);
        startActivity(intent);
    }

    public void onNavBarButton(View view){
        Intent intent = new Intent(getApplicationContext(), NavBar.class);
        startActivity(intent);
    }

    public void onCreate(View view) {
    }

    public void onLogin(View view) {
    }
}