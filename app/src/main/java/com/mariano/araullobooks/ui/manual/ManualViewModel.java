package com.mariano.araullobooks.ui.manual;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ManualViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ManualViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("");
    }

    public LiveData<String> getText() {
        return mText;
    }
}