package com.winkstec.domain.company;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "company_settings", indexes = {
        @Index(name = "idx_company_settings_company_id", columnList = "company_id"),
        @Index(name = "idx_company_settings_language", columnList = "language")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanySettings {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "uuid")
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "company_id", referencedColumnName = "id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_company_settings_company"))
    private Company company;



    @Column(name = "primary_color", length = 10)
    private String primaryColor;

    @Column(name = "secondary_color", length = 10)
    private String secondaryColor;

    @Lob
    @Column(name = "logo_url")
    private String logoUrl;

    @Lob
    @Column(name = "background_url")
    private String backgroundUrl;

    @Lob
    @Column(name = "custom_texts")
    private String customTexts;

    @Builder.Default
    @Column(name = "language", length = 5)
    private String language = "es";
}
