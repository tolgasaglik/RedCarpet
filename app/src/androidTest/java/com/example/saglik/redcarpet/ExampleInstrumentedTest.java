package com.example.saglik.redcarpet;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.saglik.redcarpet.Classes.User;
import com.example.saglik.redcarpet.Database.DatabaseWriter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private DatabaseReference mDatabase;
    // [END declare_database_ref]
    private FirebaseAuth mAuth;
    private User user;
    private User user2;
    private DatabaseWriter dw;
    private String userID;
    private Context instrumentationCtx;

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.saglik.redcarpet", appContext.getPackageName());
    }


    @Before
    public void setUp() {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser userA = mAuth.getCurrentUser();
        userID = userA.getUid();               //GET ID FROM AUTHENTICATION
        // [START initialize_database_ref]
        dw = new DatabaseWriter();
        mDatabase = FirebaseDatabase.getInstance().getReference("users/" + userID);
        // [END initialize_database_ref]
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {}
                //If user exist show the old information
                else {
                    try {
                        user = dataSnapshot.getValue(User.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Test
    public void uploadWord_Test() throws Exception {
        while(user==null){}
        assertEquals("kirchberg", user.getLocation());
        assertEquals("+352661900823", user.getPhoneNumber());
        assertEquals(userID, dw.getUserID());
    }

}
