package com.android.HMS.ui.Attendance.ViewAttendance;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.android.HMS.databinding.FragmentViewAttendanceBinding;
import com.android.HMS.ui.Attendance.MainAttendance.MainAttendance;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Objects;

public class ViewAttendanceFragment extends Fragment {

    private FragmentViewAttendanceBinding binding;
    private int mYear, mMonth, mDay;

    final Calendar calendar = Calendar.getInstance();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ViewAttendanceViewModel viewAttendanceViewModel = new ViewModelProvider(this).get(ViewAttendanceViewModel.class);

        binding = FragmentViewAttendanceBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        String mon[]={"January", "February", "March","April", "May", "June", "July", "August", "September", "October", "November", "December"};
        @SuppressLint("SimpleDateFormat") SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        String month_name = month_date.format(calendar.getTime());
        ArrayList<String> _Date=new ArrayList<>(3);
        ArrayList<String> _Room_No=new ArrayList<>(4);
        Collections.addAll(_Date,String.valueOf(mDay), month_name,String.valueOf(mYear));

        final TextView textView = binding.textViewAttendance;
        viewAttendanceViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        final AutoCompleteTextView dateInputEditText=binding.date;
        viewAttendanceViewModel.getDate().observe(getViewLifecycleOwner(), dateInputEditText::setText);

        final TextInputLayout blockLayout=binding.blockLayout;
        final TextInputLayout floorLayout=binding.floorLayout;
        final TextInputLayout srnLayout= binding.startingRoomNumberLayout;
        final TextInputLayout ernLayout= binding.endingRoomNumberLayout;

        Button buttonEditAttendance=binding.buttonViewAttendance;

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
                    intent.putExtra("Mode","View Attendance");
                    startActivity(intent);
                }
            }
            else{
                dataValid(blockLayout);
                dataValid(floorLayout);
                dataValid(srnLayout);
                dataValid(ernLayout);
            }
        });
        textWatcherFunction(blockLayout,blockSpinner);
        textWatcherFunction(floorLayout,floorSpinner);
        textWatcherFunction(srnLayout,startingRoomNoSpinner);
        textWatcherFunction(ernLayout,endingRoomNoSpinner);

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
                dataValid(emptyCheckLayout);
            }
        };
        emptyCheckEditText.addTextChangedListener(textWatcher);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}