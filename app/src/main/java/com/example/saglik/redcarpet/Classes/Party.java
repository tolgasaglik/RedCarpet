package com.example.saglik.redcarpet.Classes;

/**
 * Created by SAGLIK on 20-Dec-17.
 */

public class Party {
    private String key;
    private String name;
    private String location;
    private String date;
    private String info;
    private String organizer;
    private String imageLink;
    private float organizerRating;

    public Party(){

    }

    public Party(String name, String location, String date, String info, String organizer, float organizerRating, String imageLink){
        this.name=name;
        this.location=location;
        this.date=date;
        this.info = info;
        this.organizer=organizer;
        this.imageLink=imageLink;
        this.organizerRating=organizerRating;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getImageLink() {return imageLink; }

    public void setImageLink(String imageLink) {this.imageLink = imageLink;}

    public float getOrganizerRating() {
        return organizerRating;
    }

    public void setOrganizerRating(float organizerRating) {
        this.organizerRating = organizerRating;
    }
}
