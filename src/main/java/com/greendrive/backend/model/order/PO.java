package com.greendrive.backend.model.order;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String orderNumber;

    @Email
    @NotBlank
    private String email;

    @ElementCollection
    @CollectionTable(name = "po_items", joinColumns = @JoinColumn(name = "po_id"))
    private List<POItem> items;

    private String firstName;
    private String lastName;
    private String shippingAddress;
    private String status;
    private Long cardInfo;
    private Double total;
}
