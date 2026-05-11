package com.example.douyry_ahmed.exceptions;

public class RentalNotFoundException extends RuntimeException {
    public RentalNotFoundException(Long id) {
        super("Rental not found: " + id);
    }
}

