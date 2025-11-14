package dev.louisa.kata.domain.monetary;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class MoneyTest {
    @ParameterizedTest
    @CsvSource({
            "0, 0",
            "10.50, 10.50",
            "99.9, 99.90",
            "1000, 1000.0"
    })
    void shouldCreateMoney(double amount, BigDecimal expectedAmount) {
        var money = Money.from(amount);
        assertThat(money.equals(Money.of(expectedAmount)))
                .isTrue();
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0",
            "0, 0.00",
            "2.2, 2.20",
    })
    void shouldCompareMoney(BigDecimal amount, BigDecimal expectedAmount) {
        assertThat(Money.of(amount)).isEqualTo(Money.of(expectedAmount));
    }
    
    @ParameterizedTest
    @CsvSource({
            "0, 0.2, 0.2",
            "209.97, 0.02, 209.99"
    })
    void shouldApplyMoney(BigDecimal amount, BigDecimal otherAmount, BigDecimal expectedAmount) {
        var money = Money.of(amount).apply(Money.of(otherAmount));
        assertThat(money.equals(Money.of(expectedAmount)))
                .isTrue();
    }
}