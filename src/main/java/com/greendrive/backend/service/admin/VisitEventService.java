package com.greendrive.backend.service.admin;

public interface VisitEventService {

    public void logEvent(String ipAddress, String vehicleId, String eventType);
}
