package ca.weihu.petrichor;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

public class TagFriend extends AppCompatActivity {
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
    private String highlightID;
    private String highlightDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tag_friend);
        hideSystemUI();

        context = this;
        mAuth = FirebaseAuth.getInstance().getInstance();
        user = mAuth.getCurrentUser();
        userID = user.getUid();
        rootRef = FirebaseDatabase.getInstance().getReference();
        userRef = rootRef.child("Account").child(userID);
        accounts = new ArrayList<Account>();
        listViewFriends = (ListView) findViewById(R.id.listViewFriends);

        Bundle bundle = getIntent().getExtras();
        highlightID = bundle.getString("highlightID");
        highlightDescription = bundle.getString("description");


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
                        friendAdapter = new FriendList(TagFriend.this, accounts);
                        listViewFriends.setAdapter(friendAdapter);
                        listViewFriends.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Account account = accounts.get(i);
                                showTagFriendDialogue(account);
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

    @Override
    protected void onResume() {
        super.onResume();
        hideSystemUI();
    }

    private void showTagFriendDialogue( final Account account){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = context.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.tag_friend_dialogue, null);
        dialogBuilder.setView(dialogView);

        final TextView nameTextViewTF = (TextView) dialogView.findViewById(R.id.nameTextViewTF);
        final TextView emailTextViewTF  = (TextView) dialogView.findViewById(R.id.emailTextViewTF);
        final Button buttonTagFriend = (Button) dialogView.findViewById(R.id.buttonTagTF);
        final Button buttonCancelTF = (Button) dialogView.findViewById(R.id.buttonCancelTF);

        if(String.valueOf(account.getname()).equals("null")||String.valueOf(account.getname()).equals("")){
            nameTextViewTF.setText("No Name");
        }else {
            nameTextViewTF.setText(String.valueOf(account.getname()));
        }

        emailTextViewTF.setText(account.getusername());

        dialogBuilder.setCustomTitle(inflater.inflate(R.layout.dialogue_title_tag, null));
        final AlertDialog b = dialogBuilder.create();
        b.show();


        buttonTagFriend.setOnClickListener(new View.OnClickListener() {

            /*Method to tag friends in a highlight. The ID of the highlight is saved as a String and
            added to a list under the tagged persons userID in the database.
             */

            @Override
            public void onClick(final View view) {
                databaseAccounts = FirebaseDatabase.getInstance().getReference().child("Account");
                databaseAccounts.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot singleAccount: dataSnapshot.getChildren()){

                            //if the current account belongs to the user we want to tag and they have
                            //not already been tagged

                            if(singleAccount.getValue(Account.class).getusername().equals(account.getusername())
                                    && !singleAccount.child("taggedhighlights").hasChild(highlightID+userID)){
                                DatabaseReference dbRefTag = FirebaseDatabase.getInstance()
                                        .getReference( "Account/" + singleAccount.getKey() + "/taggedhighlights/" + highlightID + userID);

                                TaggedHighlight newTag = new TaggedHighlight(highlightID, highlightDescription, userID);
                                dbRefTag.setValue(newTag);

                                Toast.makeText(context, "Friend Tagged", Toast.LENGTH_LONG).show();
                                context.recreate();
                                onBackPressed();
                            }
                            if(singleAccount.child("taggedhighlights").hasChild(highlightID+userID)){
                                Toast.makeText(context, "Friend Already Tagged", Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(TagFriend.this, "Unable to tag friend",
                                Toast.LENGTH_SHORT);
                    }
                });

                b.dismiss();
            }
        });

        buttonCancelTF.setOnClickListener(new View.OnClickListener() {
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
        onBackPressed();
    }

    private void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}