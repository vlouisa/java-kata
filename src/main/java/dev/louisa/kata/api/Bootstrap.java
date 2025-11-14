package dev.louisa.kata.api;

import dev.louisa.kata.application.InvoiceHandler;
import dev.louisa.kata.domain.customer.Customer;
import dev.louisa.kata.domain.invoice.Invoice;
import dev.louisa.kata.domain.invoice.InvoiceItem;
import dev.louisa.kata.domain.monetary.*;
import dev.louisa.kata.infrastructure.adapter.ConsoleEmailService;
import dev.louisa.kata.infrastructure.adapter.ConsolePrinterDriver;
import dev.louisa.kata.application.InvoiceMailer;
import dev.louisa.kata.application.InvoicePrinter;

import static java.util.Collections.emptyList;

public class Bootstrap {

    public static void main(String[] args) {
        var invoiceManager = new InvoiceHandler(
                new InvoicePrinter(new ConsolePrinterDriver()),
                new InvoiceMailer(new ConsoleEmailService())
        );
        var context = InvoiceContext.of(
                DiscountRate.from(0.25),
                TaxRate.from(0.2),
                DiscountStrategy.INVOICE_LEVEL,
                TaxStrategy.INVOICE_LEVEL
        );
        var invoice = Invoice.from(
                        Customer.of("Elaine Marley", "Pirate Beach 42, Tortuga"),
                        emptyList(),
                        context)
                .add(InvoiceItem.of("Plank", Money.from(24.95), DiscountRate.from(0.1)))
                .add(InvoiceItem.of("Hat", Money.from(14.99), DiscountRate.from(0.125)))
                .add(InvoiceItem.of("Grog", Money.from(2.49), DiscountRate.NONE));

        invoiceManager.printInvoice(invoice);
        invoiceManager.sendInvoiceEmail(invoice, "elaine.marley@monkey-island.test");
    }
}

