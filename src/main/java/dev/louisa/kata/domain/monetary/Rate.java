package dev.louisa.kata.domain.monetary;

import java.math.BigDecimal;
import java.math.RoundingMode;

public sealed interface Rate permits DiscountRate, MarkupRate, TaxRate {
    
    Money applyTo(Money money);
    Money amountFor(Money base);
    String toPercentageString();

    BigDecimal rate();

    default void validate(BigDecimal rate) {
        if (rate == null)
            throw new IllegalArgumentException("Rate cannot be null");

        if (rate.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Rate cannot be negative");
    }

    default BigDecimal normalize(BigDecimal raw) {
        BigDecimal rate = raw.stripTrailingZeros();
        int scale = rate.scale();

        if (scale > 3) {
            throw new IllegalArgumentException(
                    "Rate must have at most 3 decimal places, but was: " + rate
            );
        }

        return rate.setScale(scale, RoundingMode.HALF_UP);
    }
}
