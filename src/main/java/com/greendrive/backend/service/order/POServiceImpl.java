package com.greendrive.backend.service.order;

import com.greendrive.backend.dto.admin.SalesReportDTO;
import com.greendrive.backend.dto.admin.SalesReportWrapper;
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
import java.util.*;

@Service
@RequiredArgsConstructor
public class POServiceImpl implements POService {

    private final PORepository poRepository;
    private final VehicleRepository vehicleRepository;

    @Override
    public POResponse placeOrder(PO po) {
        // Generate unique order#
        String orderNumber = "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        po.setOrderNumber(orderNumber);
        po.setStatus("ORDERED");

        // Set price for each item
        for (POItem item : po.getItems()) {
            Vehicle vehicle = vehicleRepository.findById(item.getVehicleId())
                    .orElseThrow(() -> new APIException("Vehicle not found: " + item.getVehicleId(), HttpStatus.BAD_REQUEST));
            item.setPrice(vehicle.getPrice());
        }

        // Save order
        PO savedPO = poRepository.save(po);

        return new POResponse("Order placed successfully!", savedPO);
    }

    @Override
    public POResponse trackOrder(String email, String orderNumber) {
        PO order = poRepository.findByEmailAndOrderNumber(email, orderNumber)
                .orElseThrow(() -> new APIException("Order not found with order number: " + orderNumber, HttpStatus.NOT_FOUND));
        return new POResponse("Order found", order);
    }

    @Override
    public SalesReportWrapper getSalesReport() {
        List<PO> orders = poRepository.findAll();
        Map<String, SalesReportDTO> reportMap = new HashMap<>();

        double totalRevenue = 0;

        for (PO order : orders) {
            for (POItem item : order.getItems()) {
                String vid = String.valueOf(item.getVehicleId());
                int quantity = item.getQuantity();
                double price = item.getPrice();

                reportMap.putIfAbsent(vid, new SalesReportDTO(vid, 0L, 0D));
                SalesReportDTO report = reportMap.get(vid);

                report.setQuantitySold(report.getQuantitySold() + quantity);
                double itemRevenue = quantity * price;
                report.setRevenue(report.getRevenue() + itemRevenue);
                totalRevenue += itemRevenue;
            }
        }

        List<SalesReportDTO> reports = new ArrayList<>(reportMap.values());

        // Round each revenue
        for (SalesReportDTO dto : reports) {
            dto.setRevenue(Math.round(dto.getRevenue() * 100.0) / 100.0);
        }

        // Round total revenue
        totalRevenue = Math.round(totalRevenue * 100.0) / 100.0;

        return new SalesReportWrapper(totalRevenue, reports);
    }
}
