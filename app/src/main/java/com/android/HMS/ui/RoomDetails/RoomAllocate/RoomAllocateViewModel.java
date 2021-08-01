package com.android.HMS.ui.RoomDetails.RoomAllocate;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RoomAllocateViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public RoomAllocateViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Room Allocate");
    }

    public LiveData<String> getText() {
        return mText;
    }


}