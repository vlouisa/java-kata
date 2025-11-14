package dev.louisa.kata.domain.invoice;

import dev.louisa.kata.domain.monetary.DiscountRate;
import dev.louisa.kata.domain.monetary.Money;

public record InvoiceItem(String name, Money price, DiscountRate discountRate) {
    
    public static InvoiceItem of(String name, Money price) {
        return InvoiceItem.of(name, price, DiscountRate.NONE);
    }

    public static InvoiceItem of(String name, Money price, DiscountRate discountRate) {
        return new InvoiceItem(name, price, discountRate);
    }
    
    public Money applyDiscount() {
        return price.apply(discountRate);
    }
    
    public Money discount() {
        return discountRate.amountFor(price);
    }
}
