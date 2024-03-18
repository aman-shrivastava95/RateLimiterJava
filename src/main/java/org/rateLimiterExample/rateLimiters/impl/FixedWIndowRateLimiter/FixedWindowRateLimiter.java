package org.rateLimiterExample.rateLimiters.impl.FixedWIndowRateLimiter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.rateLimiterExample.rateLimiters.api.RateLimiter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Getter
public class FixedWindowRateLimiter<T> implements RateLimiter<T> {

    private final int maxRequestPerWindow;
    private final Long window_ms ;
    private final Map<T, Window> store = new HashMap<>();


    @Override
    public  synchronized boolean  allow(T key) {
        long currentTimeInMillis = System.currentTimeMillis();
        Window userWindow = store.get(key) ;
        //create new window if the window doesn't exist or window has expired
        if (userWindow == null || currentTimeInMillis - userWindow.getStartTime() > window_ms ){
            userWindow =  new Window(currentTimeInMillis, 0) ;
        }
        System.out.println(userWindow.getRequestCount());
        if (userWindow.getRequestCount() >= maxRequestPerWindow){
            return false ;// request not allowed
        }
        //update the window
        userWindow.setRequestCount(userWindow.getRequestCount() + 1);
        store.put(key, userWindow);
        return true ;
    }
}
