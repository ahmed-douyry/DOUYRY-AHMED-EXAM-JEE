package com.example.douyry_ahmed.exceptions;

public class AgencyNotFoundException extends RuntimeException {
    public AgencyNotFoundException(Long id) {
        super("Agency not found: " + id);
    }
}

