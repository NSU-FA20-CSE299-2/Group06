package com.nsu.group06.cse299.sec02.helpmeapp.auth;

/*
Abstract authentication user model
might contain (username, password) ; (email, password) ; (phone number)
which will be implemented by extending this class
 */
public abstract class AuthenticationUser {

    /*
    uid should be enough to identify an user
    no matter by which means(email/phonenumber/gmail) user signs up
     */
    protected String mUid;

    public AuthenticationUser() {

        mUid = null;
    }

    public AuthenticationUser(String mUid) {
        this.mUid = mUid;
    }

    public String getmUid() {
        return mUid;
    }

    public void setmUid(String mUid) {
        this.mUid = mUid;
    }
}
