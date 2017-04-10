package com.bms.twitterapidemo.dependency_injection;

import com.bms.twitterapidemo.mvp.presentors.MainActivityPresenter;
import com.bms.twitterapidemo.mvp.pv_interfaces.MainActivityPresentorCallback;

import dagger.Module;
import dagger.Provides;

/**
 * Created by roshan on 06/04/17.
 */

@Module

public class PresenterDependencyModule {

    @Provides
    public MainActivityPresenter getMainActivityPresenter()
    {
        return new MainActivityPresenter();
    }
}
