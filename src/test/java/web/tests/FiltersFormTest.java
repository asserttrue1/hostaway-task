package web.tests;

import io.qameta.allure.Description;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import web.pages.FiltersPage;
import web.pages.HomePage;
import web.pages.SearchPage;

import java.time.LocalDate;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

import web.enums.Amenities;


public class FiltersFormTest {

    HomePage homePage = new HomePage();
    SearchPage searchPage = new SearchPage();
    FiltersPage filtersPage = new FiltersPage();

    @BeforeClass
    public void setUp() {
        open("https://kamil-demo.alpinizm.uz/");
    }

    @Test()
    @Description("Verify the functionality of entry fields.")
    public void entryFieldsTest() {
        int daysToAddForCheckIn = 1;
        int daysToAddForCheckOut = 5;

        homePage.searchButton().click();

        searchPage.setLocation("Aalborg");

        searchPage.setDate(daysToAddForCheckIn, "check-in");
        searchPage.setDate(daysToAddForCheckOut, "check-out");

        LocalDate expectedCheckInDate = LocalDate.now().plusDays(daysToAddForCheckIn);
        searchPage.verifyCheckinDate(expectedCheckInDate);

        LocalDate expectedCheckOutDate = LocalDate.now().plusDays(daysToAddForCheckOut);
        searchPage.verifyCheckoutDate(expectedCheckOutDate);
    }

    @Test(dependsOnMethods = "entryFieldsTest")
    @Description("Verify negative value range for Filters Form text fields.")
    public void valueRangeTestNegative() {
        String minValue = "0";
        String maxValue = "2222222222222222222222";

        searchPage.filterButton().click();
        filtersPage.filtersFormHeadline().shouldHave(text("Filters"));

        filtersPage.enterTextInTextField("From", minValue);
        filtersPage.verifyTextField("From", "");

        filtersPage.enterTextInTextField("From", maxValue);
        filtersPage.verifyTextField("From", "+21");

        filtersPage.enterTextInTextField("To", minValue);
        filtersPage.verifyTextField("To", "");

        filtersPage.enterTextInTextField("To", maxValue);
        filtersPage.verifyTextField("To", "+21");
    }

    @Test(dependsOnMethods = "valueRangeTestNegative")
    @Description("Verify positive value range for Filters Form text fields.")
    public void valueRangeTestPositive() {
        filtersPage.enterTextInTextField("From", "32");
        filtersPage.enterTextInTextField("To", "150");

        $(String.format(filtersPage.textField, "From")).shouldHave(value("32"));
        $(String.format(filtersPage.textField, "To")).shouldHave(value("150"));
    }

    @Test(dependsOnMethods = "valueRangeTestPositive")
    @Description("Verify rooms and beds value range functionality.")
    public void roomsAndBedsValuesTest() {
        filtersPage.verifyInitialState();
        filtersPage.clickPlusButtons();
        filtersPage.verifyFinalState();
    }

    @Test(dependsOnMethods = "roomsAndBedsValuesTest")
    @Description("Verify the functionality of amenities checkboxes")
    public void amenitiesCheckboxesTest() {
        filtersPage.scrollToAmenityCheckbox(Amenities.SUITABLE_FOR_CHILDREN);

        for (Amenities amenity : Amenities.values()) {
            filtersPage.clickOnAmenityCheckbox(amenity);

            if (filtersPage.isAmenityCheckboxChecked(amenity)) {
                System.out.println(amenity.getText() + " checkbox has been checked.");
            } else {
                System.out.println(amenity.getText() + " checkbox has not been checked.");
            }
        }
    }

    @Test(dependsOnMethods = "amenitiesCheckboxesTest")
    @Description("Verify clear all functionality.")
    public void clearAllTest() {
        filtersPage.clickClearAll();

        if (filtersPage.areAllCheckboxesUnchecked()) {
            System.out.println("All checkboxes successfully unchecked..");
        } else {
            System.out.println("All checkboxes not unchecked.");
        }
    }

    @Test(dependsOnMethods = "clearAllTest")
    @Description("Verify the buttons that were not used througoht the test without clicking on Apply.")
    public void checkButtonsPresence() {
        filtersPage.applyButton().shouldBe(visible);
        filtersPage.filtersFormCloseButton().shouldBe(visible);
    }
}
