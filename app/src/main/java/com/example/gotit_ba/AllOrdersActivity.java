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

public class AllOrdersActivity extends AppCompatActivity {
    //public static int i;
    public static int size;
    public static List<Integer> dates = new ArrayList<>();
    public static List<Integer> driverid = new ArrayList<>();
    public static List<String> customers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_orders);

        //setDates();
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


        setSizeDatesDrivers();
        setCustomers();
        Log.d("table", "Size = " + size );

        for (int i = 0; i < size; i++) {
            TableRow tbrow = new TableRow(this);
            //col 1
            TextView t1v = new TextView(this);
            t1v.setText(String.valueOf(dates.get(i)) + " ");
            t1v.setTextColor(Color.parseColor("#707070"));
            t1v.setGravity(Gravity.CENTER);
            tbrow.addView(t1v);
            //col2
            TextView t2v = new TextView(this);
            t2v.setText(String.valueOf(customers.get(i)) + " " );
            t2v.setTextColor(Color.parseColor("#707070"));
            t2v.setGravity(Gravity.CENTER);
            tbrow.addView(t2v);
            //col3
            TextView t3v = new TextView(this);
            t3v.setText(String.valueOf(driverid.get(i)));
            t3v.setTextColor(Color.parseColor("#707070"));
            t3v.setGravity(Gravity.CENTER);
            tbrow.addView(t3v);
            //col4
            TextView t4v = new TextView(this);
            t4v.setText(" ");
            t4v.setTextColor(Color.parseColor("#707070"));
            t4v.setGravity(Gravity.CENTER);
            tbrow.addView(t4v);
            stk.addView(tbrow);
        }

    }

    //Function to get list size (how many orders there are), date created, and drivers
    public void setSizeDatesDrivers() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Order");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> orderList, ParseException e) {
                if (e == null) {

                    //Set size of table
                    size = orderList.size();
                    Log.d("score", "Retrieved " + orderList.size() + " orders");

                    //Set list of dates
                    for (ParseObject date : orderList) {
                        int DATES = date.getInt("ord_created");
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
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> cusList, ParseException e) {
                if (e == null) {

                    for (int i = 0; i < cusList.size(); i++) {

                        ParseObject d = (ParseObject) cusList.get(i).get("cus_id");
                        d.getObjectId();

                        Log.d("cus", "object id: " + d.getObjectId());

                        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Customer");
                        query2.include(d.getObjectId());
                        query2.findInBackground(new FindCallback<ParseObject>() {

                            //@Override
                            public void done(List<ParseObject> objects, ParseException e) {


                                    customers.add(objects.get(0).getString("cus_username"));

                                    Log.d("id", "cus_username: " + objects.get(0).getString("cus_username"));



                            }

                        });
                    }

                }

                else {
                    Log.d("cus", "Error: " + e.getMessage());

                }
            }
        });
    }



}
