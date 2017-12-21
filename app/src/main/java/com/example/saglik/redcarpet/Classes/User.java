package com.example.saglik.redcarpet.Classes;

/**
 * Created by SAGLIK on 20-Dec-17.
 */

public class User {
    private String nickname;
    private String location;
    private String phoneNumber;
    private boolean admin;
    private boolean isPrivate;
    private float rating;

    public User(){

    }

    public User(String name, String location, boolean isPrivate,boolean admin){
        this.nickname=name;
        this.location=location;
        this.admin = admin;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isAdmin() { return admin;  }

    public void setAdmin(boolean admin) { this.admin = admin; }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }
}
