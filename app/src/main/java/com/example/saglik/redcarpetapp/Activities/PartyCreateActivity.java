package com.example.saglik.redcarpetapp.Activities;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.saglik.redcarpetapp.Classes.Party;
import com.example.saglik.redcarpetapp.Classes.User;
import com.example.saglik.redcarpetapp.Database.DatabaseWriter;
import com.example.saglik.redcarpetapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class PartyCreateActivity extends AppCompatActivity {
    private static final String REQUIRED = "REQUIRED";
    User organizer = new User();
    Party party = new Party();
    private Button imageButton;
    private StorageReference mStorage;
    Uri uri;
    EditText editText1;
    EditText editText2;
    EditText editText3;
    EditText editText4;
    private String imageLink;
    private int GALLERY_INTENT = 2;
    ImageView partyImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_create);
        setTitle("Create a new event");
        Button button = findViewById(R.id.button2);
        button.getBackground().setColorFilter(R.drawable.side_nav_bar, PorterDuff.Mode.MULTIPLY);

        setOrganizerName();

        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        editText4 = findViewById(R.id.editText4);
        partyImage = findViewById(R.id.party_image);

        mStorage = FirebaseStorage.getInstance().getReference();
        imageButton = findViewById(R.id.button2);
        imageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_INTENT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERY_INTENT && resultCode==RESULT_OK){
            uri = data.getData();
            partyImage.setImageURI(uri);

        }
    }
    private void setOrganizerName() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        String userID = mUser.getUid();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users"+"/"+userID);
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                organizer = dataSnapshot.getValue(User.class);
                party.setOrganizer(organizer.getNickname());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    public void createEvent(View view){
        String partyName = editText1.getText().toString();
        String partyLocation = editText2.getText().toString();
        String partyDate = editText3.getText().toString();
        String partyInfo = editText4.getText().toString();
        String partyOrganizer = organizer.getNickname();
        String organizerRating = organizer.getRating();
        if (TextUtils.isEmpty(partyName)) {
            editText1.setError(REQUIRED);
            return;
        }
        // Body is required
        if (TextUtils.isEmpty(partyLocation)) {
            editText2.setError(REQUIRED);
            return;
        }
        if (TextUtils.isEmpty(partyDate)) {
            editText3.setError(REQUIRED);
            return;
        }
        if (TextUtils.isEmpty(partyInfo)) {
            editText4.setError(REQUIRED);
            return;
        }
        party = new Party(partyName, partyLocation, partyDate, partyInfo, partyOrganizer, organizerRating,imageLink);
        if(uri == null){
            Toast.makeText(PartyCreateActivity.this, "Error! No image selected", Toast.LENGTH_LONG).show();
        }
        else {
            StorageReference filePath = mStorage.child("party_images").child(uri.getLastPathSegment());
            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageLink = taskSnapshot.getDownloadUrl().toString();
                    Toast.makeText(PartyCreateActivity.this, "Image Uploaded", Toast.LENGTH_LONG).show();
                }
            });
            DatabaseWriter dbWriter = new DatabaseWriter();
            dbWriter.createParty(party);
            Intent intent = new Intent(PartyCreateActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
