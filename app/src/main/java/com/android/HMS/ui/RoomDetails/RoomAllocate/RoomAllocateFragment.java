package com.android.HMS.ui.RoomDetails.RoomAllocate;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.HMS.R;
import com.android.HMS.data.model.DataValidity;
import com.android.HMS.data.model.RoomDetails;
import com.android.HMS.data.model.StudentDetails;
import com.android.HMS.databinding.FragmentRoomAllocateBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RoomAllocateFragment extends Fragment {

    private FragmentRoomAllocateBinding binding;
    FirebaseDatabase firebaseDatabase;
    private int _Count;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        RoomAllocateViewModel roomAllocateViewModel = new ViewModelProvider(this).get(RoomAllocateViewModel.class);

        binding = FragmentRoomAllocateBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference roomReference= FirebaseDatabase.getInstance().getReference().child("0/HMS/CietKkcasCimat/NBH/Room_Details");
        DatabaseReference studentReference=FirebaseDatabase.getInstance().getReference().child("0/HMS/CietKkcasCimat/NBH/Students_Details");

        final TextView textView = binding.textRoomAllocate;
        roomAllocateViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);


        final AutoCompleteTextView blockSpinner = binding.block;
        ArrayAdapter<CharSequence> blockAdapter = ArrayAdapter.createFromResource(root.getContext(), R.array.block, android.R.layout.simple_dropdown_item_1line);
        blockAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        blockSpinner.setAdapter(blockAdapter);

        final AutoCompleteTextView floorSpinner = binding.floor;
        blockSpinner.setOnItemClickListener((parent, view, position, id) -> {
            if (!floorSpinner.getText().toString().isEmpty()){
                floorSpinner.setText("");
                binding.floorLayout.setHelperTextEnabled(false);
                binding.floorLayout.setError(null);
            }
            ArrayAdapter<CharSequence> floorAdapter = ArrayAdapter.createFromResource(root.getContext(), DataValidity.floor(blockSpinner), android.R.layout.simple_dropdown_item_1line);
            floorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            floorSpinner.setAdapter(floorAdapter);

        });

        final AutoCompleteTextView roomNoSpinner = binding.roomNumber;{
            ArrayAdapter<CharSequence> roomNoAdapter = ArrayAdapter.createFromResource(root.getContext(), R.array.room_no, android.R.layout.simple_dropdown_item_1line);
            roomNoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            roomNoSpinner.setAdapter(roomNoAdapter);
        }
        final AutoCompleteTextView collegeNameSpinner = binding.collegeName;{
        ArrayAdapter<CharSequence> collegeNameAdapter = ArrayAdapter.createFromResource(root.getContext(), R.array.college_name, android.R.layout.simple_dropdown_item_1line);
        collegeNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        collegeNameSpinner.setAdapter(collegeNameAdapter);}
        final AutoCompleteTextView courseNameSpinner = binding.courseName;
        final AutoCompleteTextView branchNameSpinner = binding.branchName;
        final AutoCompleteTextView bloodGroupSpinner = binding.bloodGroup;{
            ArrayAdapter<CharSequence> bloodGroupAdapter = ArrayAdapter.createFromResource(root.getContext(), R.array.blood_group, android.R.layout.simple_dropdown_item_1line);
            bloodGroupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            bloodGroupSpinner.setAdapter(bloodGroupAdapter);}

        collegeNameSpinner.setOnItemClickListener((parent, view, position, id) -> {
            if (!courseNameSpinner.getText().toString().isEmpty()){
                courseNameSpinner.setText("");
                binding.courseNameLayout.setHelperTextEnabled(false);
                binding.courseNameLayout.setError(null);
            }
            if (!branchNameSpinner.getText().toString().isEmpty()){
                branchNameSpinner.setText("");
                binding.branchNameLayout.setHelperTextEnabled(false);
                binding.branchNameLayout.setError(null);
            }
            binding.branchNameLayout.setEnabled(!collegeNameSpinner.getText().toString().equals("SOA"));
            ArrayAdapter<CharSequence> courseNameAdapter = ArrayAdapter.createFromResource(root.getContext(), DataValidity.courseName(collegeNameSpinner), android.R.layout.simple_dropdown_item_1line);
            courseNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            courseNameSpinner.setAdapter(courseNameAdapter);

        });
        courseNameSpinner.setOnItemClickListener((parent, view, position, id) -> {
            if (!branchNameSpinner.getText().toString().isEmpty()){
                branchNameSpinner.setText("");
                binding.branchNameLayout.setHelperTextEnabled(false);
                binding.branchNameLayout.setError(null);
            }
            switch (courseNameSpinner.getText().toString()) {
                case "MBA":
                case "MCA":
                case "PGDCA":
                case "B Arch":
                    binding.branchNameLayout.setEnabled(false);
                    break;
                default:
                    binding.branchNameLayout.setEnabled(true);
                    ArrayAdapter<CharSequence> branchNameAdapter = ArrayAdapter.createFromResource(root.getContext(), DataValidity.branchName(courseNameSpinner), android.R.layout.simple_dropdown_item_1line);
                    branchNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    branchNameSpinner.setAdapter(branchNameAdapter);
                    break;
            }

        });

        //TextWatcher
        {
            textWatcherFunction(binding.blockLayout, binding.block, null);
            textWatcherFunction(binding.floorLayout, binding.floor, null);
            textWatcherFunction(binding.roomNumberLayout, binding.roomNumber, null);
            textWatcherFunction(binding.studentNameLayout, null, binding.studentName);
            textWatcherFunction(binding.collegeNameLayout, binding.collegeName, null);
            textWatcherFunction(binding.courseNameLayout, binding.courseName, null);
            textWatcherFunction(binding.branchNameLayout, binding.branchName, null);
            textWatcherFunction(binding.fromYearLayout, null, binding.fromYear);
            textWatcherFunction(binding.toYearLayout, null, binding.toYear);
            textWatcherFunction(binding.rollNoLayout, null, binding.rollNo);
            textWatcherFunction(binding.bloodGroupLayout, binding.bloodGroup, null);
            textWatcherFunction(binding.phoneNumberLayout, null, binding.phoneNumber);
        }

        binding.buttonRoomAllocate.setOnClickListener(v->{

            roomAllocateButton();
            if (binding.buttonRoomAllocate.isEnabled()){
                String _Block=blockSpinner.getText().toString(),_Floor=floorSpinner.getText().toString(),_Room_No=roomNoSpinner.getText().toString(),
                        _Name= Objects.requireNonNull(binding.studentName.getText()).toString(),_Roll_No= Objects.requireNonNull(binding.rollNo.getText()).toString()
                        ,_College_Name=collegeNameSpinner.getText().toString(),_Course_Name=courseNameSpinner.getText().toString(),_Branch_Name=branchNameSpinner.getText().toString()
                        ,_Blood_Group=bloodGroupSpinner.getText().toString(),_Phone_Number= Objects.requireNonNull(binding.phoneNumber.getText()).toString(),
                        _Batch=String.format("%s-%s", Objects.requireNonNull(binding.fromYear.getText()).toString(),Objects.requireNonNull(binding.toYear.getText()).toString());
                DatabaseReference roomAllocateReference=roomReference.child(_Block).child(_Floor).child(_Room_No);
                roomAllocateReference.get().addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    }
                    else {
                        _Count = 1;
                        do {
                            String nullValue = String.valueOf(Objects.requireNonNull(task.getResult()).child(String.format("%s%s", "Student ", _Count)).child("_Name").getValue());
                            if (nullValue.equals("")){
                                roomAllocateReference.child(String.format("%s%s", "Student ", _Count)).setValue(new RoomDetails(_Name, _Roll_No, _Dept(_College_Name,_Course_Name,_Branch_Name)));
                                studentReference.child(_Roll_No).setValue(new StudentDetails(_Name, _College_Name, _Course_Name, _Branch_Name, _Batch, _Roll_No, _Blood_Group, _Phone_Number, _Room_Number(_Block, _Floor, _Room_No)));
                                Toast.makeText(getActivity(), "Room Allocated Successfully", Toast.LENGTH_SHORT).show();
                                break;
                            }
                            _Count++;
                            if (_Count>4){
                                binding.roomNumberLayout.setHelperTextEnabled(true);
                                binding.roomNumberLayout.setError("Room Full!!");
                                binding.roomNumber.requestFocus();
                                binding.mainScrollView.fullScroll(ScrollView.FOCUS_UP);
                            }
                        }while (_Count<=4);

                        Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    }
                });
            }

        });

        return root;
    }

    private String _Dept(String college_name, String course_name, String branch_name) {
        return String.format("%s %s (%s)",course_name,branch_name,college_name);
    }

    private String _Room_Number(String block, String floor, String room_no) {
        switch (floor){
            case "Ground Floor":
                return String.format("%s%s%s",block,0,room_no);
            case "First Floor":
                return String.format("%s%s%s",block,1,room_no);
            case "Second Floor":
                return String.format("%s%s%s",block,2,room_no);
            default:
                return String.format("%s%s%s",block,3,room_no);
        }
    }

    public void textWatcherFunction(TextInputLayout emptyCheckLayout, @Nullable AutoCompleteTextView emptyCheckEditText, @Nullable TextInputEditText emptyInputEditText) {

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                blankError(emptyCheckLayout);
                binding.buttonRoomAllocate.setEnabled(true);
            }
        };
        if (emptyCheckEditText!=null){
            emptyCheckEditText.addTextChangedListener(textWatcher);
        }
        if (emptyInputEditText!=null){
            emptyInputEditText.addTextChangedListener(textWatcher);
        }
    }

    public void blankError(TextInputLayout textInputLayout) {
        if(Objects.requireNonNull(textInputLayout.getEditText()).getText().toString().isEmpty()) {
            textInputLayout.setHelperTextEnabled(true);
            textInputLayout.setError("Can't Leave Blank");
        }
        else {
            textInputLayout.setHelperTextEnabled(false);
            textInputLayout.setError(null);
        }
    }
    public void phoneNumberError(TextInputLayout textInputLayout) {
        if(Objects.requireNonNull(binding.phoneNumber.getText()).toString().trim().length()<10) {
            textInputLayout.setHelperTextEnabled(true);
            textInputLayout.setError("Please Enter a valid Number");
        }
        else {
            textInputLayout.setHelperTextEnabled(false);
            textInputLayout.setError(null);
        }
    }

    public boolean blankErrorBoolean(TextInputLayout textInputLayout) {
        return Objects.requireNonNull(textInputLayout.getEditText()).getText().toString().isEmpty();
    }

    public void roomAllocateButton(){

        if (blankErrorBoolean(binding.blockLayout)){
            binding.buttonRoomAllocate.setEnabled(false);
            blankError(binding.blockLayout);
        }
        if (blankErrorBoolean(binding.floorLayout)){
            binding.buttonRoomAllocate.setEnabled(false);
            blankError(binding.floorLayout);
        }
        if (blankErrorBoolean(binding.roomNumberLayout)){
            binding.buttonRoomAllocate.setEnabled(false);
            blankError(binding.roomNumberLayout);
        }
        if (blankErrorBoolean(binding.studentNameLayout)){
            binding.buttonRoomAllocate.setEnabled(false);
            blankError(binding.studentNameLayout);
        }
        if (blankErrorBoolean(binding.collegeNameLayout)){
            binding.buttonRoomAllocate.setEnabled(false);
            blankError(binding.collegeNameLayout);
        }
        if (blankErrorBoolean(binding.courseNameLayout)){
            binding.buttonRoomAllocate.setEnabled(false);
            blankError(binding.courseNameLayout);
        }
        if (binding.branchNameLayout.isEnabled()){
            if (blankErrorBoolean(binding.branchNameLayout)){
                binding.buttonRoomAllocate.setEnabled(false);
                blankError(binding.branchNameLayout);
            }
        }
        if (blankErrorBoolean(binding.fromYearLayout)){
            binding.buttonRoomAllocate.setEnabled(false);
            blankError(binding.fromYearLayout);
        }
        if (blankErrorBoolean(binding.toYearLayout)){
            binding.buttonRoomAllocate.setEnabled(false);
            blankError(binding.toYearLayout);
        }
        if (blankErrorBoolean(binding.rollNoLayout)){
            binding.buttonRoomAllocate.setEnabled(false);
            blankError(binding.rollNoLayout);
        }
        if (blankErrorBoolean(binding.bloodGroupLayout)){
            binding.buttonRoomAllocate.setEnabled(false);
            blankError(binding.bloodGroupLayout);
        }
        if (blankErrorBoolean(binding.phoneNumberLayout)){
            binding.buttonRoomAllocate.setEnabled(false);
            blankError(binding.phoneNumberLayout);
        }
        else if (Objects.requireNonNull(binding.phoneNumber.getText()).toString().trim().length()<10){
            binding.buttonRoomAllocate.setEnabled(false);
            phoneNumberError(binding.phoneNumberLayout);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}