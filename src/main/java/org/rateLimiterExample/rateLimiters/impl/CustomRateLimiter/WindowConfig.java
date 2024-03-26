package org.rateLimiterExample.rateLimiters.impl.CustomRateLimiter;

import lombok.*;

import java.time.Instant;
import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

@RequiredArgsConstructor
@Getter
public class WindowConfig {
    Deque<Instant> window = new ConcurrentLinkedDeque<>() ;

    @Setter
    @NonNull
    long creditCount  ;
}
