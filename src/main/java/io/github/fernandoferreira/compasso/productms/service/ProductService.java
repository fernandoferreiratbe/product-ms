package io.github.fernandoferreira.compasso.productms.service;

import io.github.fernandoferreira.compasso.productms.config.exception.ProductNotFoundException;
import io.github.fernandoferreira.compasso.productms.controller.dto.ProductRequest;
import io.github.fernandoferreira.compasso.productms.model.Product;
import io.github.fernandoferreira.compasso.productms.repository.ProductCriteriaRepository;
import io.github.fernandoferreira.compasso.productms.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductCriteriaRepository productCriteriaRepository;

    public Set<Product> findAll() {
        List<Product> products = this.productRepository.findAll();

        return new HashSet<>(products);
    }

    public Optional<Product> findById(Long id) {
        return this.productRepository.findById(id);
    }

    public Set<Product> searchBy(String nameOrDescription, Double minPrice, Double maxPrice) {
        log.info(String.format("Searching by Name or Description: %s, Min Price: %s and Max Price: %s",
                nameOrDescription, minPrice, maxPrice));

        List<Product> products = this.productCriteriaRepository.search(nameOrDescription, minPrice, maxPrice);

        return new HashSet<>(products);
    }

    public Product save(Product product) {
        return this.productRepository.save(product);
    }

    public Product update(Long id, ProductRequest productRequest) {
        Optional<Product> optional = this.productRepository.findById(id);

        if (optional.isEmpty()) {
            throw new ProductNotFoundException("Product id " + id + " not found");
        }

        Product product = optional.get();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());

        return product;
    }

    public Product deleteById(Long id) {
        Optional<Product> optional = this.productRepository.findById(id);

        if (optional.isEmpty()) {
            throw new ProductNotFoundException("Product id " + id + " not found");
        }

        Product product = optional.get();

        this.productRepository.delete(product);

        return product;
    }
}
