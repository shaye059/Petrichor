package ca.weihu.petrichor;

import android.provider.ContactsContract;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weihu on 2017-11-29.
 */

public class Account {

    /*==============================================================================================
        V A R I A B L E S
    ==============================================================================================*/

    private String username;
    private String name;

    //friends is implemented as an array of Strings instead of an array of accounts to save space in
    //the database. It would be more efficient to retrieve the friends information and Highlights,
    //if we used an array of accounts but it would use up far too much space in the database due
    //to the account also storing all of the highlights.
    private ArrayList<String> friends;
    private int numfriends;

    // either way still have to use database snapshot... delete this later T.N.
    //private DatabaseReference dbRefUser;


    /*==============================================================================================
        C O N S T R U C T O R S
    ==============================================================================================*/

    public Account() {
        friends = new ArrayList<String>();
        numfriends = 0;
    }

    public Account(String username, String name){
        this.username = username;
        this.name = name;
        friends = new ArrayList<String>();
        numfriends = 0;
    }


    /*==============================================================================================
        M E T H O D S
    ==============================================================================================*/


    //Getters and setters for username (users email)
    public String getusername() {
        // The purpose of these DbRefs is to be able to change DbRef names in one sole definition
        return username;
    }
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

    public void setusername(String user){
        this.username = user;
    }


    //Getters and setters for users real name
    public String getname(){
        return name;
    }

    public void setname(String name){
        this.name = name;
    }



    /*Method for adding friends.
    *Takes a String userID as input (the unique id assigned to each user when they create an account).
    *The array starts with length two and doubles each time it becomes full*/
    public void addFriend(String userID){
        friends.add(userID);
        numfriends ++;
    }
    //Works the same as add but removes
    public void removeFriend(String userID){
        friends.remove(userID);
        numfriends --;
    }

    //Getters and setters for friends
    public void setfriends(ArrayList<String> friendList){
        friends = friendList;
    }

    public ArrayList<String> getfriends(){
        return friends;
    }

    public int getnumfriends(){
        return numfriends;
    }

    public void setnumfriends(int number) {
        numfriends = number;
    }
}
