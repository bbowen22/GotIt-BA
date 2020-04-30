package com.example.gotit_ba.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.gotit_ba.R;
import com.example.gotit_ba.ui.cancellations.CancellationsFragment;
import com.example.gotit_ba.ui.complaints.ComplaintsFragment;
import com.example.gotit_ba.ui.completions.CompletionsFragment;
import com.example.gotit_ba.ui.orders.OrdersFragment;
import com.example.gotit_ba.ui.revenue.RevenueFragment;
import com.example.gotit_ba.ui.users.UsersFragment;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentTransaction frag1;
    private FragmentTransaction frag2;
    private FragmentTransaction frag3;
    private FragmentTransaction frag4;
    private FragmentTransaction frag5;
    private FragmentTransaction frag6;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        //on click for orders page
        final Button btnViewOrders = root.findViewById(R.id.view_orders);
        btnViewOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(getActivity(), AllOrdersActivity.class);
                //startActivity(i);
                frag1 = getActivity().getSupportFragmentManager().beginTransaction();
                OrdersFragment newFrag = new OrdersFragment();
                frag1.replace(R.id.nav_host_fragment, newFrag);
                frag1.addToBackStack(null);
                frag1.commit();
            }
        });

        //on click for users page
        final Button btnViewUsers = root.findViewById(R.id.view_users);
        btnViewUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(getActivity(), UsersActivity.class);
                //startActivity(i);
                frag2 = getActivity().getSupportFragmentManager().beginTransaction();
                UsersFragment newFrag = new UsersFragment();
                frag2.replace(R.id.nav_host_fragment, newFrag);
                frag2.addToBackStack(null);
                frag2.commit();
            }
        });


        //on click for completions page
        final Button btnViewCompletions = root.findViewById(R.id.view_completions);
        btnViewCompletions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(getActivity(), CompletionsActivity.class);
                //startActivity(i);
                frag3 = getActivity().getSupportFragmentManager().beginTransaction();
                CompletionsFragment newFrag = new CompletionsFragment();
                frag3.replace(R.id.nav_host_fragment, newFrag);
                frag3.addToBackStack(null);
                frag3.commit();
            }
        });

        //on click for complaints page
        final Button btnViewComplaints = root.findViewById(R.id.view_complaints);
        btnViewComplaints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(getActivity(), ComplaintsActivity.class);
                //startActivity(i);
                frag4 = getActivity().getSupportFragmentManager().beginTransaction();
                ComplaintsFragment newFrag = new ComplaintsFragment();
                frag4.replace(R.id.nav_host_fragment, newFrag);
                frag4.addToBackStack(null);
                frag4.commit();
            }
        });

        //on click for cancellations page
        final Button btnViewCancellations = root.findViewById(R.id.view_cancellations);
        btnViewCancellations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(getActivity(), CancellationsActivity.class);
                //startActivity(i);
                frag5 = getActivity().getSupportFragmentManager().beginTransaction();
                CancellationsFragment newFrag = new CancellationsFragment();
                frag5.replace(R.id.nav_host_fragment, newFrag);
                frag5.addToBackStack(null);
                frag5.commit();
            }
        });

        //on click for revenue page
        final Button btnViewRevenue = root.findViewById(R.id.view_revenue);
        btnViewRevenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(getActivity(), RevHomeActivity.class);
                //startActivity(i);
                frag6 = getActivity().getSupportFragmentManager().beginTransaction();
                RevenueFragment newFrag = new RevenueFragment();
                frag6.replace(R.id.nav_host_fragment, newFrag);
                frag6.addToBackStack(null);
                frag6.commit();
            }
        });

        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}