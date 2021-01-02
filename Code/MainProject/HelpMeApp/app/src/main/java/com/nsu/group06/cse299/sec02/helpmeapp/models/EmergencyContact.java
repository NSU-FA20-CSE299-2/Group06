package com.nsu.group06.cse299.sec02.helpmeapp.models;

public class EmergencyContact {

    private String mUsername, mPhoneNumber;

    public EmergencyContact() {
    }

    public EmergencyContact(String mUsername, String mPhoneNumber) {
        this.mUsername = mUsername;
        this.mPhoneNumber = mPhoneNumber;
    }

    public boolean isUsernameValid(){

        return mUsername!=null && mUsername.length()>=4;
    }

    public boolean isPhoneNumberValid(){

        if(mPhoneNumber!=null && !mPhoneNumber.startsWith("+88")) {

            mPhoneNumber = "+88" + mPhoneNumber;
        }

        return mPhoneNumber!=null && mPhoneNumber.length()==14;
    }

    public String getmUsername() {
        return mUsername;
    }

    public void setmUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getmPhoneNumber() {
        return mPhoneNumber;
    }

    public void setmPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }
}
