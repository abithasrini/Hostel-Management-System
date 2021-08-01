
package com.android.HMS.ui.Attendance.MainAttendance;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import com.android.HMS.R;
import com.android.HMS.data.model.RoomDetails;
import com.android.HMS.databinding.ActivityMainAttendanceBinding;
import com.android.HMS.ui.Attendance.Attendance;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;


public class MainAttendance extends AppCompatActivity {

    private ActivityMainAttendanceBinding binding;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference attendanceReference;

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_attendance);

        Intent bundle = getIntent();
        String date=bundle.getStringExtra("Date");
        String mode=bundle.getStringExtra("Mode");
        ArrayList<String> _Room_No=bundle.getStringArrayListExtra("Room Number");
        ArrayList<String> _Date_Array=bundle.getStringArrayListExtra("Date Array");
        ArrayList<CheckBox> _Student_Ambulance=new ArrayList<>(4);
        ArrayList<CheckBox> _Student_Attendance=new ArrayList<>(4);
        ArrayList<ConstraintLayout> _Student_Ambulance_Charges=new ArrayList<>(4);
        ArrayList<TextInputLayout> _Student_Ambulance_Amount=new ArrayList<>(4);
        ArrayList<TextInputEditText> _Student_Ambulance_Amount_Edit_Text=new ArrayList<>(4);
        ArrayList<TextView> _Student_Ambulance_Name=new ArrayList<>(4);
        ArrayList<TextView> _Student_Text_Name=new ArrayList<>(4);
        ArrayList<TextView> _Student_Text_Dept=new ArrayList<>(4);
        ArrayList<TableRow> _Student_Name_Table=new ArrayList<>(4);
        Collections.addAll(_Student_Name_Table,findViewById(R.id.student1TextView),findViewById(R.id.student2TextView),findViewById(R.id.student3TextView),findViewById(R.id.student4TextView));
        Collections.addAll(_Student_Text_Name,findViewById(R.id.student1Name),findViewById(R.id.student2Name),findViewById(R.id.student3Name),findViewById(R.id.student4Name));
        Collections.addAll(_Student_Text_Dept,findViewById(R.id.student1Dept),findViewById(R.id.student2Dept),findViewById(R.id.student3Dept),findViewById(R.id.student4Dept));
        Collections.addAll(_Student_Ambulance,findViewById(R.id.student1Ambulance),findViewById(R.id.student2Ambulance),findViewById(R.id.student3Ambulance),findViewById(R.id.student4Ambulance));
        Collections.addAll(_Student_Attendance,findViewById(R.id.student1Attendance),findViewById(R.id.student2Attendance),findViewById(R.id.student3Attendance),findViewById(R.id.student4Attendance));
        Collections.addAll(_Student_Ambulance_Charges,findViewById(R.id.student1),findViewById(R.id.student2),findViewById(R.id.student3),findViewById(R.id.student4));
        Collections.addAll(_Student_Ambulance_Amount,findViewById(R.id.ambulanceChargesAmountStudent1),findViewById(R.id.ambulanceChargesAmountStudent2),findViewById(R.id.ambulanceChargesAmountStudent3),findViewById(R.id.ambulanceChargesAmountStudent4));
        Collections.addAll(_Student_Ambulance_Amount_Edit_Text,findViewById(R.id.ambulanceChargesAmountStudent1EditText),findViewById(R.id.ambulanceChargesAmountStudent2EditText),findViewById(R.id.ambulanceChargesAmountStudent3EditText),findViewById(R.id.ambulanceChargesAmountStudent4EditText));
        Collections.addAll(_Student_Ambulance_Name,findViewById(R.id.ambulanceChargesStudent1),findViewById(R.id.ambulanceChargesStudent2),findViewById(R.id.ambulanceChargesStudent3),findViewById(R.id.ambulanceChargesStudent4));
        final TextView roomNoText=findViewById(R.id.room_no);
        final Button previous = findViewById(R.id.previousButton);
        final Button saveAndNext = findViewById(R.id.saveAndNext);
        final TextView Date= findViewById(R.id.date);
        String block = _Room_No.get(0);
        String floor =  _Room_No.get(1);
        int srn= Integer.parseInt(_Room_No.get(2));
        int ern= Integer.parseInt(_Room_No.get(3));

        MainAttendanceViewModelFactory mainAttendanceViewModelFactory=new MainAttendanceViewModelFactory(block,floorName(floor), srn);
        MainAttendanceViewModel mainAttendanceViewModel = new ViewModelProvider(this,mainAttendanceViewModelFactory).get(MainAttendanceViewModel.class);

        firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference roomReference= firebaseDatabase.getReference("0/HMS/CietKkcasCimat/NBH/Room_Details");

        attendanceReference=firebaseDatabase.getReference().child("0/HMS/CietKkcasCimat/NBH/Attendance").child(Objects.requireNonNull(_Date_Array).get(2)).child(_Date_Array.get(1));
        final DataSnapshot[] _DataSnapshot = new DataSnapshot[2];
        mainAttendanceViewModel.getRoomNoText().observe(this, roomNoText::setText);
        Date.setText(date);
        textWatcher(_Student_Ambulance, _Student_Ambulance_Amount, _Student_Ambulance_Amount_Edit_Text);
        mainAttendanceViewModel.getCheckBox().observe(this, checkbox -> { for (int i=0;i<4;i++) { _Student_Ambulance_Charges.get(i).setVisibility(checkbox.get(i)); } });
        for (int i=0;i<4;i++) {
            int finalI = i;
            _Student_Ambulance.get(i).setOnClickListener(v -> mainAttendanceViewModel.setAmbulance(_Student_Ambulance.get(finalI).isChecked(), finalI));
        }

        roomReference.child(block).child(floor).get().addOnSuccessListener(dataSnapshot -> {
            _DataSnapshot[0] =dataSnapshot;
            // For Loading to array
            downloadDetails(mainAttendanceViewModel,_Student_Name_Table,_DataSnapshot[0]);
            setDetails(mainAttendanceViewModel,_Student_Text_Name,_Student_Ambulance_Name,_Student_Text_Dept);
            if (!mode.equals("Take Attendance")){
                loadDetails(mainAttendanceViewModel,_DataSnapshot,_Student_Attendance,_Student_Ambulance,_Student_Ambulance_Amount_Edit_Text, _Date_Array.get(0));
            }
            saveAndNext.setEnabled(true);

        });

        attendanceReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                _DataSnapshot[1]=snapshot;
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });

        previous.setOnClickListener(v->{
            if(mainAttendanceViewModel.getRoomNo() >srn){
                mainAttendanceViewModel.decRoomNo();
                mainAttendanceViewModel.getRoomNoText().observe(this, roomNoText::setText);
                clearDetails(mainAttendanceViewModel,_Student_Attendance,_Student_Ambulance,_Student_Ambulance_Amount);
                downloadDetails(mainAttendanceViewModel,_Student_Name_Table,_DataSnapshot[0]);
                setDetails(mainAttendanceViewModel,_Student_Text_Name,_Student_Ambulance_Name,_Student_Text_Dept);
            }
            if (mainAttendanceViewModel.getRoomNo() <=srn){
                previous.setEnabled(false);
            }
            loadDetails(mainAttendanceViewModel,_DataSnapshot,_Student_Attendance,_Student_Ambulance,_Student_Ambulance_Amount_Edit_Text, _Date_Array.get(0));
            if (mode.equals("View Attendance")){
                saveAndNext.setText("Next");
            }
            else {
                if (saveAndNext.getText().equals("Save")){
                    saveAndNext.setText("Save & Next");
                }
            }
            saveAndNext.setEnabled(true);
        });

        saveAndNext.setOnClickListener(v-> {

            boolean error=false;
            textWatcher(_Student_Ambulance, _Student_Ambulance_Amount, _Student_Ambulance_Amount_Edit_Text);
            for (int j=0;j<4;j++){

                if (_Student_Ambulance.get(j).isChecked()){
                    Log.d("Hi",_Student_Ambulance_Amount_Edit_Text.get(j).getText().toString() );
                    if (_Student_Ambulance_Amount_Edit_Text.get(j).getText().toString().isEmpty()){
                        dataValid(_Student_Ambulance_Amount.get(j));
                        error=true;
                    }
                }
            }
            if (!error) {
                if (mainAttendanceViewModel.getRoomNo() < ern) {
                    if (!mode.equals("View Attendance")){
                        uploadDetails(mainAttendanceViewModel,_DataSnapshot[0],roomNoText.getText().toString(),_Date_Array,_Student_Attendance,_Student_Ambulance,_Student_Ambulance_Amount_Edit_Text);
                    }

                    mainAttendanceViewModel.incRoomNo();

                    mainAttendanceViewModel.getRoomNoText().observe(this, roomNoText::setText);

                    clearDetails(mainAttendanceViewModel,_Student_Attendance,_Student_Ambulance,_Student_Ambulance_Amount);

                    if (!mode.equals("Take Attendance"))
                        loadDetails(mainAttendanceViewModel, _DataSnapshot, _Student_Attendance, _Student_Ambulance, _Student_Ambulance_Amount_Edit_Text, _Date_Array.get(0));

                    downloadDetails(mainAttendanceViewModel,_Student_Name_Table,_DataSnapshot[0]);
                    setDetails(mainAttendanceViewModel,_Student_Text_Name,_Student_Ambulance_Name,_Student_Text_Dept);
                }

                if (saveAndNext.getText().equals("Save")){
                    Intent intent=new Intent(MainAttendance.this, Attendance.class);
                    Toast.makeText(this, "Attendance saved Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
                if (mainAttendanceViewModel.getRoomNo() == ern) saveAndNext.setText(R.string.save);

                previous.setEnabled(true);
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void textWatcher(ArrayList<CheckBox> student_ambulance, ArrayList<TextInputLayout> student_ambulance_amount, ArrayList<TextInputEditText> student_ambulance_amount_edit_text) {
        for (int i=0;i<4;i++){
            textWatcherFunction(student_ambulance_amount.get(i),student_ambulance_amount_edit_text.get(i),student_ambulance.get(i).isChecked());
        }
    }

    public void textWatcherFunction(TextInputLayout emptyCheckLayout, TextInputEditText emptyCheckEditText,boolean check) {

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
        if (check) emptyCheckEditText.addTextChangedListener(textWatcher);
        else emptyCheckEditText.removeTextChangedListener(textWatcher);

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

    public String floorName(String floor){
        switch(floor){
            case "Ground Floor":
                return "0";

            case "First Floor":
                return "1";

            case "Second Floor":
                return "2";

            default:
                return "3";
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

    public void uploadDetails(MainAttendanceViewModel mainAttendanceViewModel, DataSnapshot dataSnapshot, String roomNo, ArrayList<String> _Date_Array, ArrayList<CheckBox> _Student_Attendance, ArrayList<CheckBox> _Student_Ambulance, ArrayList<TextInputEditText> _Student_Ambulance_Amount_Edit_Text){
        for (int i = 0; i < 4; i++) {
            @SuppressLint("DefaultLocale")
            RoomDetails roomDetails = dataSnapshot.child(new DecimalFormat("00").format(mainAttendanceViewModel.getRoomNo())).child(String.format("%s%d", "Student ", i + 1)).getValue(RoomDetails.class);
            if (roomDetails != null) {
                if (!roomDetails.get_Name().equals("")) {
                    if (_Student_Attendance.get(i).isChecked()) {
                        attendanceReference.child(roomNo).child(String.format("%s (%s)", roomDetails.get_Name(), roomDetails.get_Roll_No())).child(_Date_Array.get(0)).child("Attendance").setValue("/");
                    }
                    if (!_Student_Attendance.get(i).isChecked()) {
                        attendanceReference.child(roomNo).child(String.format("%s (%s)", roomDetails.get_Name(), roomDetails.get_Roll_No())).child(_Date_Array.get(0)).child("Attendance").setValue("A");
                    }
                    if (_Student_Ambulance.get(i).isChecked()) {
                        attendanceReference.child(roomNo).child(String.format("%s (%s)", roomDetails.get_Name(), roomDetails.get_Roll_No())).child(_Date_Array.get(0)).child("Ambulance").setValue("Yes");
                        attendanceReference.child(roomNo).child(String.format("%s (%s)", roomDetails.get_Name(), roomDetails.get_Roll_No())).child(_Date_Array.get(0)).child("Ambulance Amount").setValue(Integer.parseInt(_Student_Ambulance_Amount_Edit_Text.get(i).getText().toString()));
                    }
                    if (!_Student_Ambulance.get(i).isChecked()) {
                        attendanceReference.child(roomNo).child(String.format("%s (%s)", roomDetails.get_Name(), roomDetails.get_Roll_No())).child(_Date_Array.get(0)).child("Ambulance").setValue("No");
                        attendanceReference.child(roomNo).child(String.format("%s (%s)", roomDetails.get_Name(), roomDetails.get_Roll_No())).child(_Date_Array.get(0)).child("Ambulance Amount").setValue(0);

                    }
                }
            }
        }
    }

    public void downloadDetails(MainAttendanceViewModel mainAttendanceViewModel,ArrayList<TableRow> _Student_Name_Table,DataSnapshot dataSnapshot) {
        try {
            for (int i = 0; i < 4; i++) {
                @SuppressLint("DefaultLocale")
                RoomDetails roomDetails = dataSnapshot.child(new DecimalFormat("00").format(mainAttendanceViewModel.getRoomNo())).child(String.format("%s%d", "Student ", i + 1)).getValue(RoomDetails.class);
                if (roomDetails != null) {
                    if (!roomDetails.get_Name().equals("")) {
                        _Student_Name_Table.get(i).setVisibility(View.VISIBLE);
                        mainAttendanceViewModel._Name_And_Dept_Update(roomDetails.get_Name(), roomDetails.get_Dept(), i);
                    } else {
                        _Student_Name_Table.get(i).setVisibility(View.INVISIBLE);
                    }
                }
            }
        }catch (IndexOutOfBoundsException e){
            for (int i = 0; i < 4; i++) {
                @SuppressLint("DefaultLocale")
                RoomDetails roomDetails = dataSnapshot.child(new DecimalFormat("00").format(mainAttendanceViewModel.getRoomNo())).child(String.format("%s%d", "Student ", i + 1)).getValue(RoomDetails.class);
                if (roomDetails != null) {
                    if (!roomDetails.get_Name().equals("")) {
                        _Student_Name_Table.get(i).setVisibility(View.VISIBLE);
                        mainAttendanceViewModel._Name_And_Dept(roomDetails.get_Name(), roomDetails.get_Dept());
                    } else {
                        _Student_Name_Table.get(i).setVisibility(View.INVISIBLE);
                    }
                }
            }
        }
        mainAttendanceViewModel._Name_And_Dept_Array();
    }

    public void loadDetails(MainAttendanceViewModel mainAttendanceViewModel, DataSnapshot[] _DataSnapshot,ArrayList<CheckBox> _Student_Attendance,ArrayList<CheckBox> _Student_Ambulance, ArrayList<TextInputEditText> _Student_Ambulance_Amount_Edit_Text,String _Date){
        for (int i = 0; i < 4; i++) {
            @SuppressLint("DefaultLocale")
            RoomDetails roomDetails = _DataSnapshot[0].child(new DecimalFormat("00").format(mainAttendanceViewModel.getRoomNo())).child(String.format("%s%d", "Student ", i + 1)).getValue(RoomDetails.class);
            DataSnapshot dataSnapshots=_DataSnapshot[1].child(mainAttendanceViewModel.get_Room_No_Text()).child(String.format("%s (%s)", roomDetails.get_Name(), roomDetails.get_Roll_No())).child(_Date);
            if (Objects.equals(dataSnapshots.child("Attendance").getValue(String.class), "/")){
                _Student_Attendance.get(i).setChecked(true);
            }
            if (Objects.equals(dataSnapshots.child("Attendance").getValue(String.class), "A")){
                _Student_Attendance.get(i).setChecked(false);
            }
            if (Objects.equals(dataSnapshots.child("Ambulance").getValue(String.class), "No")){
                _Student_Ambulance.get(i).setChecked(false);
            }
            if (Objects.equals(dataSnapshots.child("Ambulance").getValue(String.class), "Yes")) {
                _Student_Ambulance.get(i).setChecked(true);
                _Student_Ambulance_Amount_Edit_Text.get(i).setText(String.valueOf(dataSnapshots.child("Ambulance Amount").getValue(Long.class)));
                mainAttendanceViewModel.setAmbulance(_Student_Ambulance.get(i).isChecked(), i);
            }
        }
    }

    public void  setDetails(MainAttendanceViewModel mainAttendanceViewModel,ArrayList<TextView> _Student_Text_Name,ArrayList<TextView> _Student_Ambulance_Name,ArrayList<TextView> _Student_Text_Dept){
        for (int i=0;i<4;i++){
            int finalI=i;
            mainAttendanceViewModel.get_Student_Name().observe(this, text -> {
                _Student_Text_Name.get(finalI).setText(text.get(finalI));
                _Student_Ambulance_Name.get(finalI).setText(String.format("%s%s","Ambulance Charges for ",text.get(finalI)));
            });
            mainAttendanceViewModel.get_Department_Name().observe(this, text-> _Student_Text_Dept.get(finalI).setText(text.get(finalI)));
        }
    }

    public void clearDetails(MainAttendanceViewModel mainAttendanceViewModel,ArrayList<CheckBox> _Student_Attendance,ArrayList<CheckBox> _Student_Ambulance,ArrayList<TextInputLayout> _Student_Ambulance_Amount){
        for (int i = 0; i < 4; i++)
            mainAttendanceViewModel.clear(_Student_Attendance.get(i), _Student_Ambulance.get(i), _Student_Ambulance_Amount.get(i));
    }

}

