package io.github.fernandoferreira.compasso.productms.repository;

import io.github.fernandoferreira.compasso.productms.model.Product;
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

    @Test
    public void givenProduct_UpdateItsValues_ShouldPersistCorrectly() {
        Product product = Product.builder().name("Nike").description("Casual").price(99.9).build();
        this.productRepository.save(product);
        Long id = product.getId();
        product.setName("Tennis Nike");
        product.setDescription("Not Casual");
        product.setPrice(349.60);
        this.productRepository.save(product);
        Product productUpdated = this.productRepository.findById(id).get();

        Assertions.assertNotNull(productUpdated.getId());
        Assertions.assertEquals(id, productUpdated.getId());
        Assertions.assertEquals("Tennis Nike", productUpdated.getName());
        Assertions.assertEquals("Not Casual", productUpdated.getDescription());
        Assertions.assertEquals(349.60, productUpdated.getPrice());
    }

    @Test
    public void givenValidId_ShouldFindProductInDatabase_ReturnValidProduct() {
        Product product = Product.builder().name("Nike").description("Casual").price(99.9).build();
        this.productRepository.save(product);

        Assertions.assertDoesNotThrow(() -> this.productRepository.findById(product.getId()).get());

    }
}
