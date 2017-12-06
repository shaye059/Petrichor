package ca.weihu.petrichor;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    /*==============================================================================================
        A C T I V I T Y  L I F E C Y C L E  M E T H O D S
    ==============================================================================================*/

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }


    /*==============================================================================================
        A C T I V I T Y  M E T H O D S
    ==============================================================================================*/

    public void onTodayButton(View view) {
        Intent in = new Intent(getApplicationContext(), Today.class);
        startActivity(in);
    }

    public void onExploreButton(View view) {
        Intent intent = new Intent(getApplicationContext(), Explore.class);
        startActivity(intent);
    }

    public void onNavBarButton(View view){

        Intent intent = new Intent(getApplicationContext(), NavBar.class);
        startActivity(intent);
    }
}