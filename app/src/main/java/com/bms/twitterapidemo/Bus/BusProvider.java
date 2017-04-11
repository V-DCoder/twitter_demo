package com.bms.twitterapidemo.Bus;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by roshan on 11/04/17.
 */

public class BusProvider {

    private static Bus bus;

    private BusProvider()
    {

    }

    public static Bus getDefaultBus() {
        if(bus==null)
            bus = new Bus(ThreadEnforcer.ANY);
        return bus;
    }
}
