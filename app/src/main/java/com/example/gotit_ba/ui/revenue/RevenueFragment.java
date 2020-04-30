package com.example.gotit_ba.ui.revenue;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.gotit_ba.DeliveredOrdersActivity;
import com.example.gotit_ba.DriverRevenueActivity;
import com.example.gotit_ba.GotItRevenueActivity;
import com.example.gotit_ba.R;
import com.example.gotit_ba.VendorRevenueActivity;

public class RevenueFragment extends Fragment {

    Button btnGIRev, btnDriRev, btnVenRev, btnDelivered;
    private RevenueViewModel revenueViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //Intent i = new Intent(getActivity(), RevHomeActivity.class);
        //startActivity(i);
        revenueViewModel =
                ViewModelProviders.of(this).get(RevenueViewModel.class);
        View root = inflater.inflate(R.layout.fragment_revenue, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnGIRev = view.findViewById(R.id.rev_gi);
        btnDriRev = view.findViewById(R.id.rev_dri);
        btnVenRev = view.findViewById(R.id.rev_ven);
        btnDelivered = view.findViewById(R.id.delivered_ord);

        btnGIRev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), GotItRevenueActivity.class);
                startActivity(intent);
            }
        });

        btnDriRev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DriverRevenueActivity.class);
                startActivity(intent);
            }
        });

        btnVenRev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), VendorRevenueActivity.class);
                startActivity(intent);
            }
        });

        btnDelivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DeliveredOrdersActivity.class);
                startActivity(intent);
            }
        });
    }
}