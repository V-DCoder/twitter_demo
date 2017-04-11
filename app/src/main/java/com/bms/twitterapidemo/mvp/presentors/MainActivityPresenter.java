package com.bms.twitterapidemo.mvp.presentors;

import android.os.Handler;
import android.os.Looper;

import com.bms.twitterapidemo.Bus.BusProvider;
import com.bms.twitterapidemo.controller.MainActivityController;
import com.bms.twitterapidemo.mvp.model.SearchResult;
import com.bms.twitterapidemo.mvp.pv_interfaces.MainActivityPresentorCallback;
import com.bms.twitterapidemo.network.BearerToken;
import com.bms.twitterapidemo.network.NetworkConstants;
import com.bms.twitterapidemo.network.NetworkManager;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

/**
 * Created by roshan on 06/04/17.
 */

public class MainActivityPresenter {

    private static final long WAIT_TIME = 1000;

    NetworkManager manager;

    MainActivityController mMainActivityController;
    private MainActivityPresentorCallback mViewCallback;
    private Handler handler;
    private Runnable mKeyPunchTimeOutRunnable;


    @Inject
    public MainActivityPresenter(NetworkManager manager, MainActivityController mMainActivityController) {
        this.manager = manager;
        this.mMainActivityController = mMainActivityController;

    }

    public void startPresentor(MainActivityPresentorCallback callback) {
        mViewCallback = callback;
        handler = new Handler(Looper.getMainLooper());
        mKeyPunchTimeOutRunnable = new Runnable() {
            @Override
            public void run() {
                mViewCallback.onWaitComplete();
            }
        };
        BusProvider.getDefaultBus().register(this);

    }

    public void getBearerToken() {
        mMainActivityController = new MainActivityController(manager);

        mViewCallback.onResetDisplay();
        mMainActivityController.getBearrerToken();

    }


    public void getData(String s) {

        if (s == null)
            return;
        if (s.length() < 1)
            return;
        if (NetworkConstants.AccessToken.isEmpty()) {
            getBearerToken();

            return;
        }
        if (NetworkConstants.AccessToken.length() < 1) {
            mViewCallback.onError("Critical information not given by Twitter, Try restarting app");
            return;
        }
        mViewCallback.showProgressDialog();
        mMainActivityController.getTwitsForString(s);

    }

    @Subscribe
    public void handleBearrerTokenResponse(BearerToken token) {
        if (token != null) {

            NetworkConstants.AccessToken = token.getAccess_token();
            mViewCallback.onAccesstokenSuccess();
        } else {
            mViewCallback.onError("Critical information from Twitter not received");
        }
        mViewCallback.hideProgressDialog();
    }


    @Subscribe
    public void handleError(Throwable error) {
        error.printStackTrace();
        mViewCallback.hideProgressDialog();
        mViewCallback.onError("Oops some error occurred, Most common reason is network error");

    }

    @Subscribe
    public void handleResponse(SearchResult response) {

        if (response != null) {
            mViewCallback.hideProgressDialog();
            mViewCallback.onDataSuccess(response);
        } else {
            mViewCallback.hideProgressDialog();
            mViewCallback.onError("Data not available for query");
        }
    }

    public void display(SearchResult mSearchResult) {
        if (mSearchResult != null && mSearchResult.getStatuseList().size() > 0)
            mViewCallback.onResultReceived();
        else mViewCallback.onEmptyResult();


    }

    public void startRescheduleRunner() {
        if (handler != null) {
            handler.removeCallbacks(mKeyPunchTimeOutRunnable);
            handler.removeCallbacks(mKeyPunchTimeOutRunnable, null);
            handler.removeCallbacks(null, null);
        }
        handler.postDelayed(mKeyPunchTimeOutRunnable, WAIT_TIME);
    }


    public void stopPresentor() {
        BusProvider.getDefaultBus().unregister(this);
    }
}
