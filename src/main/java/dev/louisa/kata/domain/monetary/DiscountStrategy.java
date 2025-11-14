package dev.louisa.kata.domain.monetary;

@FunctionalInterface
public interface DiscountStrategy {
    Money calculate(Money amount, DiscountRate discountRate);

    DiscountStrategy INVOICE_LEVEL = (base, rate) -> rate.amountFor(base);
    DiscountStrategy NONE = (base, rate) -> Money.from(0);
}
