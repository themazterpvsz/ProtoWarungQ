package com.kretek.erab.managementwarung.model;

import java.lang.ref.SoftReference;

/**
 * Created by erab on 16/10/17.
 */

public class User {
    private int id;
    private String user;
    private String password;

    public int getId (){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getUser(){
        return user;
    }

    public void setUser(String user){
        this.user = user;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }
}