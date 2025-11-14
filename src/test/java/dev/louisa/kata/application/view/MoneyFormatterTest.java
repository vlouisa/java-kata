package dev.louisa.kata.application.view;

import dev.louisa.kata.domain.monetary.Money;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import static dev.louisa.kata.application.view.MoneyFormatter.format;
import static org.assertj.core.api.Assertions.assertThat;

class MoneyFormatterTest {

    @ParameterizedTest
    @CsvSource(
            value = {
                    "-1025.95|USD|en-US|-$1,025.95",
                    "-1025.95|EUR|nl-NL|€ -1.025,95",
            },
            delimiter = '|'
    )
    void shouldFormatMoney(BigDecimal amount, String currencyCode, String localeString, String expected) {
        Locale locale = Locale.forLanguageTag(localeString);

        var money = Money.of(amount, Currency.getInstance(currencyCode));
        assertThat(normalizeSpaces(format(money, locale)))
                .isEqualTo(normalizeSpaces(expected));
    }

    String normalizeSpaces(String s) {
        return s.replace('\u00A0', ' ')
                .replace('\u202F', ' ');
    }
}