package com.winkstec.security.util;

import com.winkstec.domain.user.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AuthUtils {

    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (principal instanceof User user) ? user : null;
    }

    public UUID getCurrentUserId() {
        User user = getCurrentUser();
        return user != null ? user.getId() : null;
    }

    public boolean isSelf(UUID resourceUserId) {
        UUID currentUserId = getCurrentUserId();
        return currentUserId != null && currentUserId.equals(resourceUserId);
    }

    public boolean isSystemAdmin() {
        User user = getCurrentUser();
        return user != null && user.isSystemAdmin();
    }

    public boolean isSelfOrAdmin(UUID resourceUserId) {
        return isSelf(resourceUserId) || isSystemAdmin();
    }
}
