package com.example.saglik.redcarpetapp.Database;

import com.example.saglik.redcarpetapp.Classes.Party;
import com.example.saglik.redcarpetapp.Classes.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by SAGLIK on 15-Dec-17.
 */

public class DatabaseWriter {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase, mParties;

    public DatabaseWriter() {

    }
    public void registerUser(User user){
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser userA = mAuth.getCurrentUser();
        String userID = userA.getUid();               //GET ID FROM AUTHENTICATION
        user.setPhoneNumber(userA.getPhoneNumber());
        mDatabase = FirebaseDatabase.getInstance().getReference("users/"+userID);
        mDatabase.child("nickname").setValue(user.getNickname());
        mDatabase.child("location").setValue(user.getLocation());
        mDatabase.child("admin").setValue(user.isAdmin());
        mDatabase.child("phoneNumber").setValue(user.getPhoneNumber());
    }
    //Create a party by its name and other specs attached to it on firebase
    public void createParty(Party party){
        mDatabase = FirebaseDatabase.getInstance().getReference("parties/");
        mParties = FirebaseDatabase.getInstance().getReference("parties/"+party.getName()+"/");
        mParties.child("name").setValue(party.getName());
        mParties.child("location").setValue(party.getLocation());
        mParties.child("date").setValue(party.getDate());
        mParties.child("organizer").setValue(party.getOrganizer());
        mParties.child("info").setValue(party.getInfo());
        mParties.child("URL").setValue(party.getImageLink());
    }
}
