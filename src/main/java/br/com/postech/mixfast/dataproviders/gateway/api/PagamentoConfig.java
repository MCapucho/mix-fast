package br.com.postech.mixfast.dataproviders.gateway.api;

import feign.Logger;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PagamentoConfig {

    @Bean
    Retryer retryer() {
        return new Retryer.Default(100L, 100L, 5);
    }

    @Bean
    ErrorDecoder errorDecoder() {
        return new PagamentoErrorDecoder();
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
