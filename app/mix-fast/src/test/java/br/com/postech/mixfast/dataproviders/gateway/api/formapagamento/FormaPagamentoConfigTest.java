package br.com.postech.mixfast.dataproviders.gateway.api.formapagamento;

import feign.Logger;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class FormaPagamentoConfigTest {

    @InjectMocks
    private FormaPagamentoConfig formaPagamentoConfig;

    @Test
    void retryer() {
        Retryer retryer = formaPagamentoConfig.retryer();
        assertNotNull(retryer);
    }

    @Test
    void errorDecoder() {
        ErrorDecoder errorDecoder = formaPagamentoConfig.errorDecoder();
        assertNotNull(errorDecoder);
    }

    @Test
    void feignLoggerLevel() {
        Logger.Level logger = formaPagamentoConfig.feignLoggerLevel();
        assertNotNull(logger);
    }
}