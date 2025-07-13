package com.greendrive.backend.controller;

import com.greendrive.backend.dto.SalesReportDTO;
import com.greendrive.backend.dto.UsageReportDTO;
import com.greendrive.backend.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/sales")
    public ResponseEntity<SalesReportDTO> getSalesReport() {
        return ResponseEntity.ok(reportService.getSalesReport());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/usage")
    public ResponseEntity<UsageReportDTO> getUsageReport() {
        return ResponseEntity.ok(reportService.getUsageReport());
    }
}
