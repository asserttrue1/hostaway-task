package web.pages;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ElementsCollection;
import web.enums.Amenities;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Selenide.*;
import static org.testng.Assert.assertEquals;

public class FiltersPage {
    private final String baseSelector = "//label[contains(., '%s')]";

    public String textField = "input[placeholder='%s']";

    public SelenideElement filtersFormHeadline() {
        return $(By.cssSelector("div.sc-jHcXXw > div.sc-bQCEYZ"));
    }

    public SelenideElement applyButton() {
        return $(By.cssSelector("div > button.sc-giAqHp"));
    }

    public SelenideElement clearAllButton() {
        return $(By.cssSelector("div > b"));
    }

    public SelenideElement filtersFormCloseButton() {
        return $(By.cssSelector("button.sc-fXgAZx"));
    }

    public void scrollToAmenityCheckbox(Amenities amenity) {
        $$x(String.format(baseSelector, amenity.getText())).first().scrollIntoView(true);
    }

    public void clickOnAmenityCheckbox(Amenities amenity) {
        $$x(String.format(baseSelector, amenity.getText())).first().click();
    }

    public boolean isAmenityCheckboxChecked(Amenities amenity) {
        return $$x(String.format(baseSelector, amenity.getText())).first()
                .find("input[type='checkbox']").isSelected();
    }

    public void enterTextInTextField(String field, String value) {
        String fieldSelector = String.format(textField, field);
        $(fieldSelector).clear();
        $(fieldSelector).setValue(value);
    }

    public SelenideElement getFilterFormButton(int index, String buttonType) {
        String buttonSelector = (buttonType.equalsIgnoreCase("plus"))
                ? "button.sc-flUlpA.sc-iGkqmO.eEVTIr.ckwDLe"
                : "button.sc-flUlpA.sc-eXuyPJ.eEVTIr.kTTKRj";

        ElementsCollection buttons = $$(By.cssSelector(buttonSelector));
        return buttons.get(index);
    }

    public SelenideElement clickAndVerifyButton(int index, String buttonType, boolean performClick) {
        SelenideElement button = getFilterFormButton(index, buttonType);
        if (performClick) {
            button.click();
        }
        return $("span.sc-gkCoMD.ijCcEA");
    }

    public void verifyInitialState() {
        for (int i = 0; i < 3; i++) {
            getFilterFormButton(i, "plus").shouldNotHave(attribute("disabled"));
            getFilterFormButton(i, "minus").shouldHave(attribute("disabled"));
            clickAndVerifyButton(i, "plus", false).shouldHave(Condition.text("0"));
        }
    }

    public void clickPlusButtons() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 3; j++) {
                String valueAfterClick = clickAndVerifyButton(j, "plus", true).text();
                assertEquals(valueAfterClick, String.valueOf(i + 1));
            }
        }
    }

    public void verifyFinalState() {
        for (int i = 0; i < 3; i++) {
            getFilterFormButton(i, "plus").shouldHave(attribute("disabled"));
            getFilterFormButton(i, "minus").shouldNotHave(attribute("disabled"));
            clickAndVerifyButton(i, "plus", false).shouldHave(Condition.text("10"));
        }
    }

    public void verifyTextField(String field, String expectedText) {
        String fieldSelector = String.format(textField, field);
        $(fieldSelector).shouldHave(value(expectedText));
    }

    public void clickClearAll() {
        clearAllButton().click();
    }

    public boolean areAllCheckboxesUnchecked() {
        for (Amenities amenity : Amenities.values()) {
            if (isAmenityCheckboxChecked(amenity)) {
                return false;
            }
        }
        return true;
    }
}
