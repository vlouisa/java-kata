package dev.louisa.kata.domain.monetary;

import dev.louisa.kata.domain.invoice.InvoiceItem;

import java.util.List;

@FunctionalInterface
public interface InvoiceAmountStrategy {
    Money calculate(List<InvoiceItem> items, InvoiceContext invoiceContext);

    InvoiceAmountStrategy BEFORE_ITEM_DISCOUNT = (items, invoiceContext) ->
            items.stream()
                    .map(InvoiceItem::price)
                    .reduce(Money.from(0), Money::apply);

    InvoiceAmountStrategy AFTER_ITEM_DISCOUNT = (items, invoiceContext) ->
            items.stream()
                    .map(InvoiceItem::discount)
                    .reduce(BEFORE_ITEM_DISCOUNT.calculate(items, invoiceContext), Money::apply);

    InvoiceAmountStrategy AFTER_INVOICE_DISCOUNT = (items, invoiceContext) -> {
        var total = AFTER_ITEM_DISCOUNT.calculate(items, invoiceContext);
        return total.apply(
                invoiceContext
                        .discountStrategy()
                        .calculate(total, invoiceContext.discountRate()));
    };

    InvoiceAmountStrategy AFTER_TAX = (items, invoiceContext) -> {
        var total = AFTER_INVOICE_DISCOUNT.calculate(items, invoiceContext);
        return total.apply(
                invoiceContext
                        .taxStrategy()
                        .calculate(total, invoiceContext.taxRate()));
    };
}
