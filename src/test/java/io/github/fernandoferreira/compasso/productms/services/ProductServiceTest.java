package io.github.fernandoferreira.compasso.productms.services;

import io.github.fernandoferreira.compasso.productms.config.exception.ProductNotFoundException;
import io.github.fernandoferreira.compasso.productms.controllers.dto.ProductRequest;
import io.github.fernandoferreira.compasso.productms.models.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.NoSuchElementException;
import java.util.Set;

@SpringBootTest
@ActiveProfiles(value = "homolog")
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    public void givenInvalidId_ShouldNotFindProduct_ThrowsNoSuchElementException() {
        Assertions.assertThrows(NoSuchElementException.class, () -> this.productService.findById(-1L).get());
    }

    @Test
    public void givenValidId_ShouldNotFindProductDuringUpdate_ThrowsProductNotFoundException() {
        Assertions.assertThrows(ProductNotFoundException.class, () -> this.productService.update(1L, new ProductRequest()));
    }

    @Test
    public void givenValidId_ShouldNotFindProductDuringDelete_ThrowsProductNotFoundException() {
        Assertions.assertThrows(ProductNotFoundException.class, () -> this.productService.deleteById(1L));
    }

    @Test
    public void givenNameNotRegistered_ShouldNotFindProductDuringSearch_ThrowsProductNotFoundException() {
        Set<Product> products = this.productService.searchBy("Product", -1., -1.);
        Assertions.assertTrue(products.isEmpty());
    }
}