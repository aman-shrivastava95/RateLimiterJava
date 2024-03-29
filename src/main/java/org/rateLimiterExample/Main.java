package org.rateLimiterExample;


import org.rateLimiterExample.rateLimiters.api.RateLimiter;
import org.rateLimiterExample.rateLimiters.constants.RateLimiterConstants;
import org.rateLimiterExample.rateLimiters.factories.RateLimiterFactory;
import org.rateLimiterExample.rateLimiters.models.ClientRequest;
import org.rateLimiterExample.rateLimiters.models.Strategy;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hello world!");

        //configure rate limiter
        Properties rateLimitConfig = new Properties();
        rateLimitConfig.setProperty(RateLimiterConstants.MAX_REQUEST, "5");
        rateLimitConfig.setProperty(RateLimiterConstants.TIME_WINDOW_MS,"5");
        RateLimiter<String> fixedWindowRateLimiter = RateLimiterFactory.<String>getRateLimiter(Strategy.FIXED_WINDOW, rateLimitConfig) ;
        RateLimiter<String> slidingWindowRateLimiter = RateLimiterFactory.<String>getRateLimiter(Strategy.SLIDING_WINDOW, rateLimitConfig) ;

        rateLimitConfig.setProperty(RateLimiterConstants.REFILL_PERIOD, "1000" );
        rateLimitConfig.setProperty(RateLimiterConstants.BUCKET_CAPACITY, "5") ;
        rateLimitConfig.setProperty(RateLimiterConstants.BUCKET_LEAK_RATE,"1") ;
        RateLimiter<String> tokenBucketRateLimiter = RateLimiterFactory.<String>getRateLimiter(Strategy.TOKEN_BUCKET, rateLimitConfig) ;
        RateLimiter<String> leakyBucketRateLimiter = RateLimiterFactory.<String>getRateLimiter(Strategy.LEAKY_BUCKET,rateLimitConfig);
        RateLimiter<String> customRateLimiter =  RateLimiterFactory.<String>getRateLimiter(Strategy.CUSTOM_RATE_LIMITER, rateLimitConfig);
        //configure the service
        RateLimitedService service =  new RateLimitedService(customRateLimiter) ;


//        ExecutorService executorService = Executors.newFixedThreadPool(8) ;
//        for(int i = 1 ; i <= 8 ; i++ ){
//            executorService.execute( () -> service.serveRequest(new ClientRequest("user1", "user1")));
//        }
//        Thread.sleep(10000);
//        executorService.shutdown();
         for (int i=0; i<3; i++){
             service.serveRequest(new ClientRequest("user1","user1"));
         }
         Thread.sleep(5000);
        for (int i=0; i< 8; i++){
            service.serveRequest(new ClientRequest("user1","user1"));
        }
        Thread.sleep(5000);
        System.out.println("#######################");
        for (int i=0; i<2; i++){
            service.serveRequest(new ClientRequest("user1","user1"));
        }
        Thread.sleep(5000);
        for (int i=0; i<9; i++){
            service.serveRequest(new ClientRequest("user1","user1"));
        }
    }
}
