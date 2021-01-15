package com.nsu.group06.cse299.sec02.helpmeapp.models;

import com.google.firebase.database.Exclude;

/**
 * Model class containing basic information of an user
 */
public class User {

    private String uid;
    private String username = "";
    private String email = "";
    private String dateOfBirth = "";
    private String address = "";
    private String phoneNumber = "";

    public User() {
        /*
        no parameter constructor is required to store objects in firebase
         */
    }

    public User(String mUid) {
        this.uid = mUid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String mUid) {
        this.uid = mUid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String mUsername) {
        this.username = mUsername;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String mEmail) {
        this.email = mEmail;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String mDateOfBirth) {
        this.dateOfBirth = mDateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String mAddress) {
        this.address = mAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String mPhoneNumber) {
        this.phoneNumber = mPhoneNumber;
    }
}
