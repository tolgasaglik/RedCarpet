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
    private String name;
    private String location;
    private String date;
    private String info;
    private String organizer;
    private List<String> participants = new List<String>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @NonNull
        @Override
        public Iterator<String> iterator() {
            return null;
        }

        @NonNull
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @NonNull
        @Override
        public <T> T[] toArray(@NonNull T[] ts) {
            return null;
        }

        @Override
        public boolean add(String s) {
            return false;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean containsAll(@NonNull Collection<?> collection) {
            return false;
        }

        @Override
        public boolean addAll(@NonNull Collection<? extends String> collection) {
            return false;
        }

        @Override
        public boolean addAll(int i, @NonNull Collection<? extends String> collection) {
            return false;
        }

        @Override
        public boolean removeAll(@NonNull Collection<?> collection) {
            return false;
        }

        @Override
        public boolean retainAll(@NonNull Collection<?> collection) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public String get(int i) {
            return null;
        }

        @Override
        public String set(int i, String s) {
            return null;
        }

        @Override
        public void add(int i, String s) {

        }

        @Override
        public String remove(int i) {
            return null;
        }

        @Override
        public int indexOf(Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(Object o) {
            return 0;
        }

        @NonNull
        @Override
        public ListIterator<String> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<String> listIterator(int i) {
            return null;
        }

        @NonNull
        @Override
        public List<String> subList(int i, int i1) {
            return null;
        }
    };

    public Party(){

    }

    public Party(String name, String location, String date, String info, String organizer){
        this.name=name;
        this.location=location;
        this.date=date;
        this.info = info;
        this.organizer=organizer;
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

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }
}
