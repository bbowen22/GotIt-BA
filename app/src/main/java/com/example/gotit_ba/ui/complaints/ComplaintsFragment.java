package com.example.gotit_ba.ui.complaints;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.gotit_ba.R;

public class ComplaintsFragment extends Fragment {

    private ComplaintsViewModel complaintsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        complaintsViewModel =
                ViewModelProviders.of(this).get(ComplaintsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_complaints, container, false);
        final TextView textView = root.findViewById(R.id.text_complaints);
        complaintsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}