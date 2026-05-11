package com.example.douyry_ahmed.repositories;


import com.example.douyry_ahmed.entities.Vehicle;
import com.example.douyry_ahmed.enums.VehicleStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByAgencyId(Long agencyId);
    List<Vehicle> findByAgencyIdAndStatut(Long agencyId, VehicleStatus statut);
}
