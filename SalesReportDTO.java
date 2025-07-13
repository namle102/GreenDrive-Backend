package com.greendrive.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SalesReportDTO {
    private Long totalVehiclesSold;
    private Double totalRevenue;
}
