package com.winkstec.domain.email;

import com.winkstec.domain.common.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "bandeja_email")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class BandejaEmail extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "uuid")
    private UUID id;

    @Lob
    @Column(name = "to_json", nullable = false)
    private String toJson;

    @Lob
    @Column(name = "cc_json")
    private String ccJson;

    @Lob
    @Column(name = "bcc_json")
    private String bccJson;

    @Column(name = "from_email", length = 255)
    private String fromEmail;

    @Column(name = "subject", length = 255)
    private String subject;

    @Lob
    @Column(name = "body")
    private String body;

    @Column(name = "is_html", nullable = false)
    private boolean isHtml;

    @Enumerated(EnumType.STRING)
    @Column(name = "template_type")
    private EmailTemplateTypeEnum templateType;

    @Lob
    @Column(name = "template_data_json")
    private String templateDataJson;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_entrega", nullable = false)
    private EstadoEntregaEnum estadoEntrega;

    @Column(name = "retry_count", nullable = false)
    private int retryCount;

    @Column(name = "max_retries", nullable = false)
    private int maxRetries;

    @Column(name = "fecha_envio", columnDefinition = "timestamp with time zone")
    private ZonedDateTime fechaEnvio;

    @Lob
    @Column(name = "error_message")
    private String errorMessage;

    @Column(name = "tracking_id", nullable = false, unique = true, columnDefinition = "uuid")
    private UUID trackingId;
}
