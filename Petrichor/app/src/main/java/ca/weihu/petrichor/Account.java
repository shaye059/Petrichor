package ca.weihu.petrichor;

import android.provider.ContactsContract;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by weihu on 2017-11-29.
 */

public class Account {

    /*==============================================================================================
        V A R I A B L E S
    ==============================================================================================*/

    private String username;
    private String name;

    // either way still have to use database snapshot... delete this later T.N.
    //private DatabaseReference dbRefUser;


    /*==============================================================================================
        C O N S T R U C T O R S
    ==============================================================================================*/

    public Account() {
    }

    public Account(String username) {
        this.username = username;
    }

    /*public Account(String username, DatabaseReference dbRefUser) {
        this.username = username;
        this.dbRefUser = dbRefUser;
    }*/


    /*==============================================================================================
        M E T H O D S
    ==============================================================================================*/

    // The purpose of these DbRefs is to be able to change DbRef names in one sole definition

    // Note: change Account to accounts when have time
    public static DatabaseReference getDbRefUser() {
        return ( FirebaseDatabase.getInstance().getReference("Account/" +
                FirebaseAuth.getInstance().getCurrentUser().getUid()) );
    }

    public static DatabaseReference getDbRefUserHighlights() {
        return ( FirebaseDatabase.getInstance().getReference("Account/" +
                FirebaseAuth.getInstance().getCurrentUser().getUid() + "/highlights") );
    }

    public static DatabaseReference getDbRefUserTPCs() {
        return ( FirebaseDatabase.getInstance().getReference("Account/" +
                FirebaseAuth.getInstance().getCurrentUser().getUid() + "/timePeriodCollections") );
    }

    public String getUsername(){
        return username;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
