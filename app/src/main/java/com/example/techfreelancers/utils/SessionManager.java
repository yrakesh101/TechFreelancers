package com.example.techfreelancers.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

public class SessionManager {
    private static final String USER_SESSION_KEY = "LOGIN_USER_INFO";
    public static void saveUserSession(Context context, Map userInfo) {
        // Obtain a reference to the SharedPreferences
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_SESSION_KEY, Context.MODE_PRIVATE);
        // Create an editor to modify the SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // Clear all values from the SharedPreferences
        editor.clear();
        // Set values to the SharedPreferences
        editor.putBoolean("IS_LOGIN", true);
        editor.putInt("USER_ID", ((Double) userInfo.get("userId")).intValue());
        editor.putString("USER_EMAIL", userInfo.get("email").toString());
        editor.putString("USER_TOKEN", userInfo.get("userToken").toString());
        // Apply the changes
        editor.apply();
    }

    public static void clearUserSession(Context context) {
        // Obtain a reference to the SharedPreferences
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_SESSION_KEY, Context.MODE_PRIVATE);
        // Create an editor to modify the SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // Clear all values from the SharedPreferences
        editor.clear();
        // Apply the changes
        editor.apply();
    }
}
