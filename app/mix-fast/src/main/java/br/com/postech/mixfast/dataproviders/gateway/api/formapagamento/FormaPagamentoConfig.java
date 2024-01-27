package br.com.postech.mixfast.dataproviders.gateway.api.formapagamento;

import feign.Logger;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FormaPagamentoConfig {

    @Bean(name = "retryerFormaPagamento")
    Retryer retryer() {
        return new Retryer.Default(100L, 100L, 5);
    }

    @Bean(name = "errorDecoderFormaPagamento")
    ErrorDecoder errorDecoder() {
        return new FormaPagamentoErrorDecoder();
    }

    @Bean(name = "loggerFormaPagamento")
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
