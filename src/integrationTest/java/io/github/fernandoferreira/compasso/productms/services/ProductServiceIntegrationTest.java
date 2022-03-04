package io.github.fernandoferreira.compasso.productms.services;

import io.github.fernandoferreira.compasso.productms.config.exception.ProductNotFoundException;
import io.github.fernandoferreira.compasso.productms.controllers.dto.ProductRequest;
import io.github.fernandoferreira.compasso.productms.models.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.NoSuchElementException;
import java.util.Set;

@SpringBootTest
@ActiveProfiles(value = "homolog")
@DisplayName("Product Service Unit Test")
class ProductServiceIntegrationTest {

    @Autowired
    private ProductService productService;

    @Test
    @DisplayName("Given invalid id should not find product and throws no such elements exception")
    void givenInvalidId_ShouldNotFindProduct_ThrowsNoSuchElementException() {
        var product = this.productService.findById(-1L);
        Assertions.assertThrows(NoSuchElementException.class, product::get);
    }

    @Test
    @DisplayName("Given valid id should not find product during update operation and throws product not found exception")
    void givenValidId_ShouldNotFindProductDuringUpdate_ThrowsProductNotFoundException() {
        Assertions.assertThrows(ProductNotFoundException.class, () -> this.productService.update(1L, new ProductRequest()));
    }

    @Test
    @DisplayName("Given valid id should not find product during delete operation and throws product not found exception")
    void givenValidId_ShouldNotFindProductDuringDelete_ThrowsProductNotFoundException() {
        Assertions.assertThrows(ProductNotFoundException.class, () -> this.productService.deleteById(1L));
    }

    @Test
    @DisplayName("Given name not registered should not find product during search operation and throws product not found exception")
    void givenNameNotRegistered_ShouldNotFindProductDuringSearch_ThrowsProductNotFoundException() {
        Set<Product> products = this.productService.searchBy("Product", -1., -1.);
        Assertions.assertTrue(products.isEmpty());
    }
}