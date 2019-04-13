package steps;

import api.HttpBinAPI;
import api.objects.Images;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.io.File;
import java.nio.file.Files;

public class HttpBinAPIStepDefinitions {
    private HttpBinAPI api = HttpBinAPI.getInstance();
    private byte[] lastReceivedImage;

    @When("^(.+) image is received from API$")
    public void imageIsReceivedFromAPI(String imageExtension) {
        lastReceivedImage = switch (Images.valueOf(imageExtension)) {
            case PNG -> api.getPNGImage();
            case JPEG -> api.getJPEGImage();
            case SVG -> api.getSVGImage();
            case WEBP -> api.getWEBPImage();
        };
    }

    @Then("^(.+) image should be equal to ideal one$")
    public void compareImages(String imageExtension) throws Exception {
        byte[] idealImage = switch (Images.valueOf(imageExtension)) {
            case PNG -> Files.readAllBytes(new File("src/test/resources/images/PNGImage.png").toPath());
            case JPEG -> Files.readAllBytes(new File("src/test/resources/images/JPEGImage.jpeg").toPath());
            case SVG -> Files.readAllBytes(new File("src/test/resources/images/SVGImage.svg").toPath());
            case WEBP -> Files.readAllBytes(new File("src/test/resources/images/WEBPImage.webp").toPath());
        };
        Assert.assertArrayEquals("\""+ imageExtension +"\" image is not equal to ideal one!", idealImage, lastReceivedImage);
    }

}
