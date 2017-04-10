package com.bms.twitterapidemo.mvp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by roshan on 06/04/17.
 */

public class SearchResult {

    @SerializedName("statuses")
    private ArrayList<Status> statuseList;

    public SearchResult(ArrayList<Status> statuseList) {
        this.statuseList = statuseList;
    }

    public ArrayList<Status> getStatuseList() {
        return statuseList;
    }

    public void setStatuseList(ArrayList<Status> statuseList) {
        this.statuseList = statuseList;
    }
}
