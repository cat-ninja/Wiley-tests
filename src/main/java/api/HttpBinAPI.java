package api;

import api.endpoints.HttpBinEndPoints;
import api.objects.Images;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import static api.objects.Images.*;
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

    public byte[] getPNGImage() {
        return getImage(PNG);
    }

    public byte[] getJPEGImage() {
        return getImage(JPEG);
    }
    public byte[] getSVGImage() {
        return getImage(SVG);
    }
    public byte[] getWEBPImage() {
        return getImage(WEBP);
    }

    private byte[] getImage(Images imageExtension){
        String endPoint = switch (imageExtension) {
            case PNG -> HttpBinEndPoints.getPNG;
            case JPEG -> HttpBinEndPoints.getJPEG;
            case SVG -> HttpBinEndPoints.getSVG;
            case WEBP -> HttpBinEndPoints.getWEBP;
        };
        return request()
                .when()
                .get(endPoint)
                .then()
                .extract()
                .asByteArray();
    }
}
