package com.winkstec.service.email.impl;

import com.winkstec.dto.request.email.EmailRequest;
import com.winkstec.service.email.EmailPreProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Slf4j
@Component
public class DefaultEmailPreProcessor implements EmailPreProcessor {

    @Override
    public void process(EmailRequest request) {
        if (CollectionUtils.isEmpty(request.getTo())) {
            throw new IllegalArgumentException("Destinatario vacío");
        }

        if (request.getTemplateType() == null && request.getBody() == null) {
            throw new IllegalArgumentException("Debe existir body o plantilla");
        }

        log.debug("[EMAIL PREPROCESSOR] ID: {}, asunto: {}", request.getTrackingId(), request.getSubject());
    }
}

