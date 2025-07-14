package com.greendrive.backend.service.admin;

import com.greendrive.backend.model.admin.VisitEvent;
import com.greendrive.backend.repository.VisitEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class VisitEventServiceImpl implements VisitEventService {

    private final VisitEventRepository visitEventRepository;

    @Override
    public void logEvent(String ipAddress, String vehicleId, String eventType) {
        VisitEvent event = new VisitEvent();
        event.setIpAddress(ipAddress);
        event.setVehicleId(vehicleId);
        event.setEventType(eventType);
        event.setDay(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        visitEventRepository.save(event);
    }
}
