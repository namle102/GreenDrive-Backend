package com.greendrive.backend.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class SalesReportWrapper {

    private double totalRevenue;
    private List<SalesReportDTO> data;
}
