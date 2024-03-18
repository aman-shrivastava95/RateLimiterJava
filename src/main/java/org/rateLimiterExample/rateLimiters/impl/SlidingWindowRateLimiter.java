package org.rateLimiterExample.rateLimiters.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.rateLimiterExample.rateLimiters.api.RateLimiter;

@AllArgsConstructor
@Getter
public class SlidingWindowRateLimiter<T> implements RateLimiter<T> {

    @Override
    public boolean allow(T key) {
        //TODO: implement sliding window rate limiter
        return false;
    }
}
