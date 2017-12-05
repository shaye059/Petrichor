package ca.weihu.petrichor;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by spenc on 2017-12-04.
 */

public class FriendList extends ArrayAdapter<Account> {
    private DatabaseReference databaseAccounts;
    private DatabaseReference databaseFriends;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String userID;
    private Activity context;
    private int i;
    private List<Account> accounts;


    public FriendList(Activity context,List<Account> accounts) {
        super(context, R.layout.layout_friend_list, accounts);
        this.context = context;
        this.accounts = accounts;

        mAuth = FirebaseAuth.getInstance().getInstance();
        user = mAuth.getCurrentUser();
        userID = user.getUid();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_friend_list, null, true);

        TextView textViewFriendName = (TextView) listViewItem.findViewById(R.id.textViewFriendName);
        TextView textViewFriendEmail = (TextView) listViewItem.findViewById(R.id.textViewFriendEmail);


        final Account account = accounts.get(position);
        textViewFriendEmail.setText(String.valueOf(account.getusername()));
        if(String.valueOf(account.getname()).equals("null")||String.valueOf(account.getname()).equals("")){
            textViewFriendName.setText("No Name");
        }else {
            textViewFriendName.setText(String.valueOf(account.getname()));
        }
        return listViewItem;
    }

}
