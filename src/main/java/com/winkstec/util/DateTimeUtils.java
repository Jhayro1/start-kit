package com.winkstec.util;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class DateTimeUtils {

    private static final ZoneId UTC_ZONE = ZoneOffset.UTC;

    // Tiempo actual en UTC
    public static ZonedDateTime nowUtc() {
        return ZonedDateTime.now(UTC_ZONE);
    }

    // Convierte una fecha a otra zona
    public static ZonedDateTime convertToZone(ZonedDateTime dateTime, String zoneId) {
        return dateTime.withZoneSameInstant(ZoneId.of(zoneId));
    }

    // Convierte una fecha local a UTC
    public static ZonedDateTime toUtc(ZonedDateTime localDateTime) {
        return localDateTime.withZoneSameInstant(UTC_ZONE);
    }

    // Extra: por si quieres mostrar hora legible (no uses en entidades)
    public static String formatReadable(ZonedDateTime dateTime) {
        return dateTime.withZoneSameInstant(UTC_ZONE).toString(); // ISO-8601 en UTC
    }
}
