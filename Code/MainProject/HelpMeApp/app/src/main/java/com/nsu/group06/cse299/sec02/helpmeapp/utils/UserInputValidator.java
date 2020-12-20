package com.nsu.group06.cse299.sec02.helpmeapp.utils;

import java.util.regex.Pattern;

/**
 * Validate user inputs- email, password
 */
public class UserInputValidator {

    /*
    courtesy- <https://www.geeksforgeeks.org/check-email-address-valid-not-java/>
     */
    public static boolean isEmailValid(String email){

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public static boolean isPasswordValid(String password){

        return password.length()>=6;
    }

}
