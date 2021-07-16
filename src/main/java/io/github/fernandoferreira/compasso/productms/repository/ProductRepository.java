package io.github.fernandoferreira.compasso.productms.repository;

import io.github.fernandoferreira.compasso.productms.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
