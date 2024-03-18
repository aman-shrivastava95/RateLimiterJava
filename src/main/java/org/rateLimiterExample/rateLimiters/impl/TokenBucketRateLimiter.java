package org.rateLimiterExample.rateLimiters.impl;

import org.rateLimiterExample.rateLimiters.api.RateLimiter;

public class TokenBucketRateLimiter<T> implements RateLimiter<T> {

    @Override
    public boolean allow(T key) {
        //TODO:  the logic for Token Bucket Rate limiter
        return false ;
    }
}
