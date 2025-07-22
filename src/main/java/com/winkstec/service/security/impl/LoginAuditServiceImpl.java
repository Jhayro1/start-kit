package com.winkstec.service.security.impl;

import com.winkstec.domain.user.User;
import com.winkstec.domain.user.UserLoginAudit;
import com.winkstec.repository.jpa.UserLoginAuditRepository;
import com.winkstec.service.security.LoginAuditService;
import com.winkstec.util.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoginAuditServiceImpl implements LoginAuditService {

    private final UserLoginAuditRepository auditRepository;

    @Override
    public void registerSuccess(User user, UserLoginAudit.LoginMethod method) {
        auditRepository.save(UserLoginAudit.builder()
                .user(user)
                .loginMethod(method)
                .ipAddress(getClientIp())           // Implementar como utilidad
                .userAgent(getUserAgent())          // Implementar como utilidad
                .success(true)
                .createdAt(DateTimeUtils.nowUtc())
                .build());
    }

    @Override
    public void registerFailure(String email, String ipAddress, String userAgent, UserLoginAudit.LoginMethod method, String reason) {
        auditRepository.save(UserLoginAudit.builder()
                .id(UUID.randomUUID())
                .user(null)
                .loginMethod(method)
                .ipAddress(ipAddress)
                .userAgent(StringUtils.abbreviate(userAgent, 300))
                .success(false)
                .failureReason(reason)
                .createdAt(DateTimeUtils.nowUtc())
                .build());
    }

    private String getClientIp() {
        // TODO: Implementa correctamente desde RequestContext
        return "127.0.0.1";
    }

    private String getUserAgent() {
        // TODO: Implementa correctamente desde RequestContext
        return "Mocked-Agent";
    }
}
