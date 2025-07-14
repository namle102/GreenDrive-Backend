package com.greendrive.backend.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VisitEventDTO {

    private String ipAddress;
    private String day;
    private String vehicleId;
    private String eventType;

}
