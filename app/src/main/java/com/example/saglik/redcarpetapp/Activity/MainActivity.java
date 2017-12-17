package com.example.saglik.redcarpetapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.saglik.redcarpetapp.Classes.Party;
import com.example.saglik.redcarpetapp.Classes.User;
import com.example.saglik.redcarpetapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Intent intent;
    NavigationView navigationView;

    //Listview Elements
//    String[] partyNames;
//    TypedArray partyPics;
//    String[] partyLocations;
//    boolean[] partyCheckins;
//    List<RowItem> rowItems;
    ListView partyListView;
    private DatabaseReference mDatabase;
    private ArrayList<String> partyNames = new ArrayList<>();

    boolean isUserAdmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Display parties as a list
        partyListView = findViewById(R.id.partyListView);

        refreshPartyList();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        //Edit visibility of admin mode
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setAdminView();


        partyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String partyName = (String) partyListView.getItemAtPosition(i);
                Intent intent = new Intent(MainActivity.this,PartyCheckinActivity.class);
                intent.putExtra("partyName", partyName);
                startActivity(intent);
            }
        });
    }

    public void refreshPartyList(){

        final ArrayAdapter<String> partyAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, partyNames);
        partyListView.setAdapter(partyAdapter);

        mDatabase = FirebaseDatabase.getInstance().getReference("parties/");
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Party party = dataSnapshot.getValue(Party.class);
                partyNames.add(party.getName());
                partyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private void setAdminView() {
        //Make administration invisible if current user is not admin
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser userA = mAuth.getCurrentUser();
        String userID = userA.getUid();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users"+"/"+userID);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                isUserAdmin =  user.isAdmin();
                Menu navMenu = navigationView.getMenu();
                navMenu.findItem(R.id.adminMenu).setVisible(isUserAdmin);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {
            intent = new Intent(MainActivity.this,PartyCreateActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_manage) {
            intent = new Intent(MainActivity.this,ProfileActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
