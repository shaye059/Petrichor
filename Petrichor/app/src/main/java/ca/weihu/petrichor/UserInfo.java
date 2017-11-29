package ca.weihu.petrichor;

/**
 * Created by Srikar Kovvali on 11/29/17.
 */

public class UserInfo {
    public String username;
    public String password;
    public UserInfo(){

    }

    public UserInfo(String username,String password){
        this.password=password;
        this.username=username;
    }
}
