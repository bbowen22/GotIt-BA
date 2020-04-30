package com.example.gotit_ba.ui.complaints;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.gotit_ba.Complaints;
import com.example.gotit_ba.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class ComplaintsFragment extends Fragment {

    public static int size;
    public static List<Complaints> complaints = new ArrayList<>();
    public static List<Complaints> complaints_date = new ArrayList<>();
    public static List<Complaints> complaints_stat = new ArrayList<>();
    public static List<Complaints> complaints_type = new ArrayList<>();
    public static List<Complaints> complaints_user = new ArrayList<>();
    public Button btnFilter;
    public EditText etSearch;
    public TableLayout stk;
    private ComplaintsViewModel complaintsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //Intent i = new Intent(getActivity(), ComplaintsActivity.class);
        //startActivity(i);
        complaintsViewModel =
                ViewModelProviders.of(this).get(ComplaintsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_complaints, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        stk = view.findViewById(R.id.tablemain4);
        btnFilter = view.findViewById(R.id.filter1);
        etSearch = view.findViewById(R.id.search1);
        loadComplaints();

        Spinner spinner = view.findViewById(R.id.complaints_spinner);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Choose...");
        arrayList.add("View All");
        arrayList.add("Date");
        arrayList.add("Status");
        arrayList.add("Type");
        arrayList.add("User");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String clicked = parent.getItemAtPosition(position).toString();

                if (clicked == "View All") {
                    stk.removeAllViews();
                    init();
                    init();
                    if (complaints.size() == 0) {
                        Toast.makeText(getContext(), "Sorry, no complaints to show!", Toast.LENGTH_LONG).show();
                    }

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
                                Toast.makeText(getContext(), "Sorry, search invalid!", Toast.LENGTH_LONG).show();
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
                                Toast.makeText(getContext(), "Sorry, search invalid!", Toast.LENGTH_LONG).show();
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
                                Toast.makeText(getContext(), "Sorry, search invalid!", Toast.LENGTH_LONG).show();
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
                                Toast.makeText(getContext(), "Sorry, search invalid!", Toast.LENGTH_LONG).show();
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


        //Set header row
        TableRow tbrow0 = new TableRow(getContext());
        tbrow0.setBackgroundColor(Color.parseColor("#B1CF5F"));
        TextView tv0 = new TextView(getContext());
        tv0.setText(" Date ");
        tv0.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(getContext());
        tv1.setText(" Status ");
        tv1.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(getContext());
        tv2.setText(" Type ");
        tv2.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(getContext());
        tv3.setText(" User ");
        tv3.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv3);
        stk.addView(tbrow0);

        Log.d("table", "Size = " + complaints.size());

        for (Complaints c : complaints) {
            TableRow tbrow = new TableRow(getContext());
            //col 1
            TextView t1v = new TextView(getContext());
            t1v.setText(c.getCreated() + " ");
            Log.d("table created value", " " + c.getCreated());
            //t1v.setText(" ");
            t1v.setTextColor(Color.parseColor("#707070"));
            t1v.setGravity(Gravity.LEFT);
            tbrow.addView(t1v);
            //col2
            TextView t2v = new TextView(getContext());
            t2v.setText(c.getStatus() + " ");
            t2v.setTextColor(Color.parseColor("#707070"));
            t2v.setGravity(Gravity.LEFT);
            tbrow.addView(t2v);
            //col3
            TextView t3v = new TextView(getContext());
            t3v.setText(c.getType() + " ");
            t3v.setTextColor(Color.parseColor("#707070"));
            t3v.setGravity(Gravity.LEFT);
            tbrow.addView(t3v);
            //col4
            TextView t4v = new TextView(getContext());
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
        TableRow tbrow0 = new TableRow(getContext());
        tbrow0.setBackgroundColor(Color.parseColor("#B1CF5F"));
        TextView tv0 = new TextView(getContext());
        tv0.setText(" Date ");
        tv0.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(getContext());
        tv1.setText(" Status ");
        tv1.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(getContext());
        tv2.setText(" Type ");
        tv2.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(getContext());
        tv3.setText(" User ");
        tv3.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv3);
        stk.addView(tbrow0);

        for (Complaints c1: complaints_date) {
            TableRow tbrow = new TableRow(getContext());
            //col 1
            TextView t1v = new TextView(getContext());
            t1v.setText(c1.getCreated() + " ");

            t1v.setTextColor(Color.parseColor("#707070"));
            t1v.setGravity(Gravity.LEFT);
            tbrow.addView(t1v);
            //col2
            TextView t2v = new TextView(getContext());
            t2v.setText(" " + c1.getStatus() + " ");
            t2v.setTextColor(Color.parseColor("#707070"));
            t2v.setGravity(Gravity.LEFT);
            tbrow.addView(t2v);
            //col3
            TextView t3v = new TextView(getContext());
            t3v.setText(c1.getType() + " ");
            t3v.setTextColor(Color.parseColor("#707070"));
            t3v.setGravity(Gravity.LEFT);
            tbrow.addView(t3v);
            //col4
            TextView t4v = new TextView(getContext());
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
        TableRow tbrow0 = new TableRow(getContext());
        tbrow0.setBackgroundColor(Color.parseColor("#B1CF5F"));
        TextView tv0 = new TextView(getContext());
        tv0.setText(" Date ");
        tv0.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(getContext());
        tv1.setText(" Status ");
        tv1.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(getContext());
        tv2.setText(" Type ");
        tv2.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(getContext());
        tv3.setText(" User ");
        tv3.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv3);
        stk.addView(tbrow0);

        for (Complaints c1: complaints_type) {
            TableRow tbrow = new TableRow(getContext());
            //col 1
            TextView t1v = new TextView(getContext());
            t1v.setText(c1.getCreated() + " ");

            t1v.setTextColor(Color.parseColor("#707070"));
            t1v.setGravity(Gravity.LEFT);
            tbrow.addView(t1v);
            //col2
            TextView t2v = new TextView(getContext());
            t2v.setText(" " + c1.getStatus() + " ");
            t2v.setTextColor(Color.parseColor("#707070"));
            t2v.setGravity(Gravity.LEFT);
            tbrow.addView(t2v);
            //col3
            TextView t3v = new TextView(getContext());
            t3v.setText(c1.getType() + " ");
            t3v.setTextColor(Color.parseColor("#707070"));
            t3v.setGravity(Gravity.LEFT);
            tbrow.addView(t3v);
            //col4
            TextView t4v = new TextView(getContext());
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
        TableRow tbrow0 = new TableRow(getContext());
        tbrow0.setBackgroundColor(Color.parseColor("#B1CF5F"));
        TextView tv0 = new TextView(getContext());
        tv0.setText(" Date ");
        tv0.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(getContext());
        tv1.setText(" Status ");
        tv1.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(getContext());
        tv2.setText(" Type ");
        tv2.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(getContext());
        tv3.setText(" User ");
        tv3.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv3);
        stk.addView(tbrow0);

        for (Complaints c1: complaints_stat) {
            TableRow tbrow = new TableRow(getContext());
            //col 1
            TextView t1v = new TextView(getContext());
            t1v.setText(c1.getCreated() + " ");

            t1v.setTextColor(Color.parseColor("#707070"));
            t1v.setGravity(Gravity.LEFT);
            tbrow.addView(t1v);
            //col2
            TextView t2v = new TextView(getContext());
            t2v.setText(" " + c1.getStatus() + " ");
            t2v.setTextColor(Color.parseColor("#707070"));
            t2v.setGravity(Gravity.LEFT);
            tbrow.addView(t2v);
            //col3
            TextView t3v = new TextView(getContext());
            t3v.setText(c1.getType() + " ");
            t3v.setTextColor(Color.parseColor("#707070"));
            t3v.setGravity(Gravity.LEFT);
            tbrow.addView(t3v);
            //col4
            TextView t4v = new TextView(getContext());
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
        TableRow tbrow0 = new TableRow(getContext());
        tbrow0.setBackgroundColor(Color.parseColor("#B1CF5F"));
        TextView tv0 = new TextView(getContext());
        tv0.setText(" Date ");
        tv0.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(getContext());
        tv1.setText(" Status ");
        tv1.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(getContext());
        tv2.setText(" Type ");
        tv2.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(getContext());
        tv3.setText(" User ");
        tv3.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv3);
        stk.addView(tbrow0);

        for (Complaints c1: complaints_user) {
            TableRow tbrow = new TableRow(getContext());
            //col 1
            TextView t1v = new TextView(getContext());
            t1v.setText(c1.getCreated() + " ");

            t1v.setTextColor(Color.parseColor("#707070"));
            t1v.setGravity(Gravity.LEFT);
            tbrow.addView(t1v);
            //col2
            TextView t2v = new TextView(getContext());
            t2v.setText(" " + c1.getStatus() + " ");
            t2v.setTextColor(Color.parseColor("#707070"));
            t2v.setGravity(Gravity.LEFT);
            tbrow.addView(t2v);
            //col3
            TextView t3v = new TextView(getContext());
            t3v.setText(c1.getType() + " ");
            t3v.setTextColor(Color.parseColor("#707070"));
            t3v.setGravity(Gravity.LEFT);
            tbrow.addView(t3v);
            //col4
            TextView t4v = new TextView(getContext());
            t4v.setText(c1.getUser() + " ");
            t4v.setTextColor(Color.parseColor("#707070"));
            t4v.setGravity(Gravity.LEFT);
            tbrow.addView(t4v);
            stk.addView(tbrow);
        }
    
    }
}