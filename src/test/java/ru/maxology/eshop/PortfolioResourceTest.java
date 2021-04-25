package ru.maxology.eshop;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.filter.log.LogDetail;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import ru.max0l0gy.eshop.dto.PortfolioDto;
import ru.max0l0gy.eshop.service.PortfolioService;

import javax.inject.Inject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static ru.maxology.eshop.TestUtil.MAPPER;
import static ru.maxology.eshop.TestUtil.getBody;

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@QuarkusTestResource(H2DatabaseTestResource.class)
class PortfolioResourceTest {

    @Inject
    PortfolioService portfolioService;

    @BeforeAll
    @SneakyThrows
    public void loadInitialData() {
        PortfolioDto portfolioDto = MAPPER.readValue(getBody("dto/portfolio.json"), PortfolioDto.class);
        portfolioService.persist(portfolioDto);
        portfolioDto = MAPPER.readValue(getBody("dto/portfolio-for-delete.json"), PortfolioDto.class);
        portfolioService.persist(portfolioDto);
        portfolioDto = MAPPER.readValue(getBody("dto/portfolio-for-update.json"), PortfolioDto.class);
        portfolioService.persist(portfolioDto);
    }

    @Test
    public void list() {
        given()
                .when()
                .get("/api/v1/portfolios")
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .statusCode(200)
                .body(
                        "status", is("success"),
                        "data", notNullValue(),
                        "data[0].name", is("Portfolio1"),
                        "data[0].description", is("Description for portfolio"),
                        "data[0].shortDescription", is("Short description"),
                        "data[0].images[0]", is("https://mir-s3-cdn-cf.behance.net/project_modules/fs/aa8640117440163.6075ddf3a15a0.jpg")
                )
        ;
    }

    @Test
    public void find() {
        given()
                .when()
                .get("/api/v1/portfolios/100")
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .statusCode(200)
                .body(
                        "status", is("success"),
                        "data.name", is("Portfolio1"),
                        "data.description", is("Description for portfolio"),
                        "data.shortDescription", is("Short description"),
                        "data.images[0]", is("https://mir-s3-cdn-cf.behance.net/project_modules/fs/aa8640117440163.6075ddf3a15a0.jpg")
                )
        ;
    }

    @Test
    public void findFail() {
        given()
                .when()
                .get("/api/v1/portfolios/104")
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .statusCode(200)
                .body(
                        "status", is("fail"),
                        "data", nullValue()
                )
        ;
    }

    @Test
    @SneakyThrows
    public void delete() {
        given()
                .when()
                .delete("/api/v1/portfolios/101")
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .statusCode(200)
                .body(
                        "status", is("success"),
                        "data.deleted", is(true)
                )
        ;
    }

    @Test
    @SneakyThrows
    public void add() {
        given()
                .body(getBody("dto/portfolio-for-delete.json"))
                .contentType("application/json")
                .when()
                .post("/api/v1/portfolios")
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .statusCode(200)
                .body(
                        "status", is("success"),
                        "data.id", is(103),
                        "data.name", is("Portfolio2"),
                        "data.description", is("Description for portfolio 2"),
                        "data.shortDescription", is("Short description 2"),
                        "data.images[0]", is("https://mir-s3-cdn-cf.behance.net/project_modules/max_1200/6ac307117440163.6075ddf3a2bfa.jpg")
                )
        ;

        Assertions.assertTrue(portfolioService.delete(103L));
    }

    @Test
    @SneakyThrows
    public void update() {
        Assertions.assertTrue(portfolioService.find(102L).isPresent());
        given()
                .body(getBody("dto/portfolio-for-update-attributes.json"))
                .contentType("application/json")
                .when()
                .put("/api/v1/portfolios")
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .statusCode(200)
                .body(
                        "status", is("success"),
                        "data.id", is(102),
                        "data.name", is("Portfolio 3 updated"),
                        "data.description", is("Description for portfolio 3 updated"),
                        "data.shortDescription", is("Short description 3 updated"),
                        "data.images[0]", is("https://mir-s3-cdn-cf.behance.net/project_modules/1400/ce7f05117440163.6075ddf3a4768.jpg")
                )
        ;
    }

}
