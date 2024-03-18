package org.rateLimiterExample.rateLimiters.api;

public interface RateLimiter<T> {
    public boolean  allow(T key);
}
