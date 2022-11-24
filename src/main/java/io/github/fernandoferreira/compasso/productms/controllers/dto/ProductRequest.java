package io.github.fernandoferreira.compasso.productms.controllers.dto;

import lombok.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProductRequest {

    @NotNull(message = "Name must not be null")
    @NotEmpty(message = "Name must not be empty")
    @Size(min = 3, max = 100, message = "Name size must be between 3 and 100")
    private String name;
    @NotNull(message = "Description must not be null")
    @NotEmpty(message = "Description must not be empty")
    @Size(min = 3, max = 100, message = "Description size must be between 3 and 100")
    private String description;
    @NotNull(message = "Price must not be null")
    @DecimalMin(value = "0.01", message = "Minimum price must be 0.01")
    private Double price;

}
