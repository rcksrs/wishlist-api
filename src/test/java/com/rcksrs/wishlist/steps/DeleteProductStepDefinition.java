package com.rcksrs.wishlist.steps;

import com.rcksrs.wishlist.config.RestClient;
import com.rcksrs.wishlist.domain.dto.ProductRequest;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;

import static org.hamcrest.Matchers.equalTo;

@RequiredArgsConstructor
public class DeleteProductStepDefinition {
    private final RestClient restClient;
    private String userId;
    private String sku;
    private Response response;

    @Before
    public void before() {
        restClient.request()
                .body(new ProductRequest("200", "Novo Produto 3", null, null, null))
                .when()
                .post("/api/product/user/456")
                .then()
                .statusCode(201);
    }

    @Dado("que o usuário com id {string} deseja remover o produto com SKU {string} da sua wishlist")
    public void step1_1(String userId, String sku) {
        this.userId = userId;
        this.sku = sku;
    }

    @Quando("o usuário envia a requisição para remover o produto da wishlist")
    public void step1_2() {
        this.response = restClient.request()
                .when()
                .delete(String.format("/api/product/user/%s/sku/%s", this.userId, this.sku));
    }

    @Entao("a resposta da remoção deve ter o status {int}")
    public void step1_3(int status) {
        this.response.then().statusCode(status);
    }

    @E("a resposta deve conter o erro {string}")
    public void step1_4(String error) {
        this.response.then().body("message", equalTo(error));
    }

}
