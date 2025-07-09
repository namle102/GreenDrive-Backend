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
@ToString
@EqualsAndHashCode
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    private int year;

    @Min(0)
    private int mileage;

    @NotNull
    private boolean accident;

    @DecimalMin(value = "0.0", inclusive = false)
    private double price;

    @ElementCollection
    private List<String> imageUrls;

    @Column(name = "hot_deal")
    private Boolean hotDeal;
}