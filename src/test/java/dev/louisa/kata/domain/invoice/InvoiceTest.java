package dev.louisa.kata.domain.invoice;

import dev.louisa.kata.domain.customer.Customer;
import dev.louisa.kata.domain.monetary.DiscountRate;
import dev.louisa.kata.domain.monetary.InvoiceContext;
import dev.louisa.kata.domain.monetary.Money;
import dev.louisa.kata.domain.monetary.TaxRate;
import org.junit.jupiter.api.Test;

import static dev.louisa.kata.testsupport.InvoiceAssert.assertThatInvoice;
import static java.util.Collections.emptyList;


class InvoiceTest {
    private static final InvoiceItem GTA_VI = InvoiceItem.of("GTA VI", Money.from(69.99), DiscountRate.NONE);
    private static final InvoiceItem THE_WITCHER_4 = InvoiceItem.of("The Witcher 4", Money.from(59.99), DiscountRate.NONE);
    private static final InvoiceItem GHOST_OF_YOTEI = InvoiceItem.of("Ghost of Yotei", Money.from(79.99), DiscountRate.NONE);

    @Test
    void shouldCreateInvoiceWithoutItems() {
        var customer = Customer.of("John Doe", "123 Main St");
        var invoiceContext = InvoiceContext.of(DiscountRate.NONE, TaxRate.from(0.2));
        var invoice = Invoice.from(customer, emptyList(), invoiceContext);

        assertThatInvoice(invoice)
                .hasCustomer(customer)
                .hasNoItems()
                .hasSubtotal(Money.from(0))
                .hasTaxRate(TaxRate.from(0.2))
                .hasTax(Money.from(0.0))
                .hasTotal(Money.from(0));
    }

    @Test
    void shouldCreateInvoiceWithItems() {
        var customer = Customer.of("John Doe", "123 Main St");
        var invoiceContext = InvoiceContext.of(DiscountRate.from(0.25), TaxRate.from(0.2));
        var invoice = Invoice.from(customer, emptyList(), invoiceContext)
                .add(GTA_VI)
                .add(THE_WITCHER_4)
                .add(GHOST_OF_YOTEI);

        assertThatInvoice(invoice)
                .hasCustomer(customer)
                .hasItemCount(3)
                .containsExactly(GTA_VI, THE_WITCHER_4, GHOST_OF_YOTEI)
                .hasSubtotal(Money.from(209.97))
                .hasDiscountRate(DiscountRate.from(0.25))
                .hasDiscount(Money.from(-52.49))
                .hasTaxRate(TaxRate.from(0.2))
                .hasTax(Money.from(31.50))
                .hasTotal(Money.from(188.98));
    }
}