package com.greendrive.backend.service;

import com.greendrive.backend.dto.SalesReportDTO;
import com.greendrive.backend.dto.UsageReportDTO;
import com.greendrive.backend.model.Vehicle;
import com.greendrive.backend.repository.UserRepository;
import com.greendrive.backend.repository.VehicleRepository;
import com.greendrive.backend.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {

    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReportServiceImpl(VehicleRepository vehicleRepository, UserRepository userRepository, ReviewRepository reviewRepository) {
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public SalesReportDTO getSalesReport() {
        long totalVehiclesSold = 0;
        double totalRevenue = 0.0;
        // total sold = sum of (initial quantity - current quantity) for all vehicles
        for (Vehicle v : vehicleRepository.findAll()) {
            if (v.getInitialQuantity() != null && v.getQuantity() != null) {
                int sold = v.getInitialQuantity() - v.getQuantity();
                if (sold > 0) {
                    totalVehiclesSold += sold;
                    totalRevenue += sold * v.getPrice();
                }
            }
        }
        return new SalesReportDTO(totalVehiclesSold, totalRevenue);
    }

    @Override
    public UsageReportDTO getUsageReport() {
        long totalUsers = userRepository.count();
        long totalReviews = reviewRepository.count();
        return new UsageReportDTO(totalUsers, totalReviews);
    }
}
