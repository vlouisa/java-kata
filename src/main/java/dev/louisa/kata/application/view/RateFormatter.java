package dev.louisa.kata.application.view;

import dev.louisa.kata.domain.monetary.Rate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class RateFormatter {
    
    public static String formatAsPercentage(Rate rate) {
        BigDecimal percentage = rate.rate().multiply(BigDecimal.valueOf(100))
                .setScale(1, RoundingMode.HALF_UP);

        DecimalFormat df = new DecimalFormat("#.#", DecimalFormatSymbols.getInstance(Locale.US));
        df.setMinimumFractionDigits(0);
        df.setMaximumFractionDigits(1);
        df.setGroupingUsed(false);

        return df.format(percentage) + "%";
    }
}
