package com.nsu.group06.cse299.sec02.firebasesdk.auth;

/*
    Abstraction for user signup/login authentication classes
 */
public abstract class Auth {

    abstract void signUpWithUsernamePassword(String username, String password);
    abstract void signInWithUsernamePassword(String username, String password);

    /*
        callbacks for signUp/signIn events
     */
    public interface authCallbacks{

        void onSignUpSuccess(String message);
        void onSignUpFailure(String message);

        void onSignInSuccess(String message);
        void onSignInFailure(String message);
    }
}
