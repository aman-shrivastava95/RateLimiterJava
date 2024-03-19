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

        if (userWindow == null || currentTimeInMillis - userWindow.getStartTime() > window_ms ){
            userWindow =  new Window(currentTimeInMillis, 0) ;
        }

        if (userWindow.getRequestCount() >= maxRequestPerWindow){
            return false ;
        }
        //update the current window
        userWindow.setRequestCount(userWindow.getRequestCount() + 1);
        store.put(key, userWindow);
        return true ;
    }
}
