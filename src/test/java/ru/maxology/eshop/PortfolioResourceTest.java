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

import java.util.List;

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
        portfolioService.persist(MAPPER.readValue(getBody("dto/portfolio.json"), PortfolioDto.class));
        portfolioService.persist(MAPPER.readValue(getBody("dto/portfolio-for-delete.json"), PortfolioDto.class));
        portfolioService.persist(MAPPER.readValue(getBody("dto/portfolio-for-update.json"), PortfolioDto.class));
    }

    @Test
    void list() {
        given()
                .when()
                .get("/api/v1/portfolios")
                .then()
                .log().all()
                .statusCode(200)
                .body(
                        "status", is("success"),
                        "data", notNullValue(),
                        "data", isA(List.class),
                        "data[0].name", is("Portfolio1"),
                        "data[0].description", is("Description for portfolio"),
                        "data[0].shortDescription", is("Short description"),
                        "data[0].images[0]", is("https://mir-s3-cdn-cf.behance.net/project_modules/fs/aa8640117440163.6075ddf3a15a0.jpg")
                )
        ;
    }

    @Test
    void find() {
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
    void findFail() {
        given()
                .when()
                .get("/api/v1/portfolios/144")
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
    void delete() {
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
    void add() {
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
                        "data.id", notNullValue(),
                        "data.name", is("Portfolio2"),
                        "data.description", is("Description for portfolio 2"),
                        "data.shortDescription", is("Short description 2"),
                        "data.images[0]", is("https://mir-s3-cdn-cf.behance.net/project_modules/max_1200/6ac307117440163.6075ddf3a2bfa.jpg")
                )
        ;

    }

    @Test
    @SneakyThrows
    void update() {
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

    @Test
    @SneakyThrows
    void addPortfolioWithLongDescription() {
        given()
                .body(getBody("dto/portfolio-for-delete-long-description.json"))
                .contentType("application/json")
                .when()
                .post("/api/v1/portfolios")
                .then()
                .log()
                .ifValidationFails(LogDetail.BODY)
                .statusCode(200)
                .body(
                        "status", is("success"),
                        "data.id", notNullValue(),
                        "data.name", is("Portfolio Long Description"),
                        "data.description", is("social media project, 2017- now\nLink - https://www.instagram.com/gucciholes/ \n\nREVIEW:\nFor several years I am working on my project which explores increasingly,\nunhealthy relationship of women with Instagram and dubious notions of\n‘truth’ online. I illustrate this through creation of fiction Instagram\naccount of woman who arrived in Moscow from small town in attempt to\nachieve some sort of recognition and material lifestyle which is promoted\nby society. I show my personage as stereotypical, simplified woman that\nother can consume as an object. I documenting the process of her growing\nself-obsession and consumerist values via photography.\n\nThrough constant self-advertising and change in appearance my\ncharacter gets more commercial benefits in area of goods and services,\nbut during this process, with increasing of carnal pleasures she loss of\nhumanity. That is why I use mask which grotesquely present her as a doll.\nMasks also, had a long history and were used by prostitutes in time of\ncarnival to have opportunity to connect with men from high society. In\nthis way, I consider the concept of carnavalisation according to M.\nBakhtin, through modernising of carnival notions in social net.\n\nThe female body is one of the main manifestations of the carnavalisation\nconcept today, as a metaphor for the material and festive element.\nBased on analysis of famous social pages of women and how they\nrepresent themselves, I create a visual myth of status and fashionable\nwoman for my character.\n\nCarnival and commodity relationship between people go through\nnon-stop spectacle, based on images promoted fun and easy lifestyle,\naggressive attraction and sexuality which are profitable to economy. My\nproject highlights that women as tools and as victims in such realities. It\ncould also illustrates that people today are surrounded by myth and\nthey ready to be cheated in internet to achieve some sort of voyeristic\npleasures. I critique these notions through conscious objectification of\nmy character, using the same ways of self-representation, but in\ntheatrical, parody way. So, my personage’s recognition could prove\ndistorted values and standards of beauty imposed in our society, where\nfreak or an unreal character can become an idol for people.\n"),
                        "data.shortDescription", is("social media project, 2017- now"),
                        "data.images[0]", is("https://mir-s3-cdn-cf.behance.net/project_modules/max_1200/6ac307117440163.6075ddf3a2bfa.jpg")
                )
        ;

    }

}
