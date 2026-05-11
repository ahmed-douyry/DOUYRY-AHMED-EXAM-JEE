package com.example.douyry_ahmed.web;

import com.example.douyry_ahmed.dtos.RentalDto;
import com.example.douyry_ahmed.services.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@CrossOrigin("*")
@AllArgsConstructor
public class RentalController {
    private final LocationService locationService;

    @GetMapping("/vehicles/{vehicleId}/rentals")
    public List<RentalDto> byVehicle(@PathVariable Long vehicleId) {
        return locationService.rentalsByVehicle(vehicleId);
    }

    @PostMapping("/vehicles/{vehicleId}/rentals")
    public RentalDto create(@PathVariable Long vehicleId, @RequestBody RentalDto dto) {
        return locationService.createRental(vehicleId, dto);
    }

    @PutMapping("/rentals/{rentalId}/close")
    public RentalDto close(@PathVariable Long rentalId) {
        return locationService.closeRental(rentalId);
    }
}

