package com.nsu.group06.cse299.sec02.firebasesdk.database;

import com.google.firebase.database.Query;

/*
    Interface for database classes to act as restful api endpoints

    <T> is always String
    but in case of firebase SDK it will be DatabaseReference
 */
public abstract class ApiEndPoint<T> {

    protected String mUrl;

    public ApiEndPoint(String mUrl) {
        this.mUrl = mUrl;
    }

    public abstract T toApiEndPoint();

    public String getmUrl() {
        return mUrl;
    }

}
