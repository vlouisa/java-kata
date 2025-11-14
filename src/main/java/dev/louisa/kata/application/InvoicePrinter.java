package dev.louisa.kata.application;

import dev.louisa.kata.application.view.InvoiceView;
import dev.louisa.kata.domain.port.PrinterDriver;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InvoicePrinter {
    private final PrinterDriver printerDriver;

    public void print(InvoiceView invoice) {
        printerDriver.sendToDevice("===============================");
        printerDriver.sendToDevice("       INVOICE RECEIPT         ");
        printerDriver.sendToDevice("===============================");
        printerDriver.sendToDevice("Customer: %s".formatted(invoice.customerName()));
        printerDriver.sendToDevice("Address: %s".formatted(invoice.customerAddress()));
        printerDriver.sendToDevice("--------------------------------");
        
        invoice.itemLines().forEach(this::sendItemLineToDevice);
        
        printerDriver.sendToDevice("--------------------------------");
        printerDriver.sendToDevice("Subtotal: %s".formatted(invoice.subtotal()));
        if (invoice.hasDiscount()) {
            printerDriver.sendToDevice("Discount (%s): %s".formatted(invoice.discountRate(), invoice.discount()));
        }
        if (invoice.hasTax()) {
            printerDriver.sendToDevice("Tax (%s): %s".formatted(invoice.taxRate(), invoice.tax()));
        }
        printerDriver.sendToDevice("Total: %s".formatted(invoice.total()));
        printerDriver.sendToDevice("--------------------------------");
        printerDriver.sendToDevice("Thank you for your purchase!");
    }

    private void sendItemLineToDevice(InvoiceView.ItemLine item) {
        printerDriver.sendToDevice("%s .... %s".formatted(item.description(), item.price()));
        if (item.hasDiscount()) {
            printerDriver.sendToDevice("   Discount (%s) : %s".formatted(item.discountRate(), item.discount()));
        }
    }
}
