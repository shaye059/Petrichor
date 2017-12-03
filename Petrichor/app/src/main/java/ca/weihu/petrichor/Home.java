package ca.weihu.petrichor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home extends AppCompatActivity {

    /*==============================================================================================
        V A R I A B L E S
    ==============================================================================================*/

    private DatabaseReference userDBRef;

    private String currentDayOfWeek;

    private DatabaseReference dbRefUsername;

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


        /*------------------------------------------------------------------------------------------
            PROMPTING USER FOR WEEKLY/MONTHLY/YEARLY HIGHLIGHTS SELECTION
        ------------------------------------------------------------------------------------------*/

        userDBRef = FirebaseDatabase.getInstance().getReference(
                FirebaseAuth.getInstance().getCurrentUser().getUid() );

        currentDayOfWeek = Time.currentDayOfWeek();

//        int numOfHighlightsLastWeek

        //if ()

        /*  - user is logged in (activity_home.xml only available after login screen)
            - prompt user to insert highlight of the week/month/year
         */

        // if it's Sunday and last week's total submitted highlights > 3 (of the 21 max), prompt
        if ( currentDayOfWeek == "Sun") {

        }
    }

    @Override
    protected void onStart() {

        super.onStart();

//        userDBRef.child("")


    }


    /*==============================================================================================
        A C T I V I T Y  M E T H O D S
    ==============================================================================================*/

    public void OnTodayButton(View view) {
        Intent in = new Intent(getApplicationContext(), Today.class);
        startActivity(in);
    }

    public void OnExploreButton(View view) {
        Intent intent = new Intent(getApplicationContext(), Explore.class);
        startActivity(intent);
    }
    /*public void onLogin(View view) {
        Intent in = new Intent(getApplicationContext(), Home.class);
        startActivity(in);
    }
    public void onCreate(View view) {
        Intent in = new Intent(getApplicationContext(), AccountCreate.class);
        startActivity(in);
    }

    public void onMyAccountButton(View view) {
        Intent intent = new Intent(getApplicationContext(), AccountLogin.class);
        startActivity(intent);
    }*/

    public void onNavBarButton(View view){

        Intent intent = new Intent(getApplicationContext(), NavBar.class);
        startActivity(intent);
    }

    public void onCreate(View view) {
    }

    public void onLogin(View view) {
    }
}