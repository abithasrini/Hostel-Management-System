package com.android.HMS.data.model;

public class RoomDetails {

    private String _Name;
    private String _Roll_No;
    private String _Dept;


    public RoomDetails(String _Name, String _Roll_No, String _Dept) {
        this._Name = _Name;
        this._Roll_No = _Roll_No;
        this._Dept = _Dept;
    }

    public RoomDetails() {
        this._Name = "";
        this._Roll_No = "";
        this._Dept = "";
    }

    public String get_Name() {
        return _Name;
    }

    public void set_Name(String _Name) {
        this._Name = _Name;
    }

    public String get_Roll_No() {
        return _Roll_No;
    }

    public void set_Roll_No(String _Roll_No) {
        this._Roll_No = _Roll_No;
    }

    public String get_Dept() {
        return _Dept;
    }

    public void set_Dept(String _Dept) {
        this._Dept = _Dept;
    }


}
