package web;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.NoSuchElementException;

import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$x;

public class SearchResultsPage implements Page {
    private SelenideElement filtersSection = $x("//h2[text()='Filters']");
    private ElementsCollection searchResults = $$(".product-item");
    private ElementsCollection searchResultsTitles = $$(".product-item .product-title span");

    @Override
    public boolean isOpened() {
        return filtersSection.isDisplayed();
    }

    public boolean checkThereIsNoTitlesWithoutKeyWord(String keyWord) {
        searchResultsTitles.filter(text(keyWord)).shouldHaveSize(searchResultsTitles.size());
        return true;
    }

    public int getSearchResultsCount() {
        return searchResults.size();
    }

    public boolean isEachItemHasAddToCartBtn() {
        searchResults.forEach(r -> {
//          uncomment this line if you want to check only "Add to Cart" button
//          if (!r.text().contains("ADD TO CART")) throw new NoSuchElementException("No add to card button in the item!");
            if (!r.text().contains("ADD TO CART") && !r.text().contains("VIEW ON WILEY ONLINE LIBRARY"))
                throw new NoSuchElementException("No \"ADD TO CART\" button or \"VIEW ON WILEY ONLINE LIBRARY\" button in the item!");
        });
        return true;
    }

    public List<String> getSearchResults() {
        return searchResults
                .stream()
                .map(r -> r.text().split("\n")[0])
                .collect(Collectors.toList());
    }
}