package com.solution.brothergroup.Util;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Vishnu on 01-02-2017.
 */

public class GlobalBus {

    private static EventBus sBus;

    public static EventBus getBus() {
        if (sBus == null)
            sBus = EventBus.getDefault();
        return sBus;
    }

}
