package com.example.douyry_ahmed.services;

import com.example.douyry_ahmed.dtos.AgencyDto;
import com.example.douyry_ahmed.dtos.RentalDto;
import com.example.douyry_ahmed.dtos.VehicleDto;
import com.example.douyry_ahmed.entities.*;
import com.example.douyry_ahmed.enums.FuelType;
import com.example.douyry_ahmed.enums.GearboxType;
import com.example.douyry_ahmed.enums.MotoType;
import com.example.douyry_ahmed.enums.VehicleStatus;
import com.example.douyry_ahmed.mappers.LocationMapperImpl;
import com.example.douyry_ahmed.repositories.AgencyRepository;
import com.example.douyry_ahmed.repositories.RentalRepository;
import com.example.douyry_ahmed.repositories.VehicleRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class LocationServiceImpl implements LocationService {
    private final AgencyRepository agencyRepository;
    private final VehicleRepository vehicleRepository;
    private final RentalRepository rentalRepository;
    private final LocationMapperImpl mapper;

    @Override
    public List<AgencyDto> listAgencies() {
        return agencyRepository.findAll().stream().map(mapper::fromAgency).collect(Collectors.toList());
    }

    @Override
    public List<AgencyDto> searchAgencies(String keyword) {
        if (keyword == null || keyword.isBlank()) return listAgencies();
        return agencyRepository.findByNomContainingIgnoreCase(keyword).stream().map(mapper::fromAgency).collect(Collectors.toList());
    }

    @Override
    public AgencyDto saveAgency(AgencyDto dto) {
        Agency saved = agencyRepository.save(mapper.fromAgencyDto(dto));
        return mapper.fromAgency(saved);
    }

    @Override
    public void deleteAgency(Long id) {
        agencyRepository.deleteById(id);
    }

    @Override
    public List<VehicleDto> vehiclesByAgency(Long agencyId) {
        return vehicleRepository.findByAgencyId(agencyId).stream().map(mapper::fromVehicle).collect(Collectors.toList());
    }

    @Override
    public List<VehicleDto> availableVehiclesByAgency(Long agencyId) {
        return vehicleRepository.findByAgencyIdAndStatut(agencyId, VehicleStatus.DISPONIBLE).stream()
                .map(mapper::fromVehicle).collect(Collectors.toList());
    }

    @Override
    public VehicleDto saveCar(Long agencyId, VehicleDto dto) {
        Agency agency = agencyRepository.findById(agencyId).orElseThrow();
        Car car = Car.builder()
                .marque(dto.getMarque())
                .modele(dto.getModele())
                .matricule(dto.getMatricule())
                .prixParJour(dto.getPrixParJour())
                .dateMiseEnService(dto.getDateMiseEnService())
                .statut(dto.getStatut() != null ? dto.getStatut() : VehicleStatus.DISPONIBLE)
                .agency(agency)
                .nombrePortes(dto.getNombrePortes() != null ? dto.getNombrePortes() : 5)
                .typeCarburant(dto.getTypeCarburant() != null ? FuelType.valueOf(dto.getTypeCarburant()) : FuelType.ESSENCE)
                .boiteVitesse(dto.getBoiteVitesse() != null ? GearboxType.valueOf(dto.getBoiteVitesse()) : GearboxType.MANUELLE)
                .build();
        return mapper.fromVehicle(vehicleRepository.save(car));
    }

    @Override
    public VehicleDto saveMoto(Long agencyId, VehicleDto dto) {
        Agency agency = agencyRepository.findById(agencyId).orElseThrow();
        Moto moto = Moto.builder()
                .marque(dto.getMarque())
                .modele(dto.getModele())
                .matricule(dto.getMatricule())
                .prixParJour(dto.getPrixParJour())
                .dateMiseEnService(dto.getDateMiseEnService())
                .statut(dto.getStatut() != null ? dto.getStatut() : VehicleStatus.DISPONIBLE)
                .agency(agency)
                .cylindree(dto.getCylindree() != null ? dto.getCylindree() : 125)
                .typeMoto(dto.getTypeMoto() != null ? MotoType.valueOf(dto.getTypeMoto()) : MotoType.SCOOTER)
                .casqueInclus(dto.getCasqueInclus() != null && dto.getCasqueInclus())
                .build();
        return mapper.fromVehicle(vehicleRepository.save(moto));
    }

    @Override
    public VehicleDto updateVehicleStatus(Long vehicleId, VehicleStatus statut) {
        Vehicle v = vehicleRepository.findById(vehicleId).orElseThrow();
        v.setStatut(statut);
        return mapper.fromVehicle(vehicleRepository.save(v));
    }

    @Override
    public List<RentalDto> rentalsByVehicle(Long vehicleId) {
        return rentalRepository.findByVehicleIdOrderByDateDebutDesc(vehicleId).stream()
                .map(mapper::fromRental).collect(Collectors.toList());
    }

    @Override
    public RentalDto createRental(Long vehicleId, RentalDto dto) {
        Vehicle v = vehicleRepository.findById(vehicleId).orElseThrow();
        if (v.getStatut() != VehicleStatus.DISPONIBLE) {
            throw new IllegalStateException("Vehicle not available for rental");
        }
        if (dto.getDateDebut() == null || dto.getDateFin() == null || dto.getDateFin().isBefore(dto.getDateDebut())) {
            throw new IllegalArgumentException("Invalid rental dates");
        }
        long days = ChronoUnit.DAYS.between(dto.getDateDebut(), dto.getDateFin()) + 1;
        BigDecimal total = v.getPrixParJour().multiply(BigDecimal.valueOf(days));
        Rental rental = Rental.builder()
                .dateDebut(dto.getDateDebut())
                .dateFin(dto.getDateFin())
                .montantTotal(total)
                .locataire(dto.getLocataire() != null ? dto.getLocataire() : "Client")
                .vehicle(v)
                .build();
        v.setStatut(VehicleStatus.LOUE);
        vehicleRepository.save(v);
        return mapper.fromRental(rentalRepository.save(rental));
    }

    @Override
    public RentalDto closeRental(Long rentalId) {
        Rental r = rentalRepository.findById(rentalId).orElseThrow();
        Vehicle v = r.getVehicle();
        v.setStatut(VehicleStatus.DISPONIBLE);
        vehicleRepository.save(v);
        return mapper.fromRental(r);
    }
}
