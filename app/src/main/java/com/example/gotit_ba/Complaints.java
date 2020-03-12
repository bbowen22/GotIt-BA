package com.example.gotit_ba;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Complaints {

    private Date created; //date created at
    private String status; //status
    private String type; //type
    private String user; //user

    private String pattern = "MM/dd/yyyy";
    private SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);

    //default values
    public Complaints() {
        this(null, " ", " ", " ");
    }

    //initializes with provided values
    public Complaints(Date created, String status, String type, String user) {
        this.created = created;
        this.status = status;
        this.type = type;
        this.user = user;
    }

    public String getCreated() {return dateFormat.format(created);}

    public String getStatus() {return status;}

    public String getType() {return type;}

    public String getUser() {return user;}

    public void setCreated (Date created) {this.created = created;}

    public void setstatus (String status) {this.status = status;}

    public void settype (String type) {this.type = type;}

    public void setuser (String user) {this.user = user;}
}
