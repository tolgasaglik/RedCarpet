package com.example.saglik.redcarpetapp.Classes;

/**
 * Created by SAGLIK on 20-Dec-17.
 */

public class Contact {
    private String contactName;
    private String contactNumber;

    public Contact(){

    }

    public Contact(String contactName, String contactNumber){
        this.contactName=contactName;
        this.contactNumber=contactNumber;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
