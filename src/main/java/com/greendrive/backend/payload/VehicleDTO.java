package com.greendrive.backend.payload;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDTO {

    private Long id;
    private Double price;
    private String vin;
    private String shape;
    private String make;
    private String model;
    private String color;
    private Integer year;
    private Integer mileage;
    private String description;
    private Boolean newVehicle;
    private Boolean accident;
    private Boolean hotDeal;
    private List<String> imageUrls;
}
