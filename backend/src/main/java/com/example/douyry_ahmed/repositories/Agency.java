package com.example.douyry_ahmed.repositories;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Agency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String adresse;
    private String ville;
    private String telephone;

    @OneToMany(mappedBy = "agency", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Vehicle> vehicles = new ArrayList<>();
}
