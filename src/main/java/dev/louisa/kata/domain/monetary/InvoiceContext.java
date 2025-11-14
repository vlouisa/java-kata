package dev.louisa.kata.domain.monetary;

public record InvoiceContext(
        DiscountRate discountRate,
        TaxRate taxRate,
        DiscountStrategy discountStrategy,
        TaxStrategy taxStrategy
) {
    public static InvoiceContext of(DiscountRate discountRate, TaxRate taxRate) {
        return of(
                discountRate,
                taxRate,
                DiscountStrategy.INVOICE_LEVEL,
                TaxStrategy.INVOICE_LEVEL);
    }

    public static InvoiceContext of(DiscountRate discountRate, TaxRate taxRate, DiscountStrategy discountStrategy, TaxStrategy taxStrategy) {
        return new InvoiceContext(
                discountRate, taxRate,
                discountStrategy,
                taxStrategy
        );
    }
}