package com.greendrive.backend.dto;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDTO {

    private Long id;
    private Integer quantity;
    private Double price;
    private Boolean hotDeal;
    private String shape;
    private String brand;
    private String model;
    private Integer year;
    private Integer mileage;
    private Boolean newVehicle;
    private Boolean accident;
    private String exteriorColor;
    private String interiorColor;
    private String interiorMaterial;
    private String description;
    private List<String> imageUrls;
}
