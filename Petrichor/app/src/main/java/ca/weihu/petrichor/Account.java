package ca.weihu.petrichor;

/**
 * Created by weihu on 2017-11-29.
 */

public class Account {
    private String username;
    private String name;

    public Account(){

    }

    public Account(String username){
        this.username = username;
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
