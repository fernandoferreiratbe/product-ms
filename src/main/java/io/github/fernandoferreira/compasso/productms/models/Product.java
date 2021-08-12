package io.github.fernandoferreira.compasso.productms.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;

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
