package io.github.fernandoferreira.compasso.productms.controller.dto;

import io.github.fernandoferreira.compasso.productms.model.Product;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class ProductSearchResponse {
    private Set<Product> products;
}
