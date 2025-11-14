package dev.louisa.kata.domain.monetary;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class DiscountRateTest {

    @ParameterizedTest
    @CsvSource({
            "0 , 0",
            "0.23 , 0.23",
            "0.23 , 0.230",
            "0.371 , 0.371",
            "0.371 , 0.3710",
    })
    void shouldCreateRate(BigDecimal rate, BigDecimal expectedRate) {
        assertThat(DiscountRate.of(rate))
                .isEqualTo(DiscountRate.of(expectedRate));
    }

    @Test
    void shouldThrowWhenPrecisionIsTooBig() {
        assertThatCode(() -> DiscountRate.of(new BigDecimal("0.1234")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Rate must have at most 3 decimal places, but was:");
    }

    @Test
    void shouldThrowWhenDiscountRateIsNegative() {
        assertThatCode(() -> DiscountRate.from(-0.01))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Rate cannot be negative");
    }

    @Test
    void shouldThrowWhenDiscountRateIsTooBig() {
        assertThatCode(() -> DiscountRate.from(1.01))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Discount rate cannot exceed 100%");
    }

    @Test
    void shouldThrowWhenDiscountRateIsNull() {
        assertThatCode(() -> DiscountRate.of(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Rate cannot be null");
    }

    @ParameterizedTest
    @CsvSource({
            "0, 50.00, 50.00",
            "0.1, 100.00, 90.00",
            "0.20, 50.00, 40.00",
            "0.075, 200.00, 185.00",
            "1, 300.00, 0.00"
    })
    void shouldApplyRateToMoney(BigDecimal rate, BigDecimal money, BigDecimal expectedMoney) {
        assertThat(
                DiscountRate
                        .of(rate)
                        .applyTo(Money.of(money)))
                .isEqualTo(Money.of(expectedMoney));
    }

    @ParameterizedTest
    @CsvSource({
            "0, 50.00, 0.00",
            "0.1, 100.00, -10.00",
            "0.20, 50.00, -10.00",
            "0.075, 200.00, -15.00",
            "1, 300.00, -300.00"
    })
    void shouldReturnMoneyAmountWhenApplyingRate(BigDecimal rate, BigDecimal money, BigDecimal expectedMoney) {
        assertThat(
                DiscountRate
                        .of(rate)
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
            "0.55, 55%",
            "0.996, 99.6%",
            "1,100%",
            "1.0,100%",
            "1.00, 100%"
    })
    void shouldShowPercentageAsString(BigDecimal percentage, String expectedString) {
        assertThat(
                DiscountRate.of(percentage)
                        .toPercentageString())
                .isEqualTo(expectedString);
    }
}