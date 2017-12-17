package com.example.saglik.redcarpetapp.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.saglik.redcarpetapp.R;

public class PartyCheckinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_checkin);

        Intent intent = getIntent();
        String partyName = intent.getStringExtra("partyName");
        setTitle(partyName);
    }
}
