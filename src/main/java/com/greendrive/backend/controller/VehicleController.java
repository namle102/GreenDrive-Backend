package com.greendrive.backend.controller;

import com.greendrive.backend.model.Vehicle;
import com.greendrive.backend.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<Page<Vehicle>> getAllVehicles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Vehicle> result = vehicleService.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/vehicles/{vehicleId}")
    public ResponseEntity<Vehicle> getVehicle(@PathVariable Long vehicleId) {
        return ResponseEntity.status(HttpStatus.OK).body(vehicleService.findById(vehicleId));
    }

    @GetMapping("/vehicles/sort")
    public ResponseEntity<List<Vehicle>> sortVehicles(
            @RequestParam String field,
            @RequestParam(defaultValue = "asc") String direction) {
        List<Vehicle> vehicles = vehicleService.sortBy(field, direction);
        return ResponseEntity.status(HttpStatus.OK).body(vehicles);
    }

    @GetMapping("/vehicles/filter")
    public ResponseEntity<List<Vehicle>> filterVehicles(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String shape,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Boolean accident) {
        List<Vehicle> filtered = vehicleService.filter(brand, shape, year, accident);
        return ResponseEntity.status(HttpStatus.OK).body(filtered);
    }

    @PostMapping("/vehicles")
    public ResponseEntity<Vehicle> addVehicle(@Valid @RequestBody Vehicle vehicle) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleService.addVehicle(vehicle));
    }

    @PutMapping("vehicles/{vehicleId}")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable Long vehicleId, @Valid @RequestBody Vehicle vehicle) {
        return ResponseEntity.status(HttpStatus.OK).body(vehicleService.updateVehicle(vehicleId, vehicle));
    }

    @DeleteMapping("vehicles/{vehicleId}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long vehicleId) {
        vehicleService.deleteVehicle(vehicleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
