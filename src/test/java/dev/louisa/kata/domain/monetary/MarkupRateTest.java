package dev.louisa.kata.domain.monetary;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class MarkupRateTest {

    @ParameterizedTest
    @CsvSource({
            "0 , 0",
            "0.23 , 0.23",
            "0.23 , 0.230",
            "0.371 , 0.371",
            "0.371 , 0.3710",
    })
    void shouldCreateRate(BigDecimal rate, BigDecimal expectedRate) {
        assertThat(MarkupRate.of(rate))
                .isEqualTo(MarkupRate.of(expectedRate));
    }

    @Test
    void shouldThrowWhenPrecisionIsTooBig() {
        assertThatCode(() -> MarkupRate.of(new BigDecimal("0.1234")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Rate must have at most 3 decimal places, but was:");
    }

    @Test
    void shouldThrowWhenRateIsNull() {
        assertThatCode(() -> MarkupRate.of(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Rate cannot be null");
    }

    @Test
    void shouldThrowWhenRateIsNegative() {
        assertThatCode(() -> MarkupRate.from(-0.01))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Rate cannot be negative");
    }

    @ParameterizedTest
    @CsvSource({
            "0.10, 100.00, 110.00",
            "0.20, 50.00, 60.00",
            "0.075, 200.00, 215.00",
            "0.123, 1000.00, 1123.00",
            "0.23, 29.95, 36.84",
            "0.333, 300.00, 399.90"
    })
    void shouldApplyRateToMoney(BigDecimal rate, BigDecimal money, BigDecimal expectedMoney) {
        assertThat(
                MarkupRate.of(rate)
                        .applyTo(Money.of(money)))
                .isEqualTo(Money.of(expectedMoney));
    }

    @ParameterizedTest
    @CsvSource({
            "0.10, 100.00, 10.00",
            "0.20, 50.00, 10.00",
            "0.075, 200.00, 15.00",
            "0.123, 1000.00, 123.00",
            "0.23, 29.95, 6.89",
            "0.333, 300.00, 99.90"
    })
    void shouldReturnMoneyAmountWhenApplyingRate(BigDecimal rate, BigDecimal money, BigDecimal expectedMoney) {
        assertThat(
                MarkupRate.of(rate)
                        .amountFor(Money.of(money)))
                .isEqualTo(Money.of(expectedMoney));
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0%",
            "0.0, 0%",
            "0.00, 0%",
            "0.000, 0%",
            "0.2, 20%",
            "0.67, 67%",
            "0.843, 84.3%",
            "1,100%",
            "1.0,100%",
            "1.00,100%",
            "1.000, 100%"
    })
    void shouldShowPercentageAsString(BigDecimal percentage, String expectedString) {
        var rate = MarkupRate.of(percentage).toPercentageString();
        assertThat(rate).isEqualTo(expectedString);
    }
}