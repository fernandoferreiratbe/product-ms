package io.github.fernandoferreira.compasso.productms.controller.form;

import io.github.fernandoferreira.compasso.productms.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductForm {

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

    public Product convert() {
        return new Product(this.name, this.description, this.price);
    }

}
