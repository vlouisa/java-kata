package dev.louisa.kata.domain.monetary;

import org.junit.jupiter.api.Test;

import static dev.louisa.kata.domain.monetary.TaxStrategy.*;
import static org.assertj.core.api.Assertions.assertThat;

class TaxStrategyTest {

    @Test
    void shouldCalculateTaxAmountBasedOnNoTaxStrategy() {
        var taxAmount = NONE.calculate(Money.from(100), TaxRate.from(0.21));
        
        assertThat(taxAmount)
                .isEqualTo(Money.from(0));
    }

    @Test
    void shouldCalculateTaxAmountBasedOnInvoiceLevelStrategy() {
        var taxAmount = INVOICE_LEVEL.calculate(Money.from(100), TaxRate.from(0.21));
        
        assertThat(taxAmount)
                .isEqualTo(Money.from(21));
    }
}