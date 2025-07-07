package com.greendrive.backend.payload;

import lombok.*;

import java.util.List;

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
    private double price;
    private List<String> imageUrls;
}
