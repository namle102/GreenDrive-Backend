package com.greendrive.backend.service;

import com.greendrive.backend.exception.APIException;
import com.greendrive.backend.model.Vehicle;
import com.greendrive.backend.payload.VehicleDTO;
import com.greendrive.backend.payload.VehicleResponse;
import com.greendrive.backend.repository.VehicleRepository;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;

    @Autowired
    public VehicleServiceImpl(VehicleRepository vehicleRepository, ModelMapper modelMapper) {
        this.vehicleRepository = vehicleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public VehicleResponse findAll(Pageable pageable) {
        Page<Vehicle> vehicles = vehicleRepository.findAll(pageable);

        List<VehicleDTO> vehicleDTOs = vehicles.stream()
                .map(vehicle -> modelMapper.map(vehicle, VehicleDTO.class))
                .toList();
        return new VehicleResponse(
                vehicleDTOs,
                vehicles.getNumber(),
                vehicles.getSize(),
                vehicles.getTotalElements(),
                vehicles.getTotalPages(),
                vehicles.isLast()
        );
    }

    @Override
    public VehicleDTO findById(Long vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new APIException("Vehicle not found with id: " + vehicleId));
        return modelMapper.map(vehicle, VehicleDTO.class);
    }

    @Override
    public List<VehicleDTO> sortBy(String field, String direction) {
        Sort.Direction dir = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;

        if (!field.equalsIgnoreCase("price") && !field.equalsIgnoreCase("mileage")) {
            throw new APIException("Sorting by field not supported: " + field);
        }

        return vehicleRepository.findAll(Sort.by(dir, field)).stream()
                .map(vehicle -> modelMapper.map(vehicle, VehicleDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<VehicleDTO> filter(String brand, String shape, Integer year, Boolean accident) {
        return vehicleRepository.findAll().stream()
                .filter(v -> brand == null || v.getMake().equalsIgnoreCase(brand))
                .filter(v -> shape == null || v.getShape().equalsIgnoreCase(shape))
                .filter(v -> year == null || v.getYear() == year)
                .filter(v -> accident == null || v.isAccidentHistory() == accident)
                .map(vehicle -> modelMapper.map(vehicle, VehicleDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public VehicleDTO addVehicle(VehicleDTO vehicleDTO) {
        Vehicle vehicle = modelMapper.map(vehicleDTO, Vehicle.class);
        Vehicle existingVehicle = vehicleRepository.findByVin(vehicle.getVin());

        if (existingVehicle != null) {
            throw new APIException("Vehicle with vin#: " + vehicle.getVin() + " already exists");
        }
        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        return modelMapper.map(savedVehicle, VehicleDTO.class);
    }

    @Override
    public VehicleDTO updateVehicle(Long vehicleId, VehicleDTO vehicleDTO) {
        Vehicle existingVehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new APIException("Vehicle not found with id: " + vehicleId));

        existingVehicle.setVin(vehicleDTO.getVin());
        existingVehicle.setMake(vehicleDTO.getMake());
        existingVehicle.setShape(vehicleDTO.getShape());
        existingVehicle.setModel(vehicleDTO.getModel());
        existingVehicle.setColor(vehicleDTO.getColor());
        existingVehicle.setYear(vehicleDTO.getYear());
        existingVehicle.setMileage(vehicleDTO.getMileage());
        existingVehicle.setAccidentHistory(vehicleDTO.isAccidentHistory());
        existingVehicle.setPrice(vehicleDTO.getPrice());
        existingVehicle.setImageUrls(vehicleDTO.getImageUrls());
        existingVehicle.setHotDeal(vehicleDTO.isHotDeal());

        Vehicle updatedVehicle = vehicleRepository.save(existingVehicle);
        return modelMapper.map(updatedVehicle, VehicleDTO.class);
    }

    @Override
    public void deleteVehicle(Long vehicleId) {
        Vehicle existingVehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new APIException("Vehicle not found with id: " + vehicleId));

        vehicleRepository.delete(existingVehicle);
    }

    @Override
    public List<VehicleDTO> findHotDeals() {
        return vehicleRepository.findByHotDealTrue().stream()
                .map(vehicle -> modelMapper.map(vehicle, VehicleDTO.class))
                .collect(Collectors.toList());
    }
}
