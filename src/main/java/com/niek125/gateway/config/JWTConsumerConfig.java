package com.niek125.gateway.config;

import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.niek125.gateway.utils.PemUtils.readPublicKeyFromFile;

@Configuration
public class JWTConsumerConfig {

    @Value("${dataEditor.publicKey}")
    private String publicKey;

    @Bean
    public JwtConsumer jwtConsumer() {
         return new JwtConsumerBuilder()
                .setRequireJwtId()
                .setExpectedIssuer("data-editor-token-service")
                .setVerificationKey(readPublicKeyFromFile(publicKey, "RSA"))
                .setJwsAlgorithmConstraints(AlgorithmConstraints.ConstraintType.WHITELIST, AlgorithmIdentifiers.RSA_USING_SHA256)
                .build();
    }
}
