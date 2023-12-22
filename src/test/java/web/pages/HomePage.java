package web.pages;

import org.openqa.selenium.By;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class HomePage {

    public SelenideElement searchButton() {
        return $(By.cssSelector("button[type='button'] span"));
    }
}
