package com.winkstec.service.email.cron;

import com.winkstec.domain.email.BandejaEmail;
import com.winkstec.domain.email.EstadoEntregaEnum;
import com.winkstec.mapper.EmailMapper;
import com.winkstec.repository.jpa.email.BandejaEmailRepository;
import com.winkstec.service.email.EmailPreProcessor;
import com.winkstec.service.email.EmailRetryStrategyService;
import com.winkstec.service.email.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailQueueCronJob {

    private final BandejaEmailRepository bandejaEmailRepository;
    private final EmailService emailService;
    private final EmailMapper emailMapper;
    private final EmailRetryStrategyService retryStrategy;
    private final EmailPreProcessor preProcessor;

    @Scheduled(fixedDelayString = "${winkstec.email.queue.cron-delay:10000}")
    @Transactional
    public void enviarPendientes() {
        List<BandejaEmail> pendientes = bandejaEmailRepository
                .findTop10ByEstadoEntregaOrderByCreatedAtAsc(EstadoEntregaEnum.PENDIENTE);

        for (BandejaEmail email : pendientes) {
            try {
                if (!retryStrategy.debeReintentar(email)) {
                    log.warn("[EMAIL] Se omitió correo con ID {} porque superó el límite de reintentos", email.getId());
                    email.setEstadoEntrega(EstadoEntregaEnum.FALLIDO);
                    bandejaEmailRepository.save(email);
                    continue;
                }

                var request = emailMapper.toDto(email);

                preProcessor.process(request);
                emailService.send(request);

                email.setEstadoEntrega(EstadoEntregaEnum.ENVIADO);
                email.setFechaEnvio(ZonedDateTime.now());
                email.setErrorMessage(null);

            } catch (Exception e) {
                retryStrategy.registrarError(email, e);
            }

            bandejaEmailRepository.save(email);
        }

    }
}
