package org.rateLimiterExample.rateLimiters.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ClientRequest {
    private String clientId ;
    private String clientName;
}
