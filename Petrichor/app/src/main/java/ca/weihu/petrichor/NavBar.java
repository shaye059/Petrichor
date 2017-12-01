package ca.weihu.petrichor;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NavBar extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String userID;
    private DatabaseReference databaseAccounts;
    private DatabaseReference ref;
    NavigationView navigationView;
    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance().getInstance();
        user = mAuth.getCurrentUser();
        userID = user.getUid();
        setContentView(R.layout.activity_my_profile);
        databaseAccounts = FirebaseDatabase.getInstance().getReference();
        ref = databaseAccounts.child("accounts");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_bar);






        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    public void onStart(){
        super.onStart();

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
        textView.setText(account.getname());
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
            Intent in = new Intent(getApplicationContext(), AccountLogin.class);
            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
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
