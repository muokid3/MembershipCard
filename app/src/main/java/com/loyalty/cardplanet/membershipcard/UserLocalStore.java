package com.loyalty.cardplanet.membershipcard;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by muoki on 8/14/2015.
 */
public class UserLocalStore {
    public static String SP_NAME = "userDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context)
    {
        userLocalDatabase = context.getSharedPreferences(SP_NAME,0);
    }

    public void storeUserData(User user)
    {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("name", user.merchName);
        spEditor.putString("email", user.merchUsername);
        spEditor.putString("password", user.merchPassword);
        spEditor.commit();
    }

    public User getLoggedInUser()
    {
        String merchName = userLocalDatabase.getString("name", "");
        String merchEmail = userLocalDatabase.getString("email","");
        String merchPassword = userLocalDatabase.getString("password","");


        User newUser = new User(merchName, merchEmail, merchPassword);
        return newUser;
    }

    public void setUserLoggedIn(boolean loggedIn)
    {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.commit();
    }

    public boolean getBoolUserLoggedIn()
    {
        if (userLocalDatabase.getBoolean("loggedIn", false) == true)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public void clearUserData()
    {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }
}
