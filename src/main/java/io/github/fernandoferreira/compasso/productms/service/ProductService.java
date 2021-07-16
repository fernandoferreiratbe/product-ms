package io.github.fernandoferreira.compasso.productms.service;

import io.github.fernandoferreira.compasso.productms.model.Product;
import io.github.fernandoferreira.compasso.productms.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return this.productRepository.findAll();
    }
}
