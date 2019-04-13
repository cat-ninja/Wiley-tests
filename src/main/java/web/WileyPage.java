package web;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementShould;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class WileyPage {
    private final static Logger logger = LogManager.getLogger();
    private SelenideElement navigationMenu = $(".navigation-menu-items");
    private final String navigationMenuItem = "a.collapsed:contains('%s')";
    private SelenideElement confirmChangeLocation = $(".changeLocationConfirmBtn");
    private SelenideElement dropdownMenu = $(".dropdown-items");
    private ElementsCollection dropdownMenuItems = $$(".dropdown-item a");
    private String dropdownMenuItem = ".dropdown-item a:contains('%s')";

    public WileyPage confirmLocationChange(){
        try {
            confirmChangeLocation.click();
        } catch (ElementShould e){
            logger.warn("Change location window wasn't displayed!");
        }
        return this;
    }

    public boolean isNavigationMenuDisplayed() { return navigationMenu.isDisplayed(); }

    public void checkNavigationLinkDisplayed(String navigationLinkText){
        navigationMenu.find(String.format(navigationMenuItem, navigationLinkText.toUpperCase())).shouldBe(visible);
    }

    public WileyPage openNavigationLink(String navigationLink) {
        $(String.format(navigationMenuItem, navigationLink.toUpperCase())).hover();
        return this;
    }

    public int getDropdownMenuItemsCount(){
        dropdownMenu.waitUntil(visible, 5000, 20);
        return dropdownMenuItems.exclude(not(visible)).size();
    }

    public boolean isDropDownItemDisplayed(String item){
        return $(String.format(dropdownMenuItem, item)).isDisplayed();
    }
}
