package com.cetes.directo.calculadoras.util;

import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

@Component
public class CommonsUtil {
    private static final String PATTERN = "###,###.##";

    /**
     * Formatea double a String
     */
    public static String doubleToFormatString(double valDouble) {
        DecimalFormatSymbols simbolos = DecimalFormatSymbols.getInstance(Locale.ENGLISH);
        DecimalFormat myFormatter = new DecimalFormat(PATTERN, simbolos);
        return myFormatter.format(valDouble);
    }

    public static double round(double val, int places) {
        double factor = Math.pow(10.0D, places);
        val *= factor;
        long tmp = Math.round(val);
        return tmp / factor;
    }
}
