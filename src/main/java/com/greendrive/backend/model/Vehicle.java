package com.greendrive.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DecimalMin(value = "0.0", inclusive = false)
    private Double price;

    @NotBlank
    @Column(unique = true)
    private String vin;

    @NotBlank
    private String shape;

    @NotBlank
    private String make;

    @NotBlank
    private String model;

    @NotBlank
    private String color;

    @Min(2000)
    private Integer year;

    @Min(0)
    private Integer mileage;

    @Column(length = 1000)
    private String description;

    @NotNull
    @Column(name = "new_vehicle")
    private Boolean newVehicle;

    @NotNull
    private Boolean accident;

    @Column(name = "hot_deal")
    private Boolean hotDeal;

    @ElementCollection
    private List<String> imageUrls;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;
}