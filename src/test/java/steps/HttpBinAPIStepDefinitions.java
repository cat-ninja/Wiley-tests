package steps;

import api.HttpBinAPI;
import api.objects.ImageExtensions;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.junit.Assert;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class HttpBinAPIStepDefinitions {
    private final static Logger logger = LogManager.getLogger();
    private HttpBinAPI api = HttpBinAPI.getInstance();
    private byte[] lastReceivedImage;

    /**
     * Gets image from HttpBin API.
     * @param imageExtension extension of image.
     */
    @When("^(.+) image is received from API$")
    public void getImage(String imageExtension) {
        logger.info("Getting \"" + imageExtension + "\" image from API...");
        lastReceivedImage = api.getImage(ImageExtensions.valueOf(imageExtension));
        logger.info("Image was received!");
    }

    /**
     * Compares received image with ideal stored at "test/resources/images".
     * @param imageExtension extension of image.
     * @throws IOException exception at local image reading.
     */
    @Then("^(.+) image should be equal to ideal one$")
    public void compareImages(String imageExtension) throws IOException {
        String image = ImageExtensions.valueOf(imageExtension).name();
        byte[] idealImage = Files.readAllBytes(new File("src/test/resources/images/" + image + "Image." + image).toPath());
        Assert.assertArrayEquals("\"" + imageExtension + "\" image is not equal to ideal one!", idealImage, lastReceivedImage);
    }

}
