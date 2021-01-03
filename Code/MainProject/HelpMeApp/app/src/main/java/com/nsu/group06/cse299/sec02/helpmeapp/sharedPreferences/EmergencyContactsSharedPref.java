package com.nsu.group06.cse299.sec02.helpmeapp.sharedPreferences;

import android.content.Context;

import java.util.ArrayList;

/**
 * SharedPreference for saving list of emergency contacts
 * Emergency contacts saved by- phone numbers and usernames
 * TODO: should use Room database instead
 */
public class EmergencyContactsSharedPref extends SharedPrefsUtil {

    public static EmergencyContactsSharedPref build(Context context){

        return new EmergencyContactsSharedPref(SharedPrefKeysUtil.EMERGENCY_CONTACTS_ID, context);
    }

    private EmergencyContactsSharedPref(String mSharedPreferenceId, Context mContext) {
        super(mSharedPreferenceId, mContext);
    }

    public void addPhoneNumber(String phoneNumber){

        appendDataToList(SharedPrefKeysUtil.PHONE_NUMBERS_KEY, phoneNumber);
    }

    public ArrayList<String> getPhoneNumbers(){

        return getListOfData(SharedPrefKeysUtil.PHONE_NUMBERS_KEY);
    }

    public void removePhoneNumber(String phoneNumber){

        removeDataFromList(SharedPrefKeysUtil.PHONE_NUMBERS_KEY, phoneNumber);
    }

    public boolean doesPhoneNumberAlreadyExist(String phoneNumber){

        return doesDataExistInList(SharedPrefKeysUtil.PHONE_NUMBERS_KEY, phoneNumber);
    }

    public void addUsername(String username){

        appendDataToList(SharedPrefKeysUtil.USERNAME_KEY, username);
    }

    public ArrayList<String> getUsernames(){

        return getListOfData(SharedPrefKeysUtil.USERNAME_KEY);
    }

    public void removeUsername(String username){

        removeDataFromList(SharedPrefKeysUtil.USERNAME_KEY, username);
    }

}
