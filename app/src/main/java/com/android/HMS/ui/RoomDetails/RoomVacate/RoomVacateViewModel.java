package com.android.HMS.ui.RoomDetails.RoomVacate;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RoomVacateViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public RoomVacateViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Room Vacate");
    }

    public LiveData<String> getText() {
        return mText;
    }
}