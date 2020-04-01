package com.example.gotit_ba;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class ComplaintsActivity extends AppCompatActivity {

    public static int size;
    public static List<Complaints> complaints = new ArrayList<>();
    public static List<Complaints> complaints_date = new ArrayList<>();
    public static List<Complaints> complaints_stat = new ArrayList<>();
    public static List<Complaints> complaints_type = new ArrayList<>();
    public static List<Complaints> complaints_user = new ArrayList<>();
    public Button btnFilter;
    public EditText etSearch;
    public TableLayout stk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints);

        stk = findViewById(R.id.tablemain4);

        loadComplaints();

        Spinner spinner = findViewById(R.id.complaints_spinner);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Choose...");
        arrayList.add("View All");
        arrayList.add("Date");
        arrayList.add("Status");
        arrayList.add("Type");
        arrayList.add("User");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String clicked = parent.getItemAtPosition(position).toString();
                btnFilter = findViewById(R.id.filter1);
                etSearch = findViewById(R.id.search1);

                if (clicked == "View All") {
                    init();

                } else if (clicked == "Date") {
                    stk.removeAllViews();
                    etSearch.setHint("Enter as mm/dd/yyyy");
                    btnFilter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            final String s = etSearch.getText().toString();
                            ArrayList<String> list0 = new ArrayList<>();

                            Log.d("DATE:", complaints.get(0).getCreated());
                            for (Complaints i : complaints) {
                                list0.add(i.getCreated());
                            }
                            Log.d("DATE:", list0.get(0));
                            Log.d("INPUT:", s);

                            if (list0.contains(s)) {
                                initDate(s);
                                Log.d("FLAG:", "Made it to if...");
                            } else {
                                Toast.makeText(ComplaintsActivity.this, "Sorry, search invalid!", Toast.LENGTH_LONG).show();
                            }
                        }

                    });

                } else if(clicked == "Status") {
                    stk.removeAllViews();
                    etSearch.setHint(" ");
                    btnFilter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String s = etSearch.getText().toString();
                            ArrayList<String> list1 = new ArrayList<>();

                            Log.d("USERS:", complaints.get(0).getStatus());
                            for(Complaints i : complaints){
                                list1.add(i.getStatus());
                            }
                            Log.d("FNAME:", list1.get(0));
                            Log.d("INPUT:", s);

                            if(list1.contains(s)){
                                initStat(s);
                                Log.d("FLAG:", "Made it to if...");
                            } else {
                                Toast.makeText(ComplaintsActivity.this, "Sorry, search invalid!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                } else if(clicked == "Type") {
                    stk.removeAllViews();
                    etSearch.setHint(" ");
                    btnFilter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String s = etSearch.getText().toString();
                            ArrayList<String> list2 = new ArrayList<>();

                            Log.d("completions:", complaints.get(0).getType());
                            for(Complaints i : complaints){
                                list2.add(i.getType());
                            }
                            Log.d("LNAME:", list2.get(0));
                            //Log.d("LNAME:", list2.get(1));
                            Log.d("INPUT:", s);

                            if(list2.contains(s)){
                                initType(s);
                                Log.d("FLAG:", "Made it to if...");
                            } else {
                                Toast.makeText(ComplaintsActivity.this, "Sorry, search invalid!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                } else if(clicked == "User") {
                    stk.removeAllViews();
                    etSearch.setHint(" ");
                    btnFilter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String s = etSearch.getText().toString();
                            ArrayList<String> list3 = new ArrayList<>();

                            Log.d("completions:", complaints.get(0).getUser());
                            for (Complaints i : complaints) {
                                list3.add(i.getUser());
                            }
                            Log.d("UNAME:", list3.get(0));
                            //Log.d("LNAME:", list2.get(1));
                            Log.d("INPUT:", s);

                            if (list3.contains(s)) {
                                initUser(s);
                                Log.d("FLAG:", "Made it to if...");
                            } else {
                                Toast.makeText(ComplaintsActivity.this, "Sorry, search invalid!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }



    //Function to populate table
    public void init() {
        //Clear table
        stk.removeAllViews();

        TableLayout stk = (TableLayout) findViewById(R.id.tablemain4);
        //Set header row
        TableRow tbrow0 = new TableRow(this);
        tbrow0.setBackgroundColor(Color.parseColor("#B1CF5F"));
        TextView tv0 = new TextView(this);
        tv0.setText(" Date ");
        tv0.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setText(" Status ");
        tv1.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setText(" Type ");
        tv2.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(this);
        tv3.setText(" User ");
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

    public void initDate(String s) {
        for(Complaints i : complaints){
            if (i.getCreated().equals(s)) {

                Log.d("INITDATE LIST:", i.getCreated());
                complaints_date.add(i);
                Log.d("INITDATE LIST:", String.valueOf(complaints_date.size()));
            }

            else Log.d("INITDATE LIST:", "failed");
        }
        //Set header row
        TableRow tbrow0 = new TableRow(this);
        tbrow0.setBackgroundColor(Color.parseColor("#B1CF5F"));
        TextView tv0 = new TextView(this);
        tv0.setText(" Date ");
        tv0.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setText(" Status ");
        tv1.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setText(" Type ");
        tv2.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(this);
        tv3.setText(" User ");
        tv3.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv3);
        stk.addView(tbrow0);

        for (Complaints c1: complaints_date) {
            TableRow tbrow = new TableRow(this);
            //col 1
            TextView t1v = new TextView(this);
            t1v.setText(c1.getCreated() + " ");

            t1v.setTextColor(Color.parseColor("#707070"));
            t1v.setGravity(Gravity.LEFT);
            tbrow.addView(t1v);
            //col2
            TextView t2v = new TextView(this);
            t2v.setText(" " + c1.getStatus() + " ");
            t2v.setTextColor(Color.parseColor("#707070"));
            t2v.setGravity(Gravity.LEFT);
            tbrow.addView(t2v);
            //col3
            TextView t3v = new TextView(this);
            t3v.setText(c1.getType() + " ");
            t3v.setTextColor(Color.parseColor("#707070"));
            t3v.setGravity(Gravity.LEFT);
            tbrow.addView(t3v);
            //col4
            TextView t4v = new TextView(this);
            t4v.setText(c1.getUser() + " ");
            t4v.setTextColor(Color.parseColor("#707070"));
            t4v.setGravity(Gravity.LEFT);
            tbrow.addView(t4v);
            stk.addView(tbrow);
        }
    }

    public void initType(String s) {
        for(Complaints i : complaints){
            if (i.getType().equals(s)) {
                complaints_type.add(i);
            }

            else Log.d("INITTYPE LIST:", "failed");
        }
        //Set header row
        TableRow tbrow0 = new TableRow(this);
        tbrow0.setBackgroundColor(Color.parseColor("#B1CF5F"));
        TextView tv0 = new TextView(this);
        tv0.setText(" Date ");
        tv0.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setText(" Status ");
        tv1.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setText(" Type ");
        tv2.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(this);
        tv3.setText(" User ");
        tv3.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv3);
        stk.addView(tbrow0);

        for (Complaints c1: complaints_type) {
            TableRow tbrow = new TableRow(this);
            //col 1
            TextView t1v = new TextView(this);
            t1v.setText(c1.getCreated() + " ");

            t1v.setTextColor(Color.parseColor("#707070"));
            t1v.setGravity(Gravity.LEFT);
            tbrow.addView(t1v);
            //col2
            TextView t2v = new TextView(this);
            t2v.setText(" " + c1.getStatus() + " ");
            t2v.setTextColor(Color.parseColor("#707070"));
            t2v.setGravity(Gravity.LEFT);
            tbrow.addView(t2v);
            //col3
            TextView t3v = new TextView(this);
            t3v.setText(c1.getType() + " ");
            t3v.setTextColor(Color.parseColor("#707070"));
            t3v.setGravity(Gravity.LEFT);
            tbrow.addView(t3v);
            //col4
            TextView t4v = new TextView(this);
            t4v.setText(c1.getUser() + " ");
            t4v.setTextColor(Color.parseColor("#707070"));
            t4v.setGravity(Gravity.LEFT);
            tbrow.addView(t4v);
            stk.addView(tbrow);
        }
    }

    public void initStat(String s) {
        for(Complaints i : complaints){
            if (i.getStatus().equals(s)) {
                complaints_stat.add(i);
            }

            else Log.d("INITSTAT LIST:", "failed");
        }
        //Set header row
        TableRow tbrow0 = new TableRow(this);
        tbrow0.setBackgroundColor(Color.parseColor("#B1CF5F"));
        TextView tv0 = new TextView(this);
        tv0.setText(" Date ");
        tv0.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setText(" Status ");
        tv1.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setText(" Type ");
        tv2.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(this);
        tv3.setText(" User ");
        tv3.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv3);
        stk.addView(tbrow0);

        for (Complaints c1: complaints_stat) {
            TableRow tbrow = new TableRow(this);
            //col 1
            TextView t1v = new TextView(this);
            t1v.setText(c1.getCreated() + " ");

            t1v.setTextColor(Color.parseColor("#707070"));
            t1v.setGravity(Gravity.LEFT);
            tbrow.addView(t1v);
            //col2
            TextView t2v = new TextView(this);
            t2v.setText(" " + c1.getStatus() + " ");
            t2v.setTextColor(Color.parseColor("#707070"));
            t2v.setGravity(Gravity.LEFT);
            tbrow.addView(t2v);
            //col3
            TextView t3v = new TextView(this);
            t3v.setText(c1.getType() + " ");
            t3v.setTextColor(Color.parseColor("#707070"));
            t3v.setGravity(Gravity.LEFT);
            tbrow.addView(t3v);
            //col4
            TextView t4v = new TextView(this);
            t4v.setText(c1.getUser() + " ");
            t4v.setTextColor(Color.parseColor("#707070"));
            t4v.setGravity(Gravity.LEFT);
            tbrow.addView(t4v);
            stk.addView(tbrow);
        }
    }

    public void initUser(String s) {
        for(Complaints i : complaints){
            if (i.getUser().equals(s)) {
                complaints_user.add(i);
            }

            else Log.d("INITUSER LIST:", "failed");
        }
        //Set header row
        TableRow tbrow0 = new TableRow(this);
        tbrow0.setBackgroundColor(Color.parseColor("#B1CF5F"));
        TextView tv0 = new TextView(this);
        tv0.setText(" Date ");
        tv0.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setText(" Status ");
        tv1.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setText(" Type ");
        tv2.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(this);
        tv3.setText(" User ");
        tv3.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv3);
        stk.addView(tbrow0);

        for (Complaints c1: complaints_user) {
            TableRow tbrow = new TableRow(this);
            //col 1
            TextView t1v = new TextView(this);
            t1v.setText(c1.getCreated() + " ");

            t1v.setTextColor(Color.parseColor("#707070"));
            t1v.setGravity(Gravity.LEFT);
            tbrow.addView(t1v);
            //col2
            TextView t2v = new TextView(this);
            t2v.setText(" " + c1.getStatus() + " ");
            t2v.setTextColor(Color.parseColor("#707070"));
            t2v.setGravity(Gravity.LEFT);
            tbrow.addView(t2v);
            //col3
            TextView t3v = new TextView(this);
            t3v.setText(c1.getType() + " ");
            t3v.setTextColor(Color.parseColor("#707070"));
            t3v.setGravity(Gravity.LEFT);
            tbrow.addView(t3v);
            //col4
            TextView t4v = new TextView(this);
            t4v.setText(c1.getUser() + " ");
            t4v.setTextColor(Color.parseColor("#707070"));
            t4v.setGravity(Gravity.LEFT);
            tbrow.addView(t4v);
            stk.addView(tbrow);
        }
    }
}