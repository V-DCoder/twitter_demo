package com.bms.twitterapidemo.dependency_injection;

import com.bms.twitterapidemo.network.NetworkManager;

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

}
