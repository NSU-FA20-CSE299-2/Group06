package com.nsu.group06.cse299.sec02.helpmeapp.models;

import com.google.firebase.database.Exclude;

public class EmergencyContact {

    private String mName, mPhoneNumber;

    public EmergencyContact() {
    }

    public EmergencyContact(String mName, String mPhoneNumber) {
        this.mName = mName;
        this.mPhoneNumber = mPhoneNumber;
    }

    @Exclude // otherwise unnecessarily gets saved in firebase
    public boolean isNameValid(){

        return mName!=null && !mName.isEmpty();
    }

    @Exclude // otherwise unnecessarily gets saved in firebase
    public boolean isPhoneNumberValid(){

        if(mPhoneNumber!=null && !mPhoneNumber.startsWith("+88")) {

            mPhoneNumber = "+88" + mPhoneNumber;
        }

        return mPhoneNumber!=null && mPhoneNumber.length()==14;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmPhoneNumber() {
        return mPhoneNumber;
    }

    public void setmPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }
}
