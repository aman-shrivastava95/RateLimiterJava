package org.rateLimiterExample.rateLimiters.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.rateLimiterExample.rateLimiters.api.RateLimiter;

import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@AllArgsConstructor
@Getter
public class SlidingWindowRateLimiter<T> implements RateLimiter<T> {
    private final int maxRequestPerWindow;
    private final Long window_ms;
    private final Map<T, Deque<Long>> userWindowMap = new ConcurrentHashMap<>();
    private final Lock lock  = new ReentrantLock() ;
    @Override
    public boolean allow(T key) {
        Deque<Long> userWindow = userWindowMap.computeIfAbsent(key, k-> new ConcurrentLinkedDeque<>());
        //remove all the  requests in the queue which have expired
        Long currentTime = System.currentTimeMillis();
        //this need to be atomic, other there will be race conditions in properly updating the window
        lock.lock();
        try{
            while(!userWindow.isEmpty() && currentTime  - userWindow.peekFirst() > window_ms){
                userWindow.pollFirst() ;
            }
            // process request only max requests is not breached
            if (userWindow.size() < maxRequestPerWindow){
                userWindow.addLast(currentTime);
                return true ;
            }
            return false;
        } finally {
            lock.unlock();
        }

    }
}
