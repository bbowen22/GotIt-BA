package com.example.gotit_ba.ui.cancellations;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CancellationsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CancellationsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}