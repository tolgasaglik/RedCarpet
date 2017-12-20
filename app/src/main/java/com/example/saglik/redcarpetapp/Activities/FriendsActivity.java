package com.example.saglik.redcarpetapp.Activities;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.saglik.redcarpetapp.Classes.Contact;
import com.example.saglik.redcarpetapp.Classes.User;
import com.example.saglik.redcarpetapp.R;
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

    Contact contact = new Contact();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        setTitle("RedFriends");
        friendsListView = findViewById(R.id.friendsListView);

        getContactList();
        matchUsersWithContacts();
        refreshFriendList();

    }

    private void matchUsersWithContacts() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users/");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    User user = ds.getValue(User.class);
                    for(Contact c : contactList){
                        if(user.getPhoneNumber()==c.getContactNumber()){
                            contactDisplayList.add(c.getContactName()+"\n"+c.getContactNumber());
                        }
                    }
                }
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
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
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
