package web;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class EducationPage implements Page {
    private SelenideElement header = $("li.active:contains('Education')");
    private ElementsCollection sidePanelLinks = $$(".side-panel ul a");

    @Override
    public boolean isOpened() throws Exception {
        return header.isDisplayed();
    }

    public List<String> getSidePanelLinks() {
        return sidePanelLinks
                .stream()
                .filter(SelenideElement::isDisplayed)
                .map(SelenideElement::text)
                .collect(Collectors.toList());
    }
}
