package com.example.douyry_ahmed.web;

import com.example.douyry_ahmed.dtos.VehicleDto;
import com.example.douyry_ahmed.enums.VehicleStatus;
import com.example.douyry_ahmed.services.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@CrossOrigin("*")
@AllArgsConstructor
public class VehicleController {
    private final LocationService locationService;

    @GetMapping("/agencies/{agencyId}/vehicles")
    public List<VehicleDto> byAgency(@PathVariable Long agencyId) {
        return locationService.vehiclesByAgency(agencyId);
    }

    @GetMapping("/agencies/{agencyId}/vehicles/available")
    public List<VehicleDto> available(@PathVariable Long agencyId) {
        return locationService.availableVehiclesByAgency(agencyId);
    }

    @PostMapping("/agencies/{agencyId}/vehicles/car")
    public VehicleDto saveCar(@PathVariable Long agencyId, @RequestBody VehicleDto dto) {
        return locationService.saveCar(agencyId, dto);
    }

    @PostMapping("/agencies/{agencyId}/vehicles/moto")
    public VehicleDto saveMoto(@PathVariable Long agencyId, @RequestBody VehicleDto dto) {
        return locationService.saveMoto(agencyId, dto);
    }

    @PatchMapping("/vehicles/{vehicleId}/status")
    public VehicleDto status(@PathVariable Long vehicleId, @RequestParam VehicleStatus statut) {
        return locationService.updateVehicleStatus(vehicleId, statut);
    }
}

