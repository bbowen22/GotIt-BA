package com.example.gotit_ba.ui.cancellations;

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

public class CancellationsFragment extends Fragment {

    private CancellationsViewModel cancellationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cancellationsViewModel =
                ViewModelProviders.of(this).get(CancellationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cancellations, container, false);
        final TextView textView = root.findViewById(R.id.text_cancellations);
        cancellationsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}