package com.android.HMS.ui.Attendance.MainAttendance;

import android.annotation.SuppressLint;
import android.widget.CheckBox;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.material.textfield.TextInputLayout;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class MainAttendanceViewModel extends ViewModel {

    private final MutableLiveData<String> _RoomNoText= new MutableLiveData<>();
    private final MutableLiveData<ArrayList<String>> _Student_Name= new MutableLiveData<>();
    private final MutableLiveData<ArrayList<String>> _Department_Name= new MutableLiveData<>();

    private final MutableLiveData<Integer> _RoomNo = new MutableLiveData<>();

    private final ArrayList<Integer> list =new ArrayList<Integer>(Arrays.asList(8,8,8,8));
    private final ArrayList<String> _Name=new ArrayList<String>(4);
    private final ArrayList<String> _Dept=new ArrayList<String>(4);

    private final MutableLiveData<ArrayList<Integer>> arrayListMutableLiveData= new MutableLiveData<>();

    private Integer roomNo;
    private final String _Block;
    private final String _Floor;


    @SuppressLint("DefaultLocale")
    public MainAttendanceViewModel(String _Block, String _Floor, Integer roomNo) {
        this.roomNo = roomNo;
        this._Block=_Block;
        this._Floor=_Floor;
        _RoomNoText.setValue(String.format("%s%s%s",_Block,_Floor, new DecimalFormat("00").format(roomNo)));
    }
    public void _Name_And_Dept(String student_Name, String department_Name) {
        _Name.add(student_Name);
        _Dept.add(department_Name);
    }

    public void _Name_And_Dept_Update(String student_Name, String department_Name, @Nullable int i) {
        _Name.set(i,student_Name);
        _Dept.set(i,department_Name);
    }

    public void _Name_And_Dept_Array() {
        _Student_Name.setValue(_Name);
        _Department_Name.setValue(_Dept);
    }


    public void incRoomNo() {
        roomNo++;
        _RoomNo.setValue(roomNo);
        _RoomNoText.setValue(String.format("%s%s%s",_Block,_Floor, new DecimalFormat("00").format(roomNo)));
    }

    public void decRoomNo() {
        roomNo--;
        _RoomNo.setValue(roomNo);
        _RoomNoText.setValue(String.format("%s%s%s",_Block,_Floor, new DecimalFormat("00").format(roomNo)));
    }

    public void setAmbulance(Boolean checkbox,int index){
        if (checkbox){
            list.set(index,0);
        }
        else{
            list.set(index,8);
        }
        arrayListMutableLiveData.setValue(list);
    }

    public void clear(CheckBox attendance,CheckBox ambulance, TextInputLayout textInputLayout){
        ambulance.setChecked(false);
        attendance.setChecked(true);
        Objects.requireNonNull(textInputLayout.getEditText()).getText().clear();
        textInputLayout.setError(null);
        list.set(0,8);
        list.set(1,8);
        list.set(2,8);
        list.set(3,8);
        arrayListMutableLiveData.setValue(list);
    }

    public LiveData<ArrayList<Integer>> getCheckBox() {
        return arrayListMutableLiveData ;
    }

    public LiveData<ArrayList<String>> get_Student_Name() {
        return _Student_Name;
    }

    public LiveData<ArrayList<String>> get_Department_Name() {
        return _Department_Name;
    }

    public LiveData<String> getRoomNoText() {
        return _RoomNoText;
    }

    public String get_Room_No_Text(){
        return String.format("%s%s%s",_Block,_Floor, new DecimalFormat("00").format(roomNo));
    }

    public int getRoomNo() {
        return roomNo;
    }

}
