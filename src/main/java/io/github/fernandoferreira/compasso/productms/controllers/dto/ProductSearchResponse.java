package io.github.fernandoferreira.compasso.productms.controllers.dto;

import io.github.fernandoferreira.compasso.productms.models.Product;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class ProductSearchResponse {
    private Set<Product> products;
}
