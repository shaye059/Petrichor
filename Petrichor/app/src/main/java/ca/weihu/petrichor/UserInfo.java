package ca.weihu.petrichor;

/**
 * Created by Srikar Kovvali on 11/29/17.
 */

public class UserInfo {
    private String username;
    private String password;


    public UserInfo(String username,String password){
        this.password=password;
        this.username=username;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }
}
