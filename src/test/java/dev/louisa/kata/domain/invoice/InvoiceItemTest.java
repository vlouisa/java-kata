package dev.louisa.kata.domain.invoice;

import dev.louisa.kata.domain.monetary.DiscountRate;
import dev.louisa.kata.domain.monetary.Money;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InvoiceItemTest {

    @Test
    void shouldCalculateDiscountAmount() {
        var invoiceItem = InvoiceItem.of("Test Item", Money.from(200), DiscountRate.from(0.1));
        
        assertThat(invoiceItem.discount())
                .isEqualTo(Money.from(-20));
    }

    @Test
    void shouldCalculatePriceAfterApplyingDiscount() {
        var invoiceItem = InvoiceItem.of("Test Item", Money.from(200), DiscountRate.from(0.1));
        
        assertThat(invoiceItem.applyDiscount())
                .isEqualTo(Money.from(180));
    }
}