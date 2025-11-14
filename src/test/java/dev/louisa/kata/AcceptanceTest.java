package dev.louisa.kata;

import dev.louisa.kata.application.InvoiceHandler;
import dev.louisa.kata.domain.customer.Customer;
import dev.louisa.kata.domain.invoice.Invoice;
import dev.louisa.kata.domain.invoice.InvoiceItem;
import dev.louisa.kata.domain.monetary.DiscountRate;
import dev.louisa.kata.domain.monetary.InvoiceContext;
import dev.louisa.kata.domain.monetary.Money;
import dev.louisa.kata.infrastructure.adapter.ConsoleEmailService;
import dev.louisa.kata.infrastructure.adapter.ConsolePrinterDriver;
import dev.louisa.kata.application.InvoiceMailer;
import dev.louisa.kata.application.InvoicePrinter;
import dev.louisa.kata.domain.monetary.TaxRate;
import org.approvaltests.Approvals;
import org.approvaltests.reporters.UseReporter;
import org.approvaltests.reporters.intellij.IntelliJReporter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static java.util.Collections.emptyList;

@UseReporter(IntelliJReporter.class)
class AcceptanceTest {
    private InvoiceHandler invoiceHandler;

    @BeforeEach
    void setUp() {
        invoiceHandler = new InvoiceHandler(
                new InvoicePrinter(new ConsolePrinterDriver()),
                new InvoiceMailer(new ConsoleEmailService())
        );
    }

    @Test
    void shouldPrintEmptyInvoice() {
        var invoiceContext = InvoiceContext.of(DiscountRate.NONE, TaxRate.from(0.2));
        var invoice = Invoice.from(
                Customer.of("Guybrush Threepwood", "123 Pirate St, Monkey Island"),
                emptyList(),
                invoiceContext);

        var result = capture(() -> invoiceHandler.printInvoice(invoice));

        Approvals.verify(result);
    }

    @Test
    void shouldSendEmptyInvoice() {
        var invoiceContext = InvoiceContext.of(DiscountRate.NONE, TaxRate.from(0.2));
        var invoice = Invoice.from(
                Customer.of("Guybrush Threepwood", "123 Pirate St, Monkey Island"),
                emptyList(),
                invoiceContext);

        var result = capture(() -> invoiceHandler.sendInvoiceEmail(invoice, "guybrush.threepwood@monkey-island.test"));

        Approvals.verify(result);
    }

    @Test
    void shouldPrintInvoice() {
        var invoiceContext = InvoiceContext.of(DiscountRate.from(0.12), TaxRate.from(0.2));
        var invoice = Invoice.from(
                        Customer.of("Guybrush Threepwood", "123 Pirate St, Monkey Island"),
                        emptyList(),
                        invoiceContext
                        )
                .add(InvoiceItem.of("Sword", Money.from(100.0), DiscountRate.from(0.20)))
                .add(InvoiceItem.of("Root beer", Money.from(6.95), DiscountRate.from(1)))
                .add(InvoiceItem.of("Hook, Line & Sinker", Money.from(25.99), DiscountRate.NONE));


        var result = capture(() -> invoiceHandler.printInvoice(invoice));

        Approvals.verify(result);
    }

    @Test
    void shouldSendInvoice() {
        var invoiceContext = InvoiceContext.of(DiscountRate.NONE, TaxRate.from(0.2));
        var invoice = Invoice.from(
                        Customer.of("Guybrush Threepwood", "123 Pirate St, Monkey Island"),
                        emptyList(),
                        invoiceContext)
                .add(InvoiceItem.of("Sword", Money.from(100.0), DiscountRate.from(0.20)))
                .add(InvoiceItem.of("Root beer", Money.from(6.95), DiscountRate.from(1)))
                .add(InvoiceItem.of("Hook, Line & Sinker", Money.from(25.99), DiscountRate.NONE));

        var result = capture(() -> invoiceHandler.sendInvoiceEmail(invoice, "guybrush.threepwood@monkey-island.test"));

        Approvals.verify(result);
    }

    private String capture(Runnable code) {
        var baos = new ByteArrayOutputStream();
        try (var ps = new PrintStream(baos)) {
            PrintStream oldOut = System.out;
            System.setOut(ps);
            code.run();
            System.setOut(oldOut);
        }
        return baos.toString();
    }
}