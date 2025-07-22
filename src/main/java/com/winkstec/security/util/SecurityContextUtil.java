package com.winkstec.security.util;

import com.winkstec.domain.user.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityContextUtil {
    public static User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static String getCurrentUserId() {
        return getCurrentUser().getId().toString();
    }
}
