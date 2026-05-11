package com.example.douyry_ahmed.entities;

import com.example.douyry_ahmed.enums.VehicleStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "vehicle_kind")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String marque;
    private String modele;
    @Column(unique = true)
    private String matricule;
    private BigDecimal prixParJour;
    private LocalDate dateMiseEnService;
    @Enumerated(EnumType.STRING)
    private VehicleStatus statut;

    @ManyToOne(optional = false)
    private Agency agency;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rental> rentals = new ArrayList<>();
}
