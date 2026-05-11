package com.example.douyry_ahmed.services;

import com.example.douyry_ahmed.dtos.AgencyDto;
import com.example.douyry_ahmed.dtos.RentalDto;
import com.example.douyry_ahmed.dtos.VehicleDto;
import com.example.douyry_ahmed.enums.VehicleStatus;

import java.util.List;

public interface LocationService {
    List<AgencyDto> listAgencies();
    List<AgencyDto> searchAgencies(String keyword);
    AgencyDto saveAgency(AgencyDto dto);
    void deleteAgency(Long id);

    List<VehicleDto> vehiclesByAgency(Long agencyId);
    List<VehicleDto> availableVehiclesByAgency(Long agencyId);
    VehicleDto saveCar(Long agencyId, VehicleDto dto);
    VehicleDto saveMoto(Long agencyId, VehicleDto dto);
    VehicleDto updateVehicleStatus(Long vehicleId, VehicleStatus statut);

    List<RentalDto> rentalsByVehicle(Long vehicleId);
    RentalDto createRental(Long vehicleId, RentalDto dto);
    RentalDto closeRental(Long rentalId);
}
