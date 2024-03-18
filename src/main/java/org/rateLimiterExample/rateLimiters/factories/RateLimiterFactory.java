package org.rateLimiterExample.rateLimiters.factories;

import org.rateLimiterExample.rateLimiters.api.RateLimiter;
import org.rateLimiterExample.rateLimiters.constants.RateLimiterConstants;
import org.rateLimiterExample.rateLimiters.impl.FixedWIndowRateLimiter.FixedWindowRateLimiter;
import org.rateLimiterExample.rateLimiters.models.Strategy;

import java.util.Properties;



public class RateLimiterFactory<T> {

    private RateLimiterFactory(){
    }
    public static <T> RateLimiter <T> getRateLimiter(Strategy strategy, Properties config){
        RateLimiter<T> rateLimiter = new FixedWindowRateLimiter<>(10 , 60000L);

        switch (strategy) {
            case FIXED_WINDOW :
                int max_request = Integer.parseInt(config.getProperty(RateLimiterConstants.MAX_REQUEST));
                Long window_ms = Long.parseLong(config.getProperty(RateLimiterConstants.TIME_WINDOW_MS));
                rateLimiter = new FixedWindowRateLimiter<T>(max_request, window_ms);
                break;
            case SLIDING_WINDOW:
                break;
            case TOKEN_BUCKET:
                break;
            default:

        }

        return rateLimiter ;
    }

}
