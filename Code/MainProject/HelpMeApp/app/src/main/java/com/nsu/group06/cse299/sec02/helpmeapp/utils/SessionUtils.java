package com.nsu.group06.cse299.sec02.helpmeapp.utils;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.nsu.group06.cse299.sec02.helpmeapp.LoginActivity;
import com.nsu.group06.cse299.sec02.helpmeapp.R;
import com.nsu.group06.cse299.sec02.helpmeapp.auth.Authentication;

/**
 * Class to manage user session
 * like- logout immediately
 */
public class SessionUtils {

    /*
        Authentication failed, logout immediately
         */
    public static void doHardLogout(Context context, Authentication auth) {

        Toast.makeText(context, R.string.hard_logout, Toast.LENGTH_SHORT)
                .show();

        auth.signOut();

        Intent intent = new Intent(context, LoginActivity.class);

        // clear out all activities on the back stack and open LoginActivity
        // so that back press from this point on closes the app
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        context.startActivity(intent);
    }
}
