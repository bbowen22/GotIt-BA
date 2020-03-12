package com.example.gotit_ba.ui.completions;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.gotit_ba.CompletionsActivity;
import com.example.gotit_ba.R;

public class CompletionsFragment extends Fragment {

    private CompletionsViewModel completionsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Intent i = new Intent(getActivity(), CompletionsActivity.class);
        startActivity(i);
        completionsViewModel =
                ViewModelProviders.of(this).get(CompletionsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_completions, container, false);
        /*final TextView textView = root.findViewById(R.id.text_completions);
        completionsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return root;
    }
}