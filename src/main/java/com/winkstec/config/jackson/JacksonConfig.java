package com.winkstec.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZoneId;
import java.util.TimeZone;

@Configuration
public class JacksonConfig {

    /**
     * Configura la JVM para trabajar en UTC siempre
     */
    @PostConstruct
    public void setTimeZoneToUtc() {
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of("UTC")));
    }

    /**
     * Configura un ObjectMapper centralizado, reutilizable y extensible
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        // Soporte para la API de fechas (ZonedDateTime, LocalDate, etc.)
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // usa ISO-8601 con "Z"

        // Enum como texto (no como índice)
        mapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
        mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);

        // Ignora campos nulls
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        // Ignora propiedades desconocidas al deserializar (flexibilidad para el futuro)
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        // Asegura zona horaria UTC en todo el JSON
        mapper.setTimeZone(TimeZone.getTimeZone("UTC"));

        return mapper;
    }
}
