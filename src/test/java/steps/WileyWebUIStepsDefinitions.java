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
import web.*;

import java.util.List;

public class WileyWebUIStepsDefinitions {
    private WebDriver driver = WebDriverRunner.getWebDriver();
    private final static Logger logger = LogManager.getLogger();
    private WileyPage wileyPage = new WileyPage();
    private Page currentPage;
    private List<String> searchResults;

    /**
     * Checks that Wiley page was opened and rendered correctly.
     */
    @When("Wiley page is opened")
    public void wileyPageIsOpened() {
        Assert.assertTrue("Wiley page wasn't opened!", driver.getCurrentUrl().contains("https://www.wiley.com/en-us"));
        wileyPage.confirmLocationChange();
        Assert.assertTrue("Wiley page wasn't rendered correctly!", wileyPage.isNavigationMenuDisplayed());
        Assert.assertTrue("Can't see \"Who we Serve\" subheader on the page!", wileyPage.isOpened());
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
    public void menuLinkIsOpened(String link) throws Exception {
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
            Assert.assertTrue("\"" + item + "\" is not displayed!", wileyPage.isDropDownItemDisplayed(item));
            logger.info("Item is displayed!");
        });
    }

    /**
     * Clicks on item in drop-down menu and opens it page.
     *
     * @param dropDownItem item to click.
     */
    @And("{string} page is opened from drop-down menu")
    public void pageIsOpenedFromDropDownMenu(String dropDownItem) throws Exception {
        logger.info("Opening \"" + dropDownItem + "\" page...");
        currentPage = wileyPage.clickOnDropDownItem(dropDownItem);
        Assert.assertTrue("Page was not opened!", currentPage.isOpened());
        logger.info("Page was opened!");
    }

    /**
     * Checks that all items from scenario is visible on the page.
     *
     * @param data Cucumber datatable with items.
     */
    @Then("next items are displayed under \"Who we Serve\" subheader")
    public void nextItemsAreDisplayedUnderSubheader(DataTable data) {
        List<String> items = data.asList();
        logger.info("Checking that \"" + items + "\" are displayed under \"Who we Server\" sub-header...");
        List subHeaderItems = wileyPage.getWhoWeServeBlocks();
        Assert.assertEquals("Size of subheader elements collection is different from expected!", items.size(), subHeaderItems.size());
        Assert.assertTrue("Data set from scenario does not contains all elements from the page!", items.containsAll(subHeaderItems));
        logger.info("All items are displayed and count is:" + subHeaderItems.size() + "!");
    }

    /**
     * Checks that links to WileyPlus is visible on the page and count of them is equal to expected.
     *
     * @param linksCount expected count of links on the page.
     * @throws Exception in case if we are not on the correct page.
     */
    @Then("there is {int} links to WileyPlus on the page")
    public void thereIsLinksToWileyPlusOnThePage(int linksCount) throws Exception {
        logger.info("Checking that there is " + linksCount + "links to WileyPlus on the page...");
        if (currentPage instanceof StudentsPage) {
            ((StudentsPage) currentPage).checkLinksToWileyPlus(linksCount);
        } else throw new Exception("We're not on the \"Students\" page!");
        logger.info("Links count is equal to expected!");
    }

    /**
     * Checks that content of side panel under "Subjects" is equal to expected.
     *
     * @param data Cucumber datatable with items.
     */
    @Then("next items are displayed under \"Subjects\" in side panel")
    public void nextItemsAreDisplayedInSidePanel(DataTable data) throws Exception {
        List<String> items = data.asList();
        logger.info("Checking that " + items + " are displayed in the side panel under the \"Subjects\"...");
        if (currentPage instanceof EducationPage) {
            List subHeaderItems = ((EducationPage) currentPage).getSidePanelLinks();
            Assert.assertEquals("Size of subheader elements collection is different from expected!", items.size(), subHeaderItems.size());
            Assert.assertTrue("Data set from scenario does not contains all elements from the page!", items.containsAll(subHeaderItems));
        } else throw new Exception("We're not on the \"Education\" page!");
    }

    /**
     * Clicks on the Wiley logo.
     */
    @And("home page is opened")
    public void homePageIsOpened() {
        logger.info("Opening home page...");
        wileyPage.openHomePage();
    }

    /**
     * Clicks on the search button.
     */
    @And("search button is pressed")
    public void searchButtonIsPressed() {
        logger.info("Pressing the \"Search\" button...");
        currentPage = wileyPage.pressSearchButton();
        logger.info("Button was pressed!");
    }

    /**
     * Sets value into the search field.
     *
     * @param valueToSearch value to be set.
     */
    @And("{string} is typed into the search field")
    public void isTypedIntoTheSearchField(String valueToSearch) {
        logger.info("Setting \"" + valueToSearch + "\" into the search field...");
        wileyPage.setValueForSearch(valueToSearch);
        logger.info("Value was set!");
    }


    /**
     * Checks that search autocomplete window is visible on the page.
     */
    @Then("search autocomplete window should be visible")
    public void searchAutocompleteWindowShouldBeVisible() {
        logger.info("Checking that search autocomplete window is visible...");
        wileyPage.waitForAutoCompleteWindow();
        logger.info("Autocomplete window is visible!");
    }

    /**
     * Checks items count in the section of autocomplete window.
     *
     * @param sectionName name of section.
     * @param itemsCount  expected count of items.
     * @param keyWord     word to search.
     */
    @And("{string} section has {int} items that starts with {string}")
    public void sectionHasItemsThatStartsWith(String sectionName, int itemsCount, String keyWord) {
        logger.info("Checking that count of items starting with \"" + keyWord + "\" in \"" + sectionName + " section is: " + itemsCount + "...");
        if (sectionName.toLowerCase().equals("suggestions")) {
            Assert.assertEquals("Count of items in \"Suggestions\" section is not equal to expected!", itemsCount, wileyPage.getSuggestionsItemsCount(keyWord));
        } else if (sectionName.toLowerCase().equals("products")) {
            Assert.assertEquals("Count of items in \"Products\" section is not equal to expected!", itemsCount, wileyPage.getProductsItemsCount(keyWord));
        }
        logger.info("Items count is equal to expected!");
    }

    /**
     * Checks that only titles with keyword on the SearchResults page.
     *
     * @param keyWord word to check with.
     * @throws Exception when wrong page is opened.
     */
    @Then("only titles contains {string} are displayed")
    public void onlyTitlesContainsAreDisplayed(String keyWord) throws Exception {
        logger.info("Checking that only titles with \"" + keyWord + "\" are displayed...");
        if (currentPage instanceof SearchResultsPage) {
            Assert.assertTrue("There are titles does not containing \"" + keyWord + "\"!", ((SearchResultsPage) currentPage).checkThereIsNoTitlesWithoutKeyWord(keyWord));
        } else throw new Exception("We're not on the \"Search Results\" page!");
    }

    /**
     * Checks there is N items in the search results.
     *
     * @param itemsCount number to check.
     * @throws Exception when wrong page is opened.
     */
    @And("there is {int} items in the results")
    public void thereIsItemsInTheResults(int itemsCount) throws Exception {
        logger.info("Checking there is: " + itemsCount + "in the search results...");
        if (currentPage instanceof SearchResultsPage) {
            Assert.assertEquals("Items count is different from expected!", itemsCount, ((SearchResultsPage) currentPage).getSearchResultsCount());
        } else throw new Exception("We're not on the \"Search Results\" page!");
        logger.info("Search results count as same as expected!");
    }

    /**
     * Checks that each item has "Add to Cart" button.
     *
     * @throws Exception when we are on the wrong page.
     */
    @And("each item has at least 1 \"Add to cart\" button")
    public void eachItemHasAtLeastButton() throws Exception {
        logger.info("Checking that each item has at least one \"Add to Cart\" button...");
        if (currentPage instanceof SearchResultsPage) {
            Assert.assertTrue(((SearchResultsPage) currentPage).isEachItemHasAddToCartBtn());
        } else throw new Exception("We're not on the \"Search Results\" page!");
        logger.info("Each item has at least 1 \"Add to Cart\" button!");
    }

    /**
     * Stores search results.
     *
     * @throws Exception when we are on the wrong page.
     */
    @When("search results is saved")
    public void searchResultsIsSaved() throws Exception {
        if (currentPage instanceof SearchResultsPage) {
            searchResults = ((SearchResultsPage) currentPage).getSearchResults();
        } else throw new Exception("We're not on the \"Search Results\" page!");
    }

    /**
     * Compares current search results with the saved ones.
     *
     * @throws Exception when we are on the wrong page.
     */
    @Then("search results should be equal to the saved ones")
    public void searchResultsShouldBeEqualToTheSavedOnes() throws Exception {
        logger.info("Comparing saved search results with results after pressing \"Search\" button again...");
        if (currentPage instanceof SearchResultsPage) {
            Assert.assertEquals(searchResults, ((SearchResultsPage) currentPage).getSearchResults());
        } else throw new Exception("We're not on the \"Search Results\" page!");
        logger.info("Search results are same!");
    }
}