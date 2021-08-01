package com.android.HMS.ui.RoomDetails.RoomUpdate;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.HMS.R;
import com.android.HMS.data.model.DataValidity;
import com.android.HMS.data.model.RoomDetails;
import com.android.HMS.databinding.FragmentRoomUpdateBinding;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.Objects;

public class RoomUpdateFragment extends Fragment {

    private FragmentRoomUpdateBinding binding;
    private FirebaseDatabase database;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        RoomUpdateViewModel roomUpdateViewModel = new ViewModelProvider(this).get(RoomUpdateViewModel.class);
        binding = FragmentRoomUpdateBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        database=FirebaseDatabase.getInstance();
        DatabaseReference roomReference= FirebaseDatabase.getInstance().getReference().child("0/HMS/CietKkcasCimat/NBH/Room_Details");
        DatabaseReference studentReference=FirebaseDatabase.getInstance().getReference().child("0/HMS/CietKkcasCimat/NBH/Students_Details");
        final RoomDetails[] student1 = new RoomDetails[1];

        final TextView textView = binding.textRoomUpdate;
        roomUpdateViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        final AutoCompleteTextView fromBlockSpinner = binding.fromBlock;
        {
            ArrayAdapter<CharSequence> fromBlockAdapter = ArrayAdapter.createFromResource(root.getContext(), R.array.block, android.R.layout.simple_dropdown_item_1line);
            fromBlockAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            fromBlockSpinner.setAdapter(fromBlockAdapter);
        }
        final AutoCompleteTextView fromFloorSpinner = binding.fromFloor;
        {
            fromBlockSpinner.setOnItemClickListener((parent, view, position, id) -> {
                if (!fromFloorSpinner.getText().toString().isEmpty()) {
                    fromFloorSpinner.setText("");
                    binding.fromFloorLayout.setHelperTextEnabled(false);
                    binding.fromFloorLayout.setError(null);
                }
                ArrayAdapter<CharSequence> fromFloorAdapter = ArrayAdapter.createFromResource(root.getContext(), DataValidity.floor(fromBlockSpinner), android.R.layout.simple_dropdown_item_1line);
                fromFloorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                fromFloorSpinner.setAdapter(fromFloorAdapter);

            });
        }
        final AutoCompleteTextView fromStartingRoomNoSpinner = binding.fromStartingRoomNumber;
        {
            ArrayAdapter<CharSequence> fromStartingRoomNoAdapter = ArrayAdapter.createFromResource(root.getContext(), R.array.room_no, android.R.layout.simple_dropdown_item_1line);
            fromStartingRoomNoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            fromStartingRoomNoSpinner.setAdapter(fromStartingRoomNoAdapter);
        }
        final AutoCompleteTextView fromEndingRoomNoSpinner = binding.fromEndingRoomNumber;
        {
            ArrayAdapter<CharSequence> fromEndingRoomNoAdapter = ArrayAdapter.createFromResource(root.getContext(), R.array.room_no, android.R.layout.simple_dropdown_item_1line);
            fromEndingRoomNoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            fromEndingRoomNoSpinner.setAdapter(fromEndingRoomNoAdapter);
        }
        final AutoCompleteTextView toBlockSpinner = binding.toBlock;
        {
            ArrayAdapter<CharSequence> toBlockAdapter = ArrayAdapter.createFromResource(root.getContext(), R.array.block, android.R.layout.simple_dropdown_item_1line);
            toBlockAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            toBlockSpinner.setAdapter(toBlockAdapter);
        }
        final AutoCompleteTextView toFloorSpinner = binding.toFloor;
        {
            toBlockSpinner.setOnItemClickListener((parent, view, position, id) -> {
                if (!toFloorSpinner.getText().toString().isEmpty()) {
                    toFloorSpinner.setText("");
                    binding.toFloorLayout.setHelperTextEnabled(false);
                    binding.toFloorLayout.setError(null);
                }
                ArrayAdapter<CharSequence> toFloorAdapter = ArrayAdapter.createFromResource(root.getContext(), DataValidity.floor(toBlockSpinner), android.R.layout.simple_dropdown_item_1line);
                toFloorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                toFloorSpinner.setAdapter(toFloorAdapter);
            });
        }
        final AutoCompleteTextView toStartingRoomNoSpinner = binding.toStartingRoomNumber;
        {
            ArrayAdapter<CharSequence> toStartingRoomNoAdapter = ArrayAdapter.createFromResource(root.getContext(), R.array.room_no, android.R.layout.simple_dropdown_item_1line);
            toStartingRoomNoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            toStartingRoomNoSpinner.setAdapter(toStartingRoomNoAdapter);
        }
        final AutoCompleteTextView toEndingRoomNoSpinner = binding.toEndingRoomNumber;
        {
            ArrayAdapter<CharSequence> toEndingRoomNoAdapter = ArrayAdapter.createFromResource(root.getContext(), R.array.room_no, android.R.layout.simple_dropdown_item_1line);
            toEndingRoomNoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            toEndingRoomNoSpinner.setAdapter(toEndingRoomNoAdapter);
        }
        //TextWatcher
        {
        textWatcherFunction(binding.fromBlockLayout, fromBlockSpinner);
        textWatcherFunction(binding.fromFloorLayout, fromFloorSpinner);
        textWatcherFunction(binding.fromStartingRoomNumberLayout, fromStartingRoomNoSpinner);
        textWatcherFunction(binding.fromEndingRoomNumberLayout, fromEndingRoomNoSpinner);
        textWatcherFunction(binding.toBlockLayout, toBlockSpinner);
        textWatcherFunction(binding.toFloorLayout, toFloorSpinner);
        textWatcherFunction(binding.toStartingRoomNumberLayout, toStartingRoomNoSpinner);
        textWatcherFunction(binding.toEndingRoomNumberLayout, toEndingRoomNoSpinner);
        }

        binding.buttonRoomUpdate.setOnClickListener(v -> {

            roomUpdateButton();
            if (binding.buttonRoomUpdate.isEnabled()) {
                String _From_Block=fromBlockSpinner.getText().toString(),_From_Floor=fromFloorSpinner.getText().toString(),_From_SRN=fromStartingRoomNoSpinner.getText().toString(),_From_ERN=fromEndingRoomNoSpinner.getText().toString(),
                        _To_Block=toBlockSpinner.getText().toString(),_To_Floor=toFloorSpinner.getText().toString(),_To_SRN=toStartingRoomNoSpinner.getText().toString(),_To_ERN=toEndingRoomNoSpinner.getText().toString();
                int fERN = Integer.parseInt(_From_ERN), fSRN = Integer.parseInt(_From_SRN), tERN = Integer.parseInt(_To_ERN), tSRN = Integer.parseInt(_To_SRN);
                if ((fERN - fSRN) >= 0) {
                    if ((tERN - tSRN) >= 0) {
                        if ((fERN - fSRN) == (tERN - tSRN)) {
                            roomUpdateViewModel.setfSRN(fSRN);
                            roomUpdateViewModel.settSRN(tSRN);
                            roomReference.child(_From_Block).child(_From_Floor).get().addOnCompleteListener(task -> {
                                if (!task.isSuccessful()) {
                                    Log.e("firebase", "Error getting data", task.getException());
                                } else {
                                    do {
                                        int _Count = 1;
                                        do {
                                            RoomDetails student=Objects.requireNonNull(task.getResult()).child(new DecimalFormat("00").format(roomUpdateViewModel.getfSRN())).child(String.format("%s%s", "Student ", _Count)).getValue(RoomDetails.class);
                                            roomReference.child(_To_Block).child(_To_Floor).child(new DecimalFormat("00").format(roomUpdateViewModel.gettSRN())).child(String.format("%s%s", "Student ", _Count)).setValue(student);
                                            roomReference.child(_From_Block).child(_From_Floor).child(new DecimalFormat("00").format(roomUpdateViewModel.getfSRN())).child(String.format("%s%s", "Student ", _Count)).setValue(new RoomDetails());
                                            if (!Objects.requireNonNull(student).get_Name().equals(""))   studentReference.child(Objects.requireNonNull(student).get_Dept()).child(student.get_Roll_No()).child("_Room_Number").setValue(_Room_Number(_To_Block,_To_Floor,_To_SRN));
                                            _Count++;
                                        }while (_Count <=4);
                                        roomUpdateViewModel.incFromSRN();
                                        roomUpdateViewModel.incToSRN();
                                    }while(roomUpdateViewModel.getfSRN() <= fERN);
                                }
                            });

                        } else {
                            Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                            binding.toStartingRoomNumberLayout.setHelperTextEnabled(true);
                            binding.toStartingRoomNumberLayout.setError("Total Room No Doesn't Match");

                            binding.toEndingRoomNumberLayout.setHelperTextEnabled(true);
                            binding.toEndingRoomNumberLayout.setError("Total Room No Doesn't Match");
                        }
                    } else {
                        binding.toEndingRoomNumberLayout.setHelperTextEnabled(true);
                        binding.toEndingRoomNumberLayout.setError("Ending Room Number Should be greater than Starting Room Number");
                    }
                }
                else {
                    binding.fromEndingRoomNumberLayout.setHelperTextEnabled(true);
                    binding.fromEndingRoomNumberLayout.setError("Ending Room Number Should be greater than Starting Room Number");
                }
            }
        });


        return root;
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

    public void textWatcherFunction(TextInputLayout emptyCheckLayout, AutoCompleteTextView emptyCheckEditText) {

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
                binding.buttonRoomUpdate.setEnabled(true);
            }
        };
            emptyCheckEditText.addTextChangedListener(textWatcher);


    }

    public void roomUpdateButton(){

        if (blankErrorBoolean(binding.fromBlockLayout)){
            binding.buttonRoomUpdate.setEnabled(false);
            blankError(binding.fromBlockLayout);
        }
        if (blankErrorBoolean(binding.fromFloorLayout)){
            binding.buttonRoomUpdate.setEnabled(false);
            blankError(binding.fromFloorLayout);
        }
        if (blankErrorBoolean(binding.fromStartingRoomNumberLayout)){
            binding.buttonRoomUpdate.setEnabled(false);
            blankError(binding.fromStartingRoomNumberLayout);
        }
        if (blankErrorBoolean(binding.fromEndingRoomNumberLayout)){
            binding.buttonRoomUpdate.setEnabled(false);
            blankError(binding.fromEndingRoomNumberLayout);
        }
        if (blankErrorBoolean(binding.toBlockLayout)){
            binding.buttonRoomUpdate.setEnabled(false);
            blankError(binding.toBlockLayout);
        }
        if (blankErrorBoolean(binding.toFloorLayout)){
            binding.buttonRoomUpdate.setEnabled(false);
            blankError(binding.toFloorLayout);
        }
        if (blankErrorBoolean(binding.toStartingRoomNumberLayout)){
            binding.buttonRoomUpdate.setEnabled(false);
            blankError(binding.toStartingRoomNumberLayout);
        }
        if (blankErrorBoolean(binding.toEndingRoomNumberLayout)){
            binding.buttonRoomUpdate.setEnabled(false);
            blankError(binding.toEndingRoomNumberLayout);
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

    public boolean blankErrorBoolean(TextInputLayout textInputLayout) {
        return Objects.requireNonNull(textInputLayout.getEditText()).getText().toString().isEmpty();
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}