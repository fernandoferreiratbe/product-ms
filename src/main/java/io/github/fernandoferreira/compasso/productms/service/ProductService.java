package io.github.fernandoferreira.compasso.productms.service;

import io.github.fernandoferreira.compasso.productms.config.exception.ProductNotFoundException;
import io.github.fernandoferreira.compasso.productms.controller.form.ProductForm;
import io.github.fernandoferreira.compasso.productms.model.Product;
import io.github.fernandoferreira.compasso.productms.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<Product> findById(Long id) {
        return this.productRepository.findById(id);
    }

    public Product save(ProductForm productForm) {
        Product product = productForm.convert();
        return this.productRepository.save(product);
    }

    public Product update(Long id, ProductForm productForm) {
        Optional<Product> optional = this.productRepository.findById(id);

        if (!optional.isPresent()) {
            throw new ProductNotFoundException("Product id " + id + " not found");
        }

        Product product = optional.get();
        product.setName(productForm.getName());
        product.setDescription(productForm.getDescription());
        product.setPrice(productForm.getPrice());

        return product;
    }

}
