package io.github.fernandoferreira.compasso.productms.models;

import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull @NotEmpty
    private String name;
    @NonNull @NotEmpty @Size(min = 3, max = 100)
    private String description;
    @NonNull @DecimalMin(value = "0.01")
    private Double price;

}
