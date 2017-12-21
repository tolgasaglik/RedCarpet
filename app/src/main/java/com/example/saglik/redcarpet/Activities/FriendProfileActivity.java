package com.example.saglik.redcarpet.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.saglik.redcarpet.Classes.User;
import com.example.saglik.redcarpet.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FriendProfileActivity extends AppCompatActivity {
    String friendDetails;
    Intent intent = new Intent();
    TextView textView1;
    TextView textView2;
    TextView textView3;
    RatingBar textView4;
    String[] stringParts = new String[2];
    String name;
    String phone;
    User friend = new User();
    boolean friendPrivacy;
    String PRIVATE = "Private";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);
        intent = getIntent();
        friendDetails = intent.getStringExtra("friendDetails");
        setTitle("RedFriend");

        textView1 = findViewById(R.id.editText1);
        textView2 = findViewById(R.id.editText2);
        textView3 = findViewById(R.id.editText3);
        textView4 = findViewById(R.id.editText4);

        stringParts = friendDetails.split("\n");
        name = stringParts[0];
        phone = stringParts[1];

        getFriendDetails();


    }

    private void setFriendProfile() {
        friendPrivacy = friend.isPrivate();
        textView1.setText(friend.getNickname());

        if(!friendPrivacy){
            textView2.setText(friend.getPhoneNumber());
            textView3.setText(friend.getLocation());
        }
        else{
            textView2.setText(PRIVATE);
            textView3.setText(PRIVATE);
        }
        if(friend.isAdmin())
            textView4.setRating(friend.getRating());
        else
            textView4.invalidate();
    }

    private void getFriendDetails() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users/");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    User temp = ds.getValue(User.class);
                    String tempPhone = temp.getPhoneNumber();
                    if(tempPhone.equals(phone)){
                        friend = temp;
                        break;
                    }
                }
                setFriendProfile();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }
}