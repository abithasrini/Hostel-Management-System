package com.android.HMS.ui.MessBill;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.HMS.R;

import java.util.Calendar;

public class MessBill extends AppCompatActivity {

    private int mYear, mMonth, mDay;

    final Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_bill);

        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        AutoCompleteTextView startingDate=findViewById(R.id.startingDate);
        AutoCompleteTextView endingDate=findViewById(R.id.endingDate);
        startingDate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> startingDate.setText(String.format(getString(R.string.dateFormat), dayOfMonth, monthOfYear, year)), mYear, mMonth, mDay);
            datePickerDialog.show();
        });
        endingDate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> endingDate.setText(String.format(getString(R.string.dateFormat), dayOfMonth, monthOfYear + 1, year)), mYear, mMonth, mDay);
            datePickerDialog.show();
        });
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

}