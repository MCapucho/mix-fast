package br.com.postech.mixfast.dataproviders.gateway.api.token;

import feign.Logger;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TokenConfig {

    @Bean(name = "retryerToken")
    Retryer retryer() {
        return new Retryer.Default(100L, 100L, 5);
    }

    @Bean(name = "errorDecoderToken")
    ErrorDecoder errorDecoder() {
        return new TokenErrorDecoder();
    }

    @Bean(name = "loggerToken")
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
