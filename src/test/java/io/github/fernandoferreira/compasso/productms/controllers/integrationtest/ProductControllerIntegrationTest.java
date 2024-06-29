package io.github.fernandoferreira.compasso.productms.controllers.integrationtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.fernandoferreira.compasso.productms.controllers.dto.ProductRequest;
import io.github.fernandoferreira.compasso.productms.models.Product;
import io.restassured.RestAssured;
import io.restassured.config.JsonConfig;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.path.json.config.JsonPathConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import jakarta.annotation.PostConstruct;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
@Tag("integration")
class ProductControllerIntegrationTest {

    private final String PATH = "/products";

    @LocalServerPort
    private int port;

    @PostConstruct
    public void init() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = this.port;
        RestAssured.defaultParser = Parser.JSON;
        JsonConfig jsonConfig = JsonConfig.jsonConfig().numberReturnType(JsonPathConfig.NumberReturnType.DOUBLE);
        RestAssured.config = RestAssured.config().jsonConfig(jsonConfig);
    }

    @Test
    @DisplayName("Given valid request should return all products already registered")
    void findAll_GivenValidRequest_ShouldReturnAllProductsAlreadyRegistered() {
        RestAssured
                .when()
                    .get(PATH)
                .then()
                    .assertThat()
                    .body("products.size()", is(3));
    }

    @Test
    @DisplayName("Given valid id should find a valid product")
    void findById_GivenValidProductId_ShouldFindAndReturnValidProduct() {
        RestAssured
                .when()
                    .get(PATH.concat("/1"))
                .then()
                    .assertThat()
                    .statusCode(HttpStatus.OK.value())
                    .body("id", equalTo(1))
                    .body("name", equalTo("TENIS NIKE"))
                    .body("description", equalTo("PARA CORRIDAS LONGAS"))
                    .body("price", equalTo(350.0));
    }

    @Test
    @DisplayName("Given a correct product request should create a new resource and return it")
    void givenCorrectProductRequest_ShouldCreateNewResource_ReturnNewProduct() throws Exception {
        ProductRequest productRequest = new ProductRequest(
                "New Balance", "comfort for your entire day", 239.9
        );

        String requestBody = new ObjectMapper().writeValueAsString(productRequest);

        Product productCreated = RestAssured
                .given()
                    .header("Content-Type", ContentType.JSON)
                    .and()
                    .body(requestBody)
                .when()
                    .post(PATH)
                .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .extract().as(Product.class);

        assertThat(productCreated.getId(), notNullValue());
    }
}
