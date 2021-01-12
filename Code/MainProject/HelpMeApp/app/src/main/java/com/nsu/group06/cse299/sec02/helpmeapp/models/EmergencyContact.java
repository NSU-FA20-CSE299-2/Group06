package com.nsu.group06.cse299.sec02.helpmeapp.models;

import com.google.firebase.database.Exclude;

import java.util.Objects;

public class EmergencyContact {

    private String name, phoneNumber;

    public EmergencyContact() {
    }

    public EmergencyContact(String mName, String mPhoneNumber) {
        this.name = mName;
        this.phoneNumber = mPhoneNumber;
    }

    @Exclude // otherwise unnecessarily gets saved in firebase
    public boolean isNameValid(){

        return name!=null && !name.isEmpty();
    }

    @Exclude // otherwise unnecessarily gets saved in firebase
    public boolean isPhoneNumberValid(){

        if(phoneNumber!=null && !phoneNumber.startsWith("+88")) {

            phoneNumber = "+88" + phoneNumber;
        }

        return phoneNumber!=null && phoneNumber.length()==14;
    }

    public String getName() {
        return name;
    }

    public void setName(String mName) {
        this.name = mName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String mPhoneNumber) {
        this.phoneNumber = mPhoneNumber;
    }

    @Override
    public String toString() {
        return "EmergencyContact{" +
                "mName='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmergencyContact that = (EmergencyContact) o;
        return phoneNumber.equals(that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneNumber);
    }
}
