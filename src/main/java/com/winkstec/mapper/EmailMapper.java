package com.winkstec.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.winkstec.domain.email.BandejaEmail;
import com.winkstec.dto.request.email.EmailRequest;
import com.winkstec.dto.request.email.EmailTemplateData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class EmailMapper {

    private final ObjectMapper objectMapper;

    public BandejaEmail toEntity(EmailRequest request) {
        return BandejaEmail.builder()
                .trackingId(
                        request.getTrackingId() != null
                                ? request.getTrackingId()
                                : UUID.randomUUID()
                )
                .fromEmail(request.getFrom())
                .toJson(serialize(request.getTo()))
                .ccJson(serialize(request.getCc()))
                .bccJson(serialize(request.getBcc()))
                .subject(request.getSubject())
                .body(request.getBody())
                .isHtml(request.isHtml())
                .templateType(request.getTemplateType())
                .templateDataJson(serialize(request.getTemplateData()))
                .estadoEntrega(com.winkstec.domain.email.EstadoEntregaEnum.PENDIENTE)
                .retryCount(0)
                .maxRetries(request.getMaxRetries() == 0 ? 3 : request.getMaxRetries())
                .build();
    }

    public EmailRequest toDto(BandejaEmail entity) {
        return EmailRequest.builder()
                .trackingId(entity.getTrackingId())
                .from(entity.getFromEmail())
                .to(deserializeList(entity.getToJson()))
                .cc(deserializeList(entity.getCcJson()))
                .bcc(deserializeList(entity.getBccJson()))
                .subject(entity.getSubject())
                .body(entity.getBody())
                .isHtml(entity.isHtml())
                .templateType(entity.getTemplateType())
                .templateData(deserialize(entity.getTemplateDataJson(), EmailTemplateData.class))
                .maxRetries(entity.getMaxRetries())
                .build();
    }

    private String serialize(Object obj) {
        try {
            return obj != null ? objectMapper.writeValueAsString(obj) : null;
        } catch (Exception e) {
            throw new RuntimeException("Error serializando email", e);
        }
    }

    private <T> T deserialize(String json, Class<T> clazz) {
        try {
            return json != null ? objectMapper.readValue(json, clazz) : null;
        } catch (Exception e) {
            throw new RuntimeException("Error deserializando email", e);
        }
    }

    private List<String> deserializeList(String json) {
        try {
            return json != null ? objectMapper.readValue(json, new TypeReference<>() {}) : null;
        } catch (Exception e) {
            throw new RuntimeException("Error deserializando lista de correos", e);
        }
    }
}
