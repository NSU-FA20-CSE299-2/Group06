package com.nsu.group06.cse299.sec02.helpmeapp.models;

import com.google.firebase.database.Exclude;

/**
 * Model class containing basic information of an user
 */
public class User {

    private String mUid;
    private String mUsername = "";
    private String mEmail = "";
    private String mDateOfBirth = "";
    private String mAddress = "";
    private String mPhoneNumber = "";

    public User() {
        /*
        no parameter constructor is required to store objects in firebase
         */
    }

    public User(String mUid) {
        this.mUid = mUid;
    }

    public String getmUid() {
        return mUid;
    }

    public void setmUid(String mUid) {
        this.mUid = mUid;
    }

    public String getmUsername() {
        return mUsername;
    }

    public void setmUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmDateOfBirth() {
        return mDateOfBirth;
    }

    public void setmDateOfBirth(String mDateOfBirth) {
        this.mDateOfBirth = mDateOfBirth;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getmPhoneNumber() {
        return mPhoneNumber;
    }

    public void setmPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }
}
