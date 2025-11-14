package dev.louisa.kata.application;

import dev.louisa.kata.application.view.InvoiceView;
import dev.louisa.kata.domain.port.EmailService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InvoiceMailer {
    private final EmailService emailService;

    public void send(InvoiceView invoice, String email) {
        emailService.openSession(email);

        emailService.sendToServer("Dear %s,".formatted(invoice.customerName()));
        emailService.sendToServer("Here is your invoice:");
        emailService.sendToServer("");
        invoice.itemLines().forEach(this::sendToServer);
        emailService.sendToServer("");
        emailService.sendToServer("Subtotal: %s".formatted(invoice.subtotal()));
        if (invoice.hasDiscount()) {
            emailService.sendToServer("Discount (%s): %s".formatted(invoice.discountRate(), invoice.discount()));
        }
        if (invoice.hasTax()) {
            emailService.sendToServer("Tax (%s): %s".formatted(invoice.taxRate(), invoice.tax()));
        }
        emailService.sendToServer("Total: %s".formatted(invoice.total()));
        emailService.sendToServer("");
        emailService.sendToServer("Thank you for shopping with us!");
    }

    private void sendToServer(InvoiceView.ItemLine item) {
        emailService.sendToServer("%s: %s".formatted(item.description(), item.price()));
        if (item.hasDiscount()) {
            emailService.sendToServer("   Discount (%s): %s".formatted(item.discountRate(), item.discount()));
        }
    }
}
