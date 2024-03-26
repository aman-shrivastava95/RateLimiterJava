package org.rateLimiterExample.rateLimiters.impl.CustomRateLimiter;

import lombok.AllArgsConstructor;
import org.rateLimiterExample.rateLimiters.api.RateLimiter;

import java.time.Duration;
import java.time.Instant;
import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@AllArgsConstructor
public class CustomRateLimiter<T> implements RateLimiter<T> {
    private long maxRequest ;
    private long windowLengthInSeconds ;
    private long maxCreditLimit ;
    private final Map<T, WindowConfig> userWindowConfigMap = new ConcurrentHashMap<>() ;
    @Override
    public boolean allow(T key) {
        WindowConfig userWindowConfig = userWindowConfigMap.computeIfAbsent(key, k -> new WindowConfig(0)) ;
        Instant currentTime = Instant.now() ;
        Deque<Instant> userWindow =  userWindowConfig.getWindow() ;
        updateCredit(userWindowConfig, currentTime) ;

        while( !userWindow.isEmpty() && Duration.between(userWindow.peekFirst(), currentTime).toSeconds() >= windowLengthInSeconds){
            userWindow.pollFirst() ;
        }
        if (userWindow.size() < maxRequest){
            userWindow.addLast(currentTime);
            //update the credit count for current window
            return true ;
        }else if(userWindowConfig.getCreditCount() > 0){
            userWindowConfig.setCreditCount(userWindowConfig.getCreditCount() - 1);
            return true ;
        }
        return false ;
    }

    private void updateCredit(WindowConfig userWindowConfig, Instant currentTime) {
        Deque<Instant> userWindow =  userWindowConfig.getWindow() ;
        if (!userWindow.isEmpty()){
            long timeElapsed = Duration.between(userWindow.peekLast(), Instant.now()).toSeconds() ;
            if(timeElapsed >= windowLengthInSeconds){
                userWindowConfig.setCreditCount(Math.min(maxCreditLimit, maxRequest -  userWindow.size()));
            }
        }
    }
}
