package ca.weihu.petrichor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NavBar extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /*==============================================================================================
        V A R I A B L E S
    ==============================================================================================*/

    private DatabaseReference dbRefUser;

    private String currentDayOfWeek;

    List<Highlight> highlights;
    ListView listViewHighlights;


    /*==============================================================================================
        A C T I V I T Y  L I F E C Y C L E  M E T H O D S
    ==============================================================================================*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_bar);

// please clean this up (start) --------------------------------------------------------------------

        /*getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);*/

 /*       Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();*/

// please clean this up (end) ----------------------------------------------------------------------

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        dbRefUser = Account.getDbRefUser();


        // Setting username/email into TextView (R.id.navBarUsername) on the navbar drawer

        dbRefUser.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // if the user set a name then display it
                if (dataSnapshot.getValue(Account.class).getName() != null) {

                    displayUsernameInDrawer(dataSnapshot.getValue(Account.class).getName());

                    // if the user DID NOT set a name then display user's email
                } else if (dataSnapshot.getValue(Account.class).getUsername() != null) {

                    displayUsernameInDrawer(dataSnapshot.getValue(Account.class).getUsername());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(getApplicationContext(),
                        "The read failed: " + databaseError.getCode(),
                        Toast.LENGTH_SHORT).show();
            }
        });


        /*------------------------------------------------------------------------------------------
            PROMPTING USER FOR WEEKLY/MONTHLY/YEARLY HIGHLIGHTS SELECTION
        ------------------------------------------------------------------------------------------*/

        // I N I T I A L I Z I N G

        // assign ListView to a variable and create an ADS to hold the highlights
        listViewHighlights = (ListView) findViewById(R.id.listViewHighlights);
        highlights = new ArrayList<>();

        currentDayOfWeek = Time.currentDayOfWeek();



        /*  - user is logged in (activity_home.xml only available after login screen)
            - prompt user to insert highlight of the week/month/year
         */


        // P R O M P T I N G  W E E K L Y
        /*  Note: be careful of partial weeks (1st half of week can be the end of a month and 2nd
            half of week can be the start of a new month)
        */

        // currentDay() < 8 means it's a partial week

        /*// if it's Sunday and last week's total submitted highlights > 3 (of the 21 max), prompt
        if ( currentDayOfWeek.equals("Sun") ) {

            dbRefUser.orderByChild("Highlight/TimePeriodYYYY").limitToFirst(21)
                    .addChildEventListener(new ValueEventListener())



            // equal to or less than 3: add all of them
        } else if (true) {



        }*/



        // P R O M P T I N G  M O N T H L Y


        // P R O M P T I N G  Y E A R L Y

    }


    /*==============================================================================================
        D A T A B A S E - R E L A T E D  M E T H O D S
    ==============================================================================================*/

    public void missedADay() {
        Intent intent = new Intent(getApplicationContext(), MissedADay.class);
        startActivity(intent);
    }

// NOT FINISHED: implementation
    public void promptChooseWeeklyH() {

        Toast.makeText(this, "opening Highlights", Toast.LENGTH_SHORT).show();

        /*dbRefUser.child("Highlight").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                highlights.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    Highlight highlight = postSnapshot.getValue(Highlight.class);
                    highlights.add(highlight);

                    Log.d("\n\n\nhighlight", highlight.getDescription());
                }

                HighlightListAdapter highlightsAdapter =
                        new HighlightListAdapter(NavBar.this, highlights);
                listViewHighlights.setAdapter(highlightsAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(NavBar.this, "Error retrieving highlights.",
                        Toast.LENGTH_SHORT);
            }
        });*/

        Intent in = new Intent(getApplicationContext(), HighlightList.class);
        startActivity(in);
    }


    // bugged: once logged out then logged in then the TextView shows the hint and not name...
    private void displayUsernameInDrawer(String username ) {

        // load username/email into R.id.navBarUsername

        TextView navBarUsername = (TextView) findViewById(R.id.navBarUsername);

        if (navBarUsername == null) {
            Toast.makeText(getApplicationContext(), "null", Toast.LENGTH_SHORT).show();
        }


        /*  without this if... when logout via navbar: java.lang.NullPointerException: Attempt to
            invoke virtual method 'void android.widget.TextView.setText(java.lang.CharSequence)' on
            a null object reference
         */
        if (navBarUsername != null) {
            navBarUsername.setText(username);
        }
    }


    /*==============================================================================================
        A C T I V I T Y  M E T H O D S
    ==============================================================================================*/

    public void prompt(View view) {
        //Toast.makeText(this, "prompt", Toast.LENGTH_SHORT).show();
        promptChooseWeeklyH();
    }

    public void onBtnMissedADay(View view) {
        missedADay();
    }

    public void onTodayButton(View view) {
        Intent in = new Intent(getApplicationContext(), Today.class);
        startActivity(in);
    }

    public void onExploreButton(View view) {
        Intent intent = new Intent(getApplicationContext(), Explore.class);
        startActivity(intent);
    }


    public void onNavBarButton(View view){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.openDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent in = new Intent(getApplicationContext(), MyProfile.class);
            startActivity(in);
        } else if (id == R.id.nav_friends) {
            Intent in = new Intent(getApplicationContext(), Friends.class);
            startActivity(in);
        } else if (id == R.id.nav_logout) {
            //FirebaseAuth.getInstance().signOut();
            Toast.makeText(this, "Logging out.", Toast.LENGTH_SHORT).show();
            Intent in = new Intent(getApplicationContext(), AccountLogin.class);
            //in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(in);
            finish();
        }  else if (id == R.id.nav_help) {
            Intent in = new Intent(getApplicationContext(), HelpHowToUse.class);
            startActivity(in);
        } else if (id == R.id.nav_about) {
            Intent in = new Intent(getApplicationContext(), About.class);
            startActivity(in);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
