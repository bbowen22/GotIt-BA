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

public class ComplaintsActivity extends AppCompatActivity {

    public static int size;
    public static List<Complaints> complaints = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints);

        loadComplaints();

        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                timer.cancel();
            }
        }, 1000);

        init();
    }

    //Function to populate table
    public void init() {

        TableLayout stk = (TableLayout) findViewById(R.id.tablemain4);
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

        Log.d("table", "Size = " + complaints.size());

        for (Complaints c : complaints) {
            TableRow tbrow = new TableRow(this);
            //col 1
            TextView t1v = new TextView(this);
            t1v.setText(c.getCreated() + " ");
            Log.d("table created value", " " + c.getCreated());
            //t1v.setText(" ");
            t1v.setTextColor(Color.parseColor("#707070"));
            t1v.setGravity(Gravity.LEFT);
            tbrow.addView(t1v);
            //col2
            TextView t2v = new TextView(this);
            t2v.setText(c.getStatus() + " ");
            t2v.setTextColor(Color.parseColor("#707070"));
            t2v.setGravity(Gravity.LEFT);
            tbrow.addView(t2v);
            //col3
            TextView t3v = new TextView(this);
            t3v.setText(c.getType() + " ");
            t3v.setTextColor(Color.parseColor("#707070"));
            t3v.setGravity(Gravity.LEFT);
            tbrow.addView(t3v);
            //col4
            TextView t4v = new TextView(this);
            t4v.setText(c.getUser() + " ");
            t4v.setTextColor(Color.parseColor("#707070"));
            t4v.setGravity(Gravity.LEFT);
            tbrow.addView(t4v);
            stk.addView(tbrow);
        }
    }

    public void loadComplaints() {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("SupportTicket");
        query.orderByAscending("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> orderList, ParseException e) {
                if (e == null) {

                    //Set size of table
                    size = orderList.size();
                    Log.d("SIZE", "Retrieved " + orderList.size() + " orders");


                    //Set list of order objects
                    for (ParseObject complaint : orderList) {

                        Complaints c = new Complaints(complaint.getCreatedAt(),complaint.getString("sup_status"), complaint.getString("sup_type"), complaint.getString("sup_user_id"));
                        complaints.add(c);
                        Log.d("object array date", " " + c.getCreated());
                    }

                } else {
                    Log.d("size", "Error: " + e.getMessage());

                }
            }
        });
    }
}