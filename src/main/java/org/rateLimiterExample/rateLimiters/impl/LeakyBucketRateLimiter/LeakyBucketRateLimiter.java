package org.rateLimiterExample.rateLimiters.impl.LeakyBucketRateLimiter;

import org.rateLimiterExample.rateLimiters.api.RateLimiter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LeakyBucketRateLimiter<T> implements RateLimiter<T> {
    private final long capacity ;
    private final long leakRate ;
    private final Map<T,BucketInfo> userBucketMap = new ConcurrentHashMap<>() ;

    public LeakyBucketRateLimiter(long bucketCapacity, long leakRate) {
        this.capacity = bucketCapacity ;
        this.leakRate = leakRate ;
    }

    @Override
    public synchronized boolean allow(T key) {
        long currentTime = System.currentTimeMillis();
        BucketInfo userBucket = userBucketMap.computeIfAbsent(key, k -> new BucketInfo( 0, currentTime)) ;

        updateBucket(userBucket, currentTime);
        if(userBucket.getCurrentTokenCount() < capacity){
            userBucket.setCurrentTokenCount(userBucket.getCurrentTokenCount() + 1);
            return true ;
        }
        return false;
    }

    private void updateBucket(BucketInfo userBucket, long currentTime) {
      long elapsedTime = currentTime - userBucket.getLastUpdatedTime() ;
      long toLeak = elapsedTime * leakRate / 1000 ;
      if(toLeak > 0){
          userBucket.setLastUpdatedTime(currentTime);
      }
      userBucket.setCurrentTokenCount(Math.max(0, userBucket.getCurrentTokenCount() - toLeak));
    }
}
