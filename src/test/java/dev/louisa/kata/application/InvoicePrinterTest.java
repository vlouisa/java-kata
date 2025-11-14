package dev.louisa.kata.application;

import dev.louisa.kata.application.view.InvoiceView;
import dev.louisa.kata.domain.port.PrinterDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.inOrder;

@ExtendWith(MockitoExtension.class)
class InvoicePrinterTest {
    @Mock
    private PrinterDriver printerDriver;
    private InvoicePrinter invoicePrinter;

    @BeforeEach
    void setUp() {
        invoicePrinter = new InvoicePrinter(printerDriver);
    }

    @Test
    void shouldPrintInvoice() {
        var invoiceView = InvoiceView.builder()
                .customerName("Voodoo Lady Inc.")
                .customerAddress("123 Spooky Lane, Ghost Town")
                .itemLines(List.of(
                        InvoiceView.ItemLine.of("Magic Wand", "$49.99"),
                        InvoiceView.ItemLine.of("Enchanted Broomstick", "$89.99"),
                        InvoiceView.ItemLine.of("Potion of Invisibility", "$29.99")
                ))
                .subtotal("$169.97")
                .discountRate("0%")
                .discount("$0.00")
                .taxRate("8%")
                .tax("$13.60")
                .total("$183.57")
                .build();
    
        invoicePrinter.print(invoiceView);

        getExpectedOutput().forEach(line -> 
                inOrder(printerDriver).verify(printerDriver).sendToDevice(line));        
    }

    private List<String> getExpectedOutput() {
        return List.of(
                "===============================",
                "       INVOICE RECEIPT         ",
                "===============================",
                "Customer: Voodoo Lady Inc.",
                "Address: 123 Spooky Lane, Ghost Town",
                "--------------------------------",
                "Magic Wand .... $49.99",
                "Enchanted Broomstick .... $89.99",
                "Potion of Invisibility .... $29.99",
                "--------------------------------",
                "Subtotal: $169.97",
                "Discount (0%): $0.00",
                "Tax (8%): $13.60",
                "Total: $183.57",
                "--------------------------------",
                "Thank you for your purchase!"
        );
    }
}