package com.greendrive.backend.service;

import com.greendrive.backend.model.Vehicle;
import com.greendrive.backend.payload.VehicleDTO;
import com.greendrive.backend.payload.VehicleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface VehicleService {

    // CRUD, API #1
    VehicleResponse findAll(Pageable pageable);
    VehicleDTO findById(Long id);
    VehicleDTO addVehicle(VehicleDTO vehicleDTO);
    VehicleDTO updateVehicle(Long vehicleId, VehicleDTO vehicleDTO);
    void deleteVehicle(Long vehicleId);

    // Sorting, API #2
    List<VehicleDTO> sortBy(String field, String direction);

    // Filtering, API #3
    List<VehicleDTO> filter(String brand, String shape, Integer year, Boolean accident);
}
