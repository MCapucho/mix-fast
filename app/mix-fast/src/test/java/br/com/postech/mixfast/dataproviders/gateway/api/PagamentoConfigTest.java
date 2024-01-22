package br.com.postech.mixfast.dataproviders.gateway.api;

import feign.Logger;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class PagamentoConfigTest {

    @InjectMocks
    private PagamentoConfig pagamentoConfig;

    @Test
    void retryer() {
        Retryer retryer = pagamentoConfig.retryer();
        assertNotNull(retryer);
    }

    @Test
    void errorDecoder() {
        ErrorDecoder errorDecoder = pagamentoConfig.errorDecoder();
        assertNotNull(errorDecoder);
    }

    @Test
    void feignLoggerLevel() {
        Logger.Level logger = pagamentoConfig.feignLoggerLevel();
        assertNotNull(logger);
    }
}