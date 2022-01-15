package io.github.fernandoferreira.compasso.productms.services;

import io.github.fernandoferreira.compasso.productms.models.Product;
import io.github.fernandoferreira.compasso.productms.repositories.ProductRepository;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.when;

@DisplayName("Product Service Test")
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("Invoke find all service method should return products.")
    void findAll_InvokeFindAllServiceMethod_ShouldReturnProducts() {
        Mockito.when(this.productRepository.findAll()).thenReturn(this.createProducts());

        Set<Product> products = this.productService.findAll();

        Assertions.assertThat(products.size()).isEqualTo(1);
        Assertions.assertThat(products)
                .extracting("id", "name", "description", "price")
                .doesNotContainNull()
                .contains(Tuple.tuple(1L, "DUMMY_PRODUCT_NAME", "DUMMY_PRODUCT_DESCRIPTION", 230.0));

    }

    private List<Product> createProducts() {
        Product product = Product.builder()
                .id(1L)
                .name("DUMMY_PRODUCT_NAME")
                .description("DUMMY_PRODUCT_DESCRIPTION")
                .price(230.0)
                .build();

        return List.of(product);
    }
}
