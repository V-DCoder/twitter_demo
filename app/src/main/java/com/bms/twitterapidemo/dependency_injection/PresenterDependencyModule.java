package com.bms.twitterapidemo.dependency_injection;

import com.bms.twitterapidemo.controller.MainActivityController;
import com.bms.twitterapidemo.mvp.presentors.MainActivityPresenter;
import com.bms.twitterapidemo.mvp.pv_interfaces.MainActivityPresentorCallback;
import com.bms.twitterapidemo.network.NetworkManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by roshan on 06/04/17.
 */

@Module

public class PresenterDependencyModule {

    @Provides
    public MainActivityPresenter getMainActivityPresenter(NetworkManager manager, MainActivityController controller)
    {
        return new MainActivityPresenter(manager,controller);
    }
}
