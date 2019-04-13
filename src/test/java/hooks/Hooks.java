package hooks;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelectorMode;
import com.codeborne.selenide.Selenide;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

import static com.codeborne.selenide.Screenshots.getLastScreenshot;

public class Hooks {
    private final static Logger logger = LogManager.getLogger();

    @Before
    public void before(Scenario scenario){
        System.out.println("--------------------------------------------------------------------------------------------------");
        logger.info("Scenario: " + scenario.getName());
        System.out.println("--------------------------------------------------------------------------------------------------");
        if (scenario.getSourceTagNames().contains("@web")){
            Configuration.selectorMode = SelectorMode.Sizzle;
            Configuration.screenshots = false;
            Configuration.savePageSource = false;
            Selenide.open("https://www.wiley.com/en-us");
        }
    }

    @After
    public void after(Scenario scenario) throws IOException {
        File screenshot;
        if (scenario.isFailed()){
            logger.error("Scenario is failed! ");
            if (scenario.getSourceTagNames().contains("@web")) {
                logger.info("Getting page screenshot...");
                Selenide.screenshot(String.valueOf(System.currentTimeMillis()));
                screenshot = getLastScreenshot();
                Allure.addAttachment("screenshot", "image/png", FileUtils.openInputStream(screenshot), "png");
                logger.info("Screenshot was attached to report!");
            }
        }
    }
}
