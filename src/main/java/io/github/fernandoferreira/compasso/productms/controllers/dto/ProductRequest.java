package io.github.fernandoferreira.compasso.productms.controllers.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;



@Getter
@Setter
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
