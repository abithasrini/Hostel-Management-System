package com.android.HMS.data.model;

public class StudentDetails {


    private String _Name;
    private String _College_Name;
    private String _Course_Name;
    private String _Branch_Name;
    private String _Batch;
    private String _Roll_No;
    private String _Blood_Group;
    private String _Phone_Number;
    private String _Room_Number;


    public StudentDetails(String _Name, String _College_Name, String _Course_Name,
                          String _Branch_Name, String _Batch, String _Roll_No,
                          String _Blood_Group, String _Phone_Number,
                          String _Room_Number) {

        this._Name = _Name;
        this._College_Name = _College_Name;
        this._Course_Name = _Course_Name;
        this._Branch_Name = _Branch_Name;
        this._Batch = _Batch;
        this._Roll_No = _Roll_No;
        this._Blood_Group = _Blood_Group;
        this._Phone_Number = _Phone_Number;
        this._Room_Number = _Room_Number;
    }

    public StudentDetails() {
    }

    public String get_Name() {
        return _Name;
    }

    public void set_Name(String _Name) {
        this._Name = _Name;
    }

    public String get_College_Name() {
        return _College_Name;
    }

    public void set_College_Name(String _College_Name) {
        this._College_Name = _College_Name;
    }

    public String get_Course_Name() {
        return _Course_Name;
    }

    public void set_Course_Name(String _Course_Name) {
        this._Course_Name = _Course_Name;
    }

    public String get_Branch_Name() {
        return _Branch_Name;
    }

    public void set_Branch_Name(String _Branch_Name) {
        this._Branch_Name = _Branch_Name;
    }

    public String get_Batch() {
        return _Batch;
    }

    public void set_Batch(String _Batch) {
        this._Batch = _Batch;
    }

    public String get_Roll_No() {
        return _Roll_No;
    }

    public void set_Roll_No(String _Roll_No) {
        this._Roll_No = _Roll_No;
    }

    public String get_Blood_Group() {
        return _Blood_Group;
    }

    public void set_Blood_Group(String _Blood_Group) {
        this._Blood_Group = _Blood_Group;
    }

    public String get_Phone_Number() {
        return _Phone_Number;
    }

    public void set_Phone_Number(String _Phone_Number) {
        this._Phone_Number = _Phone_Number;
    }


    public String get_Room_Number() {
        return _Room_Number;
    }

    public void set_Room_Number(String _Room_Number) {
        this._Room_Number = _Room_Number;
    }


}
