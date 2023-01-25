package com.lucky.e_wallet;

public class User {
    public String fname, lname, email;

    public User(){

    }

    public User(String fname, String lname, String email){
        this.fname = fname;
        this.lname = lname;
        this.email = email;


    }
    public String getUsername() {
        return fname;
    }

    public void setUsername(String username) {
        this.fname = username;
    }
}
