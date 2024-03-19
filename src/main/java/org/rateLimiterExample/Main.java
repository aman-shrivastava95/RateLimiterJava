package org.rateLimiterExample;


import org.rateLimiterExample.rateLimiters.api.RateLimiter;
import org.rateLimiterExample.rateLimiters.constants.RateLimiterConstants;
import org.rateLimiterExample.rateLimiters.factories.RateLimiterFactory;
import org.rateLimiterExample.rateLimiters.models.ClientRequest;
import org.rateLimiterExample.rateLimiters.models.Strategy;

import java.util.Properties;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello world!");

        //configure rate limiter
        Properties rateLimitConfig = new Properties();
        rateLimitConfig.setProperty(RateLimiterConstants.MAX_REQUEST, "5");
        rateLimitConfig.setProperty(RateLimiterConstants.TIME_WINDOW_MS,"10000");
        RateLimiter<String> fixedWindowRateLimiter = RateLimiterFactory.<String>getRateLimiter(Strategy.FIXED_WINDOW, rateLimitConfig) ;
        RateLimiter<String> slidingWindowRateLimiter = RateLimiterFactory.<String>getRateLimiter(Strategy.SLIDING_WINDOW, rateLimitConfig) ;

        //configure the service
        RateLimitedService service =  new RateLimitedService(slidingWindowRateLimiter) ;
        //TODO: start initiating service from thread once

        for(int i = 1 ; i <= 6 ; i++ ){
            service.serveRequest(new ClientRequest("user1", "user1"));
        }
    }
}