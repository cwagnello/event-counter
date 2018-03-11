package com.charles.util;

import java.util.HashMap;
import java.util.Map;

import com.charles.config.CacheConfig;

/**
 * Created by cwagnello on 3/8/18.
 */
public class SimpleCache implements MyCache {

    private static SimpleCache INSTANCE;
    private static Map<Integer, Integer> cache;
    private int cacheTimeToLive = CacheConfig.DEFAULT_MAX_TIME_IN_SECONDS;

    private SimpleCache() {
        //Not instantiable;
        if (cache == null) {
            cache = new HashMap<>();
        }
    }

    public static SimpleCache getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SimpleCache();
        }

        return INSTANCE;
    }

    @Override
    public int count(int timeWindowSize) {
        if (timeWindowSize > cacheTimeToLive) {
            throw new IllegalArgumentException("Time specified: " + timeWindowSize + ", exceeds cache time to live: " + cacheTimeToLive);
        }
        int count = 0;
        int currentTime = convertToSeconds(System.currentTimeMillis());
        for (Map.Entry entry : cache.entrySet()) {
            if ((int)entry.getKey() - currentTime < 0) {
                //System.out.println("Removing expired counts");
                cache.remove(entry);
            }
            else {
                int cacheTimeMinusOffset = (int)entry.getKey() - cacheTimeToLive;
                if (timeWindowSize + cacheTimeMinusOffset - currentTime >= 0) {
                    count += (int) entry.getValue();
                    //System.out.println("Adding counts");
                }
            }
        }
        return count;
    }

    @Override
    public void put() {
        int currentTime = convertToSeconds(System.currentTimeMillis()) + cacheTimeToLive;
        //System.out.println("Actual time: " + convertToSeconds(System.currentTimeMillis()) + ", cache expiry time: " + currentTime);
        cache.put(currentTime, cache.getOrDefault(currentTime, 0) + 1);
    }

    public void setCacheTimeToLive(int seconds) {
        if (seconds > CacheConfig.DEFAULT_MAX_TIME_IN_SECONDS) {
            throw new IllegalArgumentException("Time specified: " + seconds + ", exceeds cache time to live: " + cacheTimeToLive);
        }
        this.cacheTimeToLive = seconds;
    }

    private static int convertToSeconds(long milliseconds) {
        return (int)(milliseconds / 1000);
    }
}
