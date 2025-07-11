package com.greendrive.backend.payload;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {

    private Long id;
    private String reviewer;
    private Integer rating;
    private String comment;
    private Long vehicleId;
}
