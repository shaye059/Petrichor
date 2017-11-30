package ca.weihu.petrichor;

/**
 * Created by weihu on 2017-11-29.
 */

public class Account {
    private String username;

    public Account(){

    }

    public Account(String username){
        this.username = username;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

}
