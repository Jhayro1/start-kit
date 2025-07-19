package com.winkstec.repository.jpa;

import com.winkstec.domain.user.UserLoginAudit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserLoginAuditRepository extends JpaRepository<UserLoginAudit, UUID> {
}
