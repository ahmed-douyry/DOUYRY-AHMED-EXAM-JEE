package com.example.douyry_ahmed.web;

import com.example.douyry_ahmed.dtos.AgencyDto;
import com.example.douyry_ahmed.services.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agencies")
@CrossOrigin("*")
@AllArgsConstructor
public class AgencyController {
    private final LocationService locationService;

    @GetMapping
    public List<AgencyDto> list() {
        return locationService.listAgencies();
    }

    @GetMapping("/search")
    public List<AgencyDto> search(@RequestParam(defaultValue = "") String keyword) {
        return locationService.searchAgencies(keyword);
    }

    @PostMapping
    public AgencyDto save(@RequestBody AgencyDto dto) {
        return locationService.saveAgency(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        locationService.deleteAgency(id);
    }

}
