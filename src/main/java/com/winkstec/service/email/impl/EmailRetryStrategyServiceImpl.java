package com.winkstec.service.email.impl;

import com.winkstec.domain.email.BandejaEmail;
import com.winkstec.domain.email.EstadoEntregaEnum;
import com.winkstec.service.email.EmailRetryStrategyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Slf4j
@Service
public class EmailRetryStrategyServiceImpl implements EmailRetryStrategyService {

    @Override
    public boolean debeReintentar(BandejaEmail email) {
        return email.getRetryCount() < email.getMaxRetries();
    }

    @Override
    public void registrarError(BandejaEmail email, Exception e) {
        log.warn("[EMAIL] Fallo intento #{} - ID {} - Error: {}", email.getRetryCount() + 1, email.getId(), e.getMessage());
        email.setRetryCount(email.getRetryCount() + 1);
        email.setErrorMessage(e.getMessage());

        if (email.getRetryCount() >= email.getMaxRetries()) {
            email.setEstadoEntrega(EstadoEntregaEnum.FALLIDO);
        } else {
            email.setEstadoEntrega(EstadoEntregaEnum.ERROR);
        }
    }
}

