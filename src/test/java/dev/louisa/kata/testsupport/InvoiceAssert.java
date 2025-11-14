package dev.louisa.kata.testsupport;

import dev.louisa.kata.domain.customer.Customer;
import dev.louisa.kata.domain.invoice.Invoice;
import dev.louisa.kata.domain.invoice.InvoiceItem;
import dev.louisa.kata.domain.monetary.DiscountRate;
import dev.louisa.kata.domain.monetary.Money;
import dev.louisa.kata.domain.monetary.TaxRate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class InvoiceAssert {

    private final Invoice actual;

    private InvoiceAssert(Invoice actual) {
        this.actual = actual;
    }

    public static InvoiceAssert assertThatInvoice(Invoice actual) {
        assertThat(actual)
                .as("Invoice should not be null")
                .isNotNull();
        return new InvoiceAssert(actual);
    }

    public InvoiceAssert hasCustomer(Customer expectedCustomer) {
        assertThat(actual.customer())
                .as("Expected invoice customer to be <%s> but was <%s>",
                        expectedCustomer, actual.customer())
                .isEqualTo(expectedCustomer);
        return this;
    }

    public InvoiceAssert hasNoItems() {
        assertThat(actual.invoiceItems())
                .as("Expected invoice to have no invoice items but found <%s>", actual.invoiceItems())
                .isEmpty();
        return this;
    }

    public InvoiceAssert hasItemCount(int expectedCount) {
        assertThat(actual.invoiceItems())
                .as("Expected invoice to have <%s> invoice items but had <%s>: %s",
                        expectedCount, actual.invoiceItems().size(), actual.invoiceItems())
                .hasSize(expectedCount);
        return this;
    }

    public InvoiceAssert containsExactly(InvoiceItem... expectedItems) {
        assertThat(actual.invoiceItems())
                .as("Expected invoice items to be exactly %s but were %s",
                        List.of(expectedItems), actual.invoiceItems())
                .containsExactly(expectedItems);
        return this;
    }

    public InvoiceAssert containsItem(InvoiceItem expectedItem) {
        assertThat(actual.invoiceItems())
                .as("Expected invoice to contain invoice item <%s> but invoice items were %s",
                        expectedItem, actual.invoiceItems())
                .contains(expectedItem);
        return this;
    }

    public InvoiceAssert hasSubtotal(Money expectedSubtotal) {
        assertThat(actual.summary().subtotal())
                .as("Expected subtotal <%s> but was <%s>",
                        expectedSubtotal, actual.summary().subtotal())
                .isEqualTo(expectedSubtotal);
        return this;
    }

    public InvoiceAssert hasDiscount(Money expectedDiscount) {
        assertThat(actual.summary().discount())
                .as("Expected tax <%s> but was <%s>",
                        expectedDiscount, actual.summary().tax())
                .isEqualTo(expectedDiscount);
        return this;
    }

    public InvoiceAssert hasTax(Money expectedTax) {
        assertThat(actual.summary().tax())
                .as("Expected tax <%s> but was <%s>",
                        expectedTax, actual.summary().tax())
                .isEqualTo(expectedTax);
        return this;
    }

    public InvoiceAssert hasTotal(Money expectedTotal) {
        assertThat(actual.summary().total())
                .as("Expected total <%s> but was <%s>",
                        expectedTotal, actual.summary().total())
                .isEqualTo(expectedTotal);
        return this;
    }

    public InvoiceAssert hasTaxRate(TaxRate expectedRate) {
        assertThat(actual.summary().context().taxRate())
                .as("Expected tax rate <%s> but was <%s>",
                        expectedRate, actual.summary().context().taxRate())
                .isEqualTo(expectedRate);
        return this;
    }

    public InvoiceAssert hasDiscountRate(DiscountRate expectedRate) {
        assertThat(actual.summary().context().discountRate())
                .as("Expected discount rate <%s> but was <%s>",
                        expectedRate, actual.summary().context().discountRate())
                .isEqualTo(expectedRate);
        return this;
    }
}