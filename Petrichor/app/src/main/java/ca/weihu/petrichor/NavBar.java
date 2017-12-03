package ca.weihu.petrichor;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NavBar extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DatabaseReference dbRefUser;

    TextView navBarUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_bar);

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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        dbRefUser = FirebaseDatabase.getInstance().getReference( "Account/" +
                FirebaseAuth.getInstance().getCurrentUser().getUid() );


        // Setting username/email into R.id.navBarUsername (TextView) on the navbar drawer

        dbRefUser.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // if the user set a name then display it
                if (dataSnapshot.getValue(Account.class).getName() != null) {

                    setUsernameToDrawerTextView(dataSnapshot.getValue(Account.class).getName());

                    // if the user DID NOT set a name then display user's email
                } else if (dataSnapshot.getValue(Account.class).getUsername() != null) {

                    setUsernameToDrawerTextView(dataSnapshot.getValue(Account.class).getUsername());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(NavBar.this,
                        "The read failed: " + databaseError.getCode(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUsernameToDrawerTextView( String username ) {

        // load username/email into R.id.navBarUsername
        navBarUsername = (TextView) findViewById(R.id.navBarUsername);
        navBarUsername.setText( username );
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
