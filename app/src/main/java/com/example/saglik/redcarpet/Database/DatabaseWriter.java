package com.example.saglik.redcarpet.Database;

import com.example.saglik.redcarpet.Classes.Party;
import com.example.saglik.redcarpet.Classes.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by SAGLIK on 20-Dec-17.
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
        mDatabase.child("isPrivate").setValue(user.checkPrivate());
        mDatabase.child("phoneNumber").setValue(user.getPhoneNumber());
    }
    //Create a party by its name and other specs attached to it on firebase
    public void createParty(Party party){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        String key = db.getReference("parties/").push().getKey();
        mParties = FirebaseDatabase.getInstance().getReference("parties/"+key+"/");
        mParties.child("name").setValue(party.getName());
        mParties.child("location").setValue(party.getLocation());
        mParties.child("date").setValue(party.getDate());
        mParties.child("organizer").setValue(party.getOrganizer());
        mParties.child("info").setValue(party.getInfo());
        mParties.child("URL").setValue(party.getImageLink());
    }

    public void addParticipantToParty(String partyID){
        final String userID = getUserID();
        mDatabase = FirebaseDatabase.getInstance().getReference("parties/"+partyID+"/participants/");
        DatabaseReference mUser = FirebaseDatabase.getInstance().getReference("users/"+userID);
        mUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                mDatabase.child(userID).setValue(user.getNickname());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    public void removeParticipanFromParty (String partyID){
        String userID = getUserID();
        mDatabase = FirebaseDatabase.getInstance().getReference("parties/"+partyID+"/participants/");
        mDatabase.child(userID).removeValue();
    }

    public String getUserID(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser userA = mAuth.getCurrentUser();
        return userA.getUid();
    }
}