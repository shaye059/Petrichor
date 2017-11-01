package ca.weihu.petrichor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Submit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todaySubmitted);
    }

    public void onSubmit(View view) {
//Application Context and Activity
        Intent intent = new Intent(getApplicationContext(), SameDayPastYear.class);
        startActivityForResult (intent,0);
    }
}
