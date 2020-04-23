package com.example.gotit_ba;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class RevHomeActivity extends AppCompatActivity {

    Button btnGIRev, btnDriRev, btnVenRev, btnDelivered;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rev_home);

        btnGIRev = findViewById(R.id.rev_gi);
        btnDriRev = findViewById(R.id.rev_dri);
        btnVenRev = findViewById(R.id.rev_ven);
        btnDelivered = findViewById(R.id.delivered_ord);

        btnGIRev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RevHomeActivity.this, GotItRevenueActivity.class);
                startActivity(intent);
            }
        });

        btnDriRev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RevHomeActivity.this, DriverRevenueActivity.class);
                startActivity(intent);
            }
        });

        btnVenRev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RevHomeActivity.this, VendorRevenueActivity.class);
                startActivity(intent);
            }
        });

        btnDelivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RevHomeActivity.this, DeliveredOrdersActivity.class);
                startActivity(intent);
            }
        });
    }
}
