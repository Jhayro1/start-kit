package com.winkstec.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class BigDecimalUtils {

    // Redondea a 2 decimales, HALF_UP (típico en precios)
    public static String formatTwoDecimals(BigDecimal value) {
        if (value == null) return null;
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return df.format(value);
    }

    // Redondea a N decimales (flexible)
    public static BigDecimal round(BigDecimal value, int decimals) {
        if (value == null) return null;
        return value.setScale(decimals, RoundingMode.HALF_UP);
    }

    // Ejemplo: formatear como porcentaje 2.34%
    public static String formatPercentage(BigDecimal value) {
        if (value == null) return null;
        return formatTwoDecimals(value) + "%";
    }
}
