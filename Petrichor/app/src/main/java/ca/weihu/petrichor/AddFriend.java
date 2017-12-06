package ca.weihu.petrichor;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
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

public class AddFriend extends AppCompatActivity {
    private ProgressBar progressBar;
    private View searchButton;
    private EditText searchText;
    private DatabaseReference databaseAccounts;
    private List<Account> accounts;
    private ListView listViewAccounts;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String userID;
    private Account thisAccount;
    private AccountList accountAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_friend);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        progressBar = (ProgressBar) findViewById(R.id.searchProgress);
        progressBar.setVisibility(View.GONE);

        listViewAccounts = (ListView) findViewById(R.id.listViewAccounts);
        listViewAccounts.setVisibility(View.INVISIBLE);

        mAuth = FirebaseAuth.getInstance().getInstance();
        user = mAuth.getCurrentUser();
        userID = user.getUid();

        databaseAccounts = FirebaseDatabase.getInstance().getReference().child("Account").child(userID);
        databaseAccounts.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                thisAccount = dataSnapshot.getValue(Account.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    /*OnClick method for search button. Hides the search button and displays a progress bar.
    Searches through data base to find users whose email contains the text entered.
    Results are displayed in the listview, the progress bar disappears and is again
    replaced by the search button.*/
    public void onSearchBtn(View view) {
        View thisView = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        searchButton = findViewById(R.id.searchBtn);
        searchButton.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        searchText = (EditText) findViewById(R.id.emailEdit);
        final String searchString = searchText.getText().toString().trim();
        accounts = new ArrayList<>();

        //searching the database
        databaseAccounts = FirebaseDatabase.getInstance().getReference().child("Account");
        databaseAccounts.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            //Take a snapshot of all accounts in the database, then initiate a for loop to look at
            //each account.
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleAccount : dataSnapshot.getChildren()) {
                     /*If the account is not already in the list of friends and is not this account,
                     *create an account object*/
                    if (!thisAccount.getfriends().contains(singleAccount.getKey()) && !userID.equals(singleAccount.getKey())) {
                        Account account = singleAccount.getValue(Account.class);

                        //If the email contains the search string, add it to the list of accounts
                        if (account.getusername().contains(searchString)) {
                            accounts.add(account);
                        }
                    }
                }
                if(accounts.size() == 0){
                    Toast.makeText(AddFriend.this,
                            "No results",
                            Toast.LENGTH_SHORT).show();
                }else {

                    accountAdapter = new AccountList(AddFriend.this, accounts);
                    listViewAccounts.setAdapter(accountAdapter);
                    listViewAccounts.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        progressBar.setVisibility(View.GONE);
        searchButton.setVisibility(View.VISIBLE);


    }

    public void onBtnBack(View view) {
        onBackPressed();
    }

}
