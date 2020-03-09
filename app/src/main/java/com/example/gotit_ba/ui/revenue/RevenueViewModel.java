package com.example.gotit_ba.ui.revenue;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RevenueViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RevenueViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is revenue fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
