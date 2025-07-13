package com.greendrive.backend.service;

import com.greendrive.backend.dto.SalesReportDTO;
import com.greendrive.backend.dto.UsageReportDTO;

public interface ReportService {
    SalesReportDTO getSalesReport();
    UsageReportDTO getUsageReport();
}
