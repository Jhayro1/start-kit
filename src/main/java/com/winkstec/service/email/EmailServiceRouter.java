package com.winkstec.service.email;

import com.winkstec.dto.request.email.EmailRequest;
import com.winkstec.enums.EmailProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
@RequiredArgsConstructor
public class EmailServiceRouter implements EmailService {

    private final ApplicationContext applicationContext;

    @Value("${winkstec.email.provider:MAILHOG}")
    private EmailProvider provider;

    @Override
    public void send(EmailRequest request) {
        getProviderService().send(request);
    }

    private EmailService getProviderService() {
        return switch (provider) {
            case MAILHOG -> applicationContext.getBean("mailhogEmailService", EmailService.class);
            case SENDGRID -> applicationContext.getBean("sendGridEmailService", EmailService.class);
        };
    }
}
