package com.bodykh.Milestone1;

public class Contact implements Comparable<Contact> {

    private int id;
    private String name;
    private long phone;
    private byte[] image;


    public Contact(int id, String name, long phone, byte[] image) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.image = image;
    }


    public Contact(String name, long phone, byte[] image) {
        this.name = name;
        this.phone = phone;
        this.image = image;


    }

    @Override
    public int compareTo(Contact o) {
        return this.name.compareTo(o.name);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
