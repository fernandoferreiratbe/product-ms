package io.github.fernandoferreira.compasso.productms.repository;

import io.github.fernandoferreira.compasso.productms.model.Product;

import java.util.List;

public interface ProductCriteriaRepository {

    List<Product> search(String nameOrDescription, Double minPrice, Double maxPrice);
}
