package com.example.gotit_ba.ui.orders;

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

import com.example.gotit_ba.Orders;
import com.example.gotit_ba.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class OrdersFragment extends Fragment {

    public static int size;
    public static List<Orders> orders = new ArrayList<>();
    public static List<Orders> orders_date = new ArrayList<>();
    public static List<Orders> orders_cus = new ArrayList<>();
    public static List<Orders> orders_dri = new ArrayList<>();
    public static List<Orders> orders_sto = new ArrayList<>();
    public Button btnFilter;
    public EditText etSearch;
    public TableLayout stk;
    private OrdersViewModel ordersViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //Intent i = new Intent(getActivity(), AllOrdersActivity.class);
        //startActivity(i);

        ordersViewModel =
                ViewModelProviders.of(this).get(OrdersViewModel.class);
        View root = inflater.inflate(R.layout.fragment_orders, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        stk = view.findViewById(R.id.tablemain);
        btnFilter = view.findViewById(R.id.filter10);
        etSearch = view.findViewById(R.id.search10);
        loadOrders();

        Spinner spinner = view.findViewById(R.id.orders_spinner);
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
                    if (orders.size() == 0) {
                        Toast.makeText(getContext(), "Sorry, no orders to show!", Toast.LENGTH_LONG).show();
                    }

                } else if (clicked == "Date") {
                    stk.removeAllViews();
                    etSearch.setHint("Enter as mm/dd/yyyy");
                    btnFilter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            final String s = etSearch.getText().toString();
                            ArrayList<String> list0 = new ArrayList<>();

                            Log.d("DATE:", orders.get(0).getCreated());
                            for (Orders i : orders) {
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

                            Log.d("USERS:", orders.get(0).getCustomer());
                            for(Orders i : orders){
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

                            Log.d("orders:", orders.get(0).getDriver());
                            for(Orders i : orders){
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

                            Log.d("orders:", orders.get(0).getStore());
                            for (Orders i : orders) {
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

        Log.d("table", "Size = " + orders.size() );
        
        for (Orders o : orders) {
            TableRow tbrow = new TableRow(getContext());
            //col 1
            TextView t1v = new TextView(getContext());
            t1v.setText(o.getCreated() + " ");
            Log.d("table created value", " " + o.getCreated());
            //t1v.setText(" ");
            t1v.setTextColor(Color.parseColor("#707070"));
            t1v.setGravity(Gravity.LEFT);
            tbrow.addView(t1v);
            //col2
            TextView t2v = new TextView(getContext());
            t2v.setText(o.getCustomer() + " " );
            t2v.setTextColor(Color.parseColor("#707070"));
            t2v.setGravity(Gravity.LEFT);
            tbrow.addView(t2v);
            //col3
            TextView t3v = new TextView(getContext());
            t3v.setText(o.getDriver() + " ");
            t3v.setTextColor(Color.parseColor("#707070"));
            t3v.setGravity(Gravity.LEFT);
            tbrow.addView(t3v);
            //col4
            TextView t4v = new TextView(getContext());
            t4v.setText(o.getStore() + " ");
            t4v.setTextColor(Color.parseColor("#707070"));
            t4v.setGravity(Gravity.LEFT);
            tbrow.addView(t4v);
            stk.addView(tbrow);
        }

    }

    public void loadOrders() {
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

                        Orders o = new Orders(order.getCreatedAt(), cus.getString("cus_username"), driver.getString("dri_username"), store.getString("sto_name"));
                        orders.add(o);
                        Log.d("object array date", " " + o.getCreated());
                    }


                } else {
                    Log.d("size", "Error: " + e.getMessage());

                }
            }
        });
    }

    public void initDate(String s) {
        for(Orders i : orders){
            if (i.getCreated().equals(s)) {

                Log.d("INITDATE LIST:", i.getCreated());
                orders_date.add(i);
                Log.d("INITDATE LIST:", String.valueOf(orders_date.size()));
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

        for (Orders c1: orders_date) {
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
        for(Orders i : orders){
            if (i.getCustomer().equals(s)) {
                orders_cus.add(i);
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

        for (Orders c2: orders_cus) {
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
        for(Orders i : orders){
            if (i.getDriver().equals(s)) {
                orders_dri.add(i);
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

        for (Orders c3: orders_dri) {
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
        for(Orders i : orders){
            if (i.getStore().equals(s)) {
                orders_sto.add(i);
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

        for (Orders c4: orders_sto) {
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