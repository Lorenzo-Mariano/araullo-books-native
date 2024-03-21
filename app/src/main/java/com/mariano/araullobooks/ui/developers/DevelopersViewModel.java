package com.mariano.araullobooks.ui.developers;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DevelopersViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public DevelopersViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("");
    }

    public LiveData<String> getText() {
        return mText;
    }
}