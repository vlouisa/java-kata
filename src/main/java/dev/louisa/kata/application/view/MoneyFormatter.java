package dev.louisa.kata.application.view;

import dev.louisa.kata.domain.monetary.Money;

import java.text.NumberFormat;
import java.util.Locale;

public class MoneyFormatter {

    public static String format(Money money, Locale locale) {
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);
        format.setCurrency(money.currency());
        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(2);
        return format.format(money.amount());    
    }
}