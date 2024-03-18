package org.rateLimiterExample.rateLimiters.impl;

import org.rateLimiterExample.rateLimiters.api.RateLimiter;

public class SlidingWindowRateLimiter<T> implements RateLimiter<T> {
    @Override
    public boolean allow(T key) {
        //TODO: implement sliding window rate limiter
        return false;
    }
}
