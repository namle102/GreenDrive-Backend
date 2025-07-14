package com.greendrive.backend.repository;

import com.greendrive.backend.model.admin.VisitEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VisitEventRepository extends JpaRepository<VisitEvent, Long> {

    List<VisitEvent> findByEventType(String eventType);
    List<VisitEvent> findByVehicleId(String vehicleId);
}
