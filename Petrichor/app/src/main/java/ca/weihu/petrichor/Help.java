package ca.weihu.petrichor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

public class Help extends AppCompatActivityMod {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }

    public void onAboutButton(View view) {
        Intent in = new Intent(getApplicationContext(), HelpAbout.class);
        startActivity(in);
    }

    public void onHowToUseButton(View view) {
        Intent in = new Intent(getApplicationContext(), HelpHowToUse.class);
        startActivity(in);
    }
}