package io.github.fernandoferreira.compasso.productms.controller;

import io.github.fernandoferreira.compasso.productms.model.Product;
import io.github.fernandoferreira.compasso.productms.service.ProductService;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ActiveProfiles(value = "homolog")
@WebMvcTest
public class ProductControllerTest {

    @Autowired
    private ProductController productController;

    @MockBean
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        RestAssuredMockMvc.standaloneSetup(this.productController);
    }

    @Test
    public void givenValidId_ShouldFindItInDatabase_ReturnProductSuccessfully() {
        Optional<Product> product = Optional.of(Product.builder()
                                                            .id(1L)
                                                            .name("Nike")
                                                            .description("Casual")
                                                            .price(34.90)
                                                            .build());

        when(this.productService.findById(1L))
                .thenReturn(product);

        RestAssuredMockMvc.given()
                .accept(ContentType.JSON)
                .when()
                .get("/products/{id}", 1L)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void givenInvalidId_ShouldNotFindItInDatabase_ReturnProductNotFound() {
        when(this.productService.findById(5L)).thenReturn(Optional.empty());

        RestAssuredMockMvc.given()
                .accept(ContentType.JSON)
                .get("/products/{id}", 5L)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
