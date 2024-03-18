package org.rateLimiterExample.rateLimiters.impl.FixedWIndowRateLimiter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class Window {
    private final long startTime ;
    @Setter
    private int requestCount ;

}
