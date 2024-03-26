package org.rateLimiterExample.rateLimiters.models;

public enum Strategy {
    FIXED_WINDOW,
    SLIDING_WINDOW,
    TOKEN_BUCKET,
    LEAKY_BUCKET,
    CUSTOM_RATE_LIMITER
}
