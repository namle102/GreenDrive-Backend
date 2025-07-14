package com.greendrive.backend.service.admin;

import com.greendrive.backend.dto.admin.VisitEventDTO;
import com.greendrive.backend.dto.admin.VisitSummaryDTO;
import java.util.List;

public interface VisitEventService {

    void logEvent(String ipAddress, String vehicleId, String eventType);
    void logEvent(String ipAddress, String vehicleId, String eventType, Integer quantity);
    List<VisitEventDTO> getAllVisitEvents();
    List<VisitSummaryDTO> getVisitSummary();
}
