package dev.louisa.kata.domain.monetary;

import java.math.BigDecimal;

public record TaxRate(MarkupRate markupRate) implements Rate{
    public static final TaxRate NONE = TaxRate.from(0);
    
    public static TaxRate of(MarkupRate rate) {
        return new TaxRate(rate);
    }

    public static TaxRate from(double rate) {
        return new TaxRate(MarkupRate.from(rate));
    }

    @Override
    public Money applyTo(Money money) {
        return markupRate.applyTo(money);
    }

    @Override
    public Money amountFor(Money base) {
        return markupRate.amountFor(base);
    }

    @Override
    public String toPercentageString() {
        return markupRate.toPercentageString();
    }

    @Override
    public BigDecimal rate() {
        return markupRate.rate();
    }
}