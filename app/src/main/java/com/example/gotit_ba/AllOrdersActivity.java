package com.example.gotit_ba;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AllOrdersActivity extends AppCompatActivity {

    public static int size;
    public static List<Orders> orders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_orders);

        loadOrders();

       final Timer timer = new Timer();
        timer.schedule(new TimerTask(){
            public void run() {
                timer.cancel();
            }
        }, 1000);

        init();
    }

    //Function to populate table
    public void init() {


        TableLayout stk = (TableLayout) findViewById(R.id.tablemain);
        //Set header row
        TableRow tbrow0 = new TableRow(this);
        tbrow0.setBackgroundColor(Color.parseColor("#B1CF5F"));
        TextView tv0 = new TextView(this);
        tv0.setText(" Date ");
        tv0.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setText(" Customer ");
        tv1.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setText(" Driver ");
        tv2.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(this);
        tv3.setText(" Store ");
        tv3.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv3);
        stk.addView(tbrow0);


        Log.d("table", "Size = " + orders.size() );


        for (Orders o : orders) {
            TableRow tbrow = new TableRow(this);
            //col 1
            TextView t1v = new TextView(this);
            t1v.setText(o.getCreated() + " ");
            Log.d("table created value", " " + o.getCreated());
            //t1v.setText(" ");
            t1v.setTextColor(Color.parseColor("#707070"));
            t1v.setGravity(Gravity.LEFT);
            tbrow.addView(t1v);
            //col2
            TextView t2v = new TextView(this);
            t2v.setText(o.getCustomer() + " " );
            t2v.setTextColor(Color.parseColor("#707070"));
            t2v.setGravity(Gravity.LEFT);
            tbrow.addView(t2v);
            //col3
            TextView t3v = new TextView(this);
            t3v.setText(o.getDriver() + " ");
            t3v.setTextColor(Color.parseColor("#707070"));
            t3v.setGravity(Gravity.LEFT);
            tbrow.addView(t3v);
            //col4
            TextView t4v = new TextView(this);
            t4v.setText(o.getStore() + " ");
            t4v.setTextColor(Color.parseColor("#707070"));
            t4v.setGravity(Gravity.LEFT);
            tbrow.addView(t4v);
            stk.addView(tbrow);
        }

    }

    public void loadOrders(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Order");
        query.include("cus_id");
        query.include("dri_id");
        query.include("sto_id");
        query.orderByAscending("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> orderList, ParseException e) {
                if (e == null) {

                    //Set size of table
                    size = orderList.size();
                    Log.d("SIZE", "Retrieved " + orderList.size() + " orders");


                    //Set list of order objects
                    for (ParseObject order : orderList) {

                        ParseObject cus = (ParseObject) order.get("cus_id");
                        ParseObject driver = (ParseObject) order.get("dri_id");
                        ParseObject store = (ParseObject) order.get("sto_id");

                        Log.d("driver", " " + driver.getString("dri_username"));
                        Log.d("customer", " " + cus.getString("cus_username"));
                        Log.d("store", " " + store.getString("sto_name"));

                        Orders o = new Orders(order.getCreatedAt(),cus.getString("cus_username"), driver.getString("dri_username"), store.getString("sto_name"));
                        orders.add(o);
                        Log.d("object array date", " " + o.getCreated());
                    }


                } else {
                    Log.d("size", "Error: " + e.getMessage());

                }
            }
        });

/*
        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("SupportTicket");
        query.include("ord_id");
        query.include("order.cus_id");
        query.orderByAscending("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> orderList2, ParseException e) {
                if (e == null) {

                        ParseObject i = (ParseObject) orderList2.get(0).get("cus_id");
                        Log.d("cus", "object id: " + i.getObjectId());
                        Log.d("second query", i.getString("cus_username"));

                } else {
                    Log.d("size", "Error: " + e.getMessage());

                }
            }
        }); */

    }


    /*
    //Function to get list size (how many orders there are), date created, and drivers
    public void setSizeDatesDrivers() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Order");
        query.orderByAscending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> orderList, ParseException e) {
                if (e == null) {

                    //Set size of table
                    size = orderList.size();
                    Log.d("score", "Retrieved " + orderList.size() + " orders");

                    //Set list of dates
                    for (ParseObject date : orderList) {
                        Date DATES = date.getCreatedAt();
                        dates.add(DATES);
                    }
                    Log.d("date", "Date = " + dates.get(0));

                    //Set list of drivers
                    for (ParseObject driver : orderList) {
                        int DRIVERS = driver.getInt("dri_id");
                        driverid.add(DRIVERS);
                    }
                    Log.d("driver", "Driver = " + driverid.get(0));

                } else {
                    Log.d("size", "Error: " + e.getMessage());

                }
            }
        });
    }

    public void setCustomers() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Order");
        query.include("cus_id");
        query.orderByAscending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> cusList, ParseException e) {
                if (e == null) {

                    for (int i = 0; i < cusList.size(); i++) {

                        ParseObject d = (ParseObject) cusList.get(i).get("cus_id");
                        //d.getString("cus_username");
                        customers.add(d.getString("cus_username"));

                        Log.d("id", "cus_username: " + d.getString("cus_username"));
                       /* d.getObjectId();

                        Log.d("cus", "object id: " + d.getObjectId());

                        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Customer");
                        query2.include(d.getObjectId());
                        query2.findInBackground(new FindCallback<ParseObject>() {

                            //@Override
                            public void done(List<ParseObject> objects, ParseException e) {

                                for(ParseObject p : objects)
                                {
                                    customers.add(p.getString("cus_username"));

                                    Log.d("id", "cus_username: " + p.getString("cus_username"));
                                }
                            }

                        });*/
                 //   }
             //   } else {
              //      Log.d("cus", "Error: " + e.getMessage());

            //    }
          //  }
     //   });
  //  }
}
