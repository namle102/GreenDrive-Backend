package com.greendrive.backend.service;

import com.greendrive.backend.exception.APIException;
import com.greendrive.backend.model.Vehicle;
import com.greendrive.backend.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;

    @Autowired
    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public Page<Vehicle> findAll(Pageable pageable) {
        Page<Vehicle> vehicles = vehicleRepository.findAll(pageable);

        if (vehicles.isEmpty()) {
            throw new APIException("No vehicles found");
        }
        return vehicles;
    }

    @Override
    public Vehicle findById(Long vehicleId) {
        return vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new APIException("Vehicle not found with id: " + vehicleId));
    }

    @Override
    public List<Vehicle> sortBy(String field, String direction) {
        Sort.Direction dir = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;

        if (field.equalsIgnoreCase("price") || field.equalsIgnoreCase("mileage")) {
            return vehicleRepository.findAll(Sort.by(dir, field));
        } else {
            throw new APIException("Sorting by field not supported: " + field);
        }
    }

    @Override
    public List<Vehicle> filter(String brand, String shape, Integer year, Boolean accident) {
        return vehicleRepository.findAll().stream()
                .filter(v -> brand == null || v.getMake().equalsIgnoreCase(brand))
                .filter(v -> shape == null || v.getShape().equalsIgnoreCase(shape))
                .filter(v -> year == null || v.getYear() == year)
                .filter(v -> accident == null || v.isAccidentHistory() == accident)
                .collect(Collectors.toList());
    }

    @Override
    public Vehicle addVehicle(Vehicle vehicle) {
        Vehicle savedVehicle = vehicleRepository.findByVin(vehicle.getVin());

        if (savedVehicle != null) {
            throw new APIException("Vehicle with vin#: " + vehicle.getVin() + " already exists");
        }
        return vehicleRepository.save(vehicle);
    }

    @Override
    public Vehicle updateVehicle(Long vehicleId, Vehicle vehicle) {
        Vehicle savedVehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new APIException("Vehicle not found with id: " + vehicleId));

        savedVehicle.setVin(vehicle.getVin());
        savedVehicle.setMake(vehicle.getMake());
        savedVehicle.setShape(vehicle.getShape());
        savedVehicle.setModel(vehicle.getModel());
        savedVehicle.setColor(vehicle.getColor());
        savedVehicle.setYear(vehicle.getYear());
        savedVehicle.setMileage(vehicle.getMileage());
        savedVehicle.setAccidentHistory(vehicle.isAccidentHistory());
        savedVehicle.setPrice(vehicle.getPrice());
        vehicleRepository.save(savedVehicle);
        return savedVehicle;
    }

    @Override
    public void deleteVehicle(Long vehicleId) {
        Vehicle savedVehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new APIException("Vehicle not found with id: " + vehicleId));

        vehicleRepository.delete(savedVehicle);
    }
}
