package com.android.HMS.ui.MainMenu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.HMS.R;
import com.android.HMS.data.model.AccessLevels;
import com.android.HMS.ui.Attendance.Attendance;
import com.android.HMS.ui.LRF.LRF;
import com.android.HMS.ui.MessBill.MessBill;
import com.android.HMS.ui.RoomDetails.RoomDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import static android.content.ContentValues.TAG;

public class MainMenu extends AppCompatActivity {
    final DataSnapshot[] _AccessLevelSnap = new DataSnapshot[1];
    AccessLevels accessLevels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        DatabaseReference userReference= FirebaseDatabase.getInstance().getReference().child("0/HMS/CietKkcasCimat/NBH/Staff_Details");
        ImageButton attendance= findViewById(R.id.attendanceImage);
        ImageButton messBill=findViewById(R.id.messBillImage);
        ImageButton roomDetails=findViewById(R.id.roomDetailsImage);
        SharedPreferences prefs=getSharedPreferences("loginPrefs",MODE_PRIVATE);
        Log.d(TAG, prefs.getString("uid","Invalid"));
        userReference.child(prefs.getString("uid","Invalid")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                _AccessLevelSnap[0] =snapshot;
                accessLevels =_AccessLevelSnap[0].child("Access_Level").getValue(AccessLevels.class);

                Log.d(TAG, String.valueOf(snapshot));
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        Log.d("Second", String.valueOf(_AccessLevelSnap[0]));
        attendance.setOnClickListener(v -> {
            if (accessLevels.get_Attendance().equals("Yes")){
                Intent intent=new Intent(MainMenu.this, Attendance.class);
                startActivity(intent);
            }
            else
                Toast.makeText(this, "Access Denied", Toast.LENGTH_SHORT).show();
        });
        messBill.setOnClickListener(v->{
            if (accessLevels.get_Mess_Bill().equals("Yes")){
                Intent intent=new Intent(MainMenu.this, MessBill.class);
                startActivity(intent);
            }
            else

                Toast.makeText(this, "Access Denied", Toast.LENGTH_SHORT).show();
        });
        roomDetails.setOnClickListener(v->{
            if (accessLevels.get_Room_Details().equals("Yes")){
                Intent intent=new Intent(MainMenu.this, RoomDetails.class);
                startActivity(intent);
            }
            else
                Toast.makeText(this, "Access Denied", Toast.LENGTH_SHORT).show();
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_logout:
                SharedPreferences myPrefs = getSharedPreferences("loginPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = myPrefs.edit();
                editor.clear();
                editor.apply();
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainMenu.this, LRF.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            case R.id.action_accessModifier:
                if (accessLevels.get_Administration().equals("Yes")){
                    Intent intent1 = new Intent(MainMenu.this, AccessModifier.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent1);
                    return true;
                }
                else {
                    Toast.makeText(this, "Access Denied", Toast.LENGTH_SHORT).show();
                }
        }
        return super.onOptionsItemSelected(item);
    }
}