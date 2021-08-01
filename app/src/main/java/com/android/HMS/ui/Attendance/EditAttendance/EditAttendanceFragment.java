package com.android.HMS.ui.Attendance.EditAttendance;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.HMS.R;
import com.android.HMS.data.model.DataValidity;
import com.android.HMS.databinding.FragmentEditAttendanceBinding;
import com.android.HMS.ui.Attendance.MainAttendance.MainAttendance;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Objects;

public class EditAttendanceFragment extends Fragment {

    private FragmentEditAttendanceBinding binding;

    private int mYear, mMonth, mDay;

    final Calendar calendar = Calendar.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        EditAttendanceViewModel editAttendanceViewModel = new ViewModelProvider(this).get(EditAttendanceViewModel.class);

        binding = FragmentEditAttendanceBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        DataValidity dataValidity=new DataValidity();

        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        String mon[]={"January", "February", "March","April", "May", "June", "July", "August", "September", "October", "November", "December"};
        @SuppressLint("SimpleDateFormat") SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        String month_name = month_date.format(calendar.getTime());
        ArrayList<String> _Date=new ArrayList<>(3);
        ArrayList<String> _Room_No=new ArrayList<>(4);
        Collections.addAll(_Date,String.valueOf(mDay), month_name,String.valueOf(mYear));

        final TextView textView = binding.textEditAttendance;
        editAttendanceViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        final AutoCompleteTextView dateInputEditText=binding.date;
        editAttendanceViewModel.getDate().observe(getViewLifecycleOwner(), dateInputEditText::setText);

        final TextInputLayout blockLayout=binding.blockLayout;
        final TextInputLayout floorLayout=binding.floorLayout;
        final TextInputLayout srnLayout= binding.startingRoomNumberLayout;
        final TextInputLayout ernLayout= binding.endingRoomNumberLayout;

        Button buttonEditAttendance=binding.buttonEditAttendance;

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


        final AutoCompleteTextView startingRoomNoSpinner = binding.startingRoomNumber;
        ArrayAdapter<CharSequence> startingRoomNoAdapter = ArrayAdapter.createFromResource(root.getContext(), R.array.room_no, android.R.layout.simple_dropdown_item_1line);
        startingRoomNoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startingRoomNoSpinner.setAdapter(startingRoomNoAdapter);

        final AutoCompleteTextView endingRoomNoSpinner = binding.endingRoomNumber;
        ArrayAdapter<CharSequence> endingRoomNoAdapter = ArrayAdapter.createFromResource(root.getContext(), R.array.room_no, android.R.layout.simple_dropdown_item_1line);
        endingRoomNoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        endingRoomNoSpinner.setAdapter(endingRoomNoAdapter);

        dateInputEditText.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(root.getContext(), (view, year, monthOfYear, dayOfMonth) -> {
                dateInputEditText.setText(String.format(getString(R.string.dateFormat), dayOfMonth, monthOfYear+1, year));
                _Date.set(0, String.valueOf(dayOfMonth));
                _Date.set(1, mon[monthOfYear]);
                _Date.set(2, String.valueOf(year));
            }, mYear, mMonth, mDay);
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog.show();
        });
        buttonEditAttendance.setOnClickListener(v -> {
            if (buttonTakeAttendanceEnabled(blockLayout,floorLayout,srnLayout,ernLayout)){
                int srn= Integer.parseInt(Objects.requireNonNull(srnLayout.getEditText()).getText().toString()),ern=Integer.parseInt(Objects.requireNonNull(ernLayout.getEditText()).getText().toString());
                if(srn>ern){
                    ernLayout.setHelperTextEnabled(true);
                    ernLayout.setError("The ending room no should be greater");
                }
                else {
                    String floor = floorSpinner.getText().toString();
                    Collections.addAll(_Room_No,blockSpinner.getText().toString(),floor,startingRoomNoSpinner.getText().toString(),endingRoomNoSpinner.getText().toString());
                    Intent intent = new Intent(getActivity(), MainAttendance.class);
                    intent.putExtra("Room Number",_Room_No);
                    intent.putExtra("Date",dateInputEditText.getText().toString());
                    intent.putExtra("Date Array",_Date);
                    intent.putExtra("Mode","Edit Attendance");
                    startActivity(intent);
                }
            }
            else{
                dataValidity.dataValid(blockLayout);
                dataValidity.dataValid(floorLayout);
                dataValidity.dataValid(srnLayout);
                dataValidity.dataValid(ernLayout);
            }
        });
        dataValidity.textWatcherFunction(blockLayout,blockSpinner,null);
        dataValidity.textWatcherFunction(floorLayout,floorSpinner,null);
        dataValidity.textWatcherFunction(srnLayout,startingRoomNoSpinner,null);
        dataValidity.textWatcherFunction(ernLayout,endingRoomNoSpinner,null);

        return root;

    }

    private boolean buttonTakeAttendanceEnabled(TextInputLayout blockLayout, TextInputLayout floorLayout, TextInputLayout srnLayout, TextInputLayout ernLayout) {
        if (Objects.requireNonNull(blockLayout.getEditText()).getText().toString().isEmpty()){
            return false;
        }
        if (Objects.requireNonNull(floorLayout.getEditText()).getText().toString().isEmpty()){
            return false;
        }
        if (Objects.requireNonNull(srnLayout.getEditText()).getText().toString().isEmpty()){
            return false;
        }
        return !Objects.requireNonNull(ernLayout.getEditText()).getText().toString().isEmpty();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}