package com.bms.twitterapidemo.mvp.presentors;

import android.os.Handler;
import android.os.Looper;

import com.bms.twitterapidemo.mvp.model.SearchResult;
import com.bms.twitterapidemo.mvp.pv_interfaces.MainActivityPresentorCallback;
import com.bms.twitterapidemo.network.BearerToken;
import com.bms.twitterapidemo.network.NetworkConstants;
import com.bms.twitterapidemo.network.NetworkManager;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import twitter4j.BASE64Encoder;

/**
 * Created by roshan on 06/04/17.
 */

public class MainActivityPresenter {

    private static final long WAIT_TIME = 1000;
    @Inject
    NetworkManager manager;
    private MainActivityPresentorCallback mViewCallback;
    private Handler handler;
    private Runnable mKeyPunchTimeOutRunnable;

    public MainActivityPresenter() {

    }

    public void startPresentor(MainActivityPresentorCallback callback) {
        mViewCallback = callback;
        handler = new Handler(Looper.getMainLooper());
        mKeyPunchTimeOutRunnable= new Runnable() {
            @Override
            public void run() {
            mViewCallback.onWaitComplete();
            }
        };
    }

    public void getBearerToken(final NetworkManager manager) {

        mViewCallback.onResetDisplay();
        String encodedSecrate = BASE64Encoder.encode(NetworkConstants.BearrerSecrate.getBytes());
        encodedSecrate = "Basic " + encodedSecrate;

        manager.getRetrofitAPIClient().getBearrerTokenAPI(encodedSecrate, "application/x-www-form-urlencoded", 29, "client_credentials").enqueue(new Callback<BearerToken>() {
            @Override
            public void onResponse(Call<BearerToken> call, Response<BearerToken> response) {

                if (response.isSuccessful()) {
                    BearerToken token = response.body();
                    NetworkConstants.AccessToken = token.getAccess_token();
                    mViewCallback.onAccesstokenSuccess();
                } else {
                    mViewCallback.onError("Critical information from Twitter not received");
                }
                mViewCallback.hideProgressDialog();
            }

            @Override
            public void onFailure(Call<BearerToken> call, Throwable t) {
                mViewCallback.onError("Oops some error occurred, Most common reason is network error");
                mViewCallback.hideProgressDialog();
            }
        });


    }


    public void getData(NetworkManager networkManager, String s) {

        if(s==null)
        return;
        if (s.length()<1)
            return;
        if(NetworkConstants.AccessToken.isEmpty())
        {
            getBearerToken(networkManager);
            return;
        }
        if(NetworkConstants.AccessToken.length()<1)
        {
            mViewCallback.onError("Critical information not given by Twitter, Try restarting app");
            return;
        }
       mViewCallback.showProgressDialog();
        networkManager.getRetrofitAPIClient().getTwitForText("Bearer " + NetworkConstants.AccessToken, "\"" + s + "\"").enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                if (response.isSuccessful()) {
                    SearchResult result = response.body();
                    mViewCallback.hideProgressDialog();
                    mViewCallback.onDataSuccess(response.body());
                } else {
                    mViewCallback.hideProgressDialog();
                    mViewCallback.onError("Data not available for query");
                }

            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
                t.printStackTrace();
                mViewCallback.hideProgressDialog();
                mViewCallback.onError("Oops some error occurred, Most common reason is network error");
            }
        });

    }

    public void display(SearchResult mSearchResult) {
        if (mSearchResult != null && mSearchResult.getStatuseList().size() > 0)
            mViewCallback.onResultReceived();
        else mViewCallback.onEmptyResult();


    }

    public void startRescheduleRunner() {
        if (handler!=null)
        {
            handler.removeCallbacks(mKeyPunchTimeOutRunnable);
            handler.removeCallbacks(mKeyPunchTimeOutRunnable,null);
            handler.removeCallbacks(null,null);
        }
        handler.postDelayed(mKeyPunchTimeOutRunnable,WAIT_TIME);
    }
}
