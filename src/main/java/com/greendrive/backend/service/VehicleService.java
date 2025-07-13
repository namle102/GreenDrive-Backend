package com.greendrive.backend.service;

import com.greendrive.backend.dto.VehicleDTO;
import com.greendrive.backend.dto.VehicleResponse;

import java.util.List;

public interface VehicleService {

    // Get all vehicles with pagination and sorting
    VehicleResponse findAll(Integer page, Integer size, String sortBy, String sortDir);

    // Get vehicle by ID
    VehicleDTO findById(Long id);

    // Get hot deal vehicles with pagination
    VehicleResponse findHotDeals(Integer page, Integer size, String sortBy, String sortDir);

    // Filter by multiple optional fields (frontend chaining supported)
    List<VehicleDTO> filter(String shape, String make, String model, String color, Integer year, Boolean accident);

    // Admin - Add a new vehicle
    VehicleDTO addVehicle(VehicleDTO vehicleDTO);

    // Admin - Update existing vehicle
    VehicleDTO updateVehicle(Long vehicleId, VehicleDTO vehicleDTO);

    // Admin - Delete vehicle
    void deleteVehicle(Long vehicleId);
}
