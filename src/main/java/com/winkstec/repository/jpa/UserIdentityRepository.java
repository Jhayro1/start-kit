package com.winkstec.repository.jpa;

import com.winkstec.domain.user.UserIdentity;
import com.winkstec.domain.user.UserIdentity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserIdentityRepository extends JpaRepository<UserIdentity, UUID> {

    boolean existsByProviderAndProviderUserId(Provider provider, String providerUserId);

    Optional<UserIdentity> findByProviderAndProviderUserId(Provider provider, String providerUserId);
}
