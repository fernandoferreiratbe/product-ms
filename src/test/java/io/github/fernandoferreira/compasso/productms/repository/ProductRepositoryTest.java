package io.github.fernandoferreira.compasso.productms.repository;

import io.github.fernandoferreira.compasso.productms.config.exception.ProductNotFoundException;
import io.github.fernandoferreira.compasso.productms.model.Product;
import io.github.fernandoferreira.compasso.productms.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.NoSuchElementException;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("homolog")
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TestEntityManager testEntityManager;



    @Test
    public void givenValidProduct_ShouldCreateResource_ReturnNewProduct() {
        Product product = Product.builder().name("Nike").description("Casual").price(99.9).build();
        this.productRepository.save(product);

        Assertions.assertNotNull(product.getId());
        Assertions.assertTrue(product.getId() > 0);
        Assertions.assertEquals("Nike", product.getName());
        Assertions.assertEquals("Casual", product.getDescription());
        Assertions.assertEquals(99.9, product.getPrice());
    }

    @Test
    public void givenValidProduct_ShouldDeleteSuccessfully() {
        Product product = Product.builder().name("Nike").description("Casual").price(99.9).build();
        this.productRepository.save(product);
        this.productRepository.delete(product);

        Assertions.assertThrows(NoSuchElementException.class, () -> this.productRepository.findById(product.getId()).get());
    }

}
