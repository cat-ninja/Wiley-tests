package hooks;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Hooks {
    private final static Logger logger = LogManager.getLogger();

    @Before
    public void before(Scenario scenario){
        System.out.println("--------------------------------------------------------------------------------------------------");
        logger.info("Scenario: " + scenario.getName());
        System.out.println("--------------------------------------------------------------------------------------------------");
    }
}
