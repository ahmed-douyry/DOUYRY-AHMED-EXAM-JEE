package com.example.douyry_ahmed.mappers;

import com.example.douyry_ahmed.dtos.AgencyDto;
import com.example.douyry_ahmed.dtos.RentalDto;
import com.example.douyry_ahmed.dtos.VehicleDto;
import com.example.douyry_ahmed.entities.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class LocationMapperImpl {

    public AgencyDto fromAgency(Agency agency) {
        AgencyDto dto = new AgencyDto();
        BeanUtils.copyProperties(agency, dto);
        return dto;
    }

    public Agency fromAgencyDto(AgencyDto dto) {
        Agency agency = new Agency();
        BeanUtils.copyProperties(dto, agency);
        return agency;
    }

    public VehicleDto fromVehicle(Vehicle v) {
        VehicleDto dto = new VehicleDto();
        BeanUtils.copyProperties(v, dto);
        dto.setAgencyId(v.getAgency().getId());
        dto.setVehicleKind(v instanceof Car ? "CAR" : v instanceof Moto ? "MOTO" : "UNKNOWN");
        if (v instanceof Car car) {
            dto.setNombrePortes(car.getNombrePortes());
            dto.setTypeCarburant(car.getTypeCarburant() != null ? car.getTypeCarburant().name() : null);
            dto.setBoiteVitesse(car.getBoiteVitesse() != null ? car.getBoiteVitesse().name() : null);
        } else if (v instanceof Moto moto) {
            dto.setCylindree(moto.getCylindree());
            dto.setTypeMoto(moto.getTypeMoto() != null ? moto.getTypeMoto().name() : null);
            dto.setCasqueInclus(moto.isCasqueInclus());
        }
        return dto;
    }

    public RentalDto fromRental(Rental r) {
        RentalDto dto = new RentalDto();
        BeanUtils.copyProperties(r, dto);
        dto.setVehicleId(r.getVehicle().getId());
        return dto;
    }
}

