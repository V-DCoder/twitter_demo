package com.bms.twitterapidemo.dependency_injection;

import com.bms.twitterapidemo.controller.MainActivityController;
import com.bms.twitterapidemo.network.NetworkManager;
import com.squareup.otto.Bus;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by roshan on 06/04/17.
 */

@Module
public class NetworkDependencyModule {

    @Singleton
    @Provides
    public NetworkManager getNetworkManager()
    {
        return new NetworkManager();
    }

    @Singleton
    @Provides
    public MainActivityController getController( NetworkManager manager)
    {
        return new MainActivityController( manager);
    };
}
