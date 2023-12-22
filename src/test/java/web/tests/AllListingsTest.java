package web.tests;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import io.qameta.allure.Description;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import web.pages.AllListingsPage;

import static com.codeborne.selenide.Selenide.*;

public class AllListingsTest {

    AllListingsPage allListingsPage = new AllListingsPage();

    @BeforeClass
    public void setUp() {
        open("https://kamil-demo.alpinizm.uz/all-listings");
    }

    @Test
    @Description("Verify that All Listings page is showing the same amount of listings as All button.")
    public void allListingsTest() {
        for (int i = 0; i < 5; i++) {
            allListingsPage.scrollToEmailElement();
            sleep(2000);
        }

        ElementsCollection pageListings = allListingsPage.getPageListingsCount();
        System.out.println(pageListings.size());

        String allText = allListingsPage.getAllListingsCountText();
        System.out.println(allText);

        int numbersFromAllText = Integer.parseInt(allText.replaceAll("[^0-9]", ""));

        pageListings.shouldHave(CollectionCondition.size(numbersFromAllText));
    }
}
