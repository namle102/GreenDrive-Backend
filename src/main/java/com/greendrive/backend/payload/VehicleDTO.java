package com.greendrive.backend.payload;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDTO {

    private Long id;
    private String vin;
    private String shape;
    private String make;
    private String model;
    private String color;
    private int year;
    private int mileage;
    private boolean accidentHistory;
    private int price;
}
