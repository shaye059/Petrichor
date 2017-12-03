package ca.weihu.petrichor;

import android.provider.ContactsContract;

import com.google.firebase.database.DatabaseReference;

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
    private DatabaseReference dbRefUser;


    /*==============================================================================================
        C O N S T R U C T O R S
    ==============================================================================================*/

    public Account() {
    }

    public Account(String username, String name){
        this.username = username;
        this.name = name;
    }

    public Account(String username, DatabaseReference dbRefUser) {
        this.username = username;
        this.dbRefUser = dbRefUser;
    }


    /*==============================================================================================
        M E T H O D S
    ==============================================================================================*/

    public String getUsername(){
        return username;
    }

    public void setname(String name){
        this.name = name;
    }

    public String getname(){
        return name;
    }

    public void setusername(String user){
        this.username = user;
    }

}
