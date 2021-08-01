package com.android.HMS.data.model;

import java.util.ArrayList;

public class AttendanceDetails{

    private ArrayList<String> _Daily_Attendance=new ArrayList<>(4);
    private ArrayList<ArrayList<String>> _Monthly_Attendance=new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> _Student_Attendance=new ArrayList<>();

    public AttendanceDetails(ArrayList<String> _Daily_Attendance, ArrayList<ArrayList<String>> _Monthly_Attendance, ArrayList<ArrayList<ArrayList<String>>> _Student_Attendance) {
        this._Daily_Attendance = _Daily_Attendance;
        this._Monthly_Attendance = _Monthly_Attendance;
        this._Student_Attendance = _Student_Attendance;
    }

    public ArrayList<String> get_Daily_Attendance() {
        return _Daily_Attendance;
    }

    public void set_Daily_Attendance(ArrayList<String> _Daily_Attendance) {
        this._Daily_Attendance = _Daily_Attendance;
    }

    public ArrayList<ArrayList<String>> get_Monthly_Attendance() {
        return _Monthly_Attendance;
    }

    public void set_Monthly_Attendance(ArrayList<ArrayList<String>> _Monthly_Attendance) {
        this._Monthly_Attendance = _Monthly_Attendance;
    }

    public ArrayList<ArrayList<ArrayList<String>>> get_Student_Attendance() {
        return _Student_Attendance;
    }

    public void set_Student_Attendance(ArrayList<ArrayList<ArrayList<String>>> _Student_Attendance) {
        this._Student_Attendance = _Student_Attendance;
    }



}
