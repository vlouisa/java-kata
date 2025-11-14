package dev.louisa.kata.domain.invoice;

import dev.louisa.kata.domain.monetary.*;

import java.util.List;

import static dev.louisa.kata.domain.monetary.InvoiceAmountStrategy.*;

public record InvoiceSummary(
        Money subtotal, 
        Money discount, 
        Money tax, 
        Money total, 
        InvoiceContext context) {
    
    public static InvoiceSummary from(List<InvoiceItem> items, InvoiceContext context) {
        var totalAfterItemDiscount = AFTER_ITEM_DISCOUNT.calculate(items, context);
        
        var discount = context
                .discountStrategy()
                .calculate(totalAfterItemDiscount, context.discountRate());
        
        var totalAfterDiscount = AFTER_INVOICE_DISCOUNT.calculate(items, context);
        
        var tax = context
                .taxStrategy()
                .calculate(totalAfterDiscount, context.taxRate());
        
        var totalAfterTax = AFTER_TAX.calculate(items, context);
        
        return new InvoiceSummary(
                totalAfterItemDiscount, 
                discount, 
                tax, 
                totalAfterTax, 
                context);
    }
}
