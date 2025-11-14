package dev.louisa.kata.application.view;

import lombok.Builder;

import java.util.List;

@Builder
public record InvoiceView(
        String customerName,
        String customerAddress,
        List<ItemLine> itemLines,
        String subtotal,
        String tax,
        String discount,
        String total,
        String taxRate,
        String discountRate,
        boolean hasDiscount,
        boolean hasTax) {
    
    public record ItemLine(String description, String price, String discount, String discountRate, boolean hasDiscount) {

        public static InvoiceView.ItemLine of(String description, String price) {
            return new ItemLine(description, price, "", "", false);
        }

        public static InvoiceView.ItemLine of(String description, String price, String discount, String discountRate, boolean hasDiscount) {
            return new ItemLine(description, price, discount, discountRate, hasDiscount);
        }

    }
}