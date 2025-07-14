package com.greendrive.backend.controller;

import com.greendrive.backend.config.AppConstants;
import com.greendrive.backend.dto.VehicleDTO;
import com.greendrive.backend.dto.VehicleResponse;
import com.greendrive.backend.service.VehicleService;
import com.greendrive.backend.service.admin.VisitEventService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;
    private final VisitEventService visitEventService;

    @GetMapping
    public ResponseEntity<VehicleResponse> getAllVehicles(
            @RequestParam(name = "page",
                    defaultValue = AppConstants.PAGE_NUMBER,
                    required = false) Integer page,
            @RequestParam(name = "size",
                    defaultValue = AppConstants.PAGE_SIZE,
                    required = false) Integer size,
            @RequestParam(name = "sortBy",
                    defaultValue = AppConstants.SORT_BY,
                    required = false) String sortBy,
            @RequestParam(name = "sortDir",
                    defaultValue = AppConstants.SORT_DIR,
                    required = false) String sortDir) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(vehicleService.findAll(page, size, sortBy, sortDir));
    }

    @GetMapping("/{vehicleId}")
    public ResponseEntity<VehicleDTO> getVehicle(@PathVariable Long vehicleId,
                                                 HttpServletRequest request) {
        String ip = request.getRemoteAddr(); // real client IP
        visitEventService.logEvent(ip, String.valueOf(vehicleId), "VIEW");
        return ResponseEntity.status(HttpStatus.OK).body(vehicleService.findById(vehicleId));
    }

    @GetMapping("/deals")
    public ResponseEntity<VehicleResponse> getHotDeals(
            @RequestParam(name = "page",
                    defaultValue = AppConstants.PAGE_NUMBER,
                    required = false) Integer page,
            @RequestParam(name = "size",
                    defaultValue = AppConstants.PAGE_SIZE,
                    required = false) Integer size,
            @RequestParam(name = "sortBy",
                    defaultValue = AppConstants.SORT_BY,
                    required = false) String sortBy,
            @RequestParam(name = "sortDir",
                    defaultValue = AppConstants.SORT_DIR,
                    required = false) String sortDir) {
        return ResponseEntity.status(HttpStatus.OK).body(vehicleService.findHotDeals(page, size, sortBy, sortDir));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<VehicleDTO>> filterVehicles(
            @RequestParam(name = "shape", required = false) String shape,
            @RequestParam(name = "brand", required = false) String brand,
            @RequestParam(name = "model", required = false) String model,
            @RequestParam(name = "color", required = false) String color,
            @RequestParam(name = "year", required = false) Integer year,
            @RequestParam(name = "accident", required = false) Boolean accident) {
        List<VehicleDTO> filtered = vehicleService.filter(shape, brand, model, color, year, accident);
        return ResponseEntity.status(HttpStatus.OK).body(filtered);
    }

    @PostMapping
    public ResponseEntity<VehicleDTO> addVehicle(@Valid @RequestBody VehicleDTO vehicleDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleService.addVehicle(vehicleDTO));
    }

    @PutMapping("/{vehicleId}")
    public ResponseEntity<VehicleDTO> updateVehicle(@PathVariable Long vehicleId, @Valid @RequestBody VehicleDTO vehicleDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(vehicleService.updateVehicle(vehicleId, vehicleDTO));
    }

    @DeleteMapping("/{vehicleId}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long vehicleId) {
        vehicleService.deleteVehicle(vehicleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
