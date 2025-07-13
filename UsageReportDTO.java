package com.greendrive.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsageReportDTO {
    private Long totalUsers;
    private Long totalReviews;
}
