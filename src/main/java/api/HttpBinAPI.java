package api;

import api.endpoints.HttpBinEndPoints;
import api.objects.ImageExtensions;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class HttpBinAPI {
    private static HttpBinAPI instance = new HttpBinAPI();

    private RequestSpecification specification = new RequestSpecBuilder().setBaseUri("https://httpbin.org/").build();

    public static HttpBinAPI getInstance() {
        return instance;
    }

    private RequestSpecification request() {
        return given().spec(specification);
    }

    public byte[] getImage(ImageExtensions imageExtension){
        String endPoint = switch (imageExtension) {
            case PNG -> HttpBinEndPoints.getPNG;
            case JPEG -> HttpBinEndPoints.getJPEG;
            case SVG -> HttpBinEndPoints.getSVG;
            case WEBP -> HttpBinEndPoints.getWEBP;
        };
        return request()
                .expect()
                .statusCode(200)
                .when()
                .get(endPoint)
                .then()
                .extract()
                .asByteArray();
    }
}
