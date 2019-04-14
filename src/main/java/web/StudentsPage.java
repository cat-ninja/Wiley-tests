package web;

import com.codeborne.selenide.*;

import java.net.MalformedURLException;

import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class StudentsPage implements Page {
    private SelenideElement header = $("li.active:contains('Students')");
    private ElementsCollection wileyPlusLinks = $$("a[href*='wileyplus']");

    @Override
    public boolean isOpened() throws Exception {
        if (!WebDriverRunner.getWebDriver().getCurrentUrl().contains("https://www.wiley.com/en-us/students")) throw new MalformedURLException("We're not on the expected page!");
        return header.isDisplayed();
    }

    public boolean checkLinksToWileyPlus(int expectedCount){
        wileyPlusLinks.exclude(not(visible)).shouldHaveSize(expectedCount);
        return true;
    }
}
