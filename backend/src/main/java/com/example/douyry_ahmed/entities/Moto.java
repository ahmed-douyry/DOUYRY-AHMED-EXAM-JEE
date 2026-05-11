package com.example.douyry_ahmed.entities;


import com.example.douyry_ahmed.enums.MotoType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("MOTO")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Moto extends Vehicle {
    private int cylindree;
    @Enumerated(EnumType.STRING)
    private MotoType typeMoto;
    private boolean casqueInclus;
}
