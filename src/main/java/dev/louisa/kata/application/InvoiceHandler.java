package dev.louisa.kata.application;

import dev.louisa.kata.domain.invoice.Invoice;
import dev.louisa.kata.application.view.InvoiceViewMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InvoiceHandler {
    private final InvoicePrinter invoicePrinter;
    private final InvoiceMailer invoiceMailer;

    public void printInvoice(Invoice invoice) {
        var invoiceView = InvoiceViewMapper.toView(invoice);
        invoicePrinter.print(invoiceView);
    }

    public void sendInvoiceEmail(Invoice invoice, String  email) {
        var invoiceView = InvoiceViewMapper.toView(invoice);
        invoiceMailer.send(invoiceView, email);
    }
}
