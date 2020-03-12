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

public class UsersActivity extends AppCompatActivity {


    public static int size;
    public static List<Users> users = new ArrayList<>();
    ArrayList<Users> users_fname = new ArrayList<>();
    ArrayList<Users> users_lname = new ArrayList<>();
    ArrayList<Users> users_username = new ArrayList<>();
    public Button btnFilter;
    public EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        loadUsers();

       /* final Timer timer = new Timer();
        timer.schedule(new TimerTask(){
            public void run() {
                timer.cancel();
            }
        }, 1000);*/

        //init();

        Spinner spinner = findViewById(R.id.users_spinner);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Choose...");
        arrayList.add("Date");
        arrayList.add("First Name");
        arrayList.add("Last Name");
        arrayList.add("Username");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String clicked = parent.getItemAtPosition(position).toString();
                btnFilter = findViewById(R.id.filter);
                etSearch = findViewById(R.id.search);
                //Toast.makeText(parent.getContext(), "Selected: " + clicked, Toast.LENGTH_LONG).show();

                if (clicked == "Date") {
                    init();

                 /* btnFilter.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          ArrayList<String> list1 = new ArrayList<>();
                          for(Users i : users){
                              list1.add(i.getCreated());
                          }
                          final String s = etSearch.getText().toString();
                          if(list1.contains(s)){
                              loadDateFilter(s);
                              initDateFilter();
                          } else {
                              Toast.makeText(UsersActivity.this, "Sorry, search invalid! ", Toast.LENGTH_LONG).show();
                          }
                      }
                  }); */

                }

                else if(clicked == "First Name") {

                    btnFilter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String s = etSearch.getText().toString();
                            ArrayList<String> list1 = new ArrayList<>();

                            Log.d("USERS:", users.get(0).getFname());
                            for(Users i : users){
                                list1.add(i.getFname());
                            }
                            Log.d("FNAME:", list1.get(0));
                            Log.d("FNAME:", list1.get(1));
                            Log.d("INPUT:", s);

                            if(list1.contains(s)){
                                initFname(s);
                                Log.d("FLAG:", "Made it to if...");
                            } else {
                                Toast.makeText(UsersActivity.this, "Sorry, search invalid!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

                else if(clicked == "Last Name") {
                    btnFilter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String s = etSearch.getText().toString();
                            ArrayList<String> list2 = new ArrayList<>();

                            Log.d("USERS:", users.get(0).getLname());
                            for(Users i : users){
                                list2.add(i.getLname());
                            }
                            Log.d("LNAME:", list2.get(0));
                            //Log.d("LNAME:", list2.get(1));
                            Log.d("INPUT:", s);

                            if(list2.contains(s)){
                                initLname(s);
                                Log.d("FLAG:", "Made it to if...");
                            } else {
                                Toast.makeText(UsersActivity.this, "Sorry, search invalid!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

                else if(clicked == "Username") {
                    btnFilter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String s = etSearch.getText().toString();
                            ArrayList<String> list3 = new ArrayList<>();

                            Log.d("USERS:", users.get(0).getUsername());
                            for(Users i : users){
                                list3.add(i.getUsername());
                            }
                            Log.d("UNAME:", list3.get(0));
                            //Log.d("LNAME:", list2.get(1));
                            Log.d("INPUT:", s);

                            if(list3.contains(s)){
                                initUsername(s);
                                Log.d("FLAG:", "Made it to if...");
                            } else {
                                Toast.makeText(UsersActivity.this, "Sorry, search invalid!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }



            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });


    }

    //Function to populate main table
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

    public void initFname(String s) {

        for(Users i : users){
            if (i.getFname().equals(s)) {

                Log.d("INITFNAME LIST:", i.getFname());
                users_fname.add(i);
            }

            else Log.d("INITFNAME LIST:", "failed");
        }

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

            for (Users u2: users_fname) {
                TableRow tbrow = new TableRow(this);
                //col 1
                TextView t1v = new TextView(this);
                t1v.setText(u2.getCreated() + " ");

                t1v.setTextColor(Color.parseColor("#707070"));
                t1v.setGravity(Gravity.LEFT);
                tbrow.addView(t1v);
                //col2
                TextView t2v = new TextView(this);
                t2v.setText(" " + u2.getFname() + " ");
                t2v.setTextColor(Color.parseColor("#707070"));
                t2v.setGravity(Gravity.LEFT);
                tbrow.addView(t2v);
                //col3
                TextView t3v = new TextView(this);
                t3v.setText(u2.getLname() + " ");
                t3v.setTextColor(Color.parseColor("#707070"));
                t3v.setGravity(Gravity.LEFT);
                tbrow.addView(t3v);
                //col4
                TextView t4v = new TextView(this);
                t4v.setText(u2.getUsername() + " ");
                t4v.setTextColor(Color.parseColor("#707070"));
                t4v.setGravity(Gravity.LEFT);
                tbrow.addView(t4v);
                stk.addView(tbrow);
         break;
        }
    }

    public void initLname(String s) {

        for(Users i : users){
            if (i.getLname().equals(s)) {

                Log.d("INITLNAME LIST:", i.getLname());
                users_lname.add(i);
            }

            else Log.d("INITLNAME LIST:", "failed");
        }

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

        for (Users u3: users_lname) {
            TableRow tbrow = new TableRow(this);
            //col 1
            TextView t1v = new TextView(this);
            t1v.setText(u3.getCreated() + " ");

            t1v.setTextColor(Color.parseColor("#707070"));
            t1v.setGravity(Gravity.LEFT);
            tbrow.addView(t1v);
            //col2
            TextView t2v = new TextView(this);
            t2v.setText(" " + u3.getFname() + " ");
            t2v.setTextColor(Color.parseColor("#707070"));
            t2v.setGravity(Gravity.LEFT);
            tbrow.addView(t2v);
            //col3
            TextView t3v = new TextView(this);
            t3v.setText(u3.getLname() + " ");
            t3v.setTextColor(Color.parseColor("#707070"));
            t3v.setGravity(Gravity.LEFT);
            tbrow.addView(t3v);
            //col4
            TextView t4v = new TextView(this);
            t4v.setText(u3.getUsername() + " ");
            t4v.setTextColor(Color.parseColor("#707070"));
            t4v.setGravity(Gravity.LEFT);
            tbrow.addView(t4v);
            stk.addView(tbrow);
            break;
        }
    }

    public void initUsername(String s) {

        for(Users i : users){
            if (i.getUsername().equals(s)) {

                Log.d("INITUNAME LIST:", i.getUsername());
                users_username.add(i);
            }

            else Log.d("INITUNAME LIST:", "failed");
        }

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

        for (Users u4: users_username) {
            TableRow tbrow = new TableRow(this);
            //col 1
            TextView t1v = new TextView(this);
            t1v.setText(u4.getCreated() + " ");

            t1v.setTextColor(Color.parseColor("#707070"));
            t1v.setGravity(Gravity.LEFT);
            tbrow.addView(t1v);
            //col2
            TextView t2v = new TextView(this);
            t2v.setText(" " + u4.getFname() + " ");
            t2v.setTextColor(Color.parseColor("#707070"));
            t2v.setGravity(Gravity.LEFT);
            tbrow.addView(t2v);
            //col3
            TextView t3v = new TextView(this);
            t3v.setText(u4.getLname() + " ");
            t3v.setTextColor(Color.parseColor("#707070"));
            t3v.setGravity(Gravity.LEFT);
            tbrow.addView(t3v);
            //col4
            TextView t4v = new TextView(this);
            t4v.setText(u4.getUsername() + " ");
            t4v.setTextColor(Color.parseColor("#707070"));
            t4v.setGravity(Gravity.LEFT);
            tbrow.addView(t4v);
            stk.addView(tbrow);
            break;
        }
    }
}
