package dev.louisa.kata.domain.monetary;

@FunctionalInterface
public interface TaxStrategy {
    Money calculate(Money amount, TaxRate taxRate);
    
    TaxStrategy INVOICE_LEVEL = (base, rate) -> rate.amountFor(base);
    TaxStrategy NONE = (base, rate) -> Money.from(0);
}
