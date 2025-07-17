package com.winkstec.dto.request.email;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailTemplateData {
    private String logoUrl;
    private String empresaNombre;
    private String facebookUrl;
    private String instagramUrl;
    private String colorPrimario; // Para futuras plantillas temáticas
}

