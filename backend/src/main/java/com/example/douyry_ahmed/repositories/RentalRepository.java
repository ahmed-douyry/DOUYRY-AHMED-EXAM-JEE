package com.example.douyry_ahmed.repositories;


import com.example.douyry_ahmed.entities.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByVehicleIdOrderByDateDebutDesc(Long vehicleId);
}
