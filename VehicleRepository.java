package com.greendrive.backend.repository;

import com.greendrive.backend.model.Vehicle;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Vehicle findByVin(String vin);
    List<Vehicle> findByHotDealTrue();
}
