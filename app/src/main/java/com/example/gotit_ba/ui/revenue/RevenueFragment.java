package com.example.gotit_ba.ui.revenue;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.gotit_ba.R;

public class RevenueFragment extends Fragment {

    private RevenueViewModel revenueViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        revenueViewModel =
                ViewModelProviders.of(this).get(RevenueViewModel.class);
        View root = inflater.inflate(R.layout.fragment_revenue, container, false);
        final TextView textView = root.findViewById(R.id.text_revenue);
        revenueViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
