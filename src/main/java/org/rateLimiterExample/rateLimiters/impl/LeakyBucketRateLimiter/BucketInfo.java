package org.rateLimiterExample.rateLimiters.impl.LeakyBucketRateLimiter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class BucketInfo {
    private long currentTokenCount;
    private long lastUpdatedTime ;
}
