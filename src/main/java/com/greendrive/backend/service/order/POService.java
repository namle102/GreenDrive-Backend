package com.greendrive.backend.service.order;

import com.greendrive.backend.dto.order.POResponse;
import com.greendrive.backend.model.order.PO;
import java.util.List;

public interface POService {

    POResponse placeOrder(PO po);
    POResponse trackOrder(String email, String orderNumber);
}
