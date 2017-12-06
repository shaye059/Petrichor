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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.List;

public class NavBar extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    /*==============================================================================================
        V A R I A B L E S
    ==============================================================================================*/


    private String currentDayOfWeek;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String userID;
    private DatabaseReference databaseAccounts;
    private DatabaseReference ref;
    private NavigationView navigationView;
    private Account account;

    private List<Highlight> highlights;

    /*Fix ca.weihu.petrichor.ListView to avoid confusion!*/
    private android.widget.ListView listViewHighlights;


    /*==============================================================================================
        A C T I V I T Y  L I F E C Y C L E  M E T H O D S
    ==============================================================================================*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance().getInstance();
        user = mAuth.getCurrentUser();
        userID = user.getUid();
        databaseAccounts = FirebaseDatabase.getInstance().getReference();
        ref = databaseAccounts.child("Account");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_bar);






        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*------------------------------------------------------------------------------------------
            PROMPTING USER FOR WEEKLY/MONTHLY/YEARLY HIGHLIGHTS SELECTION
        ------------------------------------------------------------------------------------------*/

        // I N I T I A L I Z I N G

        // assign ListView to a variable and create an ADS to hold the highlights
        listViewHighlights = (android.widget.ListView) findViewById(R.id.listViewHighlights);
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

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
                setName();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


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

    private void showData(DataSnapshot  datasnapshot){
        Iterable<DataSnapshot> children = datasnapshot.getChildren();

        for(DataSnapshot accountC : children) {
            if (accountC.getKey().equals(userID)) {
                account = accountC.getValue(Account.class);
            }
        }

    }

    private void setName(){
        View navHeaderView = navigationView.getHeaderView(0);
        TextView textView = (TextView) navHeaderView.findViewById(R.id.nameView);
        if(!account.getname().equals("")) {
            textView.setText(account.getname());
        }else{
            textView.setText("Enter Name In Profile");
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
            Intent in = new Intent(getApplicationContext(), AccountLogin.class);
            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(in);
            finish();
            Toast.makeText(this, "Logged out.", Toast.LENGTH_SHORT).show();
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

    private class ListView {
    }
}
