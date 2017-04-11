package com.bms.twitterapidemo.controller;

import com.bms.twitterapidemo.Bus.BusProvider;
import com.bms.twitterapidemo.mvp.model.SearchResult;
import com.bms.twitterapidemo.network.BearerToken;
import com.bms.twitterapidemo.network.NetworkConstants;
import com.bms.twitterapidemo.network.NetworkManager;
import com.squareup.otto.Bus;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import twitter4j.BASE64Encoder;

/**
 * Created by roshan on 10/04/17.
 */

public class MainActivityController {

    private final NetworkManager networkManager;
    private Bus bus;


    public MainActivityController(NetworkManager manager) {


        this.networkManager = manager;
        initBus();
    }

    private void initBus() {
        this.bus = BusProvider.getDefaultBus();
    }


    public void getTwitsForString(String s) {
        networkManager.getRetrofitAPIClient().getTwitForText("Bearer " + NetworkConstants.AccessToken, "\"" + s + "\"")
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new MainActivityController.SearchResultHandle(), new MainActivityController.SearchErrorHandle());
    }

    public void getBearrerToken() {

        String encodedSecrate = BASE64Encoder.encode(NetworkConstants.BearrerSecrate.getBytes());
        encodedSecrate = "Basic " + encodedSecrate;

        networkManager.getRetrofitAPIClient().getBearrerTokenAPI(encodedSecrate, "application/x-www-form-urlencoded", 29, "client_credentials").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new MainActivityController.GetBearrerTokenHandle(), new MainActivityController.SearchErrorHandle());

    }


    class GetBearrerTokenHandle implements Consumer<BearerToken> {

        @Override
        public void accept(BearerToken bearerToken) throws Exception {
            bus.post(bearerToken);
        }
    }

    class SearchResultHandle implements Consumer<SearchResult> {

        @Override
        public void accept(SearchResult searchResult) throws Exception {

            bus.post(searchResult);

        }
    }

    class SearchErrorHandle implements Consumer<Throwable> {


        @Override
        public void accept(Throwable e) throws Exception {
            e.printStackTrace();
            bus.post(e);

        }
    }
}
