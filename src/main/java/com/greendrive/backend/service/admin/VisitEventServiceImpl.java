package com.greendrive.backend.service.admin;

import com.greendrive.backend.dto.admin.VisitEventDTO;
import com.greendrive.backend.dto.admin.VisitSummaryDTO;
import com.greendrive.backend.model.admin.VisitEvent;
import com.greendrive.backend.repository.VisitEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class VisitEventServiceImpl implements VisitEventService {

    private final VisitEventRepository visitEventRepository;

    @Override
    public void logEvent(String ipAddress, String vehicleId, String eventType, Integer quantity) {
        VisitEvent event = new VisitEvent();
        event.setIpAddress(ipAddress);
        event.setVehicleId(vehicleId);
        event.setEventType(eventType);
        event.setDay(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        event.setQuantity(quantity);
        visitEventRepository.save(event);
    }

    @Override
    public void logEvent(String ipAddress, String vehicleId, String eventType) {
        logEvent(ipAddress, vehicleId, eventType, 1);
    }

    @Override
    public List<VisitEventDTO> getAllVisitEvents() {
        return visitEventRepository.findAll().stream()
                .map(event -> new VisitEventDTO(
                        event.getIpAddress(),
                        event.getDay(),
                        event.getVehicleId(),
                        event.getEventType()))
                .toList();
    }

    @Override
    public List<VisitSummaryDTO> getVisitSummary() {
        List<VisitEvent> events = visitEventRepository.findAll();

        Map<String, VisitSummaryDTO> summaryMap = new HashMap<>();

        for (VisitEvent e : events) {
            String vid = e.getVehicleId();
            summaryMap.putIfAbsent(vid, new VisitSummaryDTO(vid, 0L, 0L));

            VisitSummaryDTO summary = summaryMap.get(vid);

            if ("VIEW".equalsIgnoreCase(e.getEventType())) {
                summary.setViews(summary.getViews() + e.getQuantity());
            } else if ("PURCHASE".equalsIgnoreCase(e.getEventType())) {
                summary.setPurchases(summary.getPurchases() + e.getQuantity());
            }
        }

        return new ArrayList<>(summaryMap.values());
    }
}
