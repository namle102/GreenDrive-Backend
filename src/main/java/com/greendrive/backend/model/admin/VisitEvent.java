package com.greendrive.backend.model.admin;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "visit_event")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VisitEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ipAddress;
    private String day; // yyyyMMdd
    private String vehicleId;
    private String eventType; // "VIEW" or "PURCHASE"
    private Integer quantity;
}
