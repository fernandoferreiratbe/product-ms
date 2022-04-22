package io.github.fernandoferreira.compasso.productms.controller;

import io.restassured.RestAssured;
import io.restassured.config.JsonConfig;
import io.restassured.parsing.Parser;
import io.restassured.path.json.config.JsonPathConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.PostConstruct;

import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class ProductControllerIntegrationTest {

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
                .get("/products")
                .then()
                .assertThat()
                .body("products.size()", is(3));
    }

    @Test
    @DisplayName("Given valid id should find a valid product")
    void findById_GivenValidProductId_ShouldFindAndReturnValidProduct() {
        RestAssured
                .when()
                .get("/products/1")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(1))
                .body("name", equalTo("TENIS NIKE"))
                .body("description", equalTo("PARA CORRIDAS LONGAS"))
                .body("price", equalTo(350.0));
    }

}
