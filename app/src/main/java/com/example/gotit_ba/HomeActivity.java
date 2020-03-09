package com.example.gotit_ba;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    //Variable declarations
    Button btnViewOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = new Intent(HomeActivity.this, Nav.class);
        startActivity(intent);

        /*
        //Set variables based on id from xml files
        btnViewOrders = findViewById(R.id.orders);

        //On click listener for view orders button
        btnViewOrders.setOnClickListener (new View.OnClickListener() {
            @Override
            //Navigate to all orders page
            public void onClick (View V) {
                Intent i = new Intent(HomeActivity.this, AllOrdersActivity.class);
                startActivity(i);
            }
        }); */

    }
}
