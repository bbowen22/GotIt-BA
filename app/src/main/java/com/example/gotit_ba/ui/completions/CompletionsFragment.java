package com.example.gotit_ba.ui.completions;

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

import com.example.gotit_ba.Completions;
import com.example.gotit_ba.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class CompletionsFragment extends Fragment {

    public static int size;
    public static List<Completions> completions = new ArrayList<>();
    public static List<Completions> completions_date = new ArrayList<>();
    public static List<Completions> completions_cus = new ArrayList<>();
    public static List<Completions> completions_dri = new ArrayList<>();
    public static List<Completions> completions_sto = new ArrayList<>();
    public Button btnFilter;
    public EditText etSearch;
    public TableLayout stk;
    private CompletionsViewModel completionsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //Intent i = new Intent(getActivity(), CompletionsActivity.class);
        //startActivity(i);
        completionsViewModel =
                ViewModelProviders.of(this).get(CompletionsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_completions, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        stk = view.findViewById(R.id.tablemain3);
        btnFilter = view.findViewById(R.id.filter2);
        etSearch = view.findViewById(R.id.search2);
        loadCompletions();

        Spinner spinner = view.findViewById(R.id.completions_spinner);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Choose...");
        arrayList.add("View All");
        arrayList.add("Date");
        arrayList.add("Customer");
        arrayList.add("Driver");
        arrayList.add("Store");

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
                    if (completions.size() == 0) {
                        Toast.makeText(getContext(), "Sorry, no completions to show!", Toast.LENGTH_LONG).show();
                    }

                } else if (clicked == "Date") {
                    stk.removeAllViews();
                    etSearch.setHint("Enter as mm/dd/yyyy");
                    btnFilter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            final String s = etSearch.getText().toString();
                            ArrayList<String> list0 = new ArrayList<>();

                            Log.d("DATE:", completions.get(0).getCreated());
                            for (Completions i : completions) {
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

                } else if(clicked == "Customer") {
                    stk.removeAllViews();
                    etSearch.setHint(" ");
                    btnFilter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String s = etSearch.getText().toString();
                            ArrayList<String> list1 = new ArrayList<>();

                            Log.d("USERS:", completions.get(0).getCustomer());
                            for(Completions i : completions){
                                list1.add(i.getCustomer());
                            }
                            Log.d("FNAME:", list1.get(0));
                            Log.d("INPUT:", s);

                            if(list1.contains(s)){
                                initCus(s);
                                Log.d("FLAG:", "Made it to if...");
                            } else {
                                Toast.makeText(getContext(), "Sorry, search invalid!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                } else if(clicked == "Driver") {
                    stk.removeAllViews();
                    etSearch.setHint(" ");
                    btnFilter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String s = etSearch.getText().toString();
                            ArrayList<String> list2 = new ArrayList<>();

                            Log.d("completions:", completions.get(0).getDriver());
                            for(Completions i : completions){
                                list2.add(i.getDriver());
                            }
                            Log.d("LNAME:", list2.get(0));
                            //Log.d("LNAME:", list2.get(1));
                            Log.d("INPUT:", s);

                            if(list2.contains(s)){
                                initDri(s);
                                Log.d("FLAG:", "Made it to if...");
                            } else {
                                Toast.makeText(getContext(), "Sorry, search invalid!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                } else if(clicked == "Store") {
                    stk.removeAllViews();
                    etSearch.setHint(" ");
                    btnFilter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String s = etSearch.getText().toString();
                            ArrayList<String> list3 = new ArrayList<>();

                            Log.d("completions:", completions.get(0).getStore());
                            for (Completions i : completions) {
                                list3.add(i.getStore());
                            }
                            Log.d("UNAME:", list3.get(0));
                            //Log.d("LNAME:", list2.get(1));
                            Log.d("INPUT:", s);

                            if (list3.contains(s)) {
                                initSto(s);
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
        tv1.setText(" Customer ");
        tv1.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(getContext());
        tv2.setText(" Driver ");
        tv2.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(getContext());
        tv3.setText(" Store ");
        tv3.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv3);
        stk.addView(tbrow0);


        Log.d("table", "Size = " + completions.size());


        for (Completions c : completions) {
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
            t2v.setText(" " + c.getCustomer() + " ");
            t2v.setTextColor(Color.parseColor("#707070"));
            t2v.setGravity(Gravity.LEFT);
            tbrow.addView(t2v);
            //col3
            TextView t3v = new TextView(getContext());
            t3v.setText(" " + c.getDriver() + " ");
            t3v.setTextColor(Color.parseColor("#707070"));
            t3v.setGravity(Gravity.LEFT);
            tbrow.addView(t3v);
            //col4
            TextView t4v = new TextView(getContext());
            t4v.setText(" " + c.getStore() + " ");
            t4v.setTextColor(Color.parseColor("#707070"));
            t4v.setGravity(Gravity.LEFT);
            tbrow.addView(t4v);
            stk.addView(tbrow);
        }
    }

    public void loadCompletions() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Order");
        query.include("cus_id");
        query.include("dri_id");
        query.include("sto_id");
        query.whereEqualTo("ord_status", "Fulfilled");
        query.orderByAscending("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> orderList, ParseException e) {
                if (e == null) {

                    //Set size of table
                    size = orderList.size();
                    Log.d("SIZE", "Retrieved " + orderList.size() + " orders");


                    //Set list of order objects
                    for (ParseObject completion : orderList) {

                        ParseObject cus = (ParseObject) completion.get("cus_id");
                        ParseObject driver = (ParseObject) completion.get("dri_id");
                        ParseObject store = (ParseObject) completion.get("sto_id");

                        Log.d("driver", " " + driver.getString("dri_username"));
                        Log.d("customer", " " + cus.getString("cus_username"));
                        Log.d("store", " " + store.getString("sto_name"));

                        Completions c = new Completions(completion.getCreatedAt(), cus.getString("cus_username"), driver.getString("dri_username"), store.getString("sto_name"));
                        completions.add(c);
                        Log.d("object array date", " " + c.getCreated());
                    }

                } else {
                    Log.d("size", "Error: " + e.getMessage());

                }
            }
        });
    }

    public void initDate(String s) {
        for(Completions i : completions){
            if (i.getCreated().equals(s)) {

                Log.d("INITDATE LIST:", i.getCreated());
                completions_date.add(i);
                Log.d("INITDATE LIST:", String.valueOf(completions_date.size()));
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
        tv1.setText(" Customer ");
        tv1.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(getContext());
        tv2.setText(" Driver ");
        tv2.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(getContext());
        tv3.setText(" Store ");
        tv3.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv3);
        stk.addView(tbrow0);

        for (Completions c1: completions_date) {
            TableRow tbrow = new TableRow(getContext());
            //col 1
            TextView t1v = new TextView(getContext());
            t1v.setText(c1.getCreated() + " ");

            t1v.setTextColor(Color.parseColor("#707070"));
            t1v.setGravity(Gravity.LEFT);
            tbrow.addView(t1v);
            //col2
            TextView t2v = new TextView(getContext());
            t2v.setText(" " + c1.getCustomer() + " ");
            t2v.setTextColor(Color.parseColor("#707070"));
            t2v.setGravity(Gravity.LEFT);
            tbrow.addView(t2v);
            //col3
            TextView t3v = new TextView(getContext());
            t3v.setText(c1.getDriver() + " ");
            t3v.setTextColor(Color.parseColor("#707070"));
            t3v.setGravity(Gravity.LEFT);
            tbrow.addView(t3v);
            //col4
            TextView t4v = new TextView(getContext());
            t4v.setText(c1.getStore() + " ");
            t4v.setTextColor(Color.parseColor("#707070"));
            t4v.setGravity(Gravity.LEFT);
            tbrow.addView(t4v);
            stk.addView(tbrow);
        }
    }

    public void initCus(String s) {
        for(Completions i : completions){
            if (i.getCustomer().equals(s)) {
                completions_cus.add(i);
            }

            else Log.d("INITCUS LIST:", "failed");
        }
        //Set header row
        TableRow tbrow0 = new TableRow(getContext());
        tbrow0.setBackgroundColor(Color.parseColor("#B1CF5F"));
        TextView tv0 = new TextView(getContext());
        tv0.setText(" Date ");
        tv0.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(getContext());
        tv1.setText(" Customer ");
        tv1.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(getContext());
        tv2.setText(" Driver ");
        tv2.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(getContext());
        tv3.setText(" Store ");
        tv3.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv3);
        stk.addView(tbrow0);

        for (Completions c2: completions_cus) {
            TableRow tbrow = new TableRow(getContext());
            //col 1
            TextView t1v = new TextView(getContext());
            t1v.setText(c2.getCreated() + " ");

            t1v.setTextColor(Color.parseColor("#707070"));
            t1v.setGravity(Gravity.LEFT);
            tbrow.addView(t1v);
            //col2
            TextView t2v = new TextView(getContext());
            t2v.setText(" " + c2.getCustomer() + " ");
            t2v.setTextColor(Color.parseColor("#707070"));
            t2v.setGravity(Gravity.LEFT);
            tbrow.addView(t2v);
            //col3
            TextView t3v = new TextView(getContext());
            t3v.setText(c2.getDriver() + " ");
            t3v.setTextColor(Color.parseColor("#707070"));
            t3v.setGravity(Gravity.LEFT);
            tbrow.addView(t3v);
            //col4
            TextView t4v = new TextView(getContext());
            t4v.setText(c2.getStore() + " ");
            t4v.setTextColor(Color.parseColor("#707070"));
            t4v.setGravity(Gravity.LEFT);
            tbrow.addView(t4v);
            stk.addView(tbrow);
        }
    }

    public void initDri(String s) {
        for(Completions i : completions){
            if (i.getDriver().equals(s)) {
                completions_dri.add(i);
            }

            else Log.d("INITDRI LIST:", "failed");
        }
        //Set header row
        TableRow tbrow0 = new TableRow(getContext());
        tbrow0.setBackgroundColor(Color.parseColor("#B1CF5F"));
        TextView tv0 = new TextView(getContext());
        tv0.setText(" Date ");
        tv0.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(getContext());
        tv1.setText(" Customer ");
        tv1.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(getContext());
        tv2.setText(" Driver ");
        tv2.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(getContext());
        tv3.setText(" Store ");
        tv3.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv3);
        stk.addView(tbrow0);

        for (Completions c3: completions_dri) {
            TableRow tbrow = new TableRow(getContext());
            //col 1
            TextView t1v = new TextView(getContext());
            t1v.setText(c3.getCreated() + " ");

            t1v.setTextColor(Color.parseColor("#707070"));
            t1v.setGravity(Gravity.LEFT);
            tbrow.addView(t1v);
            //col2
            TextView t2v = new TextView(getContext());
            t2v.setText(" " + c3.getCustomer() + " ");
            t2v.setTextColor(Color.parseColor("#707070"));
            t2v.setGravity(Gravity.LEFT);
            tbrow.addView(t2v);
            //col3
            TextView t3v = new TextView(getContext());
            t3v.setText(c3.getDriver() + " ");
            t3v.setTextColor(Color.parseColor("#707070"));
            t3v.setGravity(Gravity.LEFT);
            tbrow.addView(t3v);
            //col4
            TextView t4v = new TextView(getContext());
            t4v.setText(c3.getStore() + " ");
            t4v.setTextColor(Color.parseColor("#707070"));
            t4v.setGravity(Gravity.LEFT);
            tbrow.addView(t4v);
            stk.addView(tbrow);
        }
    }
    public void initSto(String s) {
        for(Completions i : completions){
            if (i.getStore().equals(s)) {
                completions_sto.add(i);
            }

            else Log.d("INITSTO LIST:", "failed");
        }
        //Set header row
        TableRow tbrow0 = new TableRow(getContext());
        tbrow0.setBackgroundColor(Color.parseColor("#B1CF5F"));
        TextView tv0 = new TextView(getContext());
        tv0.setText(" Date ");
        tv0.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(getContext());
        tv1.setText(" Customer ");
        tv1.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(getContext());
        tv2.setText(" Driver ");
        tv2.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(getContext());
        tv3.setText(" Store ");
        tv3.setTextColor(Color.parseColor("#707070"));
        tbrow0.addView(tv3);
        stk.addView(tbrow0);

        for (Completions c4: completions_sto) {
            TableRow tbrow = new TableRow(getContext());
            //col 1
            TextView t1v = new TextView(getContext());
            t1v.setText(c4.getCreated() + " ");

            t1v.setTextColor(Color.parseColor("#707070"));
            t1v.setGravity(Gravity.LEFT);
            tbrow.addView(t1v);
            //col2
            TextView t2v = new TextView(getContext());
            t2v.setText(" " + c4.getCustomer() + " ");
            t2v.setTextColor(Color.parseColor("#707070"));
            t2v.setGravity(Gravity.LEFT);
            tbrow.addView(t2v);
            //col3
            TextView t3v = new TextView(getContext());
            t3v.setText(c4.getDriver() + " ");
            t3v.setTextColor(Color.parseColor("#707070"));
            t3v.setGravity(Gravity.LEFT);
            tbrow.addView(t3v);
            //col4
            TextView t4v = new TextView(getContext());
            t4v.setText(c4.getStore() + " ");
            t4v.setTextColor(Color.parseColor("#707070"));
            t4v.setGravity(Gravity.LEFT);
            tbrow.addView(t4v);
            stk.addView(tbrow);
        }
    }

}