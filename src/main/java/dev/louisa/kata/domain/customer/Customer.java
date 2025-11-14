package dev.louisa.kata.domain.customer;

public record Customer(String name, String address) {
    
    public static Customer of(String name, String address) {
        return new Customer(name, address);
    }
}
