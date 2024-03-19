import org.junit.Test;
import org.rateLimiterExample.rateLimiters.api.RateLimiter;
import org.rateLimiterExample.rateLimiters.constants.RateLimiterConstants;
import org.rateLimiterExample.rateLimiters.factories.RateLimiterFactory;
import org.rateLimiterExample.rateLimiters.models.Strategy;

import java.util.Properties;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class RateLimiterTests {

    @Test
    public void testFixedWindowRateLimiter(){
        Properties rateLimitConfig = new Properties();
        rateLimitConfig.setProperty(RateLimiterConstants.MAX_REQUEST, "5");
        rateLimitConfig.setProperty(RateLimiterConstants.TIME_WINDOW_MS,"10000");
        RateLimiter<String> fixedWindowRateLimiter = RateLimiterFactory.<String>getRateLimiter(Strategy.FIXED_WINDOW, rateLimitConfig) ;

        for(int i=0; i<=5; i++){

            if (i<5){
                assertTrue(fixedWindowRateLimiter.allow("user1"))  ;
            }
            else{
                assertFalse(fixedWindowRateLimiter.allow("user1"));
            }
        }
    }

    @Test
    public void testSlidingWindowRateLimiter(){
        Properties rateLimitConfig = new Properties();
        rateLimitConfig.setProperty(RateLimiterConstants.MAX_REQUEST, "5");
        rateLimitConfig.setProperty(RateLimiterConstants.TIME_WINDOW_MS,"10000");
        RateLimiter<String> slidingWindowRateLimiter = RateLimiterFactory.<String>getRateLimiter(Strategy.SLIDING_WINDOW, rateLimitConfig) ;

        for(int i=0; i<=5; i++){

            if (i<5){
                assertTrue(slidingWindowRateLimiter.allow("user1"))  ;
            }
            else{
                assertFalse(slidingWindowRateLimiter.allow("user1"));
            }
        }
    }
}
