package org.rateLimiterExample.rateLimiters.impl.TokenBucketRateLimiter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TokenBucketInfo {

    private long lastRefillTime ;
    private long tokens ;
}
