package com.mariano.araullobooks.ui.phinma;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PhinmaViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public PhinmaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("");
    }

    public LiveData<String> getText() {
        return mText;
    }
}