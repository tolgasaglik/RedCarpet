package com.example.saglik.redcarpetapp.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle("Profile Settings");

        nameText = findViewById(R.id.editText1);
        locationText = findViewById(R.id.editText2);

        setEdit();


    }

    public void saveAndProceed(View view){
        String nickname = nameText.getText().toString();
        String location = locationText.getText().toString();
        if (TextUtils.isEmpty(nickname)) {
            nameText.setError(REQUIRED);
            return;
        }
        // Body is required
        if (TextUtils.isEmpty(location)) {
            locationText.setError(REQUIRED);
            return;
        }

        User user = new User(nickname,location);
        DatabaseWriter dbWriter = new DatabaseWriter();
        dbWriter.registerUser(user);
    }

    private void setEdit(){
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser userA = mAuth.getCurrentUser();
        String userID = userA.getUid();

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
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
