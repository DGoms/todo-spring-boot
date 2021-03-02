package com.davidgomes.todospringboot.config;

import lombok.Getter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConstructorBinding
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private final AppJwtProperties jwt;

    public AppProperties(AppJwtProperties jwt) {
        System.out.println("test " + jwt);
        this.jwt = jwt;
    }

    @Getter
    @ToString
    public static class AppJwtProperties {
        private final String secret;

        private final int expirationTime;

        private final String header;

        private final String tokenPrefix;

        private final String signUpUrl;

        public AppJwtProperties(String secret, int expirationTime, String header, String tokenPrefix, String signUpUrl) {
            this.secret = secret;
            this.expirationTime = expirationTime;
            this.header = header;
            this.tokenPrefix = tokenPrefix;
            this.signUpUrl = signUpUrl;
        }
    }
}
