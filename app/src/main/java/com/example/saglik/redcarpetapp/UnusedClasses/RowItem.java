package com.example.saglik.redcarpetapp.UnusedClasses;

import android.widget.Switch;

/**
 * Created by SAGLIK on 17-Dec-17.
 */

public class RowItem {
    private String partyName;
    private int partyImageID;
    private String partyLocation;
    private boolean partyCheckIn;

    public RowItem(String partyName, int partyImageID, String partyLocation, boolean partyCheckIn){
        this.partyName=partyName;
        this.partyImageID=partyImageID;
        this.partyLocation=partyLocation;
        this.partyCheckIn=partyCheckIn;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public int getPartyImageID() {
        return partyImageID;
    }

    public void setPartyImageID(int partyImageID) {
        this.partyImageID = partyImageID;
    }

    public String getPartyLocation() {
        return partyLocation;
    }

    public void setPartyLocation(String partyLocation) {
        this.partyLocation = partyLocation;
    }

    public boolean isPartyCheckIn() {
        return partyCheckIn;
    }

    public void setPartyCheckIn(boolean partyCheckIn) {
        this.partyCheckIn = partyCheckIn;
    }
}
