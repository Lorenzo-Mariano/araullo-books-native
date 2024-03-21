package com.mariano.araullobooks.ui.campus;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CampusViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CampusViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("");
    }

    public LiveData<String> getText() {
        return mText;
    }
}