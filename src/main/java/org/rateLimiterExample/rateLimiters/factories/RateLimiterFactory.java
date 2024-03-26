package org.rateLimiterExample.rateLimiters.factories;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.rateLimiterExample.rateLimiters.api.RateLimiter;
import org.rateLimiterExample.rateLimiters.constants.RateLimiterConstants;
import org.rateLimiterExample.rateLimiters.impl.CustomRateLimiter.CustomRateLimiter;
import org.rateLimiterExample.rateLimiters.impl.FixedWIndowRateLimiter.FixedWindowRateLimiter;
import org.rateLimiterExample.rateLimiters.impl.LeakyBucketRateLimiter.LeakyBucketRateLimiter;
import org.rateLimiterExample.rateLimiters.impl.SlidingWindowRateLimiter;
import org.rateLimiterExample.rateLimiters.impl.TokenBucketRateLimiter.TokenBucketRateLimiter;
import org.rateLimiterExample.rateLimiters.models.Strategy;

import java.util.Map;
import java.util.Properties;



public class RateLimiterFactory<T> {

    @AllArgsConstructor
    @Getter
    private static class WindowConfig{
        int max_request ;
        Long window_time ;
    }

    private static WindowConfig getCommonWindowConfigs(Properties config){
        int max_request = Integer.parseInt(config.getProperty(RateLimiterConstants.MAX_REQUEST));
        Long window_ms = Long.parseLong(config.getProperty(RateLimiterConstants.TIME_WINDOW_MS));
        return new WindowConfig(max_request, window_ms) ;
    }

    private RateLimiterFactory(){
    }
    public static <T> RateLimiter <T> getRateLimiter(Strategy strategy, Properties config){
        RateLimiter<T> rateLimiter = new FixedWindowRateLimiter<>(10 , 60000L);

        switch (strategy) {
            case FIXED_WINDOW :
                WindowConfig fixedWindowConfig = getCommonWindowConfigs(config);
                rateLimiter = new FixedWindowRateLimiter<T>(fixedWindowConfig.getMax_request(), fixedWindowConfig.getWindow_time());
                break;
            case SLIDING_WINDOW:
                WindowConfig slidingWindowConfig = getCommonWindowConfigs(config);
                rateLimiter = new SlidingWindowRateLimiter<T>(slidingWindowConfig.getMax_request(), slidingWindowConfig.getWindow_time());
                break;
            case TOKEN_BUCKET:
                long capacity = Long.parseLong(config.getProperty(RateLimiterConstants.MAX_REQUEST)) ;
                long refillPeriod = Long.parseLong(config.getProperty(RateLimiterConstants.REFILL_PERIOD)) ;
                rateLimiter = new TokenBucketRateLimiter<T>(capacity, refillPeriod) ;
                break;
            case LEAKY_BUCKET:
                long bucketCapacity =  Long.parseLong(config.getProperty(RateLimiterConstants.BUCKET_CAPACITY));
                long leakRate =  Long.parseLong(config.getProperty(RateLimiterConstants.BUCKET_LEAK_RATE));
                rateLimiter = new LeakyBucketRateLimiter<>(bucketCapacity, leakRate) ;
            case CUSTOM_RATE_LIMITER:
                WindowConfig customConfig = getCommonWindowConfigs(config);
                rateLimiter = new CustomRateLimiter<T>(customConfig.getMax_request(), customConfig.getWindow_time(), 5);
                break ;
            default:

        }
        return rateLimiter ;
    }
}
