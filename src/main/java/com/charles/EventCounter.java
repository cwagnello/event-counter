package com.charles;

import com.charles.util.SimpleCache;

/**
 * Created by cwagnello on 3/10/18.
 */
public class EventCounter {
    SimpleCache cache;

    public EventCounter() {
        if (cache == null) {
            cache = SimpleCache.getInstance();
        }
    }

    public void addEvent() {
        cache.put();
    }

    public int getEventCount(int seconds) {
        return cache.count(seconds);
    }
}
