package com.android.HMS.ui.RoomDetails.RoomVacate;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.HMS.data.model.RoomDetails;
import com.android.HMS.data.model.StudentDetails;
import com.android.HMS.databinding.FragmentRoomVacateBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class RoomVacateFragment extends Fragment {

    private FragmentRoomVacateBinding binding;
    private FirebaseDatabase firebaseDatabase;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) { RoomVacateViewModel roomVacateViewModel = new ViewModelProvider(this).get(RoomVacateViewModel.class);

    binding = FragmentRoomVacateBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

        final String[] roomNumber = new String[1];
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference roomReference= FirebaseDatabase.getInstance().getReference().child("0/HMS/CietKkcasCimat/NBH/Room_Details");
        DatabaseReference studentReference=FirebaseDatabase.getInstance().getReference().child("0/HMS/CietKkcasCimat/NBH/Students_Details");

        final TextView textView = binding.textRoomVacate;
        roomVacateViewModel.getText().observe(getViewLifecycleOwner(),textView::setText);
        textWatcherFunction(binding.rollNoLayout, binding.rollNo);

        binding.buttonRoomVacateSearch.setOnClickListener(v -> {
        if (blankErrorBoolean(binding.rollNoLayout)){
            blankError(binding.rollNoLayout);
            binding.buttonRoomVacate.setEnabled(false);
        }
        else {
            String rollNo= Objects.requireNonNull(binding.rollNo.getText()).toString();
            studentReference.orderByChild("_Roll_No").equalTo(rollNo).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        StudentDetails studentDetails=snapshot.child(rollNo).getValue(StudentDetails.class);
                        roomNumber[0] =studentDetails.get_Room_Number();
                        binding.roomNumber.setText(studentDetails.get_Room_Number());
                        binding.studentName.setText(studentDetails.get_Name());
                        binding.collegeName.setText(studentDetails.get_College_Name());
                        binding.courseName.setText(studentDetails.get_Course_Name());
                        if (!studentDetails.get_Branch_Name().equals("")) {
                            binding.branchNameLayout.setVisibility(View.VISIBLE);
                            binding.branchName.setText(studentDetails.get_Branch_Name());
                        } else {
                            binding.branchNameLayout.setVisibility(View.GONE);
                        }
                        binding.batch.setText(studentDetails.get_Batch());
                        binding.buttonRoomVacate.setEnabled(true);
                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }
        });

        binding.buttonRoomVacate.setOnClickListener(v -> {
            String rollNo= Objects.requireNonNull(binding.rollNo.getText()).toString();
            DatabaseReference roomNo=roomReference.child(String.valueOf(roomNumber[0].charAt(0))).child(floorName(roomNumber[0].charAt(1))).child(String.format("%c%c",roomNumber[0].charAt(2),roomNumber[0].charAt(3)));
            roomNo.orderByChild("_Roll_No").equalTo(rollNo).addListenerForSingleValueEvent(new ValueEventListener() {
                @SuppressLint("RestrictedApi")
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        int _Count=1;
                        while (_Count<=4){
                            if (String.valueOf(Objects.requireNonNull(snapshot).child(String.format("%s%s", "Student ", _Count)).child("_Roll_No").getValue()).equals(rollNo)){
                                roomNo.child(String.format("%s%s", "Student ", _Count)).setValue(new RoomDetails());
                                studentReference.child(rollNo).removeValue().addOnSuccessListener(v1 -> Toast.makeText(getActivity(), "Room Vacated Successfully", Toast.LENGTH_SHORT).show());
                            }
                            _Count++;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        });

        return root;
    }
    public String floorName(char c){
        switch (c){
            case '1':
                return "First Floor";
            case '2':
                return "Second Floor";
            case '3':
                return "Third Floor";
            default:
                return "Ground Floor";
        }
    }
    public void textWatcherFunction(TextInputLayout emptyCheckLayout, TextInputEditText emptyInputEditText) {

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
            }
        };

        emptyInputEditText.addTextChangedListener(textWatcher);
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

    public boolean blankErrorBoolean(TextInputLayout textInputLayout) {
        return Objects.requireNonNull(textInputLayout.getEditText()).getText().toString().isEmpty();
    }
@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}