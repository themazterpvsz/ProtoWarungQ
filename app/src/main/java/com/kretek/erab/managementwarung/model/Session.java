package com.kretek.erab.managementwarung.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.view.View;


/**
 * Created by erab on 18/10/17.
 */

public class Session {


    public static final String app = "ManagementWarung";

    public static String user = null;
    public static final String pass = "Password";

    public static final String loggedIn = "Sudah Masuk";
    public static final String loggedInAsAdmin = "Sudah Masuk Sebagai Admin";

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    public Session(Context context){
        sp = context.getSharedPreferences(app, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public void saveString(String keyString, String value){
        editor.putString(keyString,value);
        editor.commit();
    }

    public void saveInt(String keyString, int value){
        editor.putInt(keyString,value);
        editor.commit();
    }

    public void saveBoolean(String keyString, boolean value){
        editor.putBoolean(keyString,value);
        editor.commit();
    }

    public void saveBooleanAsAdmin(String keyString, boolean value){
        editor.clear();
        editor.putBoolean(keyString,value);
        editor.commit();
    }

    public String getId (String id){
        return user = id;
    }

    public String getUser(){
        return sp.getString(user, "");
    }

    public boolean getAlreadyLoggedIn(){
        return sp.getBoolean(loggedIn, false);
    }

    public boolean getAlreadyLoggedInAsAdmin(){
        return sp.getBoolean(loggedInAsAdmin,false);
    }

    public void logOut(){
        editor.clear();
        editor.commit();
    }
}
