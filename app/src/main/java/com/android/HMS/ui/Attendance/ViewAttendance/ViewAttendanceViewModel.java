package com.android.HMS.ui.Attendance.ViewAttendance;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Calendar;

public class ViewAttendanceViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<String> mDate;

    final Calendar calendar = Calendar.getInstance();

    @SuppressLint("DefaultLocale")
    public ViewAttendanceViewModel() {
        mText = new MutableLiveData<>();
        mDate = new MutableLiveData<>();

        mText.setValue("View Attendance");

        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);

        mDate.setValue(String.format("%d/%d/%d", mDay, mMonth +1, mYear));
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<String> getDate() {
        return mDate;
    }

}