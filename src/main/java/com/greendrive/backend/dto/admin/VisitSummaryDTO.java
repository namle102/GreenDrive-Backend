package com.greendrive.backend.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VisitSummaryDTO {

    private String vehicleId;
    private Long views;
    private Long purchases;
}
