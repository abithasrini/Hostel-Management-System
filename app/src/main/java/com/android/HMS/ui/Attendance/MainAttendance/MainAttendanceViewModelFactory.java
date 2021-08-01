package com.android.HMS.ui.Attendance.MainAttendance;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

public class MainAttendanceViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    private final Integer _RoomNo;
    private final String _Block;
    private final String _Floor;
    public MainAttendanceViewModelFactory(@NonNull String _Block, @NonNull String _Floor, @NonNull Integer _RoomNo){
        this._Block=_Block;
        this._RoomNo=_RoomNo;
        this._Floor=_Floor;
    }
    @SuppressWarnings("unchecked")
    @NotNull
    @Override
    public <T extends ViewModel> T create(@NonNull @NotNull Class<T> modelClass) {
        if(modelClass == MainAttendanceViewModel.class){
            return (T) new MainAttendanceViewModel(_Block,_Floor,_RoomNo);        }
        else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
