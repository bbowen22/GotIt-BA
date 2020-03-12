package com.example.gotit_ba.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.gotit_ba.AllOrdersActivity;
import com.example.gotit_ba.ComplaintsActivity;
import com.example.gotit_ba.CompletionsActivity;
import com.example.gotit_ba.R;
import com.example.gotit_ba.UsersActivity;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;


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
                Intent i = new Intent(getActivity(), AllOrdersActivity.class);
                startActivity(i);
            }
        });

        //on click for users page
        final Button btnViewUsers = root.findViewById(R.id.view_users);
        btnViewUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), UsersActivity.class);
                startActivity(i);
            }
        });


        //on click for completions page
        final Button btnViewCompletions = root.findViewById(R.id.view_completions);
        btnViewCompletions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CompletionsActivity.class);
                startActivity(i);
            }
        });

        //on click for complaints page
        final Button btnViewComplaints = root.findViewById(R.id.view_complaints);
        btnViewComplaints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ComplaintsActivity.class);
                startActivity(i);
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