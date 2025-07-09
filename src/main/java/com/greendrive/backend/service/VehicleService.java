package com.greendrive.backend.service;

import com.greendrive.backend.payload.VehicleDTO;
import com.greendrive.backend.payload.VehicleResponse;
import java.util.List;

public interface VehicleService {

    // CRUD, APIs 1&2
    VehicleResponse findAll(Integer page, Integer size, String sortBy, String sortDir);
    VehicleDTO findById(Long id);
    VehicleDTO addVehicle(VehicleDTO vehicleDTO);
    VehicleDTO updateVehicle(Long vehicleId, VehicleDTO vehicleDTO);
    void deleteVehicle(Long vehicleId);

    // Filtering, API 3
    List<VehicleDTO> filter(String shape, String make, String model, String color, Integer year, Boolean accident);

    // Hot Deals, API 4
    List<VehicleDTO> findHotDeals();
}
