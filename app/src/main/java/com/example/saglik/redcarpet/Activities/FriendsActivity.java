package com.example.saglik.redcarpet.Activities;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.saglik.redcarpet.Classes.Contact;
import com.example.saglik.redcarpet.Classes.User;
import com.example.saglik.redcarpet.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FriendsActivity extends AppCompatActivity {
    ListView friendsListView;
    ArrayList<Contact> contactList = new ArrayList<Contact>();
    ArrayList<String> contactDisplayList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        setTitle("RedFriends");
        friendsListView = findViewById(R.id.friendsListView);

        getContactList();
        matchUsersWithContacts();



        friendsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String friendDetails = (String) friendsListView.getItemAtPosition(i);
                Intent intent = new Intent(FriendsActivity.this,FriendProfileActivity.class);
                intent.putExtra("friendDetails", friendDetails);
                startActivity(intent);
            }
        });

    }

    private void matchUsersWithContacts() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users/");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    User user = ds.getValue(User.class);
                    String phone = user.getPhoneNumber();
                    for(Contact c : contactList){
                        String contactPhone = c.getContactNumber();
                        String contactName = c.getContactName();
                        if(phone.equals(contactPhone)){
                            contactDisplayList.add(contactName+"\n"+contactPhone);
                        }
                    }
                }
                refreshFriendList();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private void refreshFriendList() {
        ArrayAdapter<String> friendAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, contactDisplayList);
        friendsListView.setAdapter(friendAdapter);
    }

    private void getContactList() {
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Contact contact = new Contact();
                        contact.setContactName(name);
                        contact.setContactNumber(phoneNo);
                        contactList.add(contact);
                        String TAG = "Contact Details";
                        Log.i(TAG, "Name: " + name);
                        Log.i(TAG, "Phone Number: " + phoneNo);
                    }
                    pCur.close();
                }
            }
        }
        if(cur!=null){
            cur.close();
        }
    }
}