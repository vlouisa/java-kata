package dev.louisa.kata.domain.monetary;

import dev.louisa.kata.application.view.RateFormatter;

import java.math.BigDecimal;

public record MarkupRate(BigDecimal rate) implements Rate {

    public MarkupRate(BigDecimal rate) {
        validate(rate);        
        this.rate = normalize(rate);
    }
    
    public static MarkupRate of(BigDecimal rate) {
        return new MarkupRate(rate);
    }

    public static MarkupRate from(double rate) {
        return new MarkupRate(BigDecimal.valueOf(rate));
    }
    
    @Override
    public Money applyTo(Money money) {
        var factor = MarkupRate.from(1).add(this);
        return money.multiply(factor.rate());
    }

    @Override
    public Money amountFor(Money base) {
        return base.multiply(rate);
    }

    @Override
    public String toPercentageString() {
       return RateFormatter.formatAsPercentage(this);
    }
    
    private MarkupRate add(MarkupRate other) {
        return MarkupRate.of(this.rate.add(other.rate));
    }
}
