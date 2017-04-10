package com.bms.twitterapidemo.mvp.pv_interfaces;

import com.bms.twitterapidemo.mvp.model.SearchResult;

/**
 * Created by roshan on 06/04/17.
 */

public interface MainActivityPresentorCallback {

     void onAccesstokenSuccess();
    void onDataSuccess(SearchResult result);
    void onEmptyResult();
    void onResultReceived();
    void onResetDisplay();
    void onError(String error);
    void onWaitComplete();

    void showProgressDialog();
    void hideProgressDialog();


}
