package com.example.saglik.redcarpet.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saglik.redcarpet.Classes.Party;
import com.example.saglik.redcarpet.Classes.User;
import com.example.saglik.redcarpet.Database.DatabaseWriter;
import com.example.saglik.redcarpet.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PartyCheckinActivity extends AppCompatActivity {

    private TextView locationView;
    private TextView dateView;
    private TextView infoView;
    private TextView organizerView;
    private ImageView imageView;
    private Button registerButton;
    private Button mapsButton;
    private Button participantButton;
    private Party currentParty = new Party();
    private String userID;
    private String userName;
    private DatabaseReference mDatabase;
    private String partyName;
    private String partyID;
    private boolean isParticipant;
    private ListView participantList;
    private ArrayList<String> participantNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_checkin);

        Intent intent = getIntent();
        partyName = intent.getStringExtra("partyname");
        partyID = intent.getStringExtra("partyid");
        userName = intent.getStringExtra("username");
        setTitle(partyName);

        organizerView = findViewById(R.id.organizerView);
        locationView = findViewById(R.id.locationView);
        dateView = findViewById(R.id.dateView);
        infoView = findViewById(R.id.infoView);
        imageView = findViewById(R.id.imageView);
        registerButton = findViewById(R.id.registerButton);
        mapsButton = findViewById(R.id.mapsButton);
        participantButton = findViewById(R.id.participantButton);



        mDatabase = FirebaseDatabase.getInstance().getReference("parties/"+partyID+"/");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseUser userA = mAuth.getCurrentUser();
                userID = userA.getUid();
                currentParty = dataSnapshot.getValue(Party.class);
                isParticipant = dataSnapshot.child("participants").child(userID).exists();
                populateViews();
                setRegisterButton();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        //Open googlemaps on click
        mapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String map = "http://maps.google.co.in/maps?q=" + currentParty.getLocation();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
                startActivity(intent);
            }
        });
        //Register user to this party
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseWriter dbWriter = new DatabaseWriter();
                userID = dbWriter.getUserID();
                if(registerButton.getText().equals("REGISTER")){
                    dbWriter.addParticipantToParty(partyID);
                    if(!participantNames.contains(userName))
                        participantNames.add(userName);
                    registerButton.setText("UNREGISTER");
                }
                else if(registerButton.getText().equals("UNREGISTER")){
                    dbWriter.removeParticipanFromParty(partyID);
                    participantNames.remove(userName);
                    registerButton.setText("REGISTER");
                }
            }
        });
        participantList = findViewById(R.id.participantList);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("parties/"+partyName+"/participants/");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    String data = ds.getValue().toString();
                    if(!participantNames.contains(data))
                        participantNames.add(data);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });


        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, participantNames);
        participantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(participantNames.isEmpty())
                    Toast.makeText(PartyCheckinActivity.this, "You will be the first!", Toast.LENGTH_LONG).show();
                else {
                    final Dialog dialog = new Dialog(PartyCheckinActivity.this);
                    dialog.setContentView(R.layout.participant_dialog);
                    dialog.setTitle("Participants");
                    participantList = (ListView) dialog.findViewById(R.id.participantList);
                    participantList.setAdapter(adapter);
                    dialog.show();
                }
            }
        });
    }

    public void setRegisterButton(){
        if(isParticipant)
            registerButton.setText("UNREGISTER");
        else
            registerButton.setText("REGISTER");
    }

    private void populateViews() {
        organizerView.setText("Event Organizer: "+currentParty.getOrganizer());
        locationView.setText("Location: "+currentParty.getLocation());
        dateView.setText("Event Date: "+currentParty.getDate());
        infoView.setText("Event Info: "+currentParty.getInfo());
//        Picasso.with(PartyCheckinActivity.this).load(currentParty.getImageLink()).resize(200,200).centerCrop().into(imageView);
        Picasso.with(PartyCheckinActivity.this).load(currentParty.getImageLink()).fit().centerCrop()
                .placeholder(R.drawable.downloading) //image that you show during the download
                .error(R.drawable.downloaderror) //image error if doesn't download it correctly
                .into(imageView); //your imageView
    }

}

