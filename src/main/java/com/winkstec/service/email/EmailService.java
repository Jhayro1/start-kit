package com.winkstec.service.email;

import com.winkstec.dto.request.email.EmailRequest;

public interface EmailService {
    void send(EmailRequest request);
}

