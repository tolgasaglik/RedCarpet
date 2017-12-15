package com.example.saglik.redcarpetapp.Classes;

/**
 * Created by SAGLIK on 15-Dec-17.
 */

public class User {
    private String nickname;
    private String location;
    private String phoneNumber;

    public User(){

    }
    public User(String name, String location){
        this.nickname=name;
        this.location=location;
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
}
