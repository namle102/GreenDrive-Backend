package com.greendrive.backend.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SalesReportDTO {

    private String vehicleId;
    private Long quantitySold;
    private Double revenue;
}
