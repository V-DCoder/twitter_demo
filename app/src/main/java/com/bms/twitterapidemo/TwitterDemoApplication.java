package com.bms.twitterapidemo;

import android.app.Application;

import com.bms.twitterapidemo.dependency_injection.DaggerInjectionComponent;
import com.bms.twitterapidemo.dependency_injection.InjectionComponent;

/**
 * Created by roshan on 06/04/17.
 */

public class TwitterDemoApplication extends Application {
    InjectionComponent injectorComponent;
    @Override
    public void onCreate() {
        super.onCreate();

        injectorComponent = DaggerInjectionComponent.builder().build();

    }

    public InjectionComponent getInjectorComponent() {
        return injectorComponent;
    }
}
