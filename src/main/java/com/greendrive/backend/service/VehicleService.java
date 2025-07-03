package com.greendrive.backend.service;

import com.greendrive.backend.model.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VehicleService {

    // CRUD, API #1
    Page<Vehicle> findAll(Pageable pageable);
    Vehicle findById(Long id);

    // Sorting, API #2
    List<Vehicle> sortBy(String field, String direction);

    // Filtering, API #3
    List<Vehicle> filter(String brand, String shape, Integer year, Boolean accident);
}
