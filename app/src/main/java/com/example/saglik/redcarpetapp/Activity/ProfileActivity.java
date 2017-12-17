package com.example.saglik.redcarpetapp.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

import com.example.saglik.redcarpetapp.Classes.User;
import com.example.saglik.redcarpetapp.Database.DatabaseWriter;
import com.example.saglik.redcarpetapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    private static final String REQUIRED = "REQUIRED";
    private EditText nameText;
    private EditText locationText;
    private Switch adminSwitch;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private String userID;
    private FirebaseUser currentUser;
    private MenuItem adminMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle("Profile Settings");

        nameText = findViewById(R.id.editText1);
        locationText = findViewById(R.id.editText2);
        adminSwitch = (Switch)  findViewById(R.id.switch1);

        setEditTextViews();


    }

    public void saveAndProceed(View view){
        String nickname = nameText.getText().toString();
        String location = locationText.getText().toString();
        boolean isAdmin = adminSwitch.isChecked();
        if (TextUtils.isEmpty(nickname)) {
            nameText.setError(REQUIRED);
            return;
        }
        // Body is required
        if (TextUtils.isEmpty(location)) {
            locationText.setError(REQUIRED);
            return;
        }

        User user = new User(nickname,location, isAdmin);
        DatabaseWriter dbWriter = new DatabaseWriter();
        dbWriter.registerUser(user);
        Intent intent = new Intent(ProfileActivity.this,MainActivity.class);
        //intent.putExtra("type",String.valueOf(isAdmin));
        startActivity(intent);
    }

    private void setEditTextViews(){
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        userID = currentUser.getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference("users"+"/"+userID);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() == null){}                    //If user exist show the old informations
                else {
                    User user = dataSnapshot.getValue(User.class);
                    //user = getData(dataSnapshot, userId);
                    nameText.setText(user.getNickname());
                    locationText.setText(user.getLocation());
                    adminSwitch.setChecked(user.isAdmin());
                    //Disappear administrative mode from navigation bar if user is not an admin
                    if(user.isAdmin()){

                    }else{

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
