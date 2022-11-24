package io.github.fernandoferreira.compasso.productms.repositories;

import io.github.fernandoferreira.compasso.productms.models.Product;

import java.util.List;

public interface ProductCriteriaRepository {

    List<Product> search(String nameOrDescription, Double minPrice, Double maxPrice);
}
