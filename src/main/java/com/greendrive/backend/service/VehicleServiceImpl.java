package com.greendrive.backend.service;

import com.greendrive.backend.exception.APIException;
import com.greendrive.backend.model.Vehicle;
import com.greendrive.backend.dto.VehicleDTO;
import com.greendrive.backend.dto.VehicleResponse;
import com.greendrive.backend.repository.VehicleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
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
    public VehicleResponse findAll(Integer page, Integer size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Vehicle> vehiclePage = vehicleRepository.findAll(pageable);

        List<VehicleDTO> content = vehiclePage.stream()
                .map(vehicle -> modelMapper.map(vehicle, VehicleDTO.class))
                .toList();

        return new VehicleResponse(
                content,
                vehiclePage.getNumber(),
                vehiclePage.getSize(),
                vehiclePage.getTotalElements(),
                vehiclePage.getTotalPages(),
                vehiclePage.isLast()
        );
    }

    @Override
    public VehicleDTO findById(Long vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new APIException("Vehicle not found with id: " + vehicleId, HttpStatus.NOT_FOUND));
        return modelMapper.map(vehicle, VehicleDTO.class);
    }

    @Override
    public VehicleResponse findHotDeals(Integer page, Integer size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Vehicle> vehiclePage = vehicleRepository.findByHotDealTrue(pageable);

        List<VehicleDTO> content = vehiclePage.getContent()
                .stream()
                .map(vehicle -> modelMapper.map(vehicle, VehicleDTO.class))
                .collect(Collectors.toList());

        return new VehicleResponse(
                content,
                vehiclePage.getNumber(),
                vehiclePage.getSize(),
                vehiclePage.getTotalElements(),
                vehiclePage.getTotalPages(),
                vehiclePage.isLast()
        );
    }

    @Override
    public List<VehicleDTO> filter(String shape, String make, String model, String color, Integer year, Boolean accident) {
        return vehicleRepository.findAll().stream()
                .filter(v -> shape == null || v.getShape().equalsIgnoreCase(shape))
                .filter(v -> make == null || v.getBrand().equalsIgnoreCase(make))
                .filter(v -> model == null || v.getModel().equalsIgnoreCase(model))
                .filter(v -> color == null || v.getExteriorColor().equalsIgnoreCase(color))
                .filter(v -> year == null || v.getYear().equals(year))
                .filter(v -> accident == null || v.getAccident() == accident)
                .map(vehicle -> modelMapper.map(vehicle, VehicleDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public VehicleDTO addVehicle(VehicleDTO vehicleDTO) {
        Vehicle vehicle = modelMapper.map(vehicleDTO, Vehicle.class);
        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        return modelMapper.map(savedVehicle, VehicleDTO.class);
    }

    @Override
    public VehicleDTO updateVehicle(Long vehicleId, VehicleDTO vehicleDTO) {
        Vehicle existingVehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new APIException("Vehicle not found with ID: " + vehicleId, HttpStatus.NOT_FOUND));

        existingVehicle.setQuantity(vehicleDTO.getQuantity());
        existingVehicle.setPrice(vehicleDTO.getPrice());
        existingVehicle.setHotDeal(vehicleDTO.getHotDeal());
        existingVehicle.setShape(vehicleDTO.getShape());
        existingVehicle.setBrand(vehicleDTO.getBrand());
        existingVehicle.setModel(vehicleDTO.getModel());
        existingVehicle.setYear(vehicleDTO.getYear());
        existingVehicle.setMileage(vehicleDTO.getMileage());
        existingVehicle.setNewVehicle(vehicleDTO.getNewVehicle());
        existingVehicle.setAccident(vehicleDTO.getAccident());
        existingVehicle.setExteriorColor(vehicleDTO.getExteriorColor());
        existingVehicle.setInteriorColor(vehicleDTO.getInteriorColor());
        existingVehicle.setInteriorMaterial(vehicleDTO.getInteriorMaterial());
        existingVehicle.setDescription(vehicleDTO.getDescription());
        existingVehicle.setImageUrls(vehicleDTO.getImageUrls());

        Vehicle updatedVehicle = vehicleRepository.save(existingVehicle);
        return modelMapper.map(updatedVehicle, VehicleDTO.class);
    }

    @Override
    public void deleteVehicle(Long vehicleId) {
        Vehicle existingVehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new APIException("Vehicle not found with id: " + vehicleId, HttpStatus.NOT_FOUND));

        vehicleRepository.delete(existingVehicle);
    }
}
