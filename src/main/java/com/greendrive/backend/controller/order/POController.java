package com.greendrive.backend.controller.order;

import com.greendrive.backend.dto.order.POResponse;
import com.greendrive.backend.model.order.PO;
import com.greendrive.backend.service.order.POService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class POController {

    private final POService poService;

    @PostMapping("/place")
    public ResponseEntity<POResponse> placeOrder(@Valid @RequestBody PO po) {
        return ResponseEntity.status(HttpStatus.CREATED).body(poService.placeOrder(po));
    }

    @GetMapping("/track")
    public ResponseEntity<POResponse> trackOrder(@RequestParam(name = "email") String email,
                                                 @RequestParam(name = "orderNumber") String orderNumber) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(poService.trackOrder(email, orderNumber));
    }
}
