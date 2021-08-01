package com.android.HMS.data.model;

import java.util.ArrayList;
import java.util.Collections;

public class AccessLevels {

    private String _Student_Details;
    private String _Attendance;
    private String _Room_Details;
    private String _Mess_Bill;
    private String _Administration;

    ArrayList<String> arrayList =new ArrayList<>(5);

    public AccessLevels(boolean b) {
        _Student_Details ="No";
        _Attendance ="No";
        _Room_Details ="No";
        _Mess_Bill ="No";
        _Administration ="No";
    }

    public AccessLevels(String _Student_Details, String _Attendance, String _Room_Details, String _Mess_Bill, String _Administration) {
        this._Student_Details = _Student_Details;
        this._Attendance = _Attendance;
        this._Room_Details = _Room_Details;
        this._Mess_Bill = _Mess_Bill;
        this._Administration = _Administration;
    }
    public AccessLevels(){

    }

    public String get_Student_Details() {
        return _Student_Details;
    }

    public void set_Student_Details(String Student_Details) {
        this._Student_Details = Student_Details;
    }

    public String get_Attendance() {
        return _Attendance;
    }

    public void set_Attendance(String Attendance) {
        this._Attendance = Attendance;
    }

    public String get_Room_Details() {
        return _Room_Details;
    }

    public void set_Room_Details(String Room_Details) {
        this._Room_Details = Room_Details;
    }

    public String get_Mess_Bill() {
        return _Mess_Bill;
    }

    public void set_Mess_Bill(String Mess_Bill) {
        this._Mess_Bill = Mess_Bill;
    }

    public String get_Administration() {
        return _Administration;
    }

    public void set_Administration(String Administration) {
        this._Administration = Administration;
    }

    public ArrayList<String> getAll(){
        Collections.addAll(arrayList,_Administration,_Attendance,_Mess_Bill,_Room_Details,_Student_Details);
        return arrayList;
    }


}
