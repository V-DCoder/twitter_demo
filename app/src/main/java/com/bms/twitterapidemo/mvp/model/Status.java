package com.bms.twitterapidemo.mvp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by roshan on 06/04/17.
 */

public class Status {

    @SerializedName("text")
    private String text;
    @SerializedName("user")
    private User user;

    public Status(String text, User user) {
        this.text = text;
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }






}
