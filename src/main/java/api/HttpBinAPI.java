package api;

import api.endpoints.HttpBinEndPoints;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class HttpBinAPI {
    private static HttpBinAPI instance = new HttpBinAPI();

    private RequestSpecification specification = new RequestSpecBuilder().setBaseUri("https://httpbin.org/").build();

    private RequestSpecification request() {
        return given().spec(specification);
    }

    public byte[] getPNGImage() {
        return request()
                .when()
                .get(HttpBinEndPoints.getPNG)
                .then()
                .extract()
                .asByteArray();
    }
}
