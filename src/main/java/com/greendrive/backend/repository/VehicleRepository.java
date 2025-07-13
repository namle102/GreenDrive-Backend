package com.greendrive.backend.repository;

import com.greendrive.backend.model.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    Page<Vehicle> findByHotDealTrue(Pageable pageable);
    List<Vehicle> findByShapeIgnoreCase(String shape);
    List<Vehicle> findByBrandIgnoreCase(String brand);
    List<Vehicle> findByModelIgnoreCase(String model);
    List<Vehicle> findByExteriorColorIgnoreCase(String color);
    List<Vehicle> findByYear(Integer year);
    List<Vehicle> findByAccident(Boolean accident);
}
