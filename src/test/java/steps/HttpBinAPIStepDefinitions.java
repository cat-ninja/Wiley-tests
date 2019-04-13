package steps;

import api.HttpBinAPI;
import api.objects.DelayedResponse;
import api.objects.ImageExtensions;
import com.google.common.base.Stopwatch;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;

public class HttpBinAPIStepDefinitions {
    private final static Logger logger = LogManager.getLogger();
    private HttpBinAPI api = HttpBinAPI.getInstance();
    private byte[] lastReceivedImage;
    private DelayedResponse lastReceivedDelayedResponse;

    /**
     * Gets image from HttpBin API.
     *
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
     *
     * @param imageExtension extension of image.
     * @throws IOException exception at local image reading.
     */
    @Then("^(.+) image should be equal to ideal one$")
    public void compareImages(String imageExtension) throws IOException {
        String image = ImageExtensions.valueOf(imageExtension).name();
        logger.info("Comparing received " + image + " image with local one...");
        byte[] idealImage = Files.readAllBytes(new File("src/test/resources/images/" + image + "Image." + image).toPath());
        Assert.assertArrayEquals("\"" + imageExtension + "\" image is not equal to ideal one!", idealImage, lastReceivedImage);
        logger.info("Images are equal!");
    }

    /**
     * Send request and waits for response to be received.
     *
     * @param responseDelay timeout in seconds.
     */
    @When("^request is sent with (.+) seconds response delay parameter$")
    public void sendRequestWithResponseDelay(int responseDelay) {
        if (responseDelay > 10) throw new IllegalArgumentException("Parameter can't be more than 10 seconds!");
        logger.info("Getting response with " + responseDelay + " second(s) delay...");
        Stopwatch timer = Stopwatch.createStarted();
        lastReceivedDelayedResponse = api.getResponseWithDelay(responseDelay);
        long elapsedTime = timer.stop().elapsed(TimeUnit.SECONDS);
        Assert.assertFalse("Request execution took less time than was expected!", elapsedTime < responseDelay);
        logger.info("Response was successfully received!");
    }

    /**
     * Checks that timeout value is present in URL field of last received DelayedResponse object.
     *
     * @param responseDelay timeout in seconds.
     */
    @Then("^(.+) seconds delay should be present in response body")
    public void delayShouldBeInResponse(String responseDelay) {
        logger.info("Expecting to see \"" + responseDelay + "\" in response's URL field...");
        Assert.assertTrue("Response does not contains expected value!", lastReceivedDelayedResponse.getUrl().endsWith(responseDelay));
        logger.info("Expected value was found in response from API!");
    }
}
