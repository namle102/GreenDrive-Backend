package com.greendrive.backend.repository;

import com.greendrive.backend.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    Vehicle findByVin(String vin);
    List<Vehicle> findByHotDealTrue();
}
