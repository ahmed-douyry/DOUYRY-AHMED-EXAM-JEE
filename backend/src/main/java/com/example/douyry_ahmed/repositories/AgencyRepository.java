package com.example.douyry_ahmed.repositories;

import com.example.douyry_ahmed.entities.Agency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AgencyRepository extends JpaRepository<Agency, Long> {
    List<Agency> findByNomContainingIgnoreCase(String nom);
}
