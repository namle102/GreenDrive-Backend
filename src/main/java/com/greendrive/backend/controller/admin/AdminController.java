package com.greendrive.backend.controller.admin;

import com.greendrive.backend.dto.admin.SalesReportWrapper;
import com.greendrive.backend.dto.admin.VisitEventDTO;
import com.greendrive.backend.dto.admin.VisitSummaryDTO;
import com.greendrive.backend.service.admin.VisitEventService;
import com.greendrive.backend.service.order.POService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final VisitEventService visitEventService;
    private final POService poService;

    @GetMapping("/event-log")
    public ResponseEntity<List<VisitEventDTO>> getEventLog() {
        return ResponseEntity.status(HttpStatus.OK).body(visitEventService.getAllVisitEvents());
    }

    @GetMapping("/usage")
    public ResponseEntity<List<VisitSummaryDTO>> getUsageReport() {
        return ResponseEntity.status(HttpStatus.OK).body(visitEventService.getVisitSummary());
    }

    @GetMapping("/sales")
    public ResponseEntity<SalesReportWrapper> getSalesReport() {
        return ResponseEntity.ok(poService.getSalesReport());
    }
}
