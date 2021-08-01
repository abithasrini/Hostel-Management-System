package com.android.HMS.ui.MainMenu;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.HMS.R;
import com.android.HMS.data.model.AccessLevels;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;

import static android.content.ContentValues.TAG;

public class AccessModifier extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access_modifier);

        FirebaseAuth auth=FirebaseAuth.getInstance();
        DatabaseReference userReference= FirebaseDatabase.getInstance().getReference().child("0/HMS/CietKkcasCimat/NBH/Staff_Details");
        ArrayList<CheckBox> accessLevels=new ArrayList<>(5);
        Collections.addAll(accessLevels,findViewById(R.id.administrationCheck),findViewById(R.id.studentDetailsCheck),findViewById(R.id.roomDetailsCheck),findViewById(R.id.messBillCheck),findViewById(R.id.attendanceCheck));

        TextInputEditText email=findViewById(R.id.email);
        Button search=findViewById(R.id.buttonSearch);
        search.setOnClickListener(view -> {
            userReference.orderByChild("_Email").equalTo(email.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    Log.d(TAG, email.getText().toString());

                    if (snapshot.exists()){
                        findViewById(R.id.accessModifier).setVisibility(View.VISIBLE);
                        findViewById(R.id.buttonAccessUpdate).setVisibility(View.VISIBLE);
                        AccessLevels accessLevelsClass=snapshot.child("").child("Access_Level").getValue(AccessLevels.class);

                        if (accessLevelsClass.get_Administration().equals("No")){
                            accessLevels.get(0).setChecked(false);
                        }
                        if (accessLevelsClass.get_Student_Details().equals("No")){
                            accessLevels.get(1).setChecked(false);
                        }
                        if (accessLevelsClass.get_Room_Details().equals("No")){
                            accessLevels.get(2).setChecked(false);
                        }
                        if (accessLevelsClass.get_Mess_Bill().equals("No")){
                            accessLevels.get(3).setChecked(false);
                        }
                        if (accessLevelsClass.get_Attendance().equals("No")){
                            accessLevels.get(4).setChecked(false);
                        }
                    }

                    else {
                        Toast.makeText(AccessModifier.this, "Id Not Found", Toast.LENGTH_SHORT).show();
                        findViewById(R.id.accessModifier).setVisibility(View.GONE);
                        findViewById(R.id.buttonAccessUpdate).setVisibility(View.GONE);
                    }

                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        });

    }
}