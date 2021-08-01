package com.android.HMS.ui.RoomDetails.RoomUpdate;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RoomUpdateViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private Integer fSRN;
    private Integer tSRN;

    public RoomUpdateViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Room Update");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void incFromSRN() {
        fSRN++;
    }

    public void incToSRN() {
        tSRN++;
    }

    public Integer getfSRN() {
        return fSRN;
    }

    public Integer gettSRN() {
        return tSRN;
    }

    public void setfSRN(Integer fSRN) {
        this.fSRN = fSRN;
    }

    public void settSRN(Integer tSRN) {
        this.tSRN = tSRN;
    }

}