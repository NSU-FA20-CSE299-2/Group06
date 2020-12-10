package com.nsu.group06.cse299.sec02.firebasesdk.auth;

/*
Auth user with email & password
 */
public class EmailPasswordAuthUser extends AuthenticationUser {

    private String mEmail;
    private String mPassword;

    public EmailPasswordAuthUser() {
        super();
    }

    public EmailPasswordAuthUser(String mEmail, String mPassword) {

        super();

        this.mEmail = mEmail;
        this.mPassword = mPassword;
    }

    public EmailPasswordAuthUser(String mUid, String mEmail, String mPassword) {
        super(mUid);
        this.mEmail = mEmail;
        this.mPassword = mPassword;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }
}
