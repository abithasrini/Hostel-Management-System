package com.android.HMS.data.model;

public class UserDetails {
    private String _Name;
    private String _Email;
    private String _Phone_Number;
    private String _HMS_ID;

    AccessLevels accessLevels =new AccessLevels(false);

    public UserDetails() {
    }


    public UserDetails(String _Name, String _Email, String _Phone_Number, String _HMS_ID) {
        this._Name = _Name;
        this._Email = _Email;
        this._Phone_Number = _Phone_Number;
        this._HMS_ID = _HMS_ID;
    }

    public String get_Name() {
        return _Name;
    }

    public void set_Name(String Name) {
        this._Name = Name;
    }

    public String get_Email() {
        return _Email;
    }

    public void set_Email(String Email) {
        this._Email = Email;
    }

    public String get_Phone_Number() {
        return _Phone_Number;
    }

    public void set_Phone_Number(String Phone_Number) {
        this._Phone_Number = Phone_Number;
    }

    public String get_HMS_ID() {
        return _HMS_ID;
    }

    public void set_HMS_ID(String _HMS_ID) {
        this._HMS_ID = _HMS_ID;
    }

}
