package com.charles.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by cwagnello on 3/8/18.
 */
public class SimpleCacheTest {

    SimpleCache cache = SimpleCache.getInstance();

    @Test
    public void retrieveCount() throws Exception {
        cache.setCacheTimeToLive(10);
        cache.put();
        cache.put();
        Thread.sleep(2000);
        System.out.println("Cache size: " + cache.count(3));
        assertTrue("", cache.count(3) == 2);

        cache.put();
        cache.put();
        cache.put();
        Thread.sleep(5000);
        assertTrue(cache.count(10) == 5);
        assertTrue(cache.count(6) == 3);
        Thread.sleep(6000);
        assertFalse("", cache.count(10) > 0);
        assertTrue("", cache.count(10) == 0);
    }

    @Test
    public void addEvent() throws Exception {

    }

}