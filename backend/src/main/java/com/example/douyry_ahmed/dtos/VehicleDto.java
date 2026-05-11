package com.example.douyry_ahmed.dtos;

import com.example.douyry_ahmed.enums.VehicleStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class VehicleDto {
    private Long id;
    private String marque;
    private String modele;
    private String matricule;
    private BigDecimal prixParJour;
    private LocalDate dateMiseEnService;
    private VehicleStatus statut;
    private String vehicleKind;
    private Long agencyId;
    private Integer nombrePortes;
    private String typeCarburant;
    private String boiteVitesse;
    private Integer cylindree;
    private String typeMoto;
    private Boolean casqueInclus;
}