package com.greendrive.backend.controller.admin;

import com.greendrive.backend.dto.admin.VisitEventDTO;
import com.greendrive.backend.dto.admin.VisitSummaryDTO;
import com.greendrive.backend.service.admin.VisitEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/reports")
public class AdminController {

    private final VisitEventService visitEventService;

    @GetMapping("/usage")
    public ResponseEntity<List<VisitEventDTO>> getUsageReport() {
        return ResponseEntity.status(HttpStatus.OK).body(visitEventService.getAllVisitEvents());
    }

    @GetMapping("/usage-summary")
    public ResponseEntity<List<VisitSummaryDTO>> getUsageSummary() {
        return ResponseEntity.status(HttpStatus.OK).body(visitEventService.getVisitSummary());
    }
}
