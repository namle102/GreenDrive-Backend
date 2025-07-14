package com.greendrive.backend.controller.order;

import com.greendrive.backend.dto.order.POResponse;
import com.greendrive.backend.model.order.PO;
import com.greendrive.backend.service.admin.VisitEventService;
import com.greendrive.backend.service.order.POService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class POController {

    private final POService poService;
    private final VisitEventService visitEventService;

    @PostMapping("/place")
    public ResponseEntity<POResponse> placeOrder(@Valid @RequestBody PO po,
                                                 HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        po.getItems().forEach(item ->
                visitEventService.logEvent(ip, String.valueOf(item.getVehicleId()), "PURCHASE")
        );

        POResponse response = poService.placeOrder(po);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/track")
    public ResponseEntity<POResponse> trackOrder(@RequestParam(name = "email") String email,
                                                 @RequestParam(name = "orderNumber") String orderNumber) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(poService.trackOrder(email, orderNumber));
    }
}
