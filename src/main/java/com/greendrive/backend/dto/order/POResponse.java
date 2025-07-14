package com.greendrive.backend.dto.order;

import com.greendrive.backend.model.order.PO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class POResponse {

    private String message;
    private PO order;
}
