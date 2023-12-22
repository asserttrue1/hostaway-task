package web.pages;

import org.openqa.selenium.By;
import com.codeborne.selenide.ElementsCollection;

import static com.codeborne.selenide.Selenide.*;

public class AllListingsPage {
    private static final By email = By.cssSelector("a[href*='somemail@hostaways.com']");

    public ElementsCollection getPageListingsCount() {
        return $$(".sc-iNiQyp.gsJhBT a");
    }

    public String getAllListingsCountText() {
        return $(".sc-bsatvv.hYJCa").getText();
    }

    public void scrollToEmailElement() {
        $(email).scrollIntoView(true);
    }
}