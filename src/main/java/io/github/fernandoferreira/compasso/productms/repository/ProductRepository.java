package io.github.fernandoferreira.compasso.productms.repository;

import io.github.fernandoferreira.compasso.productms.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContainingAllIgnoreCase(String name);

    List<Product> findByDescriptionContainingAllIgnoreCase(String description);

}
