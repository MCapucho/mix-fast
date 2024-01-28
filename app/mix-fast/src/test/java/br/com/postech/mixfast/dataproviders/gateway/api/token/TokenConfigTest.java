package br.com.postech.mixfast.dataproviders.gateway.api.token;

import feign.Logger;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class TokenConfigTest {

    @InjectMocks
    private TokenConfig tokenConfig;

    @Test
    void retryer() {
        Retryer retryer = tokenConfig.retryer();
        assertNotNull(retryer);
    }

    @Test
    void errorDecoder() {
        ErrorDecoder errorDecoder = tokenConfig.errorDecoder();
        assertNotNull(errorDecoder);
    }

    @Test
    void feignLoggerLevel() {
        Logger.Level logger = tokenConfig.feignLoggerLevel();
        assertNotNull(logger);
    }
}