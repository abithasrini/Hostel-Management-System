package com.android.HMS.data.model;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.HMS.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import javax.annotation.Nullable;

public class DataValidity extends AppCompatActivity {

    public DataValidity(){

    }
    public static int floor(AutoCompleteTextView blockName){
        String block_Name = blockName.getText().toString();
        if ("A".equals(block_Name)) {
            return R.array.A;
        }
        return R.array.B;

    }

    public static int courseName(AutoCompleteTextView collegeName){

        String college_Name=collegeName.getText().toString();

        switch (college_Name) {
            case "CIET":
                return R.array.CIET;
            case "CIMAT":
                return R.array.CIMAT;
            case "KKCAS":
                return R.array.KKCAS;
            default:
                return R.array.SOA;
        }

    }

    public static int branchName(AutoCompleteTextView courseName){
        String  course_Name=courseName.getText().toString().trim();
        switch (course_Name) {
            case "B E":
                return R.array.B_E;
            case "B Tech":
                return R.array.B_Tech;
            case "M Phil":
            case "Ph D":
                return R.array.M_Phil_Ph_D;
            case "B Sc":
                return R.array.B_Sc;
            case "M Sc":
                return R.array.M_Sc;
            case "B Com":
                return R.array.B_Com;
            case "M Com":
                return R.array.M_Com;
            case "B A":
                return R.array.B_A;

        }
        return R.array.M_E;
    }

    public boolean checkInternet(Activity activity) {
        ConnectivityManager connectivityManager= (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifi =connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile =connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifi!=null&&wifi.isConnected()||(mobile!=null&&mobile.isConnected()))){
            return true;
        }
        else{
            Toast.makeText(activity, "No Internet Connection is Available", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void textWatcherFunction(TextInputLayout emptyCheckLayout, @Nullable AutoCompleteTextView emptyCheckEditText,@Nullable TextInputEditText textInputEditText) {

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                dataValid(emptyCheckLayout);
            }
        };

        try {

            if (!emptyCheckEditText.equals(null)){
                emptyCheckEditText.addTextChangedListener(textWatcher);
            }
        } catch (NullPointerException E){
            if (!textInputEditText.equals(null)){
                textInputEditText.addTextChangedListener(textWatcher);
            }
        }
    }

    public void dataValid(TextInputLayout emptyCheck) {

        if(Objects.requireNonNull(emptyCheck.getEditText()).getText().toString().isEmpty()) {
            emptyCheck.setHelperTextEnabled(true);
            emptyCheck.setError("Can't Leave Blank");
        }
        else{
            emptyCheck.setHelperTextEnabled(false);
            emptyCheck.setError(null);
        }
    }


}
