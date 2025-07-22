package com.winkstec.service.email.impl;

import com.winkstec.dto.request.email.EmailAttachment;
import com.winkstec.dto.request.email.EmailRequest;
import com.winkstec.service.email.EmailService;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service("mailhogEmailService")
public class MailhogEmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.from:no-reply@winkstec.com}")
    private String defaultFrom;

    @Override
    public void send(EmailRequest request) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(request.getTo().toArray(String[]::new));
            if (request.getCc() != null) helper.setCc(request.getCc().toArray(String[]::new));
            if (request.getBcc() != null) helper.setBcc(request.getBcc().toArray(String[]::new));
            helper.setFrom(request.getFrom() != null ? request.getFrom() : defaultFrom);
            helper.setSubject(request.getSubject());
            helper.setText(request.getBody(), request.isHtml());

            if (request.getAttachments() != null) {
                for (EmailAttachment att : request.getAttachments()) {
                    helper.addAttachment(att.getFilename(),
                            new ByteArrayDataSource(att.getContent(), att.getContentType()));
                }
            }

            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Error al enviar correo", e);
        }
    }
}
