package dev.louisa.kata.application.view;

import dev.louisa.kata.domain.invoice.Invoice;
import dev.louisa.kata.domain.invoice.InvoiceItem;
import dev.louisa.kata.domain.invoice.InvoiceSummary;
import dev.louisa.kata.domain.monetary.DiscountRate;
import dev.louisa.kata.domain.monetary.DiscountStrategy;
import dev.louisa.kata.domain.monetary.TaxRate;
import dev.louisa.kata.domain.monetary.TaxStrategy;

import java.util.List;
import java.util.Locale;

public final class InvoiceViewMapper {
    private static final Locale DEFAULT_LOCALE = Locale.US;

    private InvoiceViewMapper() {
    }

    public static InvoiceView toView(Invoice invoice) {
        var customer = invoice.customer();
        var summary = invoice.summary();

        List<InvoiceView.ItemLine> itemLines = invoice.invoiceItems().stream()
                .map(item -> InvoiceView.ItemLine.of(
                        item.name(),
                        MoneyFormatter.format(item.price(), DEFAULT_LOCALE),
                        MoneyFormatter.format(item.discount(), DEFAULT_LOCALE),
                        item.discountRate().toPercentageString(),
                        hasDiscount(item)))
                .toList();

        return new InvoiceView(
                customer.name(),
                customer.address(),
                itemLines,
                MoneyFormatter.format(summary.subtotal(), DEFAULT_LOCALE),
                MoneyFormatter.format(summary.tax(), DEFAULT_LOCALE),
                MoneyFormatter.format(summary.discount(), DEFAULT_LOCALE),
                MoneyFormatter.format(summary.total(), DEFAULT_LOCALE),
                summary.context().taxRate().toPercentageString(),
                summary.context().discountRate().toPercentageString(),
                hasDiscount(summary),
                hasTax(summary)
        );
    }

    private static boolean hasTax(InvoiceSummary summary) {
        return hasActiveTaxStrategy(summary) &&
                hasActiveTaxRate(summary);
    }

    private static boolean hasActiveTaxRate(InvoiceSummary summary) {
        return !TaxRate.NONE.equals(summary.context().taxRate());
    }

    private static boolean hasActiveTaxStrategy(InvoiceSummary summary) {
        return !TaxStrategy.NONE.equals(summary.context().taxStrategy());
    }

    private static boolean hasDiscount(InvoiceSummary summary) {
        return hasActiveDiscountStrategy(summary) &&
                hasActiveDiscountRate(summary);
    }

    private static boolean hasActiveDiscountRate(InvoiceSummary summary) {
        return !DiscountRate.NONE.equals(summary.context().discountRate());
    }

    private static boolean hasActiveDiscountStrategy(InvoiceSummary summary) {
        return !DiscountStrategy.NONE.equals(summary.context().discountStrategy());
    }

    private static boolean hasDiscount(InvoiceItem item) {
        return !DiscountRate.NONE.equals(item.discountRate());
    }
}