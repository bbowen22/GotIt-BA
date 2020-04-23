package com.example.gotit_ba.ui.revenue;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.gotit_ba.R;
import com.example.gotit_ba.RevHomeActivity;

public class RevenueFragment extends Fragment {

    private RevenueViewModel revenueViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Intent i = new Intent(getActivity(), RevHomeActivity.class);
        startActivity(i);
        revenueViewModel =
                ViewModelProviders.of(this).get(RevenueViewModel.class);
        View root = inflater.inflate(R.layout.fragment_revenue, container, false);
        /*final TextView textView = root.findViewById(R.id.text_revenue);
        revenueViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        }); */
        return root;
    }
}
