package com.winkstec.service.email;

import com.winkstec.dto.request.email.EmailRequest;

public interface EmailPreProcessor {
    void process(EmailRequest request);
}

