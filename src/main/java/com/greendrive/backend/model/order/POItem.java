package com.greendrive.backend.model.order;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class POItem {

    private Long vehicleId;
    private Integer quantity;
    private Double price;
}
