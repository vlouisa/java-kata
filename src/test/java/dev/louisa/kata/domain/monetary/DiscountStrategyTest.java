package dev.louisa.kata.domain.monetary;

import org.junit.jupiter.api.Test;

import static dev.louisa.kata.domain.monetary.DiscountStrategy.*;
import static org.assertj.core.api.Assertions.assertThat;

class DiscountStrategyTest {

    @Test
    void shouldCalculateDiscountAmountBasedOnNoDiscountStrategy() {
        var discount = NONE.calculate(Money.from(1000), DiscountRate.from(0.2));
        
        assertThat(discount)
                .isEqualTo(Money.from(0));
    }

    @Test
    void shouldCalculateDiscountAmountBasedOnInvoiceLevelStrategy() {
        var discount = INVOICE_LEVEL.calculate(Money.from(1000), DiscountRate.from(0.2));
        
        assertThat(discount)
                .isEqualTo(Money.from(-200));
    }
}