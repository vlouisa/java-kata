package dev.louisa.kata.application;

import dev.louisa.kata.application.view.InvoiceView;
import dev.louisa.kata.domain.port.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class InvoiceMailerTest {
    @Mock
    private EmailService emailService;
    private InvoiceMailer invoiceMailer;
    
    @BeforeEach
    void setUp() {
        invoiceMailer = new InvoiceMailer(emailService);
    }

    @Test
    void shouldMailInvoice() {
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
        
        invoiceMailer.send(invoiceView, "voodoo.lady@mi-2.test");

        verify(emailService).openSession("voodoo.lady@mi-2.test");
        getExpectedOutput().forEach(line -> 
                inOrder(emailService).verify(emailService).sendToServer(line));
    }

    private List<String> getExpectedOutput() {
        return List.of(
                "Dear Voodoo Lady Inc.,",
                "Here is your invoice:",
                "",
                "Magic Wand: $49.99",
                "Enchanted Broomstick: $89.99",
                "Potion of Invisibility: $29.99",
                "",
                "Subtotal: $169.97",
                "Discount (0%): $0.00",
                "Tax (8%): $13.60",
                "Total: $183.57",
                "",
                "Thank you for shopping with us!"
        );
    }

}