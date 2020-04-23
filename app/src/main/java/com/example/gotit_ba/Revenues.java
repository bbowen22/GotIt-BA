package com.example.gotit_ba;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Revenues {

        private Date created; //date created at
        private String driver; //driver
        private String order; //order
        private int amount; //order total

        private String pattern = "MM/dd/yyyy";
        private SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);

        //default values
        public Revenues() {
            this(null, " ", " ", 0);
        }

        //initializes with provided values
        public Revenues(Date created, String driver, String order, int amount) {
            this.created = created;
            this.driver = driver;
            this.order = order;
            this.amount = amount;
        }

        public String getCreated() {return dateFormat.format(created);}

        public String getDriver() {return driver;}

        public String getOrder() {return order;}

        public int getAmount() {return amount;}

        public void setCreated (Date created) {this.created = created;}

        //public void setstatus (String status) {this.status = status;}

        //public void settype (String type) {this.type = type;}

        //public void setuser (String user) {this.user = user;}
    }


