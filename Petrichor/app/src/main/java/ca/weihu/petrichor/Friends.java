package ca.weihu.petrichor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

/**
 * Created by spenc on 2017-12-04.
 */

public class Friends extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String userID;
    private Account thisAccount;
    private DatabaseReference rootRef;
    private DatabaseReference userRef;
    private DatabaseReference databaseFriends;
    private List<String> friends;
    private List<Account> accounts;
    private ListView listViewFriends;
    private FriendList friendAdapter;
    private Activity context;
    private DatabaseReference databaseAccounts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_friends);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        context = this;
        mAuth = FirebaseAuth.getInstance().getInstance();
        user = mAuth.getCurrentUser();
        userID = user.getUid();
        rootRef = FirebaseDatabase.getInstance().getReference();
        userRef = rootRef.child("Account").child(userID);
        accounts = new ArrayList<Account>();
        listViewFriends = (ListView) findViewById(R.id.listViewFriends);


        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                thisAccount = dataSnapshot.getValue(Account.class);
                friends = thisAccount.getfriends();

                databaseFriends = FirebaseDatabase.getInstance().getReference().child("Account");
                databaseFriends.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot accountSnapshot : dataSnapshot.getChildren()){
                            if(friends.contains(accountSnapshot.getKey())){
                                Account temp = accountSnapshot.getValue(Account.class);
                                accounts.add(temp);
                            }
                        }
                        friendAdapter = new FriendList(Friends.this, accounts);
                        listViewFriends.setAdapter(friendAdapter);
                        listViewFriends.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                            @Override
                            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Account account = accounts.get(i);
                                showDeleteFriendDialogue(account);
                                return true;
                            }
                        });

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }


    private void showDeleteFriendDialogue( final Account account){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = context.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.delete_friend_dialogue, null);
        dialogBuilder.setView(dialogView);

        final TextView nameTextViewDD = (TextView) dialogView.findViewById(R.id.nameTextViewDD);
        final TextView emailTextViewDD  = (TextView) dialogView.findViewById(R.id.emailTextViewDD);
        final Button buttonDeleteDD = (Button) dialogView.findViewById(R.id.buttonDeleteDD);
        final Button buttonCancelDD = (Button) dialogView.findViewById(R.id.buttonCancelDD);

        if(String.valueOf(account.getname()).equals("null")||String.valueOf(account.getname()).equals("")){
            nameTextViewDD.setText("No Name");
        }else {
            nameTextViewDD.setText(String.valueOf(account.getname()));
        }

        emailTextViewDD.setText(account.getusername());

        dialogBuilder.setCustomTitle(inflater.inflate(R.layout.dialogue_title_delete, null));
        final AlertDialog b = dialogBuilder.create();
        b.show();


        buttonDeleteDD.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View view) {
                databaseAccounts = FirebaseDatabase.getInstance().getReference().child("Account");
                databaseAccounts.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot singleAccount: dataSnapshot.getChildren()){
                            Account childAccount = singleAccount.getValue(Account.class);
                            if(childAccount.getusername().equals(account.getusername())) {
                                mAuth = FirebaseAuth.getInstance().getInstance();
                                user = mAuth.getCurrentUser();
                                userID = user.getUid();
                                thisAccount.removeFriend(singleAccount.getKey());
                                databaseAccounts.child(userID).child("friends").setValue(thisAccount.getfriends());
                                databaseAccounts.child(userID).child("numfriends").setValue(thisAccount.getnumfriends());
                            }
                        }
                        Toast.makeText(context, "Friend Removed", Toast.LENGTH_LONG).show();
                        //Intent in = new Intent(context, Friends.class);
                        context.recreate();
                        //context.startActivity(in);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                b.dismiss();
            }
        });

        buttonCancelDD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
            }
        });
    }


    public void onTaggedMemories(View view){
        Intent intent = new Intent(getApplicationContext(), MentionedHighlights.class);
        startActivity(intent);
    }

    public void onBtnAdd(View view){
        Intent intent = new Intent(getApplicationContext(), AddFriend.class);
        startActivity(intent);
    }

    public void onBtnBack(View view){
        Intent intent = new Intent(getApplicationContext(), NavBar.class);
        startActivity(intent);
    }
}