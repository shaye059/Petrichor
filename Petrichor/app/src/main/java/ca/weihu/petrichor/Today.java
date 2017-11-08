package ca.weihu.petrichor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Today extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_today);
    }

    public void onSubmit(View view) {
//Application Context and Activity
        Intent intent = new Intent(getApplicationContext(), Submit.class);
        startActivityForResult (intent,0);
    }

    public void onShare(View view) {
//Application Context and Activity
        Intent intent = new Intent(getApplicationContext(), Share.class);
        startActivityForResult (intent,0);
    }
}
