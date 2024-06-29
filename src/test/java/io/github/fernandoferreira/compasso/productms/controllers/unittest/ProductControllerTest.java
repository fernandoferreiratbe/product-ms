package io.github.fernandoferreira.compasso.productms.controllers.unittest;

import io.github.fernandoferreira.compasso.productms.config.exception.ErrorInterceptor;
import io.github.fernandoferreira.compasso.productms.config.exception.ProductNotFoundException;
import io.github.fernandoferreira.compasso.productms.controllers.ProductController;
import io.github.fernandoferreira.compasso.productms.controllers.dto.ProductRequest;
import io.github.fernandoferreira.compasso.productms.models.Product;
import io.github.fernandoferreira.compasso.productms.services.ProductService;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.json.JSONException;
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
class ProductControllerTest {

    @Autowired
    private ProductController productController;

    @Autowired
    private ErrorInterceptor errorInterceptor;

    @MockBean
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        RestAssuredMockMvc.standaloneSetup(this.productController, this.errorInterceptor);
    }

    @Test
    void givenValidId_ShouldFindItInDatabase_ReturnProductSuccessfully() {
        Optional<Product> product = Optional.of(Product.builder()
                                                            .id(1L)
                                                            .name("Nike")
                                                            .description("Casual")
                                                            .price(34.90)
                                                            .build());

        when(this.productService.findById(1L)).thenReturn(product);

        RestAssuredMockMvc
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/products/{id}", "1")
                .then()
                    .status(HttpStatus.OK);
    }

    @Test
    void givenInvalidId_ShouldNotFindItInDatabase_ReturnProductNotFound() {
        when(this.productService.findById(5L)).thenReturn(Optional.empty());

        RestAssuredMockMvc
                .given()
                    .header("Content-Type", "application/json")
                    .accept(ContentType.JSON)
                .when()
                    .get("/products/{id}", "5")
                .then()
                    .status(HttpStatus.NOT_FOUND);
    }

    @Test
    void givenCorrectProductRequest_ShouldCreateNewResource_ReturnNewProduct() throws Exception {
        Product product = Product.builder().name("SHIRT").description("MOUNTAIN SHIRT").price(90.0).build();
        Product savedProduct = Product.builder().id(1L).name("SHIRT").description("MOUNTAIN SHIRT").price(90.0).build();

        when(this.productService.save(product)).thenReturn(savedProduct);
        JSONObject requestBody = new JSONObject();
        requestBody.put("name", "SHIRT");
        requestBody.put("description", "MOUNTAIN SHIRT");
        requestBody.put("price", 90.0);

        MockMvcResponse response = RestAssuredMockMvc
                .given()
                    .header("Content-Type", "application/json")
                    .and()
                    .body(requestBody.toString())
                .when()
                    .post("/products")
                .then()
                    .extract()
                    .response();

        Assertions.assertEquals(201, response.statusCode());
        Assertions.assertEquals("SHIRT", response.jsonPath().getString("name"));
        Assertions.assertEquals("MOUNTAIN SHIRT", response.jsonPath().getString("description"));
        Assertions.assertEquals(1L, response.jsonPath().getLong("id"));
        Assertions.assertEquals(90.0, response.jsonPath().getDouble("price"));
    }

    @Test
    void givenInvalidProductRequest_shouldNotCreateNewProduct_returnBadRequest() throws JSONException {
        Product product = Product.builder().name("SHIRT").description("MOUNTAIN SHIRT").price(90.0).build();
        Product savedProduct = Product.builder().id(1L).name("SHIRT").description("MOUNTAIN SHIRT").price(90.0).build();

        when(this.productService.save(product)).thenReturn(savedProduct);
        JSONObject requestBody = new JSONObject();
        requestBody.put("name", "SHIRT");
        requestBody.put("price", 90.0);

        MockMvcResponse response = RestAssuredMockMvc
                .given()
                    .header("Content-Type", "application/json")
                    .and()
                    .body(requestBody.toString())
                .when()
                    .post("/products")
                .then()
                    .extract()
                    .response();

        Assertions.assertEquals(400, response.statusCode());
    }

    @Test
    void givenValidId_ShouldDeleteProductSuccessfully_ReturnOK() {
        Product product = Product.builder().name("SHIRT").description("MOUNTAIN SHIRT").price(90.0).build();
        when(this.productService.deleteById(1L)).thenReturn(product);

        RestAssuredMockMvc
                .given()
                    .header("Content-Type", "application/json")
                .when()
                    .delete("/products/{id}", "1")
                .then()
                    .status(HttpStatus.OK);
    }

    @Test
    void givenInvalidId_ShouldNotFindProduct_ThrowsProductNotFoundException() {
        when(this.productService.deleteById(5L)).thenThrow(new ProductNotFoundException(""));

        RestAssuredMockMvc
                .given()
                    .header("Content-Type", "application/json")
                .when()
                    .delete("/products/{id}", "5")
                .then()
                    .status(HttpStatus.NOT_FOUND);

    }

    @Test
    void givenInvalidProductId_ThrowsExceptionWhenTryToUpdate_ReturnNotFound() throws JSONException {
        ProductRequest productRequest = new ProductRequest("TENNIS", "CASUAL", 958.50);
        ProductNotFoundException exception = new ProductNotFoundException("Product id 1 not found");

        JSONObject requestBody = new JSONObject();
        requestBody.put("name", productRequest.getName());
        requestBody.put("description", productRequest.getDescription());
        requestBody.put("price", productRequest.getPrice());

        when(this.productService.update(1L, productRequest)).thenThrow(exception);

        RestAssuredMockMvc
                .given()
                    .header("Content-Type", "application/json")
                    .body(requestBody.toString())
                .when()
                    .put("/products/{id}", "1")
                .then()
                    .status(HttpStatus.NOT_FOUND);
    }

    @Test
    void givenValidProduct_ShouldUpdateCorrectly_ReturnOk() throws Exception {
        ProductRequest productRequest = new ProductRequest("TENNIS", "CASUAL", 958.50);

        Product product = Product.builder().id(1L).name("TENNIS").description("CASUAL").price(958.50).build();

        when(this.productService.update(1L, productRequest))
                .thenReturn(product);


        JSONObject requestBody = new JSONObject();
        requestBody.put("name", productRequest.getName());
        requestBody.put("description", productRequest.getDescription());
        requestBody.put("price", productRequest.getPrice());

        MockMvcResponse response = RestAssuredMockMvc
                .given()
                    .header("Content-Type", "application/json")
                    .and()
                    .body(requestBody.toString())
                .when()
                    .put("/products/{id}", "1")
                .then()
                    .extract()
                    .response();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("TENNIS", response.jsonPath().getString("name"));
        Assertions.assertEquals("CASUAL", response.jsonPath().getString("description"));
        Assertions.assertEquals(958.5, response.jsonPath().getDouble("price"));
        Assertions.assertEquals(1L, response.jsonPath().getLong("id"));
    }

}
