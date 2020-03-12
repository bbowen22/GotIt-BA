package com.example.gotit_ba;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Users {

    private Date created; //date created at
    private String fname; //customer first name
    private String lname; //customer last name
    private String username; //customer username

    private String pattern = "MM/dd/yyyy";
    private SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);

    //default values
    public Users() {
        this(null, " ", " ", " ");
    }

    //initializes with provided values
    public Users(Date created, String fname, String lname, String username) {
        this.created = created;
        this.fname = fname;
        this.lname = lname;
        this.username = username;

    }

    public String getCreated() {return dateFormat.format(created);}

    public String getFname() {return fname;}

    public String getLname() {return lname;}

    public String getUsername() {return username;}

    public void setCreated (Date created) {this.created = created;}

    public void setFname (String fname) {this.fname = fname;}

    public void setLname (String lname) {this.lname = lname;}

    public void setUsername (String username) {this.username = username;}



}


