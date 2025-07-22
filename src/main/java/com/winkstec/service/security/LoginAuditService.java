package com.winkstec.service.security;

import com.winkstec.domain.user.User;
import com.winkstec.domain.user.UserLoginAudit;

public interface LoginAuditService {

    void registerSuccess(User user, UserLoginAudit.LoginMethod method);

    void registerFailure(String email, String ipAddress, String userAgent, UserLoginAudit.LoginMethod method, String reason);
}
