package org.rateLimiterExample.rateLimiters.impl.LeakyBucketRateLimiter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@AllArgsConstructor
@Getter
@Setter
public class BucketInfo {
    private long currentTokenCount;
    private Instant lastUpdatedTime ;
}
