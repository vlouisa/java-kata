package dev.louisa.kata.domain.monetary;

import dev.louisa.kata.domain.invoice.InvoiceItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static dev.louisa.kata.domain.monetary.InvoiceAmountStrategy.*;
import static org.assertj.core.api.Assertions.assertThat;

class InvoiceAmountStrategyTest {

    private List<InvoiceItem> items;
    private InvoiceContext invoiceContext;
    
    @BeforeEach
    void setUp() {
        items = List.of(
                InvoiceItem.of("Item 1", Money.from(1000), DiscountRate.from(0.1)),
                InvoiceItem.of("Item 2", Money.from(500), DiscountRate.from(0.2))
        );
        
        invoiceContext = InvoiceContext.of(
                DiscountRate.from(0.3),
                TaxRate.from(0.21)
        );
    }
    
    @Test
    void shouldCalculateTotalBeforeItemDiscounts() {
        var total = BEFORE_ITEM_DISCOUNT.calculate( items, invoiceContext);
        
        assertThat(total)
                .isEqualTo(Money.from(1500));
    }

    @Test
    void shouldCalculateTotalAfterItemDiscount() {
        var total = AFTER_ITEM_DISCOUNT.calculate( items, invoiceContext);

        assertThat(total)
                .isEqualTo(Money.from(1300));
    }

    @Test
    void shouldCalculateTotalAfterInvoiceDiscount() {
        var total = AFTER_INVOICE_DISCOUNT.calculate( items, invoiceContext);

        assertThat(total)
                .isEqualTo(Money.from(910));
    }

    @Test
    void shouldCalculateTotalAfterTax() {
        var total = AFTER_TAX.calculate( items, invoiceContext);

        assertThat(total)
                .isEqualTo(Money.from(1101.10));
    }
}