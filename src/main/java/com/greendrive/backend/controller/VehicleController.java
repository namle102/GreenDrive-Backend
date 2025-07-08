package com.greendrive.backend.controller;

import com.greendrive.backend.payload.VehicleDTO;
import com.greendrive.backend.payload.VehicleResponse;
import com.greendrive.backend.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class VehicleController {

    private final VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping("/vehicles")
    public ResponseEntity<VehicleResponse> getAllVehicles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        VehicleResponse result = vehicleService.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/vehicles/{vehicleId}")
    public ResponseEntity<VehicleDTO> getVehicle(@PathVariable Long vehicleId) {
        return ResponseEntity.status(HttpStatus.OK).body(vehicleService.findById(vehicleId));
    }

    @GetMapping("/vehicles/sort")
    public ResponseEntity<List<VehicleDTO>> sortVehicles(
            @RequestParam String field,
            @RequestParam(defaultValue = "asc") String direction) {
        List<VehicleDTO> vehicles = vehicleService.sortBy(field, direction);
        return ResponseEntity.status(HttpStatus.OK).body(vehicles);
    }

    @GetMapping("/vehicles/filter")
    public ResponseEntity<List<VehicleDTO>> filterVehicles(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String shape,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Boolean accident) {
        List<VehicleDTO> filtered = vehicleService.filter(brand, shape, year, accident);
        return ResponseEntity.status(HttpStatus.OK).body(filtered);
    }

    @GetMapping("/vehicles/deals")
    public ResponseEntity<List<VehicleDTO>> getHotDeals() {
        return ResponseEntity.status(HttpStatus.OK).body(vehicleService.findHotDeals());
    }

    @PostMapping("/vehicles")
    public ResponseEntity<VehicleDTO> addVehicle(@Valid @RequestBody VehicleDTO vehicleDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleService.addVehicle(vehicleDTO));
    }

    @PutMapping("vehicles/{vehicleId}")
    public ResponseEntity<VehicleDTO> updateVehicle(@PathVariable Long vehicleId, @Valid @RequestBody VehicleDTO vehicleDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(vehicleService.updateVehicle(vehicleId, vehicleDTO));
    }

    @DeleteMapping("vehicles/{vehicleId}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long vehicleId) {
        vehicleService.deleteVehicle(vehicleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
