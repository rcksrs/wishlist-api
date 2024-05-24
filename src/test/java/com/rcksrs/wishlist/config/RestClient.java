package com.rcksrs.wishlist.config;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static io.restassured.RestAssured.given;

@Component
@RequiredArgsConstructor
public class RestClient {

    public RequestSpecification request() {
        return given()
                .port(Integer.parseInt(System.getProperty("port")))
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .urlEncodingEnabled(false)
                .when()
                .log()
                .everything();
    }
}
