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

    public byte[] getImage(ImageExtensions imageExtension) {
        String endPoint;
        switch (imageExtension) {
            case PNG:
                endPoint = HttpBinEndPoints.getPNG;
                break;
            case JPEG:
                endPoint = HttpBinEndPoints.getJPEG;
                break;
            case SVG:
                endPoint = HttpBinEndPoints.getSVG;
                break;
            case WEBP:
                endPoint = HttpBinEndPoints.getWEBP;
                break;
            default:
                throw new IllegalArgumentException("Can't recognize \"" + imageExtension + "\" image extension!");
        }
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
