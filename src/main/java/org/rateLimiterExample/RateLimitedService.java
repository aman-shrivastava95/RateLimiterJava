package org.rateLimiterExample;

import lombok.AllArgsConstructor;
import org.rateLimiterExample.rateLimiters.api.RateLimiter;
import org.rateLimiterExample.rateLimiters.models.ClientRequest;

@AllArgsConstructor
public class RateLimitedService {
    private RateLimiter<String> rateLimiter ;
    public void serveRequest(ClientRequest request){
        if(rateLimiter.allow(request.getClientId())) {
            System.out.println("Request served for client id "+ request.getClientId() + ".");
        }else{
            //in case of api return 429
            System.out.println("Rate limit exceeded for client id " + request.getClientId() + "!!!!");
        }
    }
}
