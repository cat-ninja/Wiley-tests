package web;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementShould;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class WileyPage implements Page {
    private final static Logger logger = LogManager.getLogger();
    private SelenideElement navigationMenu = $(".navigation-menu-items");
    private final String navigationMenuItem = "a.collapsed:contains('%s')";
    private SelenideElement confirmChangeLocation = $(".changeLocationConfirmBtn");
    private final String dropdownMenu = ".dropdown-items";
    private ElementsCollection dropdownMenuItems = $$(".dropdown-item a");
    private final String dropdownMenuItem = ".dropdown-item a:contains('%s')";
    private ElementsCollection whoWeServeBlocks = $$(".who-we-serve-blocks a");
    private SelenideElement wileyLogo = $("[title='Wiley']");
    private SelenideElement subHeader = $x("//h2[text()='Who we serve']");
    private SelenideElement searchButton = $("[type=\"submit\"]");
    private SelenideElement searchField = $("[type=\"search\"]");
    private SelenideElement autoCompleteField = $("aside.ui-autocomplete");
    private final String suggestionsItems = ".suggestions .search-list a span:contains('%s')";
    private final String productsItems = ".related-content-products div a span:contains('%s')";

    @Override
    public boolean isOpened() {
        return subHeader.isDisplayed();
    }

    public WileyPage confirmLocationChange() {
        try {
            confirmChangeLocation.click();
        } catch (ElementShould e) {
            logger.warn("Change location window wasn't displayed!");
        }
        return this;
    }

    public boolean isNavigationMenuDisplayed() {
        return navigationMenu.isDisplayed();
    }

    public void checkNavigationLinkDisplayed(String navigationLinkText) {
        navigationMenu.find(String.format(navigationMenuItem, navigationLinkText.toUpperCase())).shouldBe(visible);
    }

    public WileyPage openNavigationLink(String navigationLink) throws Exception {
        $(String.format(navigationMenuItem, navigationLink.toUpperCase())).hover();
        while ($$(dropdownMenu).stream().noneMatch(SelenideElement::isDisplayed)) TimeUnit.MILLISECONDS.sleep(100);
        return this;
    }

    public int getDropdownMenuItemsCount() {
        return dropdownMenuItems.exclude(not(visible)).size();
    }

    public boolean isDropDownItemDisplayed(String item) {
        return $(String.format(dropdownMenuItem, item)).isDisplayed();
    }

    public Page clickOnDropDownItem(String item) {
        $(String.format(dropdownMenuItem, item)).click();
        Page p;
        switch (item.toLowerCase()) {
            case "students":
                p = new StudentsPage();
                break;
            case "education":
                p = new EducationPage();
                break;
            default:
                throw new IllegalArgumentException("Can't recognize parameter \"" + item + "\"!");
        }
        return p;
    }

    public List<String> getWhoWeServeBlocks() {
        return whoWeServeBlocks
                .stream()
                .filter(SelenideElement::isDisplayed)
                .map(SelenideElement::text)
                .collect(Collectors.toList());
    }

    public WileyPage openHomePage(){
        wileyLogo.click();
        return new WileyPage();
    }

    public SearchResultsPage pressSearchButton(){
        searchButton.click();
        return new SearchResultsPage();
    }

    public void setValueForSearch(String value){
        searchField.setValue(value);
    }

    public void waitForAutoCompleteWindow() {
        autoCompleteField.shouldBe(visible);
    }

    public int getSuggestionsItemsCount(String keyWord){
        return $$(String.format(suggestionsItems, keyWord)).size();
    }

    public int getProductsItemsCount(String keyWord){
        return $$(String.format(productsItems, keyWord)).size();
    }
}
