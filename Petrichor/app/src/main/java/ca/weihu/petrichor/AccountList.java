package ca.weihu.petrichor;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


/**
 * This class, along with all instances, is based on the document
 * created by Miguel Garzón on 2017-05-09.
 */

public class AccountList extends ArrayAdapter<Account> {
    private Activity context;
    private List<Account> accounts;
    private DatabaseReference databaseAccounts;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String userID;
    private Account thisAccountL;


    public AccountList(Activity context, List<Account> accounts) {
        super(context, R.layout.layout_account_list, accounts);
        this.context = context;
        this.accounts = accounts;

        mAuth = FirebaseAuth.getInstance().getInstance();
        user = mAuth.getCurrentUser();
        userID = user.getUid();

        databaseAccounts = FirebaseDatabase.getInstance().getReference().child("Account").child(userID);
        databaseAccounts.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                thisAccountL = dataSnapshot.getValue(Account.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_account_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewEmail = (TextView) listViewItem.findViewById(R.id.textViewEmail);

        final Account account = accounts.get(position);
        textViewEmail.setText(String.valueOf(account.getusername()));
        if(String.valueOf(account.getname()).equals("null")||String.valueOf(account.getname()).equals("")){
            textViewName.setText("No Name");
        }else {
            textViewName.setText(String.valueOf(account.getname()));
        }
        Button addFriend = (Button) listViewItem.findViewById(R.id.buttonAdd);
        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddFriendDialogue(account);
            }
        });
        return listViewItem;
    }


    private void showAddFriendDialogue(final Account account){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this.context);
        LayoutInflater inflater = context.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_friend_dialogue, null);
        dialogBuilder.setView(dialogView);

        final TextView nameView = (TextView) dialogView.findViewById(R.id.nameTextViewD);
        final TextView emailView  = (TextView) dialogView.findViewById(R.id.emailTextViewD);
        final Button buttonAdd = (Button) dialogView.findViewById(R.id.buttonAddD);
        final Button buttonCancel = (Button) dialogView.findViewById(R.id.buttonCancelD);

        if(String.valueOf(account.getname()).equals("null")||String.valueOf(account.getname()).equals("")){
            nameView.setText("No Name");
        }else {
            nameView.setText(String.valueOf(account.getname()));
        }

        emailView.setText(account.getusername());

        dialogBuilder.setCustomTitle(inflater.inflate(R.layout.dialogue_title, null));
        final AlertDialog b = dialogBuilder.create();
        b.show();


        buttonAdd.setOnClickListener(new View.OnClickListener() {

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
                                thisAccountL.addFriend(singleAccount.getKey());
                                databaseAccounts.child(userID).child("friends").setValue(thisAccountL.getfriends());
                                databaseAccounts.child(userID).child("numfriends").setValue(thisAccountL.getnumfriends());
                            }
                        }
                        Toast.makeText(context, "Friend Added", Toast.LENGTH_LONG).show();
                        Intent in = new Intent(context, Friends.class);
                        context.recreate();
                        context.startActivity(in);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                b.dismiss();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
            }
        });
    }

}