package com.niek125.gateway.token;

import lombok.AllArgsConstructor;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TokenVerifier {
    private final JwtConsumer jwtConsumer;

    public boolean verify(String authorizationHeader) {
        final String jwt = authorizationHeader.replace("Bearer ", "");
        try {
            jwtConsumer.process(jwt);
        } catch (InvalidJwtException e) {
            return false;
        }
        return true;
    }
}
