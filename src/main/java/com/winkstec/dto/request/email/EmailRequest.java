package com.winkstec.dto.request.email;

import com.winkstec.domain.email.EmailTemplateTypeEnum;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequest {

    private UUID trackingId;

    private String from;
    private List<String> to;
    private List<String> cc;
    private List<String> bcc;

    private String subject;

    private String body;

    private boolean isHtml;

    private EmailTemplateTypeEnum templateType;

    private EmailTemplateData templateData;

    private List<EmailAttachment> attachments;

    private int maxRetries; // Defaults to 3 si no se especifica
}

