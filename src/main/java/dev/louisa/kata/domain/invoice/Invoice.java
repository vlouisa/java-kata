package dev.louisa.kata.domain.invoice;

import dev.louisa.kata.domain.customer.Customer;
import dev.louisa.kata.domain.monetary.InvoiceContext;

import java.util.ArrayList;
import java.util.List;

public record Invoice(Customer customer, List<InvoiceItem> invoiceItems, InvoiceSummary summary) {

    public static Invoice from(Customer customer, List<InvoiceItem> invoiceItems, InvoiceContext invoiceContext) {
        return new Invoice(customer, invoiceItems, InvoiceSummary.from(invoiceItems, invoiceContext));
    }

    public Invoice add(InvoiceItem item) {
        var updatedItems = new ArrayList<>(invoiceItems());
        updatedItems.add(item);
        return from(customer, updatedItems, summary.context());
    }
}
