package com.greendrive.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "vehicle")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(0)
    private Integer quantity;

    @DecimalMin(value = "0.0", inclusive = false)
    private Double price;

    @NotNull
    private Boolean hotDeal;

    @NotBlank
    private String shape;

    @NotBlank
    private String brand;

    @NotBlank
    private String model;

    @Min(2000)
    private Integer year;

    @Min(0)
    private Integer mileage;

    @NotNull
    private Boolean newVehicle;

    @NotNull
    private Boolean accident;

    @NotBlank
    private String exteriorColor;

    @NotBlank
    private String interiorColor;

    @NotBlank
    private String interiorMaterial;

    @Column(length = 1000)
    private String description;

    @ElementCollection
    private List<String> imageUrls;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;
}