package io.github.fernandoferreira.compasso.productms.controller;

import io.github.fernandoferreira.compasso.productms.model.Product;
import io.github.fernandoferreira.compasso.productms.service.ProductService;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
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

    @Test
    public void givenCorrectProductRequest_ShouldCreateNewResource_ReturnNewProduct() throws Exception {
        Product product = Product.builder().name("SHIRT").description("MOUNTAIN SHIRT").price(90.0).build();
        Product savedProduct = Product.builder().id(1L).name("SHIRT").description("MOUNTAIN SHIRT").price(90.0).build();

        when(this.productService.save(product)).thenReturn(savedProduct);
        JSONObject requestBody = new JSONObject();
        requestBody.put("name", "SHIRT");
        requestBody.put("description", "MOUNTAIN SHIRT");
        requestBody.put("price", 90.0);

        MockMvcResponse response = RestAssuredMockMvc.given()
                .header("Content-Type", "application/json")
                .and()
                .body(requestBody.toString())
                .when()
                .post("/products")
                .then()
                .extract().response();

        Assertions.assertEquals(201, response.statusCode());
        Assertions.assertEquals("SHIRT", response.jsonPath().getString("name"));
        Assertions.assertEquals("MOUNTAIN SHIRT", response.jsonPath().getString("description"));
        Assertions.assertEquals("90.0", response.jsonPath().getString("price"));
        Assertions.assertEquals("1", response.jsonPath().getString("id"));
    }
}
