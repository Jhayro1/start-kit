package com.winkstec.service.email;

import com.winkstec.domain.email.BandejaEmail;

public interface EmailRetryStrategyService {
    boolean debeReintentar(BandejaEmail email);
    void registrarError(BandejaEmail email, Exception e);
}

