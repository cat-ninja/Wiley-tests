package steps;

import com.codeborne.selenide.WebDriverRunner;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import web.WileyPage;

import java.util.List;

public class WileyWebUIStepsDefinitions {
    private WebDriver driver = WebDriverRunner.getWebDriver();
    private final static Logger logger = LogManager.getLogger();
    private WileyPage wileyPage = new WileyPage();

    /**
     * Checks that Wiley page was opened and rendered correctly.
     */
    @When("Wiley page is opened")
    public void wileyPageIsOpened() {
        Assert.assertTrue("Wiley page wasn't opened!", driver.getCurrentUrl().contains("https://www.wiley.com/en-us"));
        Assert.assertTrue("Wiley page wasn't rendered correctly!", wileyPage.isNavigationMenuDisplayed());
        wileyPage.confirmLocationChange();
    }

    /**
     * Checks that element is present in the top menu.
     *
     * @param link text of the link.
     */
    @Then("\"(.+)\" link should be visible in the top menu")
    public void linkShouldBeVisibleInTheTopMenu(String link) {
        logger.info("Checking that \"" + link + "\" link is displayed in the top menu.");
        wileyPage.checkNavigationLinkDisplayed(link);
        logger.info("Link is displayed!");
    }

    /**
     * Opens top menu item.
     *
     * @param link text of the link.
     */
    @And("{string} menu link is opened")
    public void menuLinkIsOpened(String link) {
        logger.info("Opening \"" + link + "\" menu link...");
        wileyPage.openNavigationLink(link);
        logger.info("Menu link was opened!");
    }

    /**
     * Checks items count in dropdown-menu.
     *
     * @param itemsCount expected items count.
     */
    @Then("items count in drop-down menu is {int}")
    public void itemsCountInDropDownMenuIs(int itemsCount) {
        logger.info("Expecting to see \"" + itemsCount + "\" items in drop-down menu...");
        Assert.assertEquals("Dropdown items count is different from expected!", itemsCount, wileyPage.getDropdownMenuItemsCount());
        logger.info("Items count is equal to expected!");
    }

    /**
     * Checks that dropdown menu items are displayed.
     */
    @And("next drop-down menu items are displayed")
    public void nextDropDownMenuItemsAreDisplayed(DataTable data) {
        List<String> items = data.asList();
        items.forEach(item -> {
            logger.info("Checking that \"" + item + "\" is displayed...");
            Assert.assertTrue("\"" + item + "\" is not displayed!" ,wileyPage.isDropDownItemDisplayed(item));
            logger.info("Item is displayed!");
        });
    }
}