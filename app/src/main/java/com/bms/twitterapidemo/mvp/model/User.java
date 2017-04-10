package com.bms.twitterapidemo.mvp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by roshan on 06/04/17.
 */

public class User {

    @SerializedName("name")
    private String name;

    public User(String name) {
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
