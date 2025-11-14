package dev.louisa.kata.domain.monetary;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

public record Money(BigDecimal amount, Currency currency) {
    public static final Currency DEFAULT_CURRENCY = Currency.getInstance("USD");
    
    public Money {
        // normalize all Money instances
        amount = amount.setScale(2, RoundingMode.HALF_UP);
    }
    
    public static Money of(BigDecimal amount) {
        return new Money(amount, DEFAULT_CURRENCY);
    }

    public static Money of(BigDecimal amount, Currency currency) {
        return new Money(amount, currency);
    }
    
    public static Money from(double amount) {
        return from(amount, DEFAULT_CURRENCY);
    }

    public static Money from(double amount, Currency currency) {
        return new Money(BigDecimal.valueOf(amount), currency);
    }
    
    public Money apply(Money money) {
        return new Money(this.amount.add(money.amount), this.currency);
    }
    
    public Money apply(Rate rate) {
        return rate.applyTo(this);
    }
    
    // Package private because this is a 'domain' internal operation
    Money multiply(BigDecimal multiplier) {
        return Money.of(this.amount.multiply(multiplier), this.currency);
    }
}
