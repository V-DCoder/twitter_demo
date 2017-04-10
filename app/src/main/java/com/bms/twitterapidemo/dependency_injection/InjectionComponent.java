package com.bms.twitterapidemo.dependency_injection;

import com.bms.twitterapidemo.mvp.views.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by roshan on 06/04/17.
 */

@Singleton
@Component(modules={NetworkDependencyModule.class,PresenterDependencyModule.class})

public interface InjectionComponent {
    void inject(MainActivity activity);
}
