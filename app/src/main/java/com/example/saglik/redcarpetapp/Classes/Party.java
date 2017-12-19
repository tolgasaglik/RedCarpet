package com.example.saglik.redcarpetapp.Classes;

import android.support.annotation.NonNull;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by SAGLIK on 17-Dec-17.
 */

public class Party {
    private String key;
    private String name;
    private String location;
    private String date;
    private String info;
    private String organizer;
    private String imageLink;

    public Party(){

    }

    public Party(String name, String location, String date, String info, String organizer, String imageLink){
        this.name=name;
        this.location=location;
        this.date=date;
        this.info = info;
        this.organizer=organizer;
        this.imageLink=imageLink;
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

}
