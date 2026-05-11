package com.example.douyry_ahmed.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class RentalDto {
    private Long id;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private BigDecimal montantTotal;
    private String locataire;
    private Long vehicleId;
}
