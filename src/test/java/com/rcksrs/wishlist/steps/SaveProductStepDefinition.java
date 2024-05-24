package com.rcksrs.wishlist.steps;

import com.rcksrs.wishlist.config.RestClient;
import com.rcksrs.wishlist.domain.dto.ProductRequest;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

@RequiredArgsConstructor
public class SaveProductStepDefinition {
    private final RestClient restClient;
    private String userId;
    private ProductRequest productRequest;
    private Response response;

    @Dado("que o usuário com id {string} deseja adicionar um produto à sua wishlist")
    public void step1_1(String userId) {
        this.userId = userId;
    }

    @E("o produto tem os seguintes detalhes:")
    public void step1_2(ProductRequest productRequest) {
        this.productRequest = productRequest;
    }

    @Quando("o usuário envia a requisição para salvar o produto na wishlist")
    public void step1_3() {
        this.response = restClient.request()
                .body(this.productRequest)
                .when()
                .post("/api/product/user/" + this.userId);

    }

    @Entao("a resposta deve ter o status {int}")
    public void step1_4(Integer status) {
        this.response.then().statusCode(status);
    }

    @E("a resposta deve conter o produto com nome {string} e SKU {string}")
    public void step1_5(String title, String sku) {
        this.response.then()
                .body("title", equalTo(title))
                .and()
                .body("sku", equalTo(sku));
    }

    @E("a resposta não deve conter o produto com nome {string}")
    public void step1_6(String title) {
        this.response.then()
                .body("title", not(equalTo(title)));
    }

    @E("a validação do campo {string} deve conter o erro {string}")
    public void step1_7(String field, String error) {
        this.response.then()
                .body("[0].field", equalTo(field))
                .and()
                .body("[0].message", equalTo(error));
    }
}
