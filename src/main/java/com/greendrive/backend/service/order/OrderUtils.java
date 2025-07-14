package com.greendrive.backend.service.order;

import com.greendrive.backend.exception.APIException;
import com.greendrive.backend.model.order.PO;
import com.greendrive.backend.model.order.POItem;
import com.greendrive.backend.model.Vehicle;
import com.greendrive.backend.repository.VehicleRepository;
import org.springframework.http.HttpStatus;

public class OrderUtils {

    public static double calculateTotalAndUpdateStock(PO po, VehicleRepository vehicleRepository) {
        double subtotal = 0.0;
        double totalDiscount = 0.0;

        for (POItem item : po.getItems()) {
            // Check vehicle
            Vehicle vehicle = vehicleRepository.findById(item.getVehicleId())
                    .orElseThrow(() -> new APIException("Vehicle not found with id: " + item.getVehicleId(), HttpStatus.NOT_FOUND));

            // Check stock
            if (vehicle.getQuantity() < item.getQuantity()) {
                throw new APIException("Not enough stock for vehicle: " + vehicle.getModel(), HttpStatus.BAD_REQUEST);
            }

            // Calculate total
            double price = vehicle.getPrice();
            if (vehicle.getHotDeal()) {
                double discount = price * 0.15;
                price -= discount;
                totalDiscount += discount * item.getQuantity();
            }

            // Update stock
            vehicle.setQuantity(vehicle.getQuantity() - item.getQuantity());
            vehicleRepository.save(vehicle);

            subtotal += price * item.getQuantity();
        }

        // Set discount
        po.setDiscount(Math.round(totalDiscount * 100.0) / 100.0);

        return Math.round(subtotal * 1.13 * 100.0) / 100.0; // 13% tax
    }
}