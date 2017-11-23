package ca.weihu.petrichor;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class AccountLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_login);


        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    public void onBtnBack(View view) {
        onBackPressed();
    }
    public void onLogin(View view) {
        Intent in = new Intent(getApplicationContext(), Home.class);
        startActivity(in);
    }
    public void onCreate(View view) {
        Intent in = new Intent(getApplicationContext(), AccountCreate.class);
        startActivity(in);
    }
}