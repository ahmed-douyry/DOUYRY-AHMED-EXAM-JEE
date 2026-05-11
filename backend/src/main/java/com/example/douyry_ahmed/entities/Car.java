package com.example.douyry_ahmed.entities;


import com.example.douyry_ahmed.enums.FuelType;
import com.example.douyry_ahmed.enums.GearboxType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("CAR")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Car extends Vehicle {
    private int nombrePortes;
    @Enumerated(EnumType.STRING)
    private FuelType typeCarburant;
    @Enumerated(EnumType.STRING)
    private GearboxType boiteVitesse;
}
