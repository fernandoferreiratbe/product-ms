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

    @NotNull @NotEmpty @Size(min = 3, max = 100)
    private String name;
    @NotNull @NotEmpty @Size(min = 3, max = 100)
    private String description;
    @NotNull @DecimalMin(value = "0.01")
    private Double price;

    public Product convert() {
        return new Product(this.name, this.description, this.price);
    }

}
