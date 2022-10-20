package io.github.fernandoferreira.compasso.productms.services.unittest;

import io.github.fernandoferreira.compasso.productms.models.Product;
import io.github.fernandoferreira.compasso.productms.repositories.ProductCriteriaRepository;
import io.github.fernandoferreira.compasso.productms.repositories.ProductRepository;
import io.github.fernandoferreira.compasso.productms.services.ProductService;
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
import java.util.Optional;
import java.util.Set;

@DisplayName("Product Service Test")
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductCriteriaRepository productCriteriaRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("Invoke find all service method should return products.")
    void findAll_shouldFindOneProduct_ObjectStateReceivedAsExpected() {
        Mockito.when(this.productRepository.findAll()).thenReturn(this.createProducts());

        Set<Product> products = this.productService.findAll();

        Assertions.assertThat(products.size()).isEqualTo(1);
        Assertions.assertThat(products)
                .extracting("id", "name", "description", "price")
                .doesNotContainNull()
                .contains(Tuple.tuple(1L, "DUMMY_PRODUCT_NAME", "DUMMY_PRODUCT_DESCRIPTION", 230.0));

    }

    @Test
    @DisplayName("Invoke findById passing valid product id should return a valid product")
    void findById_givenValidProductId_shouldFindProduct_returnFoundProduct() {
        Mockito.when(this.productRepository.findById(1L)).thenReturn(Optional.of(this.createProduct()));

        Optional<Product> product = this.productService.findById(1L);

        Assertions.assertThat(product.isPresent()).isTrue();
        Assertions.assertThat(product.get().getId()).isEqualTo(1L);
        Assertions.assertThat(product.get().getName()).isEqualTo("DUMMY_PRODUCT_NAME");
    }

    @Test
    @DisplayName("Given valid name should search for products and return a valid product")
    void search_givenValidName_shouldSearchForProducts_returnValidProduct() {
        Mockito.when(this.productCriteriaRepository.search("DUMMY_PRODUCT_NAME", 0., 0.))
                .thenReturn(this.createProducts());

        Set<Product> products = this.productService.searchBy("DUMMY_PRODUCT_NAME", 0., 0.);

        Assertions.assertThat(products.size()).isEqualTo(1);
        Assertions.assertThat(products)
                .extracting("id", "name", "description", "price")
                .doesNotContainNull()
                .contains(Tuple.tuple(1L, "DUMMY_PRODUCT_NAME", "DUMMY_PRODUCT_DESCRIPTION", 230.0));
    }

    private List<Product> createProducts() {
        return List.of(this.createProduct());
    }

    private Product createProduct() {
        return Product.builder()
                .id(1L)
                .name("DUMMY_PRODUCT_NAME")
                .description("DUMMY_PRODUCT_DESCRIPTION")
                .price(230.0)
                .build();
    }
}
