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

public class UsersActivity extends AppCompatActivity {


    public static int size;
    public static List<Users> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        loadUsers();

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

        TableLayout stk = (TableLayout) findViewById(R.id.tablemain2);
        //Set header row
        TableRow tbrow0 = new TableRow(this);
        tbrow0.setBackgroundColor(Color.parseColor("#B1CF5F"));
        TextView tv0 = new TextView(this);
        tv0.setText(" Date ");
        tv0.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setText(" First Name ");
        tv1.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setText(" Last Name ");
        tv2.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(this);
        tv3.setText(" Username ");
        tv3.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv3);
        stk.addView(tbrow0);


        Log.d("table", "Size = " + users.size() );


        for (Users u: users) {
            TableRow tbrow = new TableRow(this);
            //col 1
            TextView t1v = new TextView(this);
            t1v.setText(u.getCreated() + " ");
            Log.d("table created value", " " + u.getCreated());
            //t1v.setText(" ");
            t1v.setTextColor(Color.parseColor("#707070"));
            t1v.setGravity(Gravity.LEFT);
            tbrow.addView(t1v);
            //col2
            TextView t2v = new TextView(this);
            t2v.setText(" " + u.getFname() + " ");
            t2v.setTextColor(Color.parseColor("#707070"));
            t2v.setGravity(Gravity.LEFT);
            tbrow.addView(t2v);
            //col3
            TextView t3v = new TextView(this);
            t3v.setText(u.getLname() + " ");
            t3v.setTextColor(Color.parseColor("#707070"));
            t3v.setGravity(Gravity.LEFT);
            tbrow.addView(t3v);
            //col4
            TextView t4v = new TextView(this);
            t4v.setText(u.getUsername() + " ");
            t4v.setTextColor(Color.parseColor("#707070"));
            t4v.setGravity(Gravity.LEFT);
            tbrow.addView(t4v);
            stk.addView(tbrow);
        }

    }

    public void loadUsers() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Customer");
        query.whereEqualTo("cus_status", true);
        query.orderByAscending("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> orderList, ParseException e) {
                if (e == null) {

                    //Set size of table
                    size = orderList.size();
                    Log.d("SIZE", "Retrieved " + orderList.size() + " orders");


                    //Set list of user objects
                    for (ParseObject user : orderList) {

                        //ParseObject cus = (ParseObject) user.get("cus_id");
                        //ParseObject driver = (ParseObject) user.get("dri_id");
                        //ParseObject store = (ParseObject) user.get("sto_id");

                        Log.d("fname", " " + user.getString("cus_first_name"));
                        Log.d("lname", " " + user.getString("cus_last_name"));
                        Log.d("user", " " + user.getString("cus_username"));

                        Users u = new Users(user.getCreatedAt(), user.getString("cus_first_name"), user.getString("cus_last_name"), user.getString("cus_username"));
                        users.add(u);
                        Log.d("object array date", " " + u.getCreated());
                    }


                } else {
                    Log.d("size", "Error: " + e.getMessage());

                }
            }
        });
    }
}
