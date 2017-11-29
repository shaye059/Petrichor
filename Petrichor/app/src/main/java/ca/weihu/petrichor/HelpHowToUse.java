package ca.weihu.petrichor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HelpHowToUse extends AppCompatActivityMod {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_how_to_use);
    }

    public void conBtnBak(View view) {
        onBackPressed();
    }
}
