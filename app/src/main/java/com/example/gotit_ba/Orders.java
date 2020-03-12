package com.example.gotit_ba;


import java.text.SimpleDateFormat;
import java.util.Date;

public class Orders {

    private Date created; //date created at
    private String customer; //customer username
    private String driver; //driver username
    private String store; //store name

    private String pattern = "MM/dd/yyyy";
    private SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);

    //default values
    public Orders() {
        this(null, " ", " ", " ");
    }

    //initializes with provided values
    public Orders(Date created, String customer, String driver, String store) {
        this.created = created;
        this.customer = customer;
        this.driver = driver;
        this.store = store;

    }

    public String getCreated() {return dateFormat.format(created);}

    public String getCustomer() {return customer;}

    public String getDriver() {return driver;}

    public String getStore() {return store;}

    public void setCreated (Date created) {this.created = created;}

    public void setCustomer (String customer) {this.customer = customer;}

    public void setDriver (String driver) {this.driver = driver;}

    public void setStore (String store) {this.store = store;}



}
