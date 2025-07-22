package com.winkstec.service.email;


import com.winkstec.dto.request.email.EmailRequest;

public interface EmailQueueService {
    void registrarEmail(EmailRequest request);
}
