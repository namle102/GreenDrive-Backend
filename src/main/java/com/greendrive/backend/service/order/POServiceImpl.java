package com.greendrive.backend.service.order;

import com.greendrive.backend.dto.order.POResponse;
import com.greendrive.backend.exception.APIException;
import com.greendrive.backend.model.Vehicle;
import com.greendrive.backend.model.order.PO;
import com.greendrive.backend.model.order.POItem;
import com.greendrive.backend.repository.PORepository;
import com.greendrive.backend.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class POServiceImpl implements POService {

    private final PORepository poRepository;
    private final VehicleRepository vehicleRepository;

    @Override
    public POResponse placeOrder(PO po) {
        // Check stock
        for (POItem item : po.getItems()) {
            Vehicle vehicle = vehicleRepository.findById(item.getVehicleId())
                    .orElseThrow(() -> new APIException("Vehicle not found with id: " + item.getVehicleId(), HttpStatus.NOT_FOUND));

            if (vehicle.getQuantity() < item.getQuantity()) {
                throw new APIException("Not enough stock for vehicle: " + vehicle.getModel(), HttpStatus.BAD_REQUEST);
            }
        }

        // If stock is enough
        for (POItem item : po.getItems()) {
            Vehicle vehicle = vehicleRepository.findById(item.getVehicleId()).get();
            vehicle.setQuantity(vehicle.getQuantity() - item.getQuantity());
            vehicleRepository.save(vehicle); // update stock
        }

        // Generate unique order#
        String orderNumber = "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        // Save order
        po.setOrderNumber(orderNumber);
        po.setStatus("ORDERED");
        PO savedPO = poRepository.save(po);

        return new POResponse("Order placed successfully!", savedPO);
    }

    @Override
    public POResponse trackOrder(String email, String orderNumber) {
        PO order = poRepository.findByEmailAndOrderNumber(email, orderNumber)
                .orElseThrow(() -> new APIException("Order not found with order number: " + orderNumber, HttpStatus.NOT_FOUND));
        return new POResponse("Order found", order);
    }
}
