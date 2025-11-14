package dev.louisa.kata.domain.monetary;

import dev.louisa.kata.application.view.RateFormatter;

import java.math.BigDecimal;

public record DiscountRate(BigDecimal rate) implements Rate {
    public static final DiscountRate NONE = DiscountRate.of(BigDecimal.ZERO);
    public static final DiscountRate FULL = DiscountRate.of(BigDecimal.ONE);

    public DiscountRate(BigDecimal rate) {
        validate(rate);

        if (rate.compareTo(BigDecimal.ONE) > 0)
            throw new IllegalArgumentException("Discount rate cannot exceed 100%");

        this.rate = normalize(rate);
    }
    
    public static DiscountRate of(BigDecimal rate) {
        return new DiscountRate(rate);
    }

    public static DiscountRate from(double rate) {
        return new DiscountRate(BigDecimal.valueOf(rate));
    }
    
    @Override
    public Money applyTo(Money money) {
        return money.multiply(FULL.minus(this).rate());
    }

    @Override
    public Money amountFor(Money base) {
        return base.multiply(rate.negate());
    }

    @Override
    public String toPercentageString() {
       return RateFormatter.formatAsPercentage(this);
    }
    
    private DiscountRate minus(DiscountRate other) {
        return DiscountRate.of(this.rate.subtract(other.rate));
    }
}
