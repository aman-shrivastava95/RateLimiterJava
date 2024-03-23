package org.rateLimiterExample.rateLimiters.impl.TokenBucketRateLimiter;

import lombok.AllArgsConstructor;
import org.rateLimiterExample.rateLimiters.api.RateLimiter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*Provides a more controlled way of handling requests, keeps filling the bucket in regular intervals*/
@AllArgsConstructor
public class TokenBucketRateLimiter<T> implements RateLimiter<T> {

    private final long capacity ;
    private final long refillPeriod ;
    private final Map<T,TokenBucketInfo> userTokenMap = new ConcurrentHashMap<>() ;

    @Override
    public boolean allow(T key) {
        TokenBucketInfo userBucketInfo = userTokenMap.computeIfAbsent(key, k -> new TokenBucketInfo(System.currentTimeMillis(), capacity)) ;
        refillTokens(userBucketInfo);

        if (userBucketInfo.getTokens() >  0){
            userBucketInfo.setTokens(userBucketInfo.getTokens() - 1);
            return true ;
        }
        return false ;
    }

    private void refillTokens(TokenBucketInfo userBucketInfo){
        long currentTime =  System.currentTimeMillis() ;
        long timeElapsed = currentTime - userBucketInfo.getLastRefillTime() ;
        long tokensToAdd = timeElapsed/refillPeriod ;

        if (tokensToAdd > 0){
            userBucketInfo.setTokens(Math.min(capacity, userBucketInfo.getTokens()) + tokensToAdd);
            userBucketInfo.setLastRefillTime(currentTime);
        }
    }
}
