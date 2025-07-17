package com.winkstec.dto.request.email;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailAttachment {
    private String filename;
    private byte[] content;
    private String contentType;
}

