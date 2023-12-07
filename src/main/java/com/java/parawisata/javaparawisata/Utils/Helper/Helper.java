package com.java.parawisata.javaparawisata.Utils.Helper;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class Helper {
    public static String thousandsSeparator(long data) {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();

        symbols.setGroupingSeparator(',');
        formatter.setDecimalFormatSymbols(symbols);
        return formatter.format(data);
    }

    public static String thousandsSeparator(String data) {
        data = data.substring(0, data.length() - 3);
        return thousandsSeparator(Long.parseLong(data));
    }
}
