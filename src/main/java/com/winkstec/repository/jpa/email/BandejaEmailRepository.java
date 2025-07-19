package com.winkstec.repository.jpa.email;

import com.winkstec.domain.email.BandejaEmail;
import com.winkstec.domain.email.EstadoEntregaEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BandejaEmailRepository extends JpaRepository<BandejaEmail, UUID> {
    List<BandejaEmail> findTop10ByEstadoEntregaOrderByCreatedAtAsc(EstadoEntregaEnum estadoEntrega);
}

